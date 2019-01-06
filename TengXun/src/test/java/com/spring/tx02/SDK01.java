package com.spring.tx02;


import com.alibaba.fastjson.JSONObject;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class SDK01 {

    //腾讯云应用ID
    private static String appid;
    //官网获取到的秘钥
    private static String secretKey;
    //官网SecretId
    private static String secretid;

    //模板名称
    private static String template_name;
    //引擎类型引擎模型类型
    private static String engine_model_type;
    //⼦服务类型。
    private static String sub_service_type;
    //识别结果文本编码方式。0：UTF-8；1：GB2312；2：GBK；3：BIG5
    private static String result_text_format;
    //结果返回方式。 0：同步返回；1：尾包返回。
    private static String res_type;
    //语⾳音编码方式，可选，默认值为4。1wav，4sp
    private static String voice_format;
    //当前时间戳，
    private static Long timestamp;
    //签名的有效期
    private static Long expired;
    //随机正整数。用户需⾃自行生成，最长10位
    private static Long nonce;
    // 语⾳音分⽚片的序号从0开始
    private static int seq;
    // 是否为最后⼀一⽚片，最后⼀一⽚片语⾳音⽚片为1，其余为0
    private  static int end;
    // 设置超时时间单位为毫秒
    private static int timeout;
    // 设置为0
    private  static int source;

    //设置分片大小
    private static int cutlength;
    //音频文件所在路径
    private static String  filePath;

    //校验请求参数
    public static int validateRequestParam(String appid,
                                           String engine_model_type,
                                           String result_text_format,
                                           String res_type,
                                           String voice_format,
                                           String secretid,
                                           String filePath,
                                           String secretKey,
                                           int cutlength) {

        if(secretid.length()<=0){
            System.out.println("secretid can not be empty!");
            return -1;
        }
        if(secretKey.length()<=0){
            System.out.println("secretKey can not be empty!");
            return -1;
        }
        if(appid.length()<=0){
            System.out.println("appid can not be empty!");
            return -1;
        }
        if(engine_model_type.length()<=0||(engine_model_type.compareTo("8k_0")!=0&&engine_model_type.compareTo("16k_0")!=0)){
            System.out.println("engine_model_type is not vailed!");
            return -1;
        }
        if(res_type.length()<=0||(res_type.compareTo("0")!=0&&res_type.compareTo("1")!=0)){
            System.out.println("res_type is not vailed!");
            return -1;
        }
        if(result_text_format.length()<=0||(result_text_format.compareTo("0")!=0&&result_text_format.compareTo("1")!=0&&
                result_text_format.compareTo("2")!=0&&result_text_format.compareTo("3")!=0)){
            System.out.println("res_text_format is not vailed!");
            return -1;
        }
        if(voice_format.length()<=0||(voice_format.compareTo("1")!=0&&voice_format.compareTo("4")!=0&&voice_format.compareTo("6")!=0)){
            System.out.println("voice_format is not vailed!");
            return -1;
        }
        if(filePath.length()<=0){
            System.out.println("filePath can not be empty!");
            return -1;
        }
        if(cutlength<0||cutlength>200000){
            System.out.println("cutlength is not vailed!");
            return -1;
        }

        SDK01.appid=appid;
        SDK01.secretKey=secretKey;
        SDK01.secretid=secretid;
        SDK01.engine_model_type=engine_model_type;
        SDK01.res_type=res_type;
        SDK01.result_text_format=result_text_format;
        SDK01.voice_format=voice_format;
        SDK01.filePath=filePath;
        SDK01.cutlength=cutlength;
        SDK01.template_name="";

        return 0;
    }

    //发送音频文件
    public static void sendVoiceFile() {


        Map<String,Object> reqMap=new TreeMap<String,Object>();
        reqMap.put("projectid","0");
        reqMap.put("secretid",secretid);
        if(template_name.compareTo("")!=0){
            reqMap.put("template_name",template_name);
        }
        reqMap.put("sub_service_type","1");
        reqMap.put("engine_model_type",engine_model_type);
        reqMap.put("res_type",res_type);
        reqMap.put("result_text_format",result_text_format);
        reqMap.put("voice_id",createVoiceId(16));

        reqMap.put("timeout","200");
        reqMap.put("source","0");
        reqMap.put("voice_format",voice_format);
        reqMap.put("timestamp",createTimestamp());
        reqMap.put("expired",createExpired());
        reqMap.put("nonce",createRandomInt());

        long startTime=System.currentTimeMillis();
        int availableLength =0;
        String resultData="";
        try {

            //读取文件产生切片，来设置分片值
            FileInputStream ins=new FileInputStream(new File(filePath));
            availableLength = ins.available();

            System.out.print("data len is: "+availableLength);
            byte[] dataPacket=new byte[cutlength];

            int seq = 0, end = 0, len= 0;
            CloseableHttpClient httpclient = HttpClients.createDefault();


            String finallyData="";

            while(true){
                len=ins.read(dataPacket);
                if(len==cutlength||len<cutlength&&end==0){
                    if (len<cutlength) {
                        //?
                        len = (len>>2)<<2;
                        end = 1;
                    }
                    System.out.println("len: "+len);
                    reqMap.put("seq",seq+"");
                    reqMap.put("end",end+"");
                    //遍历字节流发送实时请求
                    String serviceUrl="http://aai.qcloud.com/asr/v1/"+appid+"?";
                    String requestUrl=generateRequestUrl(serviceUrl,reqMap);

                    System.err.println(requestUrl);

                    //设置签名鉴权
                    String authInfo=createSignAuth(requestUrl,secretKey);
                    System.err.println("签名： "+authInfo);
                    //执行请求文件
                    HttpPost hpost = new HttpPost(requestUrl);
                    hpost.setHeader("Authorization", authInfo);
                    hpost.setHeader("Content-Type", "application/octet-stream");
                    hpost.setEntity(new ByteArrayEntity(dataPacket));
                    CloseableHttpResponse response = httpclient.execute(hpost);

                    try {
                        System.err.println("---------------------------------");
                        System.out.println(response.getStatusLine());


//                        if(end==1){
//                            finallyData=response.getEntity().toString();
//                            System.out.println(finallyData+"*************************");
//                        }
                        System.out.println(EntityUtils.toString(response.getEntity(), "UTF-8"));
//                        if(end==1){
//                            JSONObject jsonObject = JSONObject.parseObject(EntityUtils.toString(response.getEntity(), "UTF-8"));
//                            finallyData = (String) jsonObject.get("text");
//                            System.out.println(finallyData+"*************************");
//                        }

                    } finally {

                        response.close();
                    }
                    seq++;
                }else{
                    break;
                }
                TimeUnit.MICROSECONDS.sleep(10);
            }

            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
        }
        long costTime=(System.currentTimeMillis() - startTime)/1000;

        System.out.println("程序花费的时间是："+costTime+"秒！");
    }

    //拼接完整签名原本并加密
    private static String createSignAuth(String requestUrl, String secretKey) {
        String originalData="POST"+requestUrl.substring(7);
        return Base64Encode_HmacSha1(originalData,secretKey);

    }

    //对拼接的报文进行加密
    private  static String Base64Encode_HmacSha1(String originalData, String secretKey) {
        String type="HmacSHA1";
        String encoded="";
        try {
            byte[] key = secretKey.getBytes("UTF-8");
            byte[] Sequence = originalData.getBytes("UTF-8");
            Mac hmacSHA1 = Mac.getInstance(type);
            SecretKeySpec secret = new SecretKeySpec(key, type);
            hmacSHA1.init(secret);
            byte[] Hash = hmacSHA1.doFinal(Sequence);
            encoded = Base64.getEncoder().encodeToString(Hash);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return encoded;

    }

    //生成字典顺序的url
    private static String generateRequestUrl(String serviceUrl, Map<String, Object> reqMap) {
        StringBuilder sb=new StringBuilder(serviceUrl);
        for(Map.Entry entry:reqMap.entrySet()){
            sb.append(entry.getKey());
            sb.append('=');
            sb.append(entry.getValue());
            sb.append('&');
        }

        //去掉最后一个&
        sb.setLength(sb.length()-1);
        return sb.toString();

    }

    //随机正整数。用户需⾃自行⽣生成，最长10位
    private  static String createRandomInt() {
        Random random=new Random();
        return random.nextInt(10000)+"";
    }
    //当前时间戳
    private static String createTimestamp() {
        long timestamp=System.currentTimeMillis()/1000L;
        return timestamp+"";

    }
    //签名的有效期
    private static String createExpired() {
        long expiredTime=System.currentTimeMillis()/1000L+24*60*60;
        return expiredTime+"";
    }
    private static String createVoiceId(int length) {

       // 定义一个字符串（A-Z，a-z，0-9）即62位；
        String str="zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        //由Random生成随机数
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        //长度为几就循环几次
        for(int i=0; i<length; ++i){
            //产生0-61的数字
            int number=random.nextInt(62);
            //将产生的数字通过length次承载到sb中
            sb.append(str.charAt(number));
        }
        //将承载的字符转换成字符串
        return sb.toString();

//        long voiceId = System.currentTimeMillis();
//        return voiceId+"";

    }

}
