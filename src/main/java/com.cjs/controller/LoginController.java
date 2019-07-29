package com.cjs.controller;

import com.cjs.biz.UserBiz;
import com.cjs.biz.impl.UserBizImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    private final UserBiz userBiz;

    @Autowired
    public LoginController(UserBiz userBiz) {
        this.userBiz = userBiz;
    }

    @ResponseBody
    @RequestMapping(value = "/loginAjax", produces = "application/json; charset=UTF-8")
    public Map<String, Object> login(@RequestParam("userId") String userId,
                                     @RequestParam("password") String password,
                                     @RequestParam("vcode") String vcode,
                                     HttpSession session) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("userId", userId);
            params.put("password", password);
            params.put("vcode", vcode);
            params.put("session", session);
            Map<String, Object> result = userBiz.login(params);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
