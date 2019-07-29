package com.cjs.biz;

import com.cjs.bean.Role;
import com.cjs.util.pager.PageModel;
import java.util.*;

public interface RoleBiz {
    List<Role> getRoleByPage(PageModel pageModel);

    void addRole(Role role);

    void deleteRoles(String ids);

    Role getRoleById(int id);

    void updateRole(Role role);
}
