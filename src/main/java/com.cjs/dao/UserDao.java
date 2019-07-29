package com.cjs.dao;

import com.cjs.bean.User;
import com.cjs.util.pager.PageModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface UserDao  {
    List<String> getRolesUsers(@Param("id") int id);

    List<String> findRoleUsers(@Param("id") int id);

    User getUserById(String userId);

    void updateUser(@Param("user") User user);

    int countByUser(@Param("user") User user);

    int countByUserIds(@Param("list") List params);

    int countNotByUserIds(@Param("list") List params);

    List<User> selectUsersByPage(@Param("user") User user, @Param("pageModel")PageModel pageModel);

    List<User> findUsersByPage(@Param("list") List userIds, @Param("pageModel") PageModel pageModel);

    List<User> findNotUsersByPage(@Param("list") List userIds, @Param("pageModel") PageModel pageModel);

    void deleteUserBatch(List<String> ids);

    void addUser(User user);

    void addRoleUser(@Param("roleId") int roleId, @Param("list") List userIds);

    void removeRoleUser(@Param("roleId") int roleId, @Param("list") List userIds);
}
