package com.login.only.common.aop;

import com.alibaba.fastjson.JSON;
import com.login.only.modules.model.RequestData;
import com.login.only.util.IPutil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect
//@Component
public class LoginAop {


    private Logger logger= LoggerFactory.getLogger(LoginAop.class);

    private RequestData requestData = new RequestData();


    private long startTime;
    private long costTime;


    @Pointcut(value = "@annotation(com.login.only.common.aop.LoggerAop)")
    public void cutPoint() {


    }

    @Before("cutPoint()")
    public void before(JoinPoint joinPoint) {

        startTime = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-HH-dd HH:mm:ss.SSS");
        logger.info("开始请求时间是： "+simpleDateFormat.format(new Date()));
    }


    @After("cutPoint()")
    public void after(JoinPoint joinPoint) {
        costTime = System.currentTimeMillis() - startTime;
        requestData.setCost_time(costTime);
        logger.info(requestData.toString());


    }

    @Around("cutPoint()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Signature signature = proceedingJoinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;


        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = requestAttributes.getRequest();

        String ipAddr = IPutil.getIpAddr(request);

        requestData.setClient_ip(ipAddr);
        requestData.setUrl(request.getRequestURI());

        requestData.setHttp_header(request.getHeader("Authorization"));

        requestData.setHttp_method(request.getMethod());

        requestData.setMethod_name(methodSignature.getName());

        Object[] args = proceedingJoinPoint.getArgs();
        Object[] temp=null;

        if (args.length > 1) {
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof HttpServletRequest || args[i] instanceof HttpServletResponse || args[i] instanceof Model) {

                    continue;
                }

            }
        }
        requestData.setHttp_body(JSON.toJSONString(args));
        return proceedingJoinPoint.proceed();

    }

}
