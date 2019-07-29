package com.cjs.biz;

import com.cjs.bean.Role;
import com.cjs.bean.User;
import com.cjs.util.pager.PageModel;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface UserBiz {
    Map<String, Object> login(Map<String, Object> params);

    void updateSelf(User user, HttpSession session);

    void updateUser(User user);

    List<User> getUsersByPage(User user, PageModel pageModel);

    void deleteUserByUserIds(String ids);

    String isUserValidAjax(String userId);

    void addUser(User user);

    void activeUser(User user);

    List<User> selectRoleUser(Role role, PageModel pageModel);

    List<User> selectNotRoleUser(Role role, PageModel pageModel);

    void bindUser(Role role, String ids);

    void unBindUser(Role role, String ids);

    User getUserById(String userId);

}
