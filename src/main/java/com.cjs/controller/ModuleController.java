package com.cjs.controller;

import java.util.*;

import com.cjs.bean.Module;
import com.cjs.biz.ModuleBiz;
import com.cjs.util.pager.PageModel;
import com.cjs.vo.TreeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/identity/module")
public class ModuleController {
    private final ModuleBiz moduleBiz;

    @Autowired
    public ModuleController(ModuleBiz moduleBiz) {
        this.moduleBiz = moduleBiz;
    }

    @RequestMapping("/mgrModule")
    public String mgrModule(){
        return "identity/module/mgrModule";
    }

    @ResponseBody
    @RequestMapping(value = "/loadAllModuleTrees",produces = "application/json; charset=UTF-8")
    public List<TreeData> loadAllModuleTrees() {
        try {
            return moduleBiz.loadAllModuleTress();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("/getModulesByParent")
    public String getModulesByParent(String parentCode, PageModel pageModel, Model model) {
        try {
            List<Module> sonModules = moduleBiz.getModulesByParent(parentCode, pageModel);
            model.addAttribute("modules", sonModules);
            model.addAttribute("parentCode", parentCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "identity/module/module";
    }

    @RequestMapping("/deleteModules")
    public String deleteModules(String ids, Model model) {
        try {
            moduleBiz.deleteModules(ids);
            model.addAttribute("tip", "删除成功");
        } catch (Exception e) {
            model.addAttribute("tip", "删除失败");
            e.printStackTrace();
        }
        return "forward:/identity/module/getModulesByParent";
    }

    @RequestMapping("/addModule")
    public String addModule(String parentCode, Model model, Module module) {
        try {
            moduleBiz.addModule(parentCode,module);
            model.addAttribute("parentCode", parentCode);
            model.addAttribute("tip", "添加成功");
        } catch (Exception e) {
            model.addAttribute("tip", "添加失败");
            e.printStackTrace();
        }
        return "identity/module/addModule";
    }

    @RequestMapping("/updateModule")
    public String updateModule(Module module, Model model) {
        try {
            moduleBiz.updateModule(module);
            model.addAttribute("tip", "修改成功");
        } catch (Exception e) {
            model.addAttribute("tip", "修改失败");
            e.printStackTrace();
        }
        return "identity/module/updateModule";
    }

    @RequestMapping("/showAddModule")
    public String showAddModule(String parentCode, Model model) {
        model.addAttribute("parentCode", parentCode);
        return "identity/module/addModule";
    }

    @RequestMapping("/showUpdateModule")
    public String showUpdateModule(Module module, Model model) {
        try {
            module = moduleBiz.getModuleByCode(module.getCode());
            model.addAttribute("module", module);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "identity/module/updateModule";
    }
}

