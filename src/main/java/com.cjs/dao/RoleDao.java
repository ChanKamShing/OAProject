package com.cjs.dao;


import com.cjs.bean.Role;
import com.cjs.util.pager.PageModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

@Mapper
@Repository
public interface RoleDao {
    List<Role> selectRolesByUserId(@Param("userId") String userId);

    List<Role> findRolesByPage(@Param("pageModel") PageModel pageModel);

    int countAllRoles();

    void addRole(@Param("role") Role role);

    void deleteRolesBtach(@Param("list") List params);

    Role getRoleById(@Param("id") int id);

    void updateRole(@Param("role") Role role);
}
