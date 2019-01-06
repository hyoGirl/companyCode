package com.spring.tx03;

public class RASRtest {
    public static void main(String[] args){
        String secret_key="7II71mcIrKcicKuIVWLzZ1RgprlcO5lG";
        String secretid="AKIDtfMvJNiG2e5JNHEd7b0RluvlQHyUBxus";
        String appid="1257141353";

        //识别引擎 8k_0 or 16k_0
        String engine_model_type="8k_0";

        //结果返回方式 0：同步返回 or 1：尾包返回
        String res_type="0";

        // 识别结果文本编码方式 0:UTF-8,1:GB2312,2:GBK,3:BIG5
        String result_text_format="0";

        // 语音编码方式 1:wav 4:sp 6:skill
        String voice_format="1";

//        String filepath="D:\\MyProject\\TestCase\\test2.wav";
        String filepath="d:\\testtx\\002.wav";

        // 语音切片长度 cutlength<200000
        int cutlength=6400;
        int res= RASRsdk.setConfig(secret_key,secretid,appid,engine_model_type,res_type,result_text_format,voice_format,filepath,cutlength);
        if(res<0){
            return;
        }
        RASRsdk.sendVoice();
    }
}
