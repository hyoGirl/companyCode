package com.mybatis.log.controller;

import com.alibaba.fastjson.JSONObject;
import com.mybatis.log.config.IConstants;
import com.mybatis.log.config.ResponseTip;
import com.mybatis.log.pojo.User;
import com.mybatis.log.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class UserController {


    @Autowired
    UserService userService;

    @ApiOperation(value = "getUser", notes = "管理员接口获取用户")
    @ApiImplicitParam(paramType="query",name="id",dataType="long",required=true, value="用户ID" )
    @RequestMapping(method = RequestMethod.GET, value = "/user")
    public String getUser(@RequestParam long id){

        ResponseTip tip=new ResponseTip();

        User userById = userService.findUserById(id);

        tip.setData(userById);
        tip.setStatus(IConstants.RESULT_INT_SUCCESS);
        tip.setMessage("查询成功");

        return JSONObject.toJSONString(tip);

    }


    @ApiOperation(value = "addUser", notes = "管理员接口新增用户")
    @RequestMapping(method = RequestMethod.POST, value = "/user")
    public String addUser(@RequestBody User user) {
        ResponseTip tip=new ResponseTip();
        userService.addUser(user);
        tip.setStatus(IConstants.RESULT_INT_SUCCESS);
        tip.setMessage("新增成功");
        return JSONObject.toJSONString(tip);
    }


    @ApiOperation(value = "updateUser", notes = "管理员接口更新用户")
    @RequestMapping(method = RequestMethod.PUT, value = "/user")
    public String updateUser(@RequestBody User user) {
        ResponseTip tip=new ResponseTip();


        ;
        if (userService.updateUser(user)) {
            tip.setStatus(IConstants.RESULT_INT_SUCCESS);
            tip.setMessage("更新成功");
        } else {
            tip.setStatus(IConstants.RESULT_INT_ERROR);
            tip.setMessage("更新失败");
        }
        return JSONObject.toJSONString(tip);
    }

    @ApiOperation(value = "delUser", notes = "管理员接口删除用户")
    @RequestMapping(method = RequestMethod.DELETE, value = "/user")
    @ApiImplicitParam(paramType="query",name="id",dataType="long",required=true, value="用户ID" )
    public String delUser(@RequestParam long id) {
        ResponseTip tip=new ResponseTip();
        if (userService.deleteUserByID(id)) {
            tip.setStatus(IConstants.RESULT_INT_SUCCESS);
            tip.setMessage("删除成功");
        } else {
            tip.setStatus(IConstants.RESULT_INT_ERROR);
            tip.setMessage("删除失败");
        }
        return JSONObject.toJSONString(tip);
    }

}
