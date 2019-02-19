//package com.spring.util;
//
//import com.offbytwo.jenkins.JenkinsServer;
//import com.offbytwo.jenkins.model.*;
//import com.release.constant.JenkinsResult;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.IOException;
//import java.util.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class JenkinsUtil {
//
//    private static Logger logger = LoggerFactory.getLogger(JenkinsUtil.class);
//
//    public static Map<String, Object> doJob(Map<String, String> params, String jobName, String operation, String env) {
//        JenkinsServer jenkinsServer = null;
//        try {
//            jenkinsServer = JenkinsServerUtil.getJenkinsServer();
////            JobWithDetails serverJob = jenkinsServer.getJob(jobName);
//            Map<String, Job> jobs = jenkinsServer.getJobs();
//            JobWithDetails job = jobs.get(jobName).details();
//            job.build(params);
//            //老版本测试不需要参数时注释掉
////            job.build();
//
//            String constant = jobName + "   运行的线程为：" + Thread.currentThread().getName();
//            int buildNumber = job.getNextBuildNumber();
//            logger.info(constant + " 本次构建的buildNumber: " + buildNumber);
//            int lastNumber = job.getLastBuild().getNumber();
//            logger.info(constant + " 上一次获取的构建编号是： " + lastNumber);
//
//
//            Date startDate = new Date();
//            while (lastNumber != buildNumber) {
//                Thread.sleep(7000);
//                lastNumber = getJobInfo(jenkinsServer, jobName);
//                //调试时，放开打印语句
//                System.out.println(constant + " 循环判断中获取的构建编号为： " + lastNumber);
//                System.out.println(constant + " 本来应该获取的构建编号为： " + buildNumber);
//            }
//            Date endDate = new Date();
//            logger.info(constant + "  构建花费毫秒数为： " + (endDate.getTime() - startDate.getTime()) + "   本次构建完毕");
//            JobWithDetails serverJob = jenkinsServer.getJob(jobName);
//            int number = serverJob.getLastCompletedBuild().getNumber();
//            logger.info(constant + "  最近的构建编号为： " + number);
//            BuildWithDetails currentDetail = serverJob.getLastBuild().details();
//            String consoleOutputText = currentDetail.getConsoleOutputText();
//            BuildResult result = currentDetail.getResult();
//            logger.info(constant + "  构建编号为：" + buildNumber + "的执行jenkins控制台显示的结果为： " + result);
//
////            String deployName = "";
////            if (!result.equals(BuildResult.SUCCESS)) {
////                deployName = "errorName";
////            } else {
////                /**
////                 * TODO  下面的是比较简单的判断方法
////                 */
////                deployName = getFinalResultByRegex(consoleOutputText);
////            }
//
//
//            //判断操作类型是deploy/storage/rollback  以及 env是sit/uat
//
//            Map<String, String> jenkinsConsoleResult = new HashMap<>();
//            String deployName = "";
//            String finalResult = "";
//            if (env.equals("sit")) {
//                if (operation.equals("storage") || operation.equals("deploy")) {
//                    jenkinsConsoleResult = getFinalResultByRegex(consoleOutputText, result);
//                    deployName = jenkinsConsoleResult.get("deployName");
//                    finalResult = jenkinsConsoleResult.get("finalResult");
//                }
//                if (operation.equals("rollback")) {
//                    jenkinsConsoleResult = getFinalResultByRegexForRollBack(consoleOutputText, result);
//                    deployName = jenkinsConsoleResult.get("deployName");
//                    finalResult = jenkinsConsoleResult.get("finalResult");
//                }
//            }
//            if(env.equals("uat")){
//                if (operation.equals("deploy")) {
//                    jenkinsConsoleResult = getFinalResultUat(consoleOutputText, result,jobName);
//                    deployName = jenkinsConsoleResult.get("deployName");
//                    finalResult = jenkinsConsoleResult.get("finalResult");
//                }
//            }
//
//
//            Map<String, Object> resultMap = new HashMap<>();
//            resultMap.put("deployStartTime", startDate); //部署开始时间
//            resultMap.put("deployEndTime", endDate);   //部署结束时间
//            resultMap.put("deployResult", finalResult);     //部署结果
//            resultMap.put("buildNum", buildNumber);    //构建版本号
//            resultMap.put("deployName", deployName);   //当前部署版本名称
//            resultMap.put("branch", params.get("branch"));   //分支信息
//            return resultMap;
//        } catch (Exception e) {
////            e.printStackTrace();
//        } finally {
//            JenkinsServerUtil.closeConnection();
//        }
//        return null;
//    }
//
//    /**
//     * 获取UAT执行的结果
//     *
//     * @param consoleOutputText
//     * @param result
//     * @return
//     */
//    private static Map<String, String> getFinalResultUat(String consoleOutputText, BuildResult result, String jobName) {
//
//
//        String serviceId = jobName.substring(0, jobName.indexOf("-"));
//        Integer id = Integer.valueOf(serviceId);
//        Map<String, String> regexDataMap = new HashMap<>();
//
//        String deployName = "";
//        String finalResult = "";
//
//        // 大于1000的是tar_name  小于的是jar_name
//        if(id>=1000){
//            if (result.equals(BuildResult.FAILURE)) {
//                finalResult = getFailedResultByRegex(consoleOutputText, finalResult, JenkinsResult.FAILURE,"uat");
//                regexDataMap.put("finalResult", finalResult);
//            }
//
//            if (result.equals(BuildResult.SUCCESS)) {
//                finalResult = getSuccessResultByRegex(consoleOutputText, finalResult,"uat");
//                regexDataMap.put("finalResult", finalResult);
//            }
//            String regexString1 = "(?m)tar_name=.*";
//            Pattern pattern1 = Pattern.compile(regexString1);
//            Matcher match1 = pattern1.matcher(consoleOutputText);
//            String findData = "ErrorName";
//            if (match1.find()) {
//                findData = match1.group();
//            }
//
//            if (!StringUtils.isEmpty(findData) && !findData.equals("ErrorName")) {
//                int index = findData.indexOf("tar_name=");
//                int jar = findData.indexOf(".gz");
//                findData = findData.substring(index+9, jar + 3);
//            }
//            regexDataMap.put("deployName", findData);
//
//
//        }else{
//            if (result.equals(BuildResult.FAILURE)) {
//                finalResult = getFailedResultByRegex(consoleOutputText, finalResult, JenkinsResult.FAILURE,"uat");
//                regexDataMap.put("finalResult", finalResult);
//            }
//
//            if (result.equals(BuildResult.SUCCESS)) {
//                finalResult = getSuccessResultByRegex(consoleOutputText, finalResult,"uat");
//                regexDataMap.put("finalResult", finalResult);
//            }
//            String regexString1 = "(?m)jar_name=.*";
//            Pattern pattern1 = Pattern.compile(regexString1);
//            Matcher match1 = pattern1.matcher(consoleOutputText);
//            String findData = "ErrorName";
//            if (match1.find()) {
//                findData = match1.group();
//            }
//
//            if (!StringUtils.isEmpty(findData) && !findData.equals("ErrorName")) {
//                int index = findData.indexOf("jar_name=");
//                int jar = findData.indexOf(".jar");
//                findData = findData.substring(index + 9, jar + 4);
//            }
//            regexDataMap.put("deployName", findData);
//
//
//        }
//        return regexDataMap;
//
//    }
//
//    /**
//     * 重新获取构建信息
//     * @param jenkinsServer
//     * @param jobName
//     * @return
//     */
//    private static int getJobInfo(JenkinsServer jenkinsServer, String jobName) {
//        try {
//            Map<String, Job> jobs = jenkinsServer.getJobs();
//            Job job = jobs.get(jobName);
//            JobWithDetails serverJob = jenkinsServer.getJob(jobName);
//            Build lastCompletedBuild = serverJob.getLastCompletedBuild();
//            if (lastCompletedBuild == null) {
//                return 0;
//            }
////            System.out.println("重新获取最后一次的构建历史的编号为: "+number1);
//            return lastCompletedBuild.getNumber();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }
//
//    /**
//     * 获取SIT回退的jar包名称，以及最终的结果
//     * <p>
//     * 回退有两种可能失败，1：是公钥  2是启动失败
//     *
//     * @param consoleOutputText
//     * @param result
//     * @return
//     */
//    private static Map<String, String> getFinalResultByRegexForRollBack(String consoleOutputText, BuildResult result) {
//
//        String deployName = "";
//        String finalResult = "";
//
//        Map<String, String> regexDataMap = new HashMap<>();
//        if (result.equals(BuildResult.FAILURE)) {
//            finalResult = getFailedResultByRegex(consoleOutputText, finalResult, JenkinsResult.FAILURE,"sit");
//            regexDataMap.put("finalResult", finalResult);
//        }
//        if (result.equals(BuildResult.SUCCESS)) {
//            finalResult = getSuccessResultByRegex(consoleOutputText, finalResult,"sit");
//            regexDataMap.put("finalResult", finalResult);
//        }
//
//        String regexString1 = "(?m)Upload.*.jar.*";
//        Pattern pattern1 = Pattern.compile(regexString1);
//        Matcher match1 = pattern1.matcher(consoleOutputText);
//        String findData = "ErrorName";
//        if (match1.find()) {
//            findData = match1.group();
//        }
//
//        if (!StringUtils.isEmpty(findData) && !findData.equals("ErrorName")) {
//            findData = findData.substring(7, findData.lastIndexOf("jar") + 3);
//        }
//        System.out.println("回退后，产生的jar包是："+findData);
//        regexDataMap.put("deployName", findData);
//        return regexDataMap;
//
//    }
//
//
////        1：首先根据最终结果是成功还是失败。
////
////        如果是FAILURE，
////
////        去判断构建结果
////
////        构建结果如果成功，就是会出现版本号，
////
////        构建失败就是失败，为了获取哪种类型的失败
////
////                就去判断后面这4种
////
////        公钥验证失败：Permission denied (publickey,gssapi-keyex,gssapi-with-mic)
////        启动脚本、待部署文件未找到：No such file or directory
//
////        程序部署异常，端口未启动：is timeout for 30 seconds
////        Git制品库推送报错：fatal
////
////        如果最终结果是SUCCESS   此时存在问题，中间出了错误后，还是会最终显示成功
////
////        还是要去判断构建成功获取版本号
////
////                然后还是要去判断
////
////        公钥验证失败：Permission denied (publickey,gssapi-keyex,gssapi-with-mic)
////        启动脚本、待部署文件未找到：No such file or directory
////
////        程序部署异常，端口未启动：is timeout for 30 seconds
////          Git制品库推送报错：fatal
////
////
////        问题是： 能不能这4种存在的时候，jenkins 就是失败的，不是成功的。成功结果没效果###################
//
//    /**
//     * TODO
//     * 获取SIT发布以及入库的最后的具体结果和部署名称
//     *
//     * @param consoleOutputText
//     * @param result
//     * @return
//     */
//    private static Map<String, String> getFinalResultByRegex(String consoleOutputText, BuildResult result) {
//
//        String deployName = "";
//        String finalResult = "";
//
//        Map<String, String> regexDataMap = new HashMap<>();
//        if (result.equals(BuildResult.FAILURE)) {
//
//            String regex_Build_Fail = "(?m)^BUILD FAILED.*";
//            Pattern pattern_Build_Fail = Pattern.compile(regex_Build_Fail);
//            Matcher match_build_Fail = pattern_Build_Fail.matcher(consoleOutputText);
//            if (match_build_Fail.find()) {
//                finalResult = JenkinsResult.BUILD_FAILED;
//                regexDataMap.put("finalResult", finalResult);
//                regexDataMap.put("deployName", "ErrorName");
//            } else {
//                String regex_Build_Success = "((?m)^Now.*T_.* | (?m)now.*T_.*)";
//                Pattern pattern_Build_Success = Pattern.compile(regex_Build_Success);
//                Matcher match_build_Success = pattern_Build_Success.matcher(consoleOutputText);
//                if (match_build_Success.find()) {
//                    regexDataMap.put(JenkinsResult.BUILD_SUCCESS, match_build_Success.group(0));
//                    String matchData = match_build_Success.group(0);
//                    deployName = matchData.substring(matchData.indexOf("T"), matchData.length());
//                    regexDataMap.put("deployName", deployName);
//                }
//                finalResult = getFailedResultByRegex(consoleOutputText, finalResult, JenkinsResult.FAILURE,"sit");
//                regexDataMap.put("finalResult", finalResult);
//            }
//        }
//        if (result.equals(BuildResult.SUCCESS)) {
//            String regex_Build_Success = "((?m)^Now.*T_.* | (?m)now.*T_.*)";
//            Pattern pattern_Build_Success = Pattern.compile(regex_Build_Success);
//            Matcher match_build_Success = pattern_Build_Success.matcher(consoleOutputText);
//
//            if (match_build_Success.find()) {
//                regexDataMap.put(JenkinsResult.BUILD_SUCCESS, match_build_Success.group(0));
//                String matchData = match_build_Success.group(0);
//                deployName = matchData.substring(matchData.indexOf("T"), matchData.length());
//                regexDataMap.put("deployName", deployName);
////                regexMap.put("finalResult", JenkinsResult.ALL_SUCCESS);
//
//            }
//            finalResult = getSuccessResultByRegex(consoleOutputText, finalResult,"sit");
//            regexDataMap.put("finalResult", finalResult);
//        }
//        return regexDataMap;
//    }
//
//
//    /**
//     * 获取jenkins控制台最后一行结果显示为成功的最终结果
//     *
//     * @param consoleOutputText
//     * @param finalResult
//     * @return
//     */
//    private static String getSuccessResultByRegex(String consoleOutputText, String finalResult,String env) {
//        return getFailedResultByRegex(consoleOutputText, finalResult, JenkinsResult.ALL_SUCCESS,env);
//    }
//
//
//    /**
//     * 获取jenkins控制台最后一行结果显示为失败的最终结果
//     *
//     * @param consoleOutputText
//     * @param finalResult
//     * @return
//     */
//    private static String getFailedResultByRegex(String consoleOutputText, String finalResult, String flag,String env) {
//        String regexString2 = "((?m)Permission denied .*|(?m)No such file or directory.*|is timeout for 30 seconds.*|fatal.*)";
//        Pattern pattern2 = Pattern.compile(regexString2);
//        Matcher m2 = pattern2.matcher(consoleOutputText);
//        List<String> datalist2 = new ArrayList<>();
//        while (m2.find()) {
//            datalist2.add(m2.group());
//            logger.info("正则获取的结果为： " + m2.group());
//        }
//        if (datalist2.size() >= 1) {
//            for (int i = 0; i < datalist2.size(); i++) {
//                String regexStr = datalist2.get(i);
//                if (regexStr.contains("Permission denied")) {
//                    finalResult = JenkinsResult.KEY_FAILED;
//                    break;
//                }
//                if(env.equals("sit")){
//                    if (regexStr.contains("No such file or directory")) {
//                        finalResult = JenkinsResult.FILE_FAILED;
//                        break;
//                    }
//                }
//                if (regexStr.contains("is timeout for")) {
//                    finalResult = JenkinsResult.DEPLOY_FAILED;
//                    break;
//                }
//                if (regexStr.contains("fatal")) {
//                    finalResult = JenkinsResult.GIT_PUSH_FAILED;
//                    break;
//                }
//            }
//        } else {
//            finalResult = flag;
//        }
//        return finalResult;
//    }
//
//
//    /**
//     * 简单判断结果，获取部署名称
//     *
//     * @param consoleOutputText
//     * @return
//     */
//    private static String getFinalResultByRegex(String consoleOutputText) {
//
//        String regexString = "(?m)^Now.*T_.*";
//        Pattern pattern_Now = Pattern.compile(regexString);
//        Matcher m = pattern_Now.matcher(consoleOutputText);
//
//        List<String> datalist = new ArrayList<>();
//        String regexData = "";
//        while (m.find()) {
//            datalist.add(m.group(0));
//        }
//        String deployName = "";
//        if (datalist.size() >= 1) {
//            regexData = datalist.get(0);
//            deployName = regexData.substring(regexData.indexOf("T"), regexData.length());
//            logger.info("正则获取的版本号为： " + deployName);
//        }
//        return deployName;
//    }
//}
