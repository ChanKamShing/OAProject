package com.cjs.controller;

import com.cjs.biz.UserModelBiz;
import com.cjs.dto.UserModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.*;

@Controller
@RequestMapping("/oa")
public class RequestController {
    private final UserModelBiz userModelBiz;

    @Autowired
    public RequestController(UserModelBiz userModelBiz) {
        this.userModelBiz = userModelBiz;
    }

    @RequestMapping("/login")
    public String requestLogin() {
        System.out.println("进入登录界面!");
        return "login";
    }

    @RequestMapping("/main")
    public String requestMain(Model model) {
        System.out.println("进入Controller的/oa/main方法");
        try {
            //获取当前用户拥有的权限
            List<UserModule> userModules = userModelBiz.getUserPopedomModules();
            model.addAttribute("userPopedomModules", userModules);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "main";
    }

    @RequestMapping("/home")
    public String requestHome() {
        return "home";
    }
}
