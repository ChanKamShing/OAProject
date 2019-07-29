package com.cjs.biz;

import com.cjs.bean.Module;
import com.cjs.util.pager.PageModel;
import com.cjs.vo.TreeData;

import java.util.*;

public interface ModuleBiz {
    List<TreeData> loadAllModuleTress();

    List<Module> getModulesByParent(String parentCode, PageModel pageModel);

    List<Module> getModulesByParent(String parentCode);

    void deleteModules(String ids);

    String getNextSonCode(String parentCode, int codeLen) throws Exception;

    void addModule(String parentCode, Module module);

    void updateModule(Module module);

    Module getModuleByCode(String code);
}
