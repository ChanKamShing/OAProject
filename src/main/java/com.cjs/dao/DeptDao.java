package com.cjs.dao;

import com.cjs.bean.Dept;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface DeptDao{
    //只查询id和name
    List<Dept> findDepts();
    //查询所有信息
    List<Dept> getAllDepts();
}
