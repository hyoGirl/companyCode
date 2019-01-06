package com.spring.tx;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.cvm.v20170312.CvmClient;
import com.tencentcloudapi.cvm.v20170312.models.DescribeZonesRequest;
import com.tencentcloudapi.cvm.v20170312.models.DescribeZonesResponse;

/**
 * @Auther: Administrator
 * @Date: 2018/8/1 0001 08:50
 * @Description:
 */
public class TengXunCvmClient {


    public static void main(String[] args) {


        try {
            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey
            Credential cred = new Credential("AKIDnTzMciENxMaeWFjALSO9iARXSCoA2QK4", "iFbnncGm7SCtPkMuKZkjZEl8MPI0EIBt");

            // 实例化要请求产品(以cvm为例)的client对象
            CvmClient client = new CvmClient(cred, "ap-guangzhou");

            // 实例化一个请求对象
            DescribeZonesRequest req = new DescribeZonesRequest();

            // 通过client对象调用想要访问的接口，需要传入请求对象
            DescribeZonesResponse resp = client.DescribeZones(req);

            // 输出json格式的字符串回包
            System.out.println(DescribeZonesRequest.toJsonString(resp));
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }
}
