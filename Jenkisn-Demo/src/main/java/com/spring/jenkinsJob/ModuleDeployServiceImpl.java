package com.spring.jenkinsJob;
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.alibaba.fastjson.TypeReference;
//import com.baomidou.mybatisplus.mapper.EntityWrapper;
//import com.baomidou.mybatisplus.mapper.Wrapper;
//import com.baomidou.mybatisplus.service.impl.ServiceImpl;
//import com.offbytwo.jenkins.JenkinsServer;
//import com.release.dao.ModuleConfigMapper;
//import com.release.entity.Deploy;
//import com.release.entity.ModuleConfig;
//import com.release.entity.PipelineReleaseModule;
//import com.release.service.DeployService;
//import com.release.service.ModuleConfigService;
//import com.release.service.ModuleDeployService;
//import com.release.service.PipelineReleaseModuleService;
//import com.release.util.CommandUtil;
//import com.release.util.JenkinsUtil;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//import java.util.concurrent.*;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModuleDeployServiceImpl  {

    private static Logger logger = LoggerFactory.getLogger(ModuleDeployServiceImpl.class);

//
//    @Autowired
//    ModuleConfigService moduleConfigService;
//
//    @Autowired
//    DeployService deployService;
//
//
//    @Autowired
//    PipelineReleaseModuleService pipelineReleaseModuleService;
//
//
//    /**
//     * 一个个获取分支，已经作废
//     *
//     * @param srcGitAddress
//     * @return
//     */
//    public String findBranchesByModuleID(String srcGitAddress) {
//
//        String osName = System.getProperty("os.name").toLowerCase();
//        List<String> backResult = new ArrayList<>();
//
////        windows10  需要安装软件才可以进行调试
//        String WinCmd = "cmd /c git ls-remote -h " + srcGitAddress;
//
////        linux
//        String LinuxCmd = "/usr/local/bin/git ls-remote -h " + srcGitAddress;
//
//        if (osName.startsWith("windows")) {
//            backResult = CommandUtil.execWinCMD(WinCmd);
//        } else {
//            backResult = CommandUtil.execLinuxCommand(LinuxCmd, "");
//        }
//
//        System.out.println(JSON.toJSONString(backResult));
////        List<String> branchName=new ArrayList<>();
//        String branchName = null;
//        for (int i = 0; i < backResult.size(); i++) {
//            int beginIndex = backResult.get(i).lastIndexOf("/");
//            String name = backResult.get(i).substring(beginIndex + 1, backResult.get(i).length());
////            branchName.add(name);
//            branchName += name + ",";
//        }
//
//        System.err.println("分支信息为： " + branchName);
//        ModuleConfig moduleConfig = moduleConfigService.selectOne(new EntityWrapper<ModuleConfig>().eq("src_git_address", srcGitAddress));
//        moduleConfig.setBranches(branchName);
//        moduleConfigService.updateById(moduleConfig);
//        return branchName;
//    }
//
//
//    @Override
//    public List<Map<String, String>> findBranches(JSONArray jsonArray) {
//
//        List<Map<String, String>> resultList = new ArrayList<>();
//        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(jsonArray.size());
//        for (int i = 0; i < jsonArray.size(); i++) {
//
//            JSONObject jsonObject = jsonArray.getJSONObject(i);
//            String moduleId = jsonObject.getString("moduleId");
//            String srcGitAddress = jsonObject.getString("srcGitAddress");
//            String osName = System.getProperty("os.name").toLowerCase();
//
//            //        windows10  需要安装软件才可以进行调试
//            String WinCmd = "cmd /c git ls-remote -h " + srcGitAddress;
//            //        linux
//            String LinuxCmd = "/usr/local/bin/git ls-remote -h " + srcGitAddress;
//            List<String> backResult = new ArrayList<>();
//            if (osName.startsWith("windows")) {
//                System.out.println("windows 下执行的命令为：" + WinCmd);
//                backResult = CommandUtil.execWinCMD(WinCmd);
//            } else {
//                System.out.println("linux 下执行的命令为：" + LinuxCmd);
//                backResult = CommandUtil.execLinuxCommand(LinuxCmd, "");
//            }
//            StringBuilder stringBuilder = new StringBuilder();
//            for (int j = 0; j < backResult.size(); j++) {
//                int beginIndex = backResult.get(j).lastIndexOf("/");
//                String name = backResult.get(j).substring(beginIndex + 1, backResult.get(j).length());
//                stringBuilder.append(name).append(",");
//            }
//            stringBuilder.setLength(stringBuilder.length()-1);
//
//            ModuleConfig moduleConfig = moduleConfigService.selectOne(new EntityWrapper<ModuleConfig>().eq("src_git_address", srcGitAddress));
//            moduleConfig.setBranches(stringBuilder.toString());
//            moduleConfigService.updateById(moduleConfig);
//            Map<String, String> data = new HashMap<>();
//            data.put("moduleId", moduleId);
//            data.put("branches", stringBuilder.toString());
//            resultList.add(data);
//        }
//        return resultList;
//    }
//
//
//    /**
//     * sit发布
//     * @param jsonArray
//     * @return
//     */
//    @Override
//    public List<Map<String, Object>> confirmSit(JSONArray jsonArray) {
//        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(jsonArray.size());
//
//        List list = new ArrayList();
//
//        long startTime = new Date().getTime();
//        List<Future<Map<String, Object>>> taskResults = new ArrayList<Future<Map<String, Object>>>();
//
//        for (int i = 0; i < jsonArray.size(); i++) {
//
//            JSONObject jsonObject = jsonArray.getJSONObject(i);
//            String jobName = jsonObject.getString("jobName");
//            Map<String, String> paramsMap = new HashMap<>();
//            //模拟发布失败，采用老版本测试。需要注释下面的
//            paramsMap.put("branch", jsonObject.getString("branch"));
//            paramsMap.put("deploy", jsonObject.getString("deploy"));
//            paramsMap.put("version", jsonObject.getString("version"));
//            paramsMap.put("rollback", jsonObject.getString("rollback"));
//
//            try {
//                Callable<Map<String, Object>> callable = new Callable<Map<String, Object>>() {
//                    @Override
//                    public Map<String, Object> call() throws Exception {
//                        Map<String, Object> mapJob = new HashMap<>();
//                        try {
//                            mapJob = JenkinsUtil.doJob(paramsMap, jobName,"deploy","sit");
//                            mapJob.put("deployStatus", jsonObject.getString("deploy"));
//                            mapJob.put("env", "sit");
//                            mapJob.put("moduleId", jsonObject.getString("moduleId"));
//                            mapJob.put("operation", "deploy");
//                            mapJob.put("pipelineId", jsonObject.getString("pipelineId"));
//                            mapJob.put("version", paramsMap.get("version"));
//                            mapJob.put("visible", "1");
//                            mapJob.put("serverIds", "");
//                            /**
//                             * TODO
//                             * 假如失败了，此时的版本名称根本就获取不到。
//                             *
//                             * 需要一个错误案列来测试代码
//                             */
//                            return mapJob;
//                        } catch (Exception e) {
////                            e.printStackTrace();
//                        }
//                        return null;
//                    }
//                };
//                Future<Map<String, Object>> future = fixedThreadPool.submit(callable);
//                taskResults.add(future);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//        for (int i = 0; i < taskResults.size(); i++) {
//
//            try {
//                Future<Map<String, Object>> mapFuture = taskResults.get(i);
//                Map<String, Object> map = mapFuture.get();
//                list.add(map);
//
//                if (!map.isEmpty()&& map!=null) {
//                    UpdateDBAfterJob(map, "deploy","sit");
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        System.out.println("最终花费：" + (new Date().getTime() - startTime) + "  毫秒");
//        fixedThreadPool.shutdown();
//        return list;
//    }
//
//
//    /**
//     * sit入库
//     * @param jsonArray
//     * @return
//     */
//    @Override
//    public List<Map<String, Object>> sitStorage(JSONArray jsonArray) {
//
//        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(jsonArray.size());
//        List<Future<Map<String, Object>>> taskResults = new ArrayList<Future<Map<String, Object>>>();
//        List list = new ArrayList();
//        long startTime = new Date().getTime();
//        for (int i = 0; i < jsonArray.size(); i++) {
//
//            JSONObject jsonObject = jsonArray.getJSONObject(i);
//            String jobName = jsonObject.getString("jobName");
//            Map<String, String> paramsMap = new HashMap<>();
//            paramsMap.put("TagName", jsonObject.getString("sitFutureDeployName"));
//            Map<String, Object> map = null;
//            try {
//
//                Callable<Map<String, Object>> callable = new Callable<Map<String, Object>>() {
//                    @Override
//                    public Map<String, Object> call() throws Exception {
//
//                        Map<String, Object> mapJob = new HashMap<>();
//                        try {
//                            mapJob = JenkinsUtil.doJob(paramsMap, jobName,"storage","sit");
//                            mapJob.put("env", "sit");
//                            mapJob.put("moduleId", jsonObject.getString("moduleId"));
//                            mapJob.put("operation", "storage");
//                            mapJob.put("pipelineId", jsonObject.getString("pipelineId"));
//                            mapJob.put("visible", "1");
//                            mapJob.put("serverIds", "");
//                            mapJob.put("comment", jsonObject.getString("comment"));
////                            mapJob.put("deployName", jsonObject.getString("sitFutureDeployName"));
//                            String deployName = (String) mapJob.get("deployName");
//                            if(!("ErrorName").equals(deployName) && !StringUtils.isEmpty(deployName)){
//                                mapJob.put("deployName", jsonObject.getString("sitFutureDeployName"));
//                            }else{
//                                mapJob.put("deployName",jsonObject.getString("sitFutureDeployName"));
//                            }
//                            /**
//                             * TODO
//                             *
//                             * 制品库此时假如入库失败了，那么保存在制品库的版本还是原先的版本。
//                             *
//                             * 需要通过脚本命令来获取，并且和传入的版本进行比较，最后返回正确的版本
//                             *
//                             * 需要一个错误案列来测试代码
//                             */
//                            return mapJob;
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        return null;
//                    }
//                };
//                Future<Map<String, Object>> future = fixedThreadPool.submit(callable);
//                taskResults.add(future);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//
//        for (int i = 0; i < taskResults.size(); i++) {
//
//            try {
//                Future<Map<String, Object>> mapFuture = taskResults.get(i);
//                Map<String, Object> map = mapFuture.get();
//                list.add(map);
////                long time = new Date().getTime();
////                logger.info("#################线程： " + Thread.currentThread().getName() + " 进行jenkins 发布 " + jobName + " 总耗时为：" + (new Date().getTime() - time));
//                if (!map.isEmpty() && map!=null) {
//                    UpdateDBAfterJob(map, "storage","sit");
//
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        fixedThreadPool.shutdown();
//        System.out.println("最终花费：" + (new Date().getTime() - startTime) + "  毫秒");
//        return list;
//    }
//
//
//    /**
//     * sit回退
//     * @param jsonArray
//     * @return
//     */
//    @Override
//    public List<Map<String, Object>> rollBackSit(JSONArray jsonArray) {
//
//        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(jsonArray.size());
//
//        List list = new ArrayList();
//
//        long startTime = new Date().getTime();
//        List<Future<Map<String, Object>>> taskResults = new ArrayList<Future<Map<String, Object>>>();
//
//        for (int i = 0; i < jsonArray.size(); i++) {
//
//            JSONObject jsonObject = jsonArray.getJSONObject(i);
//            String jobName = jsonObject.getString("jobName");
//            Map<String, String> paramsMap = new HashMap<>();
//            //回退时，branch version 没有任何效果，deploy必须是yes
//
//            paramsMap.put("branch", "test");
//            paramsMap.put("deploy", "yes");
//            paramsMap.put("version", "0.0");
//            paramsMap.put("rollback", jsonObject.getString("rollBackDeployName"));
//
//            try {
//                Callable<Map<String, Object>> callable = new Callable<Map<String, Object>>() {
//                    @Override
//                    public Map<String, Object> call() throws Exception {
//                        Map<String, Object> mapJob = new HashMap<>();
//                        try {
//                            mapJob = JenkinsUtil.doJob(paramsMap, jobName,"rollback","sit");
//                            mapJob.put("deployStatus", "yes");
//                            mapJob.put("env", "sit");
//                            mapJob.put("moduleId", jsonObject.getString("moduleId"));
//                            mapJob.put("operation", "rollback");
//                            mapJob.put("pipelineId", jsonObject.getString("pipelineId"));
//                            mapJob.put("version", paramsMap.get("version"));
//                            mapJob.put("visible", "1");
//                            mapJob.put("serverIds", "");
//                            mapJob.put("comment", jsonObject.getString("comment"));
//                            String deployName = (String) mapJob.get("deployName");
//                            if(!("ErrorName").equals(deployName) && !StringUtils.isEmpty(deployName)){
//                                mapJob.put("deployName", jsonObject.getString("rollBackDeployName"));
//                            }else{
//                                mapJob.put("deployName",jsonObject.getString("sitCurrentDeployName"));
//                            }
//
//                            /**
//                             * TODO
//                             * 需要一个错误案列来测试代码
//                             */
//                            return mapJob;
//                        } catch (Exception e) {
////                            e.printStackTrace();
//                        }
//                        return null;
//                    }
//                };
//                Future<Map<String, Object>> future = fixedThreadPool.submit(callable);
//                taskResults.add(future);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//        for (int i = 0; i < taskResults.size(); i++) {
//            try {
//                Future<Map<String, Object>> mapFuture = taskResults.get(i);
//                Map<String, Object> map = mapFuture.get();
//                list.add(map);
//                if (!map.isEmpty()) {
//                    UpdateDBAfterJob(map, "rollback","sit");
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        System.out.println("最终花费：" + (new Date().getTime() - startTime) + "  毫秒");
//        fixedThreadPool.shutdown();
//        return list;
//
//    }
//
//    /**
//     * UAT 环境下发布
//     *
//     * @param jsonArray
//     * @return
//     */
//    @Override
//    public List<Map<String, Object>> confirmUat(JSONArray jsonArray) {
//
//        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(jsonArray.size());
//
//        List list = new ArrayList();
//
//        long startTime = new Date().getTime();
//        List<Future<Map<String, Object>>> taskResults = new ArrayList<Future<Map<String, Object>>>();
//
//        for (int i = 0; i < jsonArray.size(); i++) {
//
//            JSONObject jsonObject = jsonArray.getJSONObject(i);
//            String jobName = jsonObject.getString("jobName");
//            Map<String, String> paramsMap = new HashMap<>();
//            paramsMap.put("rollback", jsonObject.getString("sitFutureDeployName"));
//
//            try {
//                Callable<Map<String, Object>> callable = new Callable<Map<String, Object>>() {
//                    @Override
//                    public Map<String, Object> call() throws Exception {
//                        Map<String, Object> mapJob = new HashMap<>();
//                        try {
//                            mapJob = JenkinsUtil.doJob(paramsMap, jobName,"deploy","uat");
//                            mapJob.put("env", "uat");
//                            mapJob.put("moduleId", jsonObject.getString("moduleId"));
//                            mapJob.put("operation", "deploy");
//                            mapJob.put("pipelineId", jsonObject.getString("pipelineId"));
//                            mapJob.put("visible", "1");
//                            mapJob.put("serverIds", "");
//                            mapJob.put("comment", jsonObject.getString("comment"));
//                            String deployName = (String) mapJob.get("deployName");
//                            if(!("ErrorName").equals(deployName) && !StringUtils.isEmpty(deployName)){
//                                mapJob.put("deployName", jsonObject.getString("sitFutureDeployName"));
//                            }else{
//                                mapJob.put("deployName",jsonObject.getString("uatCurrentDeployName"));
//                            }
//
//                            /**
//                             * TODO
//                             *
//                             * 需要一个错误案列来测试代码
//                             */
//                            return mapJob;
//                        } catch (Exception e) {
////                            e.printStackTrace();
//                        }
//                        return null;
//                    }
//                };
//                Future<Map<String, Object>> future = fixedThreadPool.submit(callable);
//                taskResults.add(future);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//        for (int i = 0; i < taskResults.size(); i++) {
//
//            try {
//                Future<Map<String, Object>> mapFuture = taskResults.get(i);
//                Map<String, Object> map = mapFuture.get();
//                list.add(map);
//
//                if (!map.isEmpty()  && map!=null) {
//                    UpdateDBAfterJob(map, "deploy","uat");
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        System.out.println("最终花费：" + (new Date().getTime() - startTime) + "  毫秒");
//        fixedThreadPool.shutdown();
//        return list;
//    }
//
//
//    /**
//     * Job执行完毕后，开始更改数据库中数据
//     *
//     * @param map
//     * @param operation 代表了是发布/入库/回退
//     */
//    private void UpdateDBAfterJob(Map<String, Object> map, String operation,String env) {
//
//        //插入到deploy表
//        Deploy deploy = JSON.parseObject(JSON.toJSONString(map), new TypeReference<Deploy>() {
//        });
//        Wrapper<Deploy> eq = new EntityWrapper<Deploy>().eq("pipelineId", deploy.getPipelineId())
//                .eq("moduleId", deploy.getModuleId())
//                .eq("jenkinsJob", 1).orderBy("id",false);
//        Deploy deployOne = deployService.selectOne(eq);
//        deploy.setId(deployOne.getId());
//        deploy.setJenkinsJob(2);
//
//        deployService.updateById(deploy);
////        deployService.insert(deploy);
//
//        //更新pipeline_release_module表数据
//        long pipelineId = deploy.getPipelineId();
//        Integer moduleId = deploy.getModuleId().intValue();
//        ModuleConfig moduleConfig = moduleConfigService.selectOne(new EntityWrapper<ModuleConfig>().eq("id", moduleId));
//        String product = "";
//        if (moduleConfig != null) {
//            product = moduleConfig.getProduct();
//        }
//        //这里会把以前的数据都覆盖掉。
//        Wrapper<PipelineReleaseModule> releaseModuleWrapper = new EntityWrapper<PipelineReleaseModule>().eq("pipelineId", pipelineId).eq("moduleId", moduleId.longValue());
//        List<PipelineReleaseModule> modules = pipelineReleaseModuleService.selectList(releaseModuleWrapper);
//
//
//        if (modules.size() > 0) {
//            List<PipelineReleaseModule> data = new ArrayList<>();
//            for (int j = 0; j < modules.size(); j++) {
//                PipelineReleaseModule module = modules.get(j);
//                //入库操作，就是更改制品库id  qualityDeployId
//                if (operation.equals("storage")) {
//                    module.setQualityDeployId(deploy.getId().intValue()); // 从上面获取,部署ID此时就是入制品库的ID
//                }
//                if (operation.equals("deploy")) {
//
//                    if(env.equals("uat")){
//                        module.setUatDeployId(deploy.getId().intValue());
//                        module.setUatStatus(deploy.getDeployResult());
//                    }
//                    if(env.equals("sit")){
//                        // 如果是deploy 就是更改sitDeployID
//                        module.setSitDeployId(deploy.getId().intValue());// 从上面获取
//                        module.setSitStatus(deploy.getDeployResult());
//                    }
//
//                }
//                if (operation.equals("rollback")) {
//                    // 如果是rollback 就是更改SitRollBackId
//                    module.setSitRollBackId(deploy.getId().intValue());
//                }
//                module.setVisible(1);
//                module.setCreatedTime(new Date());
//                module.setProduct(product);
//                module.setPipelineId(pipelineId);
//                module.setModuleId(moduleId.longValue());
//                data.add(module);
//            }
//            pipelineReleaseModuleService.updateBatchById(data);
//        } else {
//            PipelineReleaseModule module = new PipelineReleaseModule();
//
//            if (operation.equals("storage")) {
//                //入库操作，就是更改制品库id  qualityDeployId
//                module.setQualityDeployId(deploy.getId().intValue()); // 从上面获取,部署ID此时就是入制品库的ID
//            }
//            if (operation.equals("deploy")) {
//                // 如果是deploy 就是更改sitDeployID
//                module.setSitDeployId(deploy.getId().intValue());
//            }
//            if (operation.equals("rollback")) {
//                // 如果是rollback 就是更改SitRollBackId
//                module.setSitRollBackId(deploy.getId().intValue());
//            }
//            module.setSitStatus(deploy.getDeployResult());
//            module.setVisible(1);
//            module.setCreatedTime(new Date());
//            module.setProduct(product);
//            module.setPipelineId(pipelineId);
//            module.setModuleId(moduleId.longValue());
//            pipelineReleaseModuleService.insert(module);
//        }
//    }
}

