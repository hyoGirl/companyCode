package com.spring.jenkins;

public interface JenkinsResult {


    /**
     * build构建成功  BUILD SUCCESSFUL in 7s
     */
    String BUILD_SUCCESS="build_success";


    /**
     * build构建失败  BUILD FAILED
     */
    String BUILD_FAILED="build_failed";


    /**
     * 公钥验证失败  Permission denied (publickey,gssapi-keyex,gssapi-with-mic)
     */
    String KEY_FAILED="key_Permission_denied";


    /**
     * 启动脚本、待部署文件未找到  No such file or directory
     */
    String FILE_FAILED="file_Not_Found";


    /**
     * 程序部署异常，端口未启动 is timeout for 30 seconds
     */
    String DEPLOY_FAILED="deploy_failed";


    /**
     * Git制品库推送报错：fatal
     */
    String GIT_PUSH_FAILED="fatal";

    /**
     * 未知错误：fatal
     */
    String UNKNOWN_FAILED="unknown_failed";


    /**
     * 完全成功。上面全部都成功了
     */

    String ALL_SUCCESS="SUCCESS";

    /**
     * 失败。排除上面失败的
     */

    String FAILURE="FAILURE";

}
