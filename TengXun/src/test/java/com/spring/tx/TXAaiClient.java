package com.spring.tx;

import java.io.File;
import java.io.FileInputStream;
import java.util.Base64;

import com.tencentcloudapi.aai.v20180522.AaiClient;
import com.tencentcloudapi.aai.v20180522.models.SentenceRecognitionRequest;
import com.tencentcloudapi.aai.v20180522.models.SentenceRecognitionResponse;
import com.tencentcloudapi.common.AbstractClient;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import sun.misc.BASE64Encoder;

/**
 * @Auther: Administrator
 * @Date: 2018/8/1 0001 08:52
 * @Description:
 */
public class TXAaiClient {


    private static String endpoint = "aai.tencentcloudapi.com";
    private static String version = "2018-08-02";
    private static String region = "ap-shanghai";

    public static void main(String[] args) throws Exception {

        //原始文件
        File file = new File("d:\\testtx\\002.WAV");
        FileInputStream ins = new FileInputStream(file);
        byte[] buf = new byte[8192];
        int len = 0;
        StringBuffer sb = new StringBuffer();
        while ((len = ins.read(buf)) != -1) {
            sb.append(new String(buf, 0, len));
        }
        //文件数据
        String data = sb.toString();

        Base64.Encoder encoder = Base64.getEncoder();
        String encodedText = encoder.encodeToString(data.getBytes());

        //文件长度
        long length = file.length();
        Integer dataLen = Integer.valueOf(String.valueOf(length));


        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setReqMethod("POST"); // post请求(默认为post请求)
        httpProfile.setConnTimeout(50); // 请求连接超时时间，单位为秒(默认60秒)
//	     httpProfile.setEndpoint("aai.ap-shanghai.tencentcloudapi.com"); // 指定接入地域域名(默认就近接入)
        httpProfile.setEndpoint("aai.tencentcloudapi.com"); // 指定接入地域域名(默认就近接入)

        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setSignMethod("HmacSHA1"); // 指定签名算法(默认为HmacSHA256)
        clientProfile.setHttpProfile(httpProfile);


        Credential cred = new Credential("AKIDnTzMciENxMaeWFjALSO9iARXSCoA2QK4", "iFbnncGm7SCtPkMuKZkjZEl8MPI0EIBt");

        // 实例化要请求产品client对象
        AaiClient aaiclient = new AaiClient(cred, region, clientProfile);

        SentenceRecognitionRequest sr = new SentenceRecognitionRequest();

        sr.setData(encodedText);
        sr.setProjectId(0);

        //子服务类型。0：离线语音识别。1：实时流式识别，2，一句话识别。?????
        sr.setSubServiceType(2);

        sr.setEngSerViceType("16k");
        sr.setSourceType(1);
        sr.setVoiceFormat("WAV");
        sr.setDataLen(dataLen);
        sr.setUsrAudioKey("OOXX");


        SentenceRecognitionResponse sentenceRecognition = aaiclient.SentenceRecognition(sr);

        String result = sentenceRecognition.getResult();


        System.out.println(result);


    }


}
