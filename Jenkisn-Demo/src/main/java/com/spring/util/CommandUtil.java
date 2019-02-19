package com.spring.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandUtil {


    public CommandUtil() {
        throw  new RuntimeException("can not generate the constructor function");
    }

    public static List<String> execWinCMD(String command) {

        List<String> data = new ArrayList<>();
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
//                sb.append(line+"\n");
//                data.add(line+System.lineSeparator());
                data.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static String execCMD(String command) {
        StringBuilder sb = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (Exception e) {
            return e.toString();
        }
        return sb.toString();
    }



    public static List<String> execLinuxCommand(String comand, String params) {
        List<String> processList = new ArrayList<String>();

        StringBuilder result = new StringBuilder();
        try {
            Runtime run = Runtime.getRuntime();
            String[] cmdarry_linux = {"/bin/bash", "-c", comand + params};
            String[] cmdarry_windows = {"cmd", "/c", comand + params};
            String[] cmdarry_mac = {"/bin/sh", "-c", comand + params};
            String[] cmdarry = new String[3];
            String OS = System.getProperty("os.name").toLowerCase();
            if (OS.indexOf("linux") >= 0) {
                System.arraycopy(cmdarry_linux, 0, cmdarry, 0, cmdarry_linux.length);
            } else if (OS.indexOf("mac") >= 0) {
                System.arraycopy(cmdarry_mac, 0, cmdarry, 0, cmdarry_mac.length);
            } else {
                System.arraycopy(cmdarry_windows, 0, cmdarry, 0, cmdarry_windows.length);
            }
            System.out.println(Arrays.toString(cmdarry));
            Process process = run.exec(cmdarry);
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
            StringBuffer sb = new StringBuffer();

            String line = "";

            while ((line = br.readLine()) != null) {
                processList.add(line);
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return processList;
    }


    public static String execCommand(String comand, String params) {
        String result = "";
        try {
            Runtime run = Runtime.getRuntime();
            String[] cmdarry_linux = {"/bin/bash", "-c", comand + params};
            String[] cmdarry_windows = {"cmd", "/c", comand + params};
            String[] cmdarry_mac = {"/bin/sh", "-c", comand + params};
            String[] cmdarry = new String[3];
            String OS = System.getProperty("os.name").toLowerCase();
            if (OS.indexOf("linux") >= 0) {
                System.arraycopy(cmdarry_linux, 0, cmdarry, 0, cmdarry_linux.length);
            } else if (OS.indexOf("mac") >= 0) {
                System.arraycopy(cmdarry_mac, 0, cmdarry, 0, cmdarry_mac.length);
            } else {
                System.arraycopy(cmdarry_windows, 0, cmdarry, 0, cmdarry_windows.length);
            }
            System.out.println("----------------------------------------------");
            System.out.println("执行的shell命令："+Arrays.toString(cmdarry));
            System.out.println("----------------------------------------------");
            Process process = run.exec(cmdarry);
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
            StringBuffer sb = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                result = result + line;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

}
