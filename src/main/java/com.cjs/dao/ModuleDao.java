package com.cjs.dao;

import com.cjs.bean.Module;
import com.cjs.util.pager.PageModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ModuleDao {
    void setCode(List<String> codes);

    List<Module> findModules(@Param("parentCode") String parentCode, @Param("sonCodeLen") int sonCodeLen);

    List<Module> findAllModules();

    String findUniqueEntity(@Param("parentCode") String parentCode, @Param("sonCodeLen") int sonCodeLen);

    void addModule(@Param("module") Module module);

    Module getModuleByCode(@Param("code") String code);

    Module findOneByCode(@Param("code") String code);

    int countModules(@Param("parentCode") String parentCode, @Param("sonCodeLen") int sonCodeLen);

    List<Module> findModulesByPage(@Param("param") Map<String,Object> param, @Param("pageModel") PageModel pageModel);

    void updateModule(@Param("module") Module module);
}
