package com.mybatis.log.config;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.DateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;

@Component
@Intercepts(value = {@Signature(type = Executor.class, args = {MappedStatement.class, Object.class}, method = "update")
                ,@Signature(type = Executor.class, args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}, method = "query")
                ,@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class })
        })
public class Mysql implements Interceptor {


    private Properties properties;

    @Override
    public void setProperties(Properties properties) {

        this.properties=properties;
    }

    //调用mybatis自带的plugin,返回的就是一个代理对象，
    @Override
    public Object plugin(Object o) {

        return Plugin.wrap(o,this);
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        Object result=null;
        long start = System.currentTimeMillis();
        //调用原来的方法
        result=invocation.proceed();
        long time=System.currentTimeMillis()-start;


        //获取xml中的sql节点的所有内容
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];


        //Sql执行的参数
        Object params=null;
        if(invocation.getArgs().length>1){
            params = invocation.getArgs()[1];

        }
        //获取sql的id 这个id就是namespace和sql片段中的id
        String sqlId = mappedStatement.getId();

        //其中包含sql语句(该sql语句中可能包含 ? 这样的占位符), 以及一组parameter mapping(ParameterMapping类的实例),
        // 注意这组parameter mapping是Mybatis内部生成的(通过读取#{xx}中的内容)

        //BoundSql就是封装myBatis最终产生的sql类
        BoundSql boundSql = mappedStatement.getBoundSql(params);

        //获取整个mybatis的全局配置文件configuration.xml包含了sqlSessionFactory等
        Configuration configuration = mappedStatement.getConfiguration();


        //获取具体的sql
        String sql =getfinallySql(configuration, boundSql, sqlId,time);

        System.err.println(sql);

        return result;
    }

    //封装sql语句。返回具体的耗时以及具体的sql语句
    private String getfinallySql(Configuration configuration, BoundSql boundSql, String sqlId, long time) {

        String sql=showSql(configuration,boundSql);

        Map<String,Object> map=new ConcurrentHashMap<String,Object>();
        map.put("sqlID",sqlId);
        map.put("time",time);
        map.put("sql",sql);

        StringBuilder str = new StringBuilder(300);
        str.append("执行的Sql的Id："+sqlId);
        str.append("耗时："+time+"毫秒");
        str.append("具体执行sql ： ");
        str.append(sql);
        return str.toString();


    }

    //将sql中的？号进行替换
    public static String showSql(Configuration configuration, BoundSql boundSql) {

        // 获取参数
        Object parameterObject = boundSql.getParameterObject();

        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        // sql语句中多个空格都用一个空格代替
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (!CollectionUtils.isEmpty(parameterMappings) && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();

            // 获取类型处理器注册器，类型处理器的功能是进行java类型和数据库类型的转换　　　　　　
            // 如果根据parameterObject.getClass(）可以找到对应的类型，则替换
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(parameterObject)));
            } else {
                // MetaObject主要是封装了originalObject对象，提供了get和set的方法用于获取和设置originalObject的属性值,
                // 主要支持对JavaBean、Collection、Map三种类型对象的操作
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
                    }else{sql=sql.replaceFirst("\\?","缺失");}
                }
            }
        }
        return sql;
    }


    private static String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(new Date()) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }

        }
        return value;
    }

}
