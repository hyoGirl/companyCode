package com.spring.tx02;

/**
 * @Auther: Administrator
 * @Date: 2018/8/3 0003
 * @Description:
 */
public class SDK01Test {

    public static void main(String[] args) {


//        String appid="1257220479";
        String appid="1257141353";

//        String secretKey=" iFbnncGm7SCtPkMuKZkjZEl8MPI0EIBt";  我的
        String secretKey="7II71mcIrKcicKuIVWLzZ1RgprlcO5lG";

//        String secretid="AKIDnTzMciENxMaeWFjALSO9iARXSCoA2QK4";  我的
        String secretid="AKIDtfMvJNiG2e5JNHEd7b0RluvlQHyUBxus";

        String filePath="d:\\testtx\\1.wav";

        String engine_model_type="8k_0";
        String res_type="0";

//        String res_text_format="0";
       String result_text_format="0";

        String voice_format="1";

        int cutlength=64000;

        int code = SDK01.validateRequestParam(appid,engine_model_type, result_text_format, res_type, voice_format, secretid, filePath, secretKey, cutlength);


        if(code<0){
            return;
        }

        SDK01.sendVoiceFile();
    }
}
