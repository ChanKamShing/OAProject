package com.cjs.controller;

import com.cjs.bean.User;
import com.cjs.biz.UserBiz;
import java.util.*;
import com.cjs.util.OaContants;
import com.cjs.util.pager.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/identity/user")
public class UserController {
    private final UserBiz userBiz;

    @Autowired
    public UserController(UserBiz userBiz) {
        this.userBiz = userBiz;
    }

    @RequestMapping("/updateSelf")
    public String updateSelf(User user, Model model, HttpSession session) {
        try {
            userBiz.updateSelf(user, session);
            session.setAttribute(OaContants.USER_SESSION, userBiz.getUserById(user.getUserId()));
            model.addAttribute("tip", "修改成功");
        } catch (Exception e) {
            model.addAttribute("tip", "修改失败");
            e.printStackTrace();
        }
        return "home";
    }

    @RequestMapping("/selectUser")
    public String selectUser(User user, HttpServletRequest request, PageModel pageModel, Model model) {
        try {
            if (request.getMethod().toLowerCase().contains("get")) {
                if (user != null && !StringUtils.isEmpty(user.getName())) {
                    String name = user.getName();
                    name = new String(name.getBytes("ISO-8859-1"), "UTF-8");
                    user.setName(name);
                }

                List<User> users = userBiz.getUsersByPage(user, pageModel);
                model.addAttribute("users", users);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "identity/user/user";
    }

    @RequestMapping("/deleteUser")
    public String deleteUser(String ids, Model model) {
        try {
            userBiz.deleteUserByUserIds(ids);
            model.addAttribute("tip", "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("tip", "删除失败");
        }
        return "forward:/identity/user/selectUser";
    }

    @RequestMapping("/showAddUser")
    public String showAddUser() {
        return "identity/user/addUser";
    }

    @ResponseBody
    @RequestMapping("/isUserValidAjax")
    public String isUserValid(String userId) {
        try {
            return userBiz.isUserValidAjax(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("addUser")
    public String addUser(User user, Model model) {
        try {
            userBiz.addUser(user);
            model.addAttribute("tip", "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("tip", "添加失败");
        }
        return "identity/user/addUser";
    }

    @RequestMapping("/updateUser")
    public String updateUser(User user, Model model) {
        try {
            userBiz.updateUser(user);
            model.addAttribute("tip", "更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("tip", "更新失败");
        }
        return "identity/user/updateUser";
    }

    @RequestMapping("/activeUser")
    public String activeUser(User user, Model model) {
        try {
            userBiz.activeUser(user);
            model.addAttribute("tip", user.getStatus()==1?"激活成功":"冻结成功");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("tip", user.getStatus() == 1 ? "激活失败" : "冻结失败");
        }
        return "forward:/identity/user/selectUser";
    }

    @RequestMapping("/showPreUser")
    public String showPreUser(String userId, Model model) {
        try {
            User user = userBiz.getUserById(userId);
            model.addAttribute("user", user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "identity/user/preUser";
    }

    @RequestMapping("showUpdateUser")
    public String showUpdateUser(String userId, Model model) {
        try {
            User user = userBiz.getUserById(userId);
            model.addAttribute("user", user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "identity/user/updateUser";
    }
}
