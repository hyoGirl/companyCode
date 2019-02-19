package com.spring.jenkins;

import com.alibaba.fastjson.JSON;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.helper.Range;
import com.offbytwo.jenkins.model.*;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

public class JobBuildIng {

    public static void main(String[] args) throws Exception {


//        getJobInfo();

//        doJobDeploy();


//        doJobBuildNoDeploy();

//        getApiTokenByName();

        getInfo();


    }


    /**
     * 判断任务是否正在执行中
     */

    private static void getInfo() throws Exception {

        String jobName = "162-alarmservice-sit";
        String requestUrl = "http://10.1.10.24:8080/jenkins";
        String username = "v-xulei";
//        String password = "c048dbeefd883d0e1b6dba23c963d88f";  //apitoken
        String password = "P@ss12345";


        URI serverUri = new URI(requestUrl);
        JenkinsServer jenkinsServer = new JenkinsServer(serverUri, username, password);


        Map<String, Job> jobs = jenkinsServer.getJobs();

        System.out.println(JSON.toJSON(jobs));


        //?    判断当前job是否正在执行中   获取正在运行的job是什么？

        boolean running = jenkinsServer.isRunning();

        JobWithDetails jobWithDetails = jenkinsServer.getJob("162-alarmservice-sit");

        boolean inQueue = jobWithDetails.isInQueue();
        System.out.println("任务是否在队列中： " + inQueue);
        //表现这个job是不是在排队中
        /**
         * 1：这个job是不是在队列中排队，如果不是。表示1：它正在运行，2：它没有运行
         *
         */

        //获取buildHistory  根据某一个具体名字来获取是否是在构建

        Range range=Range.build().to(5).build();


        List<Build> allBuilds = jenkinsServer.getJob("162-alarmservice-sit").getBuilds();

//        List<Build> allBuilds = jenkinsServer.getJob("162-alarmservice-sit").getAllBuilds(range);
        for (int i = 0; i < allBuilds.size(); i++) {

            Build build = allBuilds.get(i);

            if(build.details().isBuilding()){
                System.out.println("job正在执行中  "+build.details().getId());
//                System.out.println(build.details().getId());
            }

            System.out.println(build.getNumber());
        }



        //获取的是jenkins上执行机的状态
        Map<String, Computer> computers = jenkinsServer.getComputers();

        for (Map.Entry<String, Computer> entry : computers.entrySet()) {
            String key = entry.getKey();
            Computer value = entry.getValue();
            System.out.println(key + "-->" + value.getDisplayName());

            //master-->master
            //lajitong-->lajitong
        }



//        JobWithDetails uatWithDetails = jenkinsServer.getJob("162-alarmservice-uat");
//        boolean inQueueUat = uatWithDetails.isInQueue();
//        System.out.println("任务是否在队列中： "+inQueueUat);


//        boolean buildable = jobWithDetails.isBuildable();
//        System.out.println("任务是都已经构建完毕： "+buildable);


    }


    /**
     * 通过用户名获取用户的API-TOKEN
     */
    private static void getApiTokenByName() throws Exception {

        String jobName = "DEV-hello-BUILD-DEPLOY";
        String requestUrl = "http://10.1.10.24:8080/jenkins";
        String username = "v-xulei";
        String password = "c048dbeefd883d0e1b6dba23c963d88f";


        URI serverUri = new URI(requestUrl);
        JenkinsServer jenkinsServer = new JenkinsServer(serverUri, username, password);


        JobWithDetails details = jenkinsServer.getJob(jobName).details();


        JobWithDetails job = jenkinsServer.getJob(jobName);


        Map<String, View> views = jenkinsServer.getViews();

//        System.out.println(JSON.toJSON(views));


//        http://Jenkins_IP:8080/user/zhangyi/configure


    }





    /*
     **
     * 使用job来完整部署,
     */

    public static void doJobDeploy() throws Exception {

        String jobName = "DEV-hello-BUILD-DEPLOY";
        String requestUrl = "http://10.1.10.24:8080/jenkins";
        String username = "xulei";
//        String password = "saic888888";
        String password = "d18037a9ee46c883d57ba7f9902d8842";   // API Token

//        URI serverUri, String username, String passwordOrToken

        URI serverUri = new URI(requestUrl);
        JenkinsServer jenkinsServer = new JenkinsServer(serverUri, username, password);

        if (jenkinsServer.isRunning()) {

            JobWithDetails job = jenkinsServer.getJob("DEV-hello-BUILD-DEPLOY");

            try {

                int nextBuildNumber = job.getNextBuildNumber();

                System.out.println("构建编号为： " + nextBuildNumber);


                job.build();

            } catch (IOException e) {
                e.printStackTrace();
            }


            BuildWithDetails details = job.getLastBuild().details();

            String consoleOutputText = details.getConsoleOutputText();

            BuildResult result = details.getResult();

            if (result.equals(BuildResult.SUCCESS)) {

                System.out.println("构建成功！！！！");
            }


//            details.toString();

//            System.out.println(JSON.toJSON(details));

            System.out.println();


            System.out.println(result);
            System.out.println(consoleOutputText);


        }


    }


    /**
     * 获取执行完毕的job信息
     *
     * @throws Exception
     */
    public static void getJobInfo() throws Exception {

//        DEV-hello-BUILD-DEPLOY

        String jobName = "DEV-hello-BUILD-DEPLOY";

        String requestUrl = "http://10.1.10.24:8080/jenkins/";

        String username = "xulei";
        String password = "saic888888";
//        URI serverUri, String username, String passwordOrToken
        URI serverUri = new URI(requestUrl);
        JenkinsServer jenkinsServer = new JenkinsServer(serverUri, username, password);

        Map<String, Job> jobs = jenkinsServer.getJobs();

        System.out.println(JSON.toJSON(jobs));

        System.out.println();

        Job job = jobs.get(jobName);


        JobWithDetails serverJob = jenkinsServer.getJob("DEV-hello-BUILD-DEPLOY");


//        String jobXml = jenkinsServer.getJobXml(jobName);
//
//        System.out.println("xml 文件是： "+jobXml);

        System.out.println();

        int nextBuildNumber = serverJob.getNextBuildNumber();
        System.out.println("下一次构建的number是： " + nextBuildNumber);


        Build lastBuild = serverJob.getLastBuild();
        System.out.println("lastBuild:  " + lastBuild.getNumber());

//        List<Build> allBuilds = serverJob.getAllBuilds();
//        System.out.println("allBuilds:  "+allBuilds);


        //获取一次成功的信息
        Build lastSuccessfulBuild = serverJob.getLastSuccessfulBuild();


        int number = lastSuccessfulBuild.getNumber();

        System.out.println("获取最后一次成功的构建历史的编号: " + number);


        int number1 = serverJob.getLastBuild().getNumber();
        System.out.println("获取最后一次的构建历史的编号: " + number);


        int queueId = lastSuccessfulBuild.getQueueId();

        System.out.println("获取最后一次成功的queueId: " + queueId);

        String url = lastSuccessfulBuild.getUrl();

        System.out.println("获取最后一次成功的url: " + url);

        long duration = lastSuccessfulBuild.details().getDuration();

        System.out.println("获取最后一次成功的持续毫秒数目: " + duration);


        BuildWithDetails details = serverJob.getLastBuild().details();

//
//        //获取构建的控制台输出
//        String consoleOutputText = details.getConsoleOutputText();
//        System.out.println(consoleOutputText);

        //获取构建的最终执行结果 是成功还是失败
        BuildResult result = details.getResult();
        System.out.println("LastBuild detail" + result);

    }
}


//        new URI("此处是Jenkins访问路径，eg：http://localhost:8088/"), "此处是用户名，eg: zhangsan", "此处是用户密码：eg: 110110");
//        String jobName = "DEV-hello-BUILD-DEPLOY";
//        JenkinsServer jenkins = new JenkinsServer(new URI("http://10.1.10.24:8080/jenkins/view/demo/"), "xulei", "saic888888");
//
//        // 判断jenkins是否running
//        if(jenkins.isRunning()){
//            // 获取jenkins构建脚本
//            String jobXml = jenkins.getJobXml(jobName);
//            // 修改构建脚本
////        　　jenkins.updateJob("jobName",newJobXml);
//                    // 构建对应的job
//            jenkins.getJob(jobName).build();
//                        // 获取html格式日志
//            String consoleOutputHtml = jenkins.getJob(jobName).getLastBuild().details().getConsoleOutputHtml();
//
////            String consoleOutputHtml1 = jenkins.getJob(jobName).getLastBuild().details().getConsoleOutputHtml();
//            // 获取text格式日志
//            String consoleOutputText = jenkins.getJob(jobName).getLastBuild().details().getConsoleOutputText();
//
//            // 获取执行结果（是否成功）
//            BuildResult result = jenkins.getJob(jobName).getLastBuild().details().getResult();
//
//            System.out.println(result);
//
//        }