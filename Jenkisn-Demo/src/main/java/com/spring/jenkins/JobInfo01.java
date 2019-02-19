package com.spring.jenkins;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.*;

import java.net.URI;
import java.util.Map;

public class JobInfo01 {

    public static void main(String[] args) throws Exception {

        getJobInfo();

    }


    public static void getJobInfo() throws Exception {

//        DEV-hello-BUILD-DEPLOY

        String jobName = "162-alarmservice-sit";

        String requestUrl = "http://10.1.10.24:8080/jenkins";
        String username = "v-xulei";
        String apiToken = "e0ae895c0f44d4f7bca843cf9e70f27c";   // API Token

//        URI serverUri, String username, String passwordOrToken

        URI serverUri = new URI(requestUrl);
        JenkinsServer jenkinsServer = new JenkinsServer(serverUri, username, apiToken);


        Map<String, Job> jobs = jenkinsServer.getJobs();

        Job job = jobs.get(jobName);


        JobWithDetails serverJob = jenkinsServer.getJob(jobName);


        System.out.println();

        int nextBuildNumber = serverJob.getNextBuildNumber();
        System.out.println("下一次构建的number是： "+nextBuildNumber);


        Build lastBuild = serverJob.getLastBuild();
        System.out.println("lastBuild:  "+lastBuild.getNumber());

//        List<Build> allBuilds = serverJob.getAllBuilds();
//        System.out.println("allBuilds:  "+allBuilds);


        //获取一次成功的信息
        Build lastSuccessfulBuild = serverJob.getLastSuccessfulBuild();

        int number = lastSuccessfulBuild.getNumber();

        System.out.println("获取最后一次成功的构建历史的编号: "+number);


        int number1 = serverJob.getLastBuild().getNumber();
        System.out.println("获取最后一次的构建历史的编号: "+number1);


//        int queueId = lastSuccessfulBuild.getQueueId();
//
//        System.out.println("获取最后一次成功的queueId: "+queueId);

        String url = lastSuccessfulBuild.getUrl();

        System.out.println("获取最后一次成功的url: "+url);

        long duration = lastSuccessfulBuild.details().getDuration();

        System.out.println("获取最后一次成功的持续毫秒数目: "+duration);


        BuildWithDetails details = serverJob.getLastBuild().details();

//
//        //获取构建的控制台输出
//        String consoleOutputText = details.getConsoleOutputText();
//        System.out.println(consoleOutputText);

        //获取构建的最终执行结果 是成功还是失败
        BuildResult result = details.getResult();
        System.out.println("LastBuild detail  "+result);

    }
}
