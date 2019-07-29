package com.cjs.biz.impl;

import com.cjs.bean.Module;
import com.cjs.biz.PopedomBiz;
import com.cjs.biz.UserModelBiz;
import com.cjs.dao.ModuleDao;
import com.cjs.dao.PopeDomDao;
import com.cjs.dto.UserModule;
import com.cjs.util.OaContants;
import com.cjs.util.OaException;
import com.cjs.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserModuleBizImpl implements UserModelBiz {
    private final PopeDomDao popeDomDao;
    private final ModuleDao moduleDao;

    @Autowired
    public UserModuleBizImpl(ModuleDao moduleDao, PopeDomDao popeDomDao) {
        this.moduleDao = moduleDao;
        this.popeDomDao = popeDomDao;
    }

    @Override
    public List<UserModule> getUserPopedomModules() {
        try {
            List<String> popedomModuleCodes = popeDomDao.getUserPopeDomModuleCodes(UserHolder.getCurrentUser().getUserId());
            if (popedomModuleCodes != null && popedomModuleCodes.size() > 0) {
//                Map<Module, List<Module>> userModuleMap = new LinkedHashMap<>();
                Map<Module, List<Module>> userModuleMap = new LinkedHashMap<>();
                Map<String, List<Module>> userModuleStringMap = new LinkedHashMap<>();

                Module firstModule = null;
                List<Module> secondModules = null;
                for (String moduleCode : popedomModuleCodes) {
                    //截取当前模块的第一级模块编号
                    String firstCode = moduleCode.substring(0, OaContants.CODE_LEN);
                    //查询第一级模块对象
                    firstModule = moduleDao.getModuleByCode(firstCode);
                    firstModule.setName(firstModule.getName().replaceAll("-", ""));

//                    if (!userModuleMap.containsKey(firstModule)) {
                    if (!userModuleStringMap.containsKey(firstModule.getName())) {
                        secondModules = new ArrayList<>();
                        userModuleMap.put(firstModule, secondModules);
                        userModuleStringMap.put(firstModule.getName(), secondModules);
                    }

                    Module secondModule = moduleDao.getModuleByCode(moduleCode);
                    secondModule.setName(secondModule.getName().replaceAll("-", ""));
                    secondModules.add(secondModule);
                }

                List<UserModule> userModules = new ArrayList<>();

                userModuleMap.forEach((module, modules) -> {
                    UserModule userModule = new UserModule();
                    userModule.setFirstModule(module);
                    userModule.setSecondModules(modules);
                    userModules.add(userModule);
                });

                return userModules;
            }
            return null;
        } catch (Exception e) {
            throw new OaException("查询当前用户的权限模块", e);
        }
    }
}
