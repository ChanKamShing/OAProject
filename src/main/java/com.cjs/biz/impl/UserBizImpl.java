package com.cjs.biz.impl;

import com.cjs.bean.Role;
import com.cjs.bean.User;
import com.cjs.biz.UserBiz;
import com.cjs.dao.ModuleDao;
import com.cjs.dao.PopeDomDao;
import com.cjs.dao.RoleDao;
import com.cjs.dao.UserDao;
import com.cjs.util.CommonContains;
import com.cjs.util.OaContants;
import com.cjs.util.OaException;
import com.cjs.util.UserHolder;
import com.cjs.util.pager.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class UserBizImpl implements UserBiz {
    private final ModuleDao moduleDao;
    private final PopeDomDao popeDomDao;
    private final RoleDao roleDao;

    private final UserDao userDao;

    @Autowired
    public UserBizImpl(UserDao userDao, PopeDomDao popeDomDao, ModuleDao moduleDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.popeDomDao = popeDomDao;
        this.moduleDao = moduleDao;
        this.roleDao = roleDao;
    }

    @Override
    public Map<String, Object> login(Map<String, Object> param) {
        Map<String,Object> result = new HashMap<>();
        try {
            String userId = (String) param.get("userId");//登录名
            String password = (String) param.get("password");//登录密码
            String vcode = (String) param.get("vcode");//输入的验证码

            HttpSession session = (HttpSession) param.get("session");
            if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(password) || StringUtils.isEmpty(vcode)) {
                result.put("status", 0);
                result.put("tip", "参数有空值");
            } else {
                //获取系统生成的校正码
                String sysCode = (String) session.getAttribute(CommonContains.VERIFY_SESSION);
                if (vcode.equalsIgnoreCase(sysCode)) {
                    //判断登录用户是否已经存在
                    User user = userDao.getUserById(userId);
                    if (user != null) {
                        if (user.getPassword().equals(password)) {
                            if (user.getStatus() == 1) {
                                session.setAttribute(OaContants.USER_SESSION, user);
                                result.put("status", 1);
                                result.put("tip", "登录成功");
                                UserHolder.addCurrentUser(user);

                                //登录成功后，立即查找user的权限
                                //key->parentUrl;value->sonUrls;
                                Map<String, List<String>> userAllOperasPopedomUrls = getUserAllOperasPopedomUrls();
                                session.setAttribute(OaContants.USER_ALL_OPERAS_POPEDOM_URLS,userAllOperasPopedomUrls);
                            } else {
                                result.put("status", 5);
                                result.put("tip", "账户还没有激活,请联系管理员激活");
                            }
                        } else {
                            result.put("status", 2);
                            result.put("tip", "密码错误");
                        }
                    } else {
                        result.put("status", 3);
                        result.put("tip", "不存在该用户");
                    }
                } else {
                    result.put("status", 4);
                    result.put("tip", "验证码不正确");
                }
            }
            return result;
        }catch (Exception e) {
            throw new OaException("异步登录业务层抛出异常", e);
        }
    }

    /**
     * 更改自己的信息
     * @param user:除了userId跟原来数据库的数据一样之外，其他信息都有可能不一样
     * @param session
     */
    @Transactional
    @Override
    public void updateSelf(User user, HttpSession session) {
        try {
            //获取数据库的user信息
            User sessionUser = userDao.getUserById(user.getUserId());
            sessionUser.setModifyDate(new Date());
            sessionUser.setModifier(user);//主要是将user的userId更新到数据库的modifier列
            sessionUser.setName(user.getName());
            sessionUser.setEmail(user.getEmail());
            sessionUser.setTel(user.getTel());
            sessionUser.setPhone(user.getPhone());
            sessionUser.setQuestion(user.getQuestion());
            sessionUser.setAnswer(user.getAnswer());
            sessionUser.setQqNum(user.getQqNum());
            if (sessionUser.getDept() != null) {
                sessionUser.getDept().getName();
                sessionUser.setDept(user.getDept());
            }
            if (sessionUser.getJob() != null) {
                sessionUser.getJob().getName();
                sessionUser.setJob(user.getJob());
            }
            userDao.updateUser(sessionUser);
            session.setAttribute(OaContants.USER_SESSION,sessionUser);
        } catch (Exception e) {
            throw new OaException("修改用户失败", e);
        }
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        try {
//            User sessionUser = userDao.getUserById(user.getUserId());
            user.setModifyDate(new Date());
            user.setModifier(UserHolder.getCurrentUser());
            userDao.updateUser(user);
        } catch (Exception e) {
            throw new OaException("修改用户异常",e);
        }
    }

    @Override
    public List<User> getUsersByPage(User user, PageModel pageModel) {
        try {
            if (user == null) {
                user = new User();
            }
            //根据输入条件，查询符合条件的所有数据条数,用于显示有多少页
            int recordCount = userDao.countByUser(user);
            pageModel.setRecordCount(recordCount);
            //分页查询
            List<User> users = userDao.selectUsersByPage(user, pageModel);
            return users;
        } catch (Exception e) {
            throw new OaException("分页查询用户出错", e);
        }
    }

    @Transactional
    @Override
    public void deleteUserByUserIds(String ids) {
        try {
            List<String> idList = new ArrayList<>();
            Collections.addAll(idList, ids.split(","));
            userDao.deleteUserBatch(idList);
        } catch (Exception e) {
            throw new OaException("删除用户出异常", e);
        }
    }

    @Override
    public String isUserValidAjax(String userId) {
        try {
            User user = userDao.getUserById(userId);
            return user == null ? "success" : "error";
        } catch (Exception e) {
            throw new OaException("校验用户登录名是否注册异常", e);
        }
    }

    @Transactional
    @Override
    public void addUser(User user) {
        try {
            user.setCheckDate(new Date());
            user.setCreater(UserHolder.getCurrentUser());
            userDao.addUser(user);
        } catch (Exception e) {
            throw new OaException("添加用户异常", e);
        }
    }

    @Transactional
    @Override
    public void activeUser(User user) {
        try {
            user.setCheckDate(new Date());
            user.setChecker(UserHolder.getCurrentUser());
            userDao.updateUser(user);
        } catch (Exception e) {
            throw new OaException("激活用户失败", e);
        }
    }

    @Override
    public List<User> selectRoleUser(Role role, PageModel pageModel) {
        try {
            List<String> userIds = userDao.findRoleUsers(role.getId());
            int recordCount = userDao.countByUserIds(userIds);
            pageModel.setRecordCount(recordCount);
            List<User> users = userDao.findUsersByPage(userIds, pageModel);
            return users;
        } catch (Exception e) {
            throw new OaException("查询角色下的用户信息异常", e);
        }
    }

    @Override
    public List<User> selectNotRoleUser(Role role, PageModel pageModel) {
        try {
            List<String> userIds = userDao.getRolesUsers(role.getId());
            int recordCount = userDao.countNotByUserIds(userIds);
            pageModel.setRecordCount(recordCount);
            List<User> users = userDao.findNotUsersByPage(userIds, pageModel);
            return users;
        } catch (Exception e) {
            throw new OaException("查询不属于角色下的用户信息异常", e);
        }
    }

    @Transactional
    @Override
    public void bindUser(Role role, String ids) {
        try {
            int roleId = role.getId();
            List<String> userIds = new ArrayList<>();
            Collections.addAll(userIds, ids.split(","));
            userDao.addRoleUser(roleId, userIds);
        } catch (Exception e) {
            throw new OaException("绑定角色下的用户异常", e);
        }
    }

    @Transactional
    @Override
    public void unBindUser(Role role, String ids) {
        try {
            int roleId = role.getId();
            List<String> userIds = new ArrayList<>();
            Collections.addAll(userIds, ids.split(","));
            userDao.removeRoleUser(roleId, userIds);
        } catch (Exception e) {
            throw new OaException("解绑角色下的用户异常", e);
        }
    }

    @Override
    public User getUserById(String userId) {
        return userDao.getUserById(userId);
    }

    private Map<String, List<String>> getUserAllOperasPopedomUrls() {
        try {
            //根据user_id获取该用户的所有opera_code,即子模块的编号
            List<String> userAllPopedomOperasCodes = popeDomDao.getUserPopeDomOperasCodes(UserHolder.getCurrentUser().getUserId());
            if (userAllPopedomOperasCodes != null && userAllPopedomOperasCodes.size() > 0) {
                Map<String, List<String>> userAllOperasPopedomUrls = new HashMap<>();
                //父模块地址
                String moduleUrl = "";
                //子模块地址
                List<String> moduleOperaUrls = null;
                for (String operaCode : userAllPopedomOperasCodes) {
                    String parentModuleCode = operaCode.substring(0, operaCode.length() - OaContants.CODE_LEN);
                    moduleUrl = moduleDao.getModuleByCode(parentModuleCode).getUrl();
                    if (!userAllOperasPopedomUrls.containsKey(moduleUrl)) {
                        moduleOperaUrls = new ArrayList<>();
                        userAllOperasPopedomUrls.put(moduleUrl, moduleOperaUrls);
                    }
                    moduleOperaUrls.add(moduleDao.getModuleByCode(operaCode).getUrl());
                }
                return userAllOperasPopedomUrls;
            }
            return null;
        } catch (Exception e) {
            throw new OaException("登录查询用户权限出异常", e);
        }
    }
}
