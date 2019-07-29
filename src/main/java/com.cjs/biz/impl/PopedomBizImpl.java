package com.cjs.biz.impl;

import com.cjs.bean.Module;
import com.cjs.bean.Popedom;
import com.cjs.bean.Role;
import com.cjs.biz.PopedomBiz;
import com.cjs.dao.ModuleDao;
import com.cjs.dao.PopeDomDao;
import com.cjs.util.OaException;
import com.cjs.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PopedomBizImpl implements PopedomBiz {
    private final ModuleDao moduleDao;
    private final PopeDomDao popeDomDao;

    @Autowired
    public PopedomBizImpl(PopeDomDao popeDomDao, ModuleDao moduleDao) {
        this.popeDomDao = popeDomDao;
        this.moduleDao = moduleDao;
    }

    @Override
    public List<String> getRoleModuleOperaCodes(Role role, String parentCode) {
        try {
            List<String> roleModuleOperasCodes = popeDomDao.findByIdAndParentCode(role.getId(), parentCode);
            return roleModuleOperasCodes;
        } catch (Exception e) {
            throw new OaException("查询当前角色在当前模块所拥有的操作权限编号异常", e);
        }
    }

    @Transactional
    @Override
    public void bindPopedom(String codes, Role role, String parentCode) {
        try {
            popeDomDao.setByIdAndParentCode(role.getId(),parentCode);
            if (!StringUtils.isEmpty(codes)) {
                List<Popedom> popedoms = new ArrayList<>();
                Module parent = moduleDao.getModuleByCode(parentCode);
                for (String code : codes.split(",")) {
                    Popedom popedom = new Popedom();
                    popedom.setRole(role);
                    popedom.setModule(parent);
                    popedom.setOpera(moduleDao.getModuleByCode(code));
                    popedom.setCreateDate(new Date());
                    popedom.setCreater(UserHolder.getCurrentUser());
                    popedoms.add(popedom);
                }
                popeDomDao.addPopedomBatch(popedoms);
            }
        } catch (Exception e) {
            throw new OaException("给角色绑定某个模块的操作权限异常", e);
        }
    }
}
