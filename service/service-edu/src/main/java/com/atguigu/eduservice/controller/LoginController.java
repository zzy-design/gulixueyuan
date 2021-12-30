package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@Api(description = "管理员登录", tags = {"课堂管理"})
@RestController
@RequestMapping("/eduService/user")
//@CrossOrigin
public class LoginController {

    @PostMapping("/login")
    public R login() {
        return R.ok().data("token", "admin");
    }

    @GetMapping("/info")
    public R info() {
        return R.ok()
                .data("roles", "管理员")
                .data("name", "张智洋")
                .data("avatar", "https://ww2.sinaimg.cn/bmiddle/007X1oDJgy1gvai682pmvj61bk180gth02.jpg");
    }

}

