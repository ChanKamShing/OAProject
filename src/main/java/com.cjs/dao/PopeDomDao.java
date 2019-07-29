package com.cjs.dao;

import com.cjs.bean.Popedom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface PopeDomDao{
    List<String> findByIdAndParentCode(@Param("id") int id, @Param("parentCode") String parentCode);

    void setByIdAndParentCode(@Param("id") int id, @Param("parentCode") String parentCode);

    List<String> getUserPopeDomModuleCodes(@Param("userId") String userId);

    List<String> getUserPopeDomOperasCodes(@Param("userId") String userId);

    void addPopedomBatch(@Param("list") List list);
}
