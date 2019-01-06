package com.spring.tx03;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.entity.ByteArrayEntity;
public class RASRsdk {
    private static String secret_key,secretid,appid,engine_model_type,res_type,result_text_format,voice_format,filepath,template_name;
    private static int cutlength;

    private static String generateUrl(String serverUrl, Map<String,String> mapReq){
        StringBuilder strBuilder = new StringBuilder(serverUrl);

        if (mapReq.containsKey("appid")) {
            strBuilder.append(mapReq.get("appid"));
        }

        strBuilder.append('?');

        // to make that all the parameters are sorted by ASC order
        TreeMap<String, String> sortedMap = new TreeMap(mapReq);

        for (Map.Entry<String, String> entry : sortedMap.entrySet()) {
            if (entry.getKey().equals("appid")) {
                continue;
            }
            strBuilder.append(entry.getKey());
            strBuilder.append('=');
            strBuilder.append(entry.getValue());
            strBuilder.append('&');
        }

        if (mapReq.size() > 0) {
            strBuilder.setLength(strBuilder.length() - 1);
        }

//        System.out.println("Generated URL: " + strBuilder);

        return strBuilder.toString();
    }

    private static String base64_hmac_sha1(String value, String keyStr) {
        String encoded = "";
        String type = "HmacSHA1";
        try {
            byte[] key = (keyStr).getBytes("UTF-8");
            byte[] Sequence = (value).getBytes("UTF-8");

            Mac HMAC = Mac.getInstance(type);
            SecretKeySpec secretKey = new SecretKeySpec(key, type);

            HMAC.init(secretKey);
            byte[] Hash = HMAC.doFinal(Sequence);

            encoded = Base64.getEncoder().encodeToString(Hash);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encoded;
    }

    /*
     * UNIXEpoch 时间�?
     */
    private static String toUNIXEpoch() {
        long unixTime = System.currentTimeMillis() / 1000L;
        return unixTime + "";
    }

    /*
     * ExpiredUNIXEpoch 时间�?
     */
   private static String toExpiredUNIXEpoch() {
        long unixTime = System.currentTimeMillis() / 1000L + 24 * 60 * 60 ;
        return unixTime + "";
    }

    private static String getNonce(){
        Random random = new Random(System.currentTimeMillis());
        return random.nextInt(10000) + "";
    }

   private static String createSign(String serverUrl, String secretKey) throws UnsupportedEncodingException,NoSuchAlgorithmException, InvalidKeyException {
        String strToBeEncoded = "POST" + serverUrl.substring(7);
//        System.out.println("String to be encoded: " + strToBeEncoded);
        return base64_hmac_sha1(strToBeEncoded, secretKey);
    }
    private static String getRandomString(int length){
        //定义一个字符串（A-Z，a-z，0-9）即62位；
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
    }
    public  static int setConfig(
            String secret_key,
            String secretid,
            String appid,
            String engine_model_type,
            String res_type,
            String result_text_format,
            String voice_format,
            String filepath,
            int cutlength
    ){
        return RASRsdk.setConfig(secret_key, secretid, appid,engine_model_type,res_type, result_text_format, voice_format, filepath,cutlength,"");
    }
    public static int setConfig(
            String secret_key,
            String secretid,
            String appid,
            String engine_model_type,
            String res_type,
            String result_text_format,
            String voice_format,
            String filepath,
            int cutlength,
            String template_name

    ){

       if(secret_key.length()<=0){
            System.out.println("secret_key can not be empty!");
            return -1;
        }
        if(secretid.length()<=0){
            System.out.println("secretid can not be empty!");
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
            System.out.println("result_text_format is not vailed!");
            return -1;
        }
        if(voice_format.length()<=0||(voice_format.compareTo("1")!=0&&voice_format.compareTo("4")!=0&&voice_format.compareTo("6")!=0)){
            System.out.println("voice_format is not vailed!");
            return -1;
        }
        if(filepath.length()<=0){
            System.out.println("filepath can not be empty!");
            return -1;
        }
        if(cutlength<0||cutlength>200000){
            System.out.println("cutlength is not vailed!");
            return -1;
        }
        RASRsdk.secret_key=secret_key;
        RASRsdk.secretid=secretid;
        RASRsdk.appid=appid;
        RASRsdk.engine_model_type=engine_model_type;
        RASRsdk.res_type=res_type;
        RASRsdk.result_text_format=result_text_format;
        RASRsdk.voice_format=voice_format;
        RASRsdk.filepath=filepath;
        RASRsdk.cutlength=cutlength;
        RASRsdk.template_name=template_name;

        return 0;
    }
    public static int sendVoice(){
        Map<String,String> reqMap= new TreeMap();
        reqMap.put("appid",appid);
        reqMap.put("secretid",secretid);
        reqMap.put("projectid","0");
        if(template_name.compareTo("")!=0){
            reqMap.put("template_name",template_name);
        }
        reqMap.put("sub_service_type","1");
        reqMap.put("engine_model_type",engine_model_type);
        reqMap.put("res_type",res_type);
        reqMap.put("result_text_format",result_text_format);
        reqMap.put("voice_id",getRandomString(16));
        reqMap.put("timeout","200");
        reqMap.put("source","0");
        reqMap.put("voice_format",voice_format);
        reqMap.put("timestamp",toUNIXEpoch());
        reqMap.put("expired",toExpiredUNIXEpoch());
        reqMap.put("nonce",getNonce());
        FileInputStream fileInputStream=null;
        int avaiable=0;
        long starttime=System.currentTimeMillis();
        try{
            fileInputStream = new FileInputStream(new File(filepath));
            avaiable=fileInputStream.available();
            System.out.print("data len is: ");
            System.out.println(avaiable);
            byte[] dataPacket = new byte[cutlength];
            CloseableHttpClient httpclient = HttpClients.createDefault();
            int seq = 0, end = 0, n = 0;
            while(true){
                n=fileInputStream.read(dataPacket);
                if(n==cutlength||(n<cutlength&&end==0)){
                    if (n<cutlength) {
                        n = (n>>2)<<2;
                        end = 1;
                    }
                    System.out.println("n :"+n);
                    reqMap.put("seq",seq+"");
                    reqMap.put("end",end+"");
                    //1、serverUrl
                    String _url = "http://aai.qcloud.com/asr/v1/";
                    String serverUrl=generateUrl(_url,reqMap);
                    System.err.println("Server URL: " + serverUrl);
                    //2、设置header
                    String authinfo = createSign(serverUrl, secret_key);
                    System.err.println("签名: " + authinfo);
                    HttpPost hpost = new HttpPost(serverUrl);
                    hpost.setHeader("Authorization", authinfo);
                    hpost.setHeader("Content-Type", "application/octet-stream");
                    hpost.setEntity(new ByteArrayEntity(dataPacket));
                    CloseableHttpResponse response = httpclient.execute(hpost);
                    try {
                        System.out.println("----------------------------------------");
                        System.out.println(response.getStatusLine());
                        System.out.println(EntityUtils.toString(response.getEntity(), "utf-8"));
                    } finally {
                        response.close();
                    }
                    seq++;
                }else{
                    break;
                }
                TimeUnit.MILLISECONDS.sleep(10);
            }
            httpclient.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        long endtime = System.currentTimeMillis();
        System.out.println("程序运行时间为："+(endtime-starttime)/1000+"秒！");
        return 0;
    }
}
