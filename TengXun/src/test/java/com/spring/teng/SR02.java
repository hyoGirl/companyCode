package com.spring.teng;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.springframework.http.RequestEntity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.util.UUID;
import java.nio.charset.Charset;


/**
 * @Auther: Administrator
 * @Date: 2018/8/1 0001 09:10
 * @Description:
 */
public class SR02 {

    public static void main(String[] args) throws IOException {


        String url="http://aai.qcloud.com/asr/v1/1257220479?end=1&engine_model_type=8k_0&expired=1533261960&nonce=89292&res_type=0&result_text_format=0&secretid=AKIDnTzMciENxMaeWFjALSO9iARXSCoA2QK4&seq=0&source=0&sub_service_type=0&template_name=hello_14520&timeout=10&timestamp=1533101734&voice_format=1&voice_id=001";

        HttpPost post = null;
        try {
            HttpClient httpClient = new DefaultHttpClient();


            //获取文件长度
            File file=new File("D:\\testtx\\002.WAV");
            long length = file.length();
            int fileLengths = new Long(length).intValue();
            String fileLength= String.valueOf(length);
            System.err.println(fileLength);


            //读取文件转换为字符串
            StringBuffer buffer = new StringBuffer();
            BufferedReader bf= new BufferedReader(new FileReader("D:\\testtx\\002.WAV"));
            String s = null;
            while((s = bf.readLine())!=null){//使用readLine方法，一次读一行
                buffer.append(s.trim());
            }
            bf.close();
            String fileDataStr = buffer.toString();


            post = new HttpPost(url);

            // 构造消息头
            post.setHeader("Content-type", "application/octet-stream");
            post.setHeader("Host", "aai.qcloud.com");
//            post.setHeader("Authorization", "tWG9sDdJkl%2BDsh%2FEkdfuiSymqCY%3D");
            post.setHeader("Authorization", "ZdXqniGVSSoFtim%2FBAPFBGDVX6s%3D");
//            post.setHeader("Content-Length",fileLength);



//            StringEntity entity = new StringEntity(fileDataStr, Charset.forName("UTF-8"));
//            entity.setContentEncoding("UTF-8");

            // 构建消息实体
            StringEntity entity=new StringEntity(fileDataStr);
            post.setEntity(entity);

            HttpResponse response = httpClient.execute(post);
            System.out.println(response.getEntity());

            // 检验返回码
            int statusCode = response.getStatusLine().getStatusCode();

            System.out.println(statusCode);

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(post != null){
                try {
                    post.releaseConnection();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }



}
