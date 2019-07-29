package com.cjs.biz.impl;

import com.cjs.bean.Role;
import com.cjs.biz.RoleBiz;
import com.cjs.dao.RoleDao;
import com.cjs.util.OaException;
import com.cjs.util.UserHolder;
import com.cjs.util.pager.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class RoleBizImpl implements RoleBiz {
    private final RoleDao roleDao;

    @Autowired
    public RoleBizImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public List<Role> getRoleByPage(PageModel pageModel) {
        try {
            pageModel.setRecordCount(roleDao.countAllRoles());
            List<Role> roles = roleDao.findRolesByPage(pageModel);
            return roles;
        } catch (Exception e) {
            throw new OaException("查询角色异常", e);
        }
    }

    @Transactional
    @Override
    public void addRole(Role role) {
        try {
            role.setCreateDate(new Date());
            role.setCreater(UserHolder.getCurrentUser());
            roleDao.addRole(role);
        } catch (Exception e) {
            throw new OaException("添加角色异常", e);
        }
    }

    @Transactional
    @Override
    public void deleteRoles(String ids) {
        try {
            List<Integer> params = new ArrayList<Integer>();
            for (String id : ids.split(",")) {
                params.add(Integer.valueOf(id));
            }
            roleDao.deleteRolesBtach(params);
        } catch (Exception e) {
            throw new OaException("批量删除角色异常", e);
        }
    }

    @Override
    public Role getRoleById(int id) {
        try {
            return roleDao.getRoleById(id);
        } catch (Exception e) {
            throw new OaException("根据id查询角色异常", e);
        }
    }

    @Transactional
    @Override
    public void updateRole(Role role) {
        try {
            role.setModifier(UserHolder.getCurrentUser());
            role.setModifyDate(new Date());
            roleDao.updateRole(role);
        } catch (Exception e) {
            throw new OaException("根据id更新角色失败", e);
        }
    }
}
