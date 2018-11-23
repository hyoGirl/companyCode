package com.spring.tx.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.*;

@RestController
public class HelloController {

    static String testUrl="http://aai.qcloud.com/asr/v1/";

    static String appid="1257220479";

    static String end="1";

    static int expired=1676858760;

    static int nonce=123458;








    @RequestMapping("/tx")
    public String testApp() throws IOException {

        RestTemplate restTemplate = new RestTemplate();

        String url="http://aai.qcloud.com/asr/v1/"+appid+"?"+"end=1"+"&"
                +"engine_model_type=8k_0"+"&"
                +"expired=1533175560"+"&"
                +"nonce=89292"+"&"
                +"res_type=0"+"&"
                +"result_text_format=0"+"&"
                +"secretid=AKIDnTzMciENxMaeWFjALSO9iARXSCoA2QK4"+"&"
                +"seq=1"+"&"
                +"source=0"+"&"
                +"sub_service_type=1"+"&"
                +"template_name=hello_14520"+"&"
                +"timeout=10"+"&"
                +"timestamp=1533016741"+"&"
                +"voice_format=4"+"&"
                +"voice_id=001";


        /**
         *
         * POSTaai.qcloud.com/asr/v1/1257220479?engine_model_type=8k_0&expired=1533175560&nonce=89292&res_type=0&result_text_format=0&secretId=AKIDnTzMciENxMaeWFjALSO9iARXSCoA2QK4&seq=1&source=0&sub_service_type=1&template_name=hello_14520&timeout=10&timestamp=1533027235&voice_format=4&voice_id=1
         *
         *
         */
    //增加了签名的字符串
    //POSTaai.qcloud.com/asr/v1/1257220479?Signature=Un%2Bc9zq7DUEWjRQAPiGume5hgTw%3D&engine_model_type=8k_0&expired=1533175560&nonce=89292&res_type=0&result_text_format=0&secretId=AKIDnTzMciENxMaeWFjALSO9iARXSCoA2QK4&seq=1&source=0&sub_service_type=1&template_name=hello_14520&timeout=10&timestamp=1533027235&voice_format=4&voice_id=1

    //Un%2Bc9zq7DUEWjRQAPiGume5hgTw%3D  签名字符串



        //Base64Encode(HmacSha1(签名原⽂文,SecretKey)) 


        //获取文件长度
        File file=new File("D:\\testtx\\001.WAV");
        long length = file.length();
        String fileLength = String.valueOf(length);


        //拼接请求头
        HttpHeaders headers = new HttpHeaders();
        //生成签名
        headers.set("Authorization","Un%2Bc9zq7DUEWjRQAPiGume5hgTw%3D ");

        headers.set("Host","aai.qcloud.com");
        headers.set("Content-Length",fileLength);
        headers.set("Content-Type","application/x-www-form-urlencoded");


        //获取本地音频文件
        StringBuffer buffer = new StringBuffer();
        BufferedReader bf= new BufferedReader(new FileReader("D:\\testtx\\001.WAV"));
        String s = null;
        while((s = bf.readLine())!=null){//使用readLine方法，一次读一行
            buffer.append(s.trim());
        }
        String fileDataStr = buffer.toString();

            //
//       $ret = http_curl_exec($serverUrl,$data,$rsp_str,

//        $http_code,'POST',10,array(),$header);


//        HttpEntity<String> formEntity = new HttpEntity<String>(jsonObj.toString(), headers);


//        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();

//        postParameters.put("",);


        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("data", fileDataStr);

        HttpEntity<MultiValueMap> requestEntity  = new HttpEntity<MultiValueMap>(requestBody, headers);

        //发送post请求
//        String responseEntity = restTemplate.postForObject(url, (Object) requestEntity, String.class);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);

        System.out.println(responseEntity.getBody());



        return responseEntity.getBody();

    }

}
