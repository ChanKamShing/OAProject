package com.cjs.biz;

import com.cjs.bean.Role;
import com.cjs.dto.UserModule;

import java.util.List;

public interface PopedomBiz {
    List<String> getRoleModuleOperaCodes(Role role, String parentCode);

    void bindPopedom(String codes, Role role, String parentCode);
}
