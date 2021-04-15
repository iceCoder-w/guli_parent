package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author 王冰炜
 * @create 2021-04-09 16:26
 */

@Api(tags = "登录退出")
@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin //解决跨域
public class EduLoginController {
    //login
    @ApiOperation(value = "登录")
    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }

    //info
    @ApiOperation(value = "获取用户信息")
    @GetMapping("info")
    public R info(){
        return R.ok()
                .data("roles","admin")
                .data("name","admin")
                .data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif")
                .data("introduction","This is admin!");
    }

    //logout
    @ApiOperation(value = "退出")
    @PostMapping("logout")
    public R logout(){
        return R.ok()
                .data("token","admin")
                .data("roles","admin");
    }

}
