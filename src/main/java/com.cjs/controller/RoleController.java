package com.cjs.controller;

import com.cjs.bean.Role;
import com.cjs.bean.User;
import com.cjs.biz.RoleBiz;
import java.util.*;

import com.cjs.biz.UserBiz;
import com.cjs.util.pager.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/identiry/role")
public class RoleController {
    private final UserBiz userBiz;
    private final RoleBiz roleBiz;

    @Autowired
    public RoleController(RoleBiz roleBiz, UserBiz userBiz) {
        this.roleBiz = roleBiz;
        this.userBiz = userBiz;
    }

    @RequestMapping("/selectRole")
    public String selectRole(PageModel pageModel, Model model) {
        try {
            List<Role> roles = roleBiz.getRoleByPage(pageModel);
            model.addAttribute("roles", roles);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "identity/role/role";
    }

    @RequestMapping("/addRole")
    public String addRole(Role role, Model model) {
        try {
            roleBiz.addRole(role);
            model.addAttribute("tip", "添加成功");
        } catch (Exception e) {
            model.addAttribute("tip", "添加失败");
            e.printStackTrace();
        }
        return "identity/role/addRole";
    }

    @RequestMapping("/deleteRole")
    public String deleteRole(String ids, Model model){
        try {
            roleBiz.deleteRoles(ids);
            model.addAttribute("tip", "删除成功");
        } catch (Exception e) {
            model.addAttribute("tip", "删除失败");
            e.printStackTrace();
        }
        return "forward:/identity/role/selectRole";
    }

    @RequestMapping("/showAddRole")
    public String showAddRole() {
        return "identity/role/addRole";
    }

    @RequestMapping("/showUpdateRole")
    public String showUpdateRole(Role role, Model model) {
        try {
            role = roleBiz.getRoleById(role.getId());
            model.addAttribute("role", role);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "identity/role/updateRole";
    }

    @RequestMapping("/selectRoleUser")
    public String selectRoleUser(Role role, PageModel pageModel, Model model) {
        try {
            List<User> users = userBiz.selectRoleUser(role, pageModel);
            role = roleBiz.getRoleById(role.getId());
            model.addAttribute("users", users);
            model.addAttribute("role", role);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "identity/role/bindUser/roleUser";
    }

    @RequestMapping("/showBindUser")
    public String selectNotRoleUser(Role role, PageModel pageModel, Model model) {
        try {
            List<User> users = userBiz.selectNotRoleUser(role, pageModel);
            role = roleBiz.getRoleById(role.getId());
            model.addAttribute("users", users);
            model.addAttribute("role", role);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "identity/role/bindUser/bindUser";
    }

    @RequestMapping("bindUser")
    public String bindUser(Role role, String ids, Model model) {
        try {
            userBiz.bindUser(role, ids);
            model.addAttribute("tip", "绑定成功");
        } catch (Exception e) {
            model.addAttribute("tip", "绑定失败");
            e.printStackTrace();
        }
        return "forward:/identity/role/showBindUser";
    }

    @RequestMapping("/unBindUser")
    public String unBindUser(Role role, String ids, Model model) {
        try {
            userBiz.unBindUser(role,ids);
            model.addAttribute("tip", "解绑成功");
        } catch (Exception e) {
            model.addAttribute("tip", "解绑失败");
            e.printStackTrace();
        }
        return "forward:/identity/role/selectRoleUser";
    }
}
