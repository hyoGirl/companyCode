import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.nio.charset.Charset;

/**
 * @Auther: Administrator
 * @Date: 2018/7/31 0031 17:25
 * @Description:
 */
public class Restmain {


    public static void main(String[] args)throws IOException {



        RestTemplate restTemplate = new RestTemplate();

        String url="http://aai.qcloud.com/asr/v1/"+"1257220479"+"?"
                +"end=1"+"&"
                +"engine_model_type=8k_0"+"&"
                +"expired=1533175560"+"&"
                +"nonce=89292"+"&"
                +"res_type=0"+"&"
                +"result_text_format=0"+"&"
                +"secretid=AKIDnTzMciENxMaeWFjALSO9iARXSCoA2QK4"+"&"
                +"seq=0"+"&"
                +"source=0"+"&"
                +"sub_service_type=0"+"&" //⼦服务类型。⼦服务类型 0-全⽂文转写 1 实时流式 识别
                +"template_name=hello_14520"+"&" //模板名称，模板是众多参数的组合
                +"timeout=10"+"&"
                +"timestamp=1533088439"+"&"
                +"voice_format=1"+"&"
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
        File file=new File("D:\\testtx\\002.WAV");
        long length = file.length();
        int fileLengths = new Long(length).intValue();
        String fileLength= String.valueOf(length);

        System.err.println(fileLength);





        //获取本地音频文件
        StringBuffer buffer = new StringBuffer();
        BufferedReader bf= new BufferedReader(new FileReader("D:\\testtx\\002.WAV"));
        String s = null;
        while((s = bf.readLine())!=null){//使用readLine方法，一次读一行
            buffer.append(s.trim());
        }
        bf.close();
        String fileDataStr = buffer.toString();



//       $ret = http_curl_exec($serverUrl,$data,$rsp_str,
//        $http_code,'POST',10,array(),$header);




/*        HttpClient httpClient = new DefaultHttpClient();

        HttpPost post = new HttpPost(url);
        post.setHeader("Authorization","Un%2Bc9zq7DUEWjRQAPiGume5hgTw%3D");
        post.setHeader("Host","aai.qcloud.com");
        post.setHeader("Content-Length",fileLength);
        StringEntity entity = new StringEntity(fileDataStr, Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        post.setEntity(entity);
        HttpResponse response = httpClient.execute(post);
        System.out.println(response.getEntity());
*/


        //拼接请求头
        HttpHeaders headers = new HttpHeaders();
        //生成签名
        headers.add("Authorization","Snd0bx9kr91db3PR9ise56iLkOo%3D");

        headers.add("Host","aai.qcloud.com");
        headers.add("Content-Length",fileLength);


//        headers.set("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
        headers.set("Content-Type","application/octet-stream");




        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("Data", fileDataStr);

        HttpEntity<MultiValueMap> requestEntity = new HttpEntity<MultiValueMap>(requestBody, headers);

        //发送post请求
//        String responseEntity = restTemplate.postForObject(url, (Object) requestEntity, String.class);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);

        System.out.println(responseEntity.getBody());


        String value = new String(responseEntity.getBody().getBytes("ISO-8859-1"),"utf-8");


        System.out.println(value);







    }
}
