package com.login.only.common.intercept;

import com.login.only.common.HttpHelper;
import com.login.only.common.RequestWrapper;
import com.login.only.modules.model.RequestData;
import com.login.only.util.IPutil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;


//@Component
public class LoginInterceptor implements HandlerInterceptor {


    ServletRequest requestWrapper;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        RequestData requestData=new RequestData();


        requestWrapper=new RequestWrapper(httpServletRequest);


        String authorization = httpServletRequest.getHeader("Authorization");
        String requestURI = httpServletRequest.getRequestURI();
        String method = httpServletRequest.getMethod();

        requestData.setClient_ip(IPutil.getIpAddr(httpServletRequest));


    //multipart/form-data

        String contentType = httpServletRequest.getContentType();

        System.out.println(contentType);

//        BufferedReader reader = requestWrapper.getReader();
//        char[] buf = new char[1024];
//        int len = 0;
//        StringBuffer contentBuffer = new StringBuffer();
//        while ((len = reader.read(buf)) != -1) {
//            contentBuffer.append(buf, 0, len);
//        }
//        String content= contentBuffer.toString();
//        requestData.setHttp_body(content);


        String body = HttpHelper.getBodyString(requestWrapper);

        String decode = URLDecoder.decode(body, "UTF-8");
        requestData.setHttp_body(decode);



       /* if("application/json".equals(contentType)){
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpServletRequest.getInputStream(), "UTF-8"));
            StringBuffer sb = new StringBuffer();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            String decode = URLDecoder.decode(sb.toString(), "UTF-8");
            requestData.setHttp_body(decode);
        }else{
            Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();

            String paramData = JSON.toJSONString(parameterMap,
                    SerializerFeature.DisableCircularReferenceDetect,
                    SerializerFeature.WriteMapNullValue);
            requestData.setHttp_body(JSON.toJSONString(paramData));
        }*/
        requestData.setUrl(requestURI);
        requestData.setHttp_method(method);
        requestData.setHttp_header(authorization);
        requestWrapper.setAttribute("requestData",requestData);
        requestWrapper.setAttribute("requestTime",System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {



    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {



        Long requestTime = Long.valueOf(requestWrapper.getAttribute("requestTime").toString());

        RequestData requestData = (RequestData) requestWrapper.getAttribute("requestData");

        requestData.setCost_time((System.currentTimeMillis()-requestTime));

        System.out.println("请求数据为： "+requestData);


    }
}
