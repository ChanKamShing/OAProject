package com.cjs.dao;

import com.cjs.bean.Job;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.*;

@Mapper
@Repository
public interface JobDao  {
    List<Job> findJobs();
}
