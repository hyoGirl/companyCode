package com.spring.util;

import com.offbytwo.jenkins.JenkinsServer;

import java.net.URI;
import java.net.URISyntaxException;

public class JenkinsServerUtil {

    private static String requestUrl = "http://10.1.10.24:8080/jenkins";
    private static String username = "v-xulei";  //gittest
    private static String apiToken = "c048dbeefd883d0e1b6dba23c963d88f";   // API Token
    private static String password = "Newpwd_123";


    private static ThreadLocal<JenkinsServer> connContainers=new ThreadLocal<JenkinsServer>();

    public static JenkinsServer getJenkinsServer(){


        JenkinsServer jenkinsServer = connContainers.get();
        try {
            if(jenkinsServer==null){
                URI serverUri = new URI(requestUrl);
                jenkinsServer = new JenkinsServer(serverUri, username, apiToken);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } finally {
            connContainers.set(jenkinsServer);
        }
        return jenkinsServer;
    }

    public static void closeConnection(){
        JenkinsServer jenkinsServer = connContainers.get();
        try {
            if(jenkinsServer!=null){

                jenkinsServer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connContainers.remove();
        }
    }




}
