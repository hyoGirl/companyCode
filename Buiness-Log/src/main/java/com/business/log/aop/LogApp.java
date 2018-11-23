package com.business.log.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class LogApp {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut(value ="@annotation(com.business.log.aop.BuinessLog)" )
    public void pointCut(){

    }


    @Around(value = "pointCut()")
    public  Object doPoint(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {


        Object result = proceedingJoinPoint.proceed();
        try {
            handler(proceedingJoinPoint);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;



    }

    private void handler(ProceedingJoinPoint proceedingJoinPoint) throws NoSuchMethodException {

        //获取请求的方法名
        Signature signature = proceedingJoinPoint.getSignature();

        MethodSignature methodSignature=null;

        if(!(signature instanceof MethodSignature)){
            throw  new  RuntimeException("这个注解只能用于方法！！");
        }

        methodSignature= (MethodSignature) signature;

        //方法名和形参列表共同组成方法签名。   String com.business.log.controller.UserController.getUser(long)
        log.info(methodSignature.toString());

        Object target = proceedingJoinPoint.getTarget();

        Method currentMethod = target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
        BuinessLog annotation = currentMethod.getAnnotation(BuinessLog.class);


        Object[] params = proceedingJoinPoint.getArgs();

        String bussinessName = annotation.name();
        String key = annotation.key();

        StringBuilder sb = new StringBuilder();

        for (Object param : params) {
            sb.append(param);
            sb.append("&");
        }

        log.info("操作名称："+bussinessName);
        log.info("key: "+key);
        log.info("操作参数："+sb.toString());

//        //需要记录下修改前后的数据
        if(bussinessName.indexOf("更新")!=-1){

            //获取之前的bean


            //获取现在的bean



        }




    }

}
