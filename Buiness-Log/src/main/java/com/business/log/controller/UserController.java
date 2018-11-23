package com.business.log.controller;


import com.alibaba.fastjson.JSONObject;
import com.business.log.aop.BuinessLog;
import com.business.log.config.IConstants;
import com.business.log.config.ResponseTip;
import com.business.log.pojo.User;
import com.business.log.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class UserController {


    @Autowired
    UserService userService;

    @BuinessLog(name = "查询数据")
    @RequestMapping(method = RequestMethod.GET, value = "/user")
    public String getUser(@RequestParam long id) {

        ResponseTip tip = new ResponseTip();

        User userById = userService.findUserById(id);

        tip.setData(userById);
        tip.setStatus(IConstants.RESULT_INT_SUCCESS);
        tip.setMessage("查询成功");

        return JSONObject.toJSONString(tip);

    }

    @BuinessLog(name = "新增数据")
    @RequestMapping(method = RequestMethod.POST, value = "/user")
    public String addUser(@RequestBody User user) {

        ResponseTip tip = new ResponseTip();
        userService.addUser(user);
        tip.setStatus(IConstants.RESULT_INT_SUCCESS);
        tip.setMessage("新增成功");

        return JSONObject.toJSONString(tip);
    }

    @BuinessLog(name = "更新数据")
    @RequestMapping(method = RequestMethod.PUT, value = "/user")
    public String updateUser(@RequestBody User user) {

        User oldUser = userService.findUserById(user.getId());


        ResponseTip tip = new ResponseTip();
        if (userService.updateUser(user)) {
            tip.setStatus(IConstants.RESULT_INT_SUCCESS);
            tip.setMessage("更新成功");
        } else {
            tip.setStatus(IConstants.RESULT_INT_ERROR);
            tip.setMessage("更新失败");
        }
        return JSONObject.toJSONString(tip);
    }

    @BuinessLog(name = "删除数据")
    @RequestMapping(method = RequestMethod.DELETE, value = "/user")
    public String delUser(@RequestParam long id) {
        ResponseTip tip = new ResponseTip();
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
