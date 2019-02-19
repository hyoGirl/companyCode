package com.spring.jenkins;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.BuildResult;
import com.offbytwo.jenkins.model.BuildWithDetails;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JobFailTest {

    private static Logger logger = LoggerFactory.getLogger(JobFailTest.class);


    public static void main(String[] args) throws Exception {

        String jobName = "SIT-common-mockserver-BUILD-DEPLOY";

        String requestUrl = "http://10.1.10.24:8080/jenkins/";

        String username = "xulei";
        String password = "d18037a9ee46c883d57ba7f9902d8842";   // API Token

        URI serverUri = new URI(requestUrl);
        JenkinsServer jenkinsServer = new JenkinsServer(serverUri, username, password);

        Map<String, Job> jobs = jenkinsServer.getJobs();
        Job job = jobs.get(jobName);

        JobWithDetails serverJob = jenkinsServer.getJob(jobName);
        System.out.println();

        BuildWithDetails currentDetail = serverJob.getLastBuild().details();

        String consoleOutputText= currentDetail.getConsoleOutputText();


        BuildResult result = currentDetail.getResult();

        Map<String, String> jenkinsConsoleResult = getFinalResultByRegexAndResult(consoleOutputText, result);
        String deployName = jenkinsConsoleResult.get("deployName");
        String finalResult = jenkinsConsoleResult.get("finalResult");
        System.out.println("deployName:"+deployName);
        System.out.println("finalResult:"+finalResult);


    }


    private static Map<String, String> getFinalResultByRegexAndResult(String consoleOutputText, BuildResult result) {

        String deployName = "";
        String finalResult = "";

        Map<String, String> regexDataMap = new HashMap<>();
        if (result.equals(BuildResult.FAILURE)) {

            String regex_Build_Fail = "(?m)^BUILD FAILED.*";
            Pattern pattern_Build_Fail = Pattern.compile(regex_Build_Fail);
            Matcher match_build_Fail = pattern_Build_Fail.matcher(consoleOutputText);
            if (match_build_Fail.find()) {
                finalResult = JenkinsResult.BUILD_FAILED;
                regexDataMap.put("finalResult", finalResult);
                regexDataMap.put("deployName", "");
            } else {
                String regex_Build_Success = "(?m)^Now.*T_.*";
                Pattern pattern_Build_Success = Pattern.compile(regex_Build_Success);
                Matcher match_build_Success = pattern_Build_Success.matcher(consoleOutputText);

                if (match_build_Success.find()) {
                    regexDataMap.put(JenkinsResult.BUILD_SUCCESS, match_build_Success.group(0));
                    String matchData = match_build_Success.group(0);
                    deployName = matchData.substring(matchData.indexOf("T"), matchData.length());
                    regexDataMap.put("deployName", deployName);
                }

                String regexString2 = "((?m)Permission denied .*|(?m)No such file or directory.*|is timeout for 30 seconds.*|fatal.*)";
                Pattern pattern2 = Pattern.compile(regexString2);
                Matcher m2 = pattern2.matcher(consoleOutputText);
                List<String> datalist2 = new ArrayList<>();
                while (m2.find()) {
                    datalist2.add(m2.group());
                    logger.info(m2.group());
                }
                if (datalist2.size() > 1) {
                    for (int i = 0; i < datalist2.size(); i++) {
                        String regexStr = datalist2.get(i);
                        if (regexStr.contains("Permission denied")) {
                            finalResult = JenkinsResult.KEY_FAILED;
                        } else if (regexStr.contains("No such file or directory")) {
                            finalResult = JenkinsResult.FILE_FAILED;
                        } else if (regexStr.contains("is timeout for")) {
                            finalResult = JenkinsResult.DEPLOY_FAILED;
                        } else if (regexStr.contains("fatal")) {
                            finalResult = JenkinsResult.GIT_PUSH_FAILED;
                        } else {
                            finalResult = JenkinsResult.UNKNOWN_FAILED;
                        }
                    }
                } else {
                    finalResult = JenkinsResult.FAILURE;
                }
                regexDataMap.put("finalResult", finalResult);
            }
        }
        if (result.equals(BuildResult.SUCCESS)) {
            String regex_Build_Success = "(?m)^Now.*T_.*";
            Pattern pattern_Build_Success = Pattern.compile(regex_Build_Success);
            Matcher match_build_Success = pattern_Build_Success.matcher(consoleOutputText);

            if (match_build_Success.find()) {
                regexDataMap.put(JenkinsResult.BUILD_SUCCESS, match_build_Success.group(0));
                String matchData = match_build_Success.group(0);
                deployName = matchData.substring(matchData.indexOf("T"), matchData.length());
                regexDataMap.put("deployName", deployName);
//                regexMap.put("finalResult", JenkinsResult.ALL_SUCCESS);

            }
            String regexString2 = "((?m)Permission denied .*|(?m)No such file or directory.*|is timeout for 30 seconds.*|fatal.*)";
            Pattern pattern2 = Pattern.compile(regexString2);
            Matcher m2 = pattern2.matcher(consoleOutputText);
            List<String> datalist2 = new ArrayList<>();
            while (m2.find()) {
                datalist2.add(m2.group());
                logger.info(m2.group());
            }
            if (datalist2.size() > 1) {
                for (int i = 0; i < datalist2.size(); i++) {
                    String regexStr = datalist2.get(i);
                    if (regexStr.contains("Permission denied")) {
                        finalResult = JenkinsResult.KEY_FAILED;
                    } else if (regexStr.contains("No such file or directory")) {
                        finalResult = JenkinsResult.FILE_FAILED;
                    } else if (regexStr.contains("is timeout for")) {
                        finalResult = JenkinsResult.DEPLOY_FAILED;
                    } else if (regexStr.contains("fatal")) {
                        finalResult = JenkinsResult.GIT_PUSH_FAILED;
                    } else {
                        finalResult = JenkinsResult.UNKNOWN_FAILED;
                    }
                }
            } else {
                finalResult = JenkinsResult.ALL_SUCCESS;
            }
            regexDataMap.put("finalResult", finalResult);
        }
        return regexDataMap;
    }
}