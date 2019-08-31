package com.yun.springboot_swagger_demo.controller;


import com.yun.springboot_swagger_demo.bean.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@Api(value = "用户模块",description = "用户接口上的信息")//用在类上 说明该类的作用
public class sayController {
    //模拟数据库
    public static List<User> users=new ArrayList<>();
    static {
        users.add(new User("张三","123") );
        users.add(new User("李四","123"));
    }

    //获取用户列表的方法
    @ApiOperation(value = "获取用户列表",notes = "获取所有用户的详细信息")//方法描述
    @GetMapping("/users")
    public Object users(){
        Map<String,Object> map=new HashMap<>();
        map.put("users",users);
        return map;
    }

    @ApiOperation(value = "获取单个用户",notes = "根据id获取用户信息")
    @ApiImplicitParam(value = "用户id",paramType = "path")//具体参数说明
    @GetMapping("user/{id}")
    public User getUserById(@PathVariable("id")int id){
        return users.get(id);
    }

    @ApiOperation(value = "添加用户",notes = "根据传入的用户信息添加")
    @ApiImplicitParam(value = "用户对象",paramType ="query")
    @PostMapping("/user")
    public Object add(User user){
        return users.add(user);
    }




}
