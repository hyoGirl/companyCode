package com.spring.jenkins;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.*;

import java.net.URI;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeployJob01 {

    public static void main(String[] args) throws Exception {

        String requestUrl = "http://10.1.10.24:8080/jenkins";
        String username = "v-xulei";
        String apiToken = "c048dbeefd883d0e1b6dba23c963d88f";   // API Token

        URI serverUri = new URI(requestUrl);
        JenkinsServer jenkinsServer = new JenkinsServer(serverUri, username, apiToken);


        deploy(jenkinsServer);
    }


    private static void deploy(JenkinsServer jenkinsServer) throws Exception {


        String jobName = "162-alarmservice-sit";



        //获取当前本次的运行情况
//        String jobName = "162-alarmservice-sit";
//        String requestUrl = "http://10.1.10.24:8080/jenkins";
//        String username = "xulei";
//        String password = "d18037a9ee46c883d57ba7f9902d8842";   // API Token
//
//        URI serverUri = new URI(requestUrl);
//        JenkinsServer jenkinsServer = new JenkinsServer(serverUri, username, password);

        Map<String, String> params = new HashMap<String, String>();
        params.put("branch", "develop");
        params.put("version", "1.1");
//        params.put("Deploy","yes");
        params.put("deploy", "no");
        params.put("rollback", "false");


        Map<String, Job> jobs = jenkinsServer.getJobs();
        JobWithDetails job = jobs.get(jobName).details();


        //开始执行本次任务
        job.build(params);

        // 即将执行任务的jobId
        int buildNumber = job.getNextBuildNumber();
        System.out.println("本次构建的buildNumber: "+buildNumber);


        //上一个完成的编号
        int lastNumber =job.getLastBuild().getNumber();
        System.out.println("上一次获取的构建编号是： "+lastNumber);


        /**
         * 假如是第一次怎么判断？
         *  第一次是从1开始构建的。
         */
        Date startDate = new Date();
        //1：怎么判断本次执行完毕了？
        while(lastNumber!=buildNumber){
//            System.out.println("本次还没有构建完毕");
//            TimeUnit.SECONDS.sleep(10);
            Thread.sleep(7000);

            lastNumber = getJobInfo(jenkinsServer);
            System.out.println("  循环判断中获取的构建编号为： "+lastNumber);
            System.out.println("  本来应该获取的构建编号为： "+buildNumber);
        }
        Date endDate = new Date();

        System.out.println("构建花费毫秒数为： "+(endDate.getTime()-startDate.getTime()));
        System.out.println("本次构建完毕");

        //根据某个编号来获取本次是否执行完毕？
//        BuildWithDetails buildNumberDetails = job.getBuildByNumber(buildNumber).details();
//
//        Build buildByNumber = job.getBuildByNumber(buildNumber);
//
//
//
//        boolean isBuilding = job.getBuildByNumber(buildNumber).details().isBuilding();

//        System.out.println(isBuilding);
////
//        if(!isBuilding){
//
//
//        }

        //这个地方必须重新获取job
        JobWithDetails serverJob = jenkinsServer.getJob(jobName);

        int number = serverJob.getLastBuild().getNumber();


        System.out.println("最近的构建编号为： "+number);


        BuildWithDetails currentDetail = serverJob.getLastBuild().details();

        String consoleOutputText= currentDetail.getConsoleOutputText();


        System.out.println("构建编号为："+number+"的控制台输出： "+consoleOutputText);

        String regexString="(?m)^Now.*T_.*";

        Pattern pattern= Pattern.compile(regexString);

        Matcher m=pattern.matcher(consoleOutputText);

        List<String> datalist=new ArrayList<>();

        while(m.find()){
            datalist.add(m.group(0));
        }
        String ss = datalist.get(0);

        System.out.println("正则获取的版本号为： "+ss.substring(ss.indexOf("T"), ss.length()));

        BuildResult result = currentDetail.getResult();

        System.out.println("构建编号为："+number+"的执行结果为： "+result);


    }


    public static int getJobInfo(JenkinsServer jenkinsServer) throws Exception {

//        DEV-hello-BUILD-DEPLOY

        String jobName = "162-alarmservice-sit";

        Map<String, Job> jobs = jenkinsServer.getJobs();
        Job job = jobs.get(jobName);

        JobWithDetails serverJob = jenkinsServer.getJob(jobName);
        System.out.println();


        Build lastCompletedBuild = serverJob.getLastCompletedBuild();
        if(lastCompletedBuild==null){
            return 0;
        }
        int number1 = lastCompletedBuild.getNumber();
        System.out.println("重新获取最后一次的构建历史的编号为: "+number1);
        return number1;

    }
}
