import sun.nio.cs.ext.GBK;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;
import java.util.TreeMap;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class TencentCloudAPIDemo {

    private final static String CHARSET = "UTF-8";

    public static String sign(String s, String key, String method) throws Exception {
        Mac mac = Mac.getInstance(method);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(CHARSET), mac.getAlgorithm());
        mac.init(secretKeySpec);
        byte[] hash = mac.doFinal(s.getBytes(CHARSET));
        return DatatypeConverter.printBase64Binary(hash);
    }

    public static String getStringToSign(TreeMap<String, Object> params) {

//        StringBuilder s2s = new StringBuilder("POSTaai.qcloud.com/?");
        StringBuilder s2s = new StringBuilder("POSTaai.qcloud.com/asr/v1/1257220479?");//pHHhSl8%2BLb6t5e98YEKGpP%2BU5PA%3D
        // 签名时要求对参数进行字典排序，此处用TreeMap保证顺序
        for (String k : params.keySet()) {
            s2s.append(k).append("=").append(params.get(k).toString()).append("&");
        }
        return s2s.toString().substring(0, s2s.length() - 1);
    }

    public static String getUrl(TreeMap<String, Object> params) throws UnsupportedEncodingException {

//        StringBuilder url = new StringBuilder("https://cvm.tencentcloudapi.com/?");
        StringBuilder url = new StringBuilder("POSTaai.qcloud.com/asr/v1/1257220479?");

        // 实际请求的url中对参数顺序没有要求
        for (String k : params.keySet()) {
            // 需要对请求串进行urlencode，由于key都是英文字母，故此处仅对其value进行urlencode
            url.append(k).append("=").append(URLEncoder.encode(params.get(k).toString(), CHARSET)).append("&");
        }
        return url.toString().substring(0, url.length() - 1);
    }

    public static void main(String[] args) throws Exception {





        TreeMap<String, Object> params = new TreeMap<String, Object>(); // TreeMap可以自动排序

        // 实际调用时应当使用随机数，例如：params.put("Nonce", new Random().nextInt(java.lang.Integer.MAX_VALUE));
        params.put("nonce", 89292); // 随机正整数。用户需⾃自⾏行行⽣生成，最长10位
        params.put("secretId", "AKIDnTzMciENxMaeWFjALSO9iARXSCoA2QK4"); // 官⽹网SecretId
        params.put("engine_model_type", "8k_0"); // 引擎类型引擎模型类型。8k_0:8k通⽤用，16k_0:16k通用。
        params.put("end", "1"); // 是否为最后⼀一⽚片，最后⼀一⽚片语⾳音⽚片为1，其余为0
        params.put("expired", 1533261960); // 签名的有效期
        params.put("res_type", 0); // 结果返回⽅方式。 0：同步返回；1：尾包返回。
        params.put("result_text_format", 0); //识别结果⽂文本编码⽅方式。0：UTF-8；1：GB2312；2：GBK；3：BIG5
        params.put("seq", 0); // 语⾳音分⽚片的序号从0开始
        params.put("source", 0); // 设置为0
        params.put("template_name", "hello_14520"); // 模板名称，
        params.put("timeout", 10); // 设置超时时间单位为毫秒
        params.put("timestamp", 1533101734); // 当前时间戳，
        params.put("voice_format", 1); // 语⾳音编码⽅方式，可选，默认值为4。1wav，4sp
        params.put("voice_id", 001); //
        params.put("Signature", sign(getStringToSign(params), "iFbnncGm7SCtPkMuKZkjZEl8MPI0EIBt", "HmacSHA1"));

        System.out.println(getUrl(params));
    }
}
