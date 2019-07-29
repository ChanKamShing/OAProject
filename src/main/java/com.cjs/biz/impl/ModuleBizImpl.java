package com.cjs.biz.impl;

import com.cjs.bean.Module;
import com.cjs.biz.ModuleBiz;
import com.cjs.dao.ModuleDao;
import com.cjs.util.OaContants;
import com.cjs.util.OaException;
import com.cjs.util.UserHolder;
import com.cjs.util.pager.PageModel;
import com.cjs.vo.TreeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class ModuleBizImpl implements ModuleBiz {
    private final ModuleDao moduleDao;

    @Autowired
    public ModuleBizImpl(ModuleDao moduleDao) {
        this.moduleDao = moduleDao;
    }

    @Override
    public List<TreeData> loadAllModuleTress() {
        try {
            //查询所有模块信息
            List<Module> modules = moduleDao.findAllModules();
            //拼装成dtree需要的树节点
            List<TreeData> treeDatas = new ArrayList<>();

            for (Module m : modules) {
                TreeData treeData = new TreeData();
                treeData.setId(m.getCode());
                treeData.setName(m.getName());
                String pid = m.getCode().length() == OaContants.CODE_LEN ? "0" : m.getCode().substring(0, m.getCode().length() - OaContants.CODE_LEN);
                treeData.setPid(pid);
                treeDatas.add(treeData);
            }
            return treeDatas;
        } catch (Exception e) {
            throw new OaException("加载模块树异常", e);
        }
    }

    @Override
    public List<Module> getModulesByParent(String parentCode, PageModel pageModel) {
        try {
            parentCode = parentCode==null?"":parentCode;
            int sonLength = parentCode.length()+OaContants.CODE_LEN;
            parentCode += "%";
            Map<String, Object> param = new HashMap<>();
            param.put("parentCode", parentCode);
            param.put("sonLength", sonLength);

            int recordCount = moduleDao.countModules(parentCode, sonLength);
            pageModel.setRecordCount(recordCount);

            List<Module> modules = moduleDao.findModulesByPage(param, pageModel);
            return modules;
        } catch (Exception e) {
            throw new OaException("查询子模块异常", e);
        }
    }

    @Override
    public List<Module> getModulesByParent(String parentCode) {
        try {
            parentCode = parentCode==null?"":parentCode + "%";
            int sonLength = parentCode.length()+OaContants.CODE_LEN;
            List<Module> modules = moduleDao.findModules(parentCode, sonLength);
            return modules;
        } catch (Exception e) {
            throw new OaException("查询子模块异常", e);
        }
    }

    @Transactional
    @Override
    public void deleteModules(String ids) {
        try {
            List<String> codes = new ArrayList<>();
            Collections.addAll(codes, ids.split(","));
            moduleDao.setCode(codes);
        } catch (Exception e) {
            throw new OaException("批量删除订单失败", e);
        }
    }

    @Override
    public String getNextSonCode(String parentCode, int codeLen) throws Exception{
        parentCode = (parentCode == null ? "" : parentCode) + "%";
        String maxSonCode = moduleDao.findUniqueEntity(parentCode, parentCode.length() + codeLen);
        System.out.println("当前最大子节点编号是：maxSonCode-------->" + maxSonCode);

        //保存最终的下一个子节点编号
        String nextSonCode = "";

        if (StringUtils.isEmpty(maxSonCode)) {
            String preSuffix = "";
            for (int i = 0; i < codeLen - 1; i++) {
                preSuffix += "0";
            }
            nextSonCode = parentCode + preSuffix + 1;
        } else {
            //子节点存在，截取当前子节点的步长
            String currentMaxCode = maxSonCode.substring(parentCode.length());
            int maxSonCodeInt = Integer.valueOf(currentMaxCode);
            maxSonCodeInt++;
            if ((maxSonCodeInt + "").length() > codeLen) {
                throw new OaException("编号越界");
            } else {
                String preSuffix = "";
                for (int i = 0; i < codeLen - (maxSonCodeInt + "").length(); i++) {
                    preSuffix += "0";
                }

                nextSonCode = parentCode+preSuffix+maxSonCodeInt;
            }
        }

        return nextSonCode;
    }

    @Transactional
    @Override
    public void addModule(String parentCode, Module module) {
        try {
            module.setCode(getNextSonCode(parentCode, OaContants.CODE_LEN));
            module.setCreateDate(new Date());
            module.setCreater(UserHolder.getCurrentUser());
            moduleDao.addModule(module);
        } catch (Exception e) {
            throw new OaException("添加子菜单失败", e);
        }
    }

    @Transactional
    @Override
    public void updateModule(Module module) {
        try {
            Module sessionModule = moduleDao.findOneByCode(module.getCode());
            sessionModule.setModifier(UserHolder.getCurrentUser());
            sessionModule.setModifyDate(new Date());
            sessionModule.setName(module.getName());
            sessionModule.setRemark(module.getRemark());
            sessionModule.setUrl(module.getUrl());
            moduleDao.updateModule(sessionModule);
        } catch (Exception e) {
            throw new OaException("修改模块失败", e);
        }
    }

    @Override
    public Module getModuleByCode(String code) {
        try {
            return moduleDao.getModuleByCode(code);
        } catch (Exception e) {
            throw new OaException("获取模块失败",e);
        }
    }

}
