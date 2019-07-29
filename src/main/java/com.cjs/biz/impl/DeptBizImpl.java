package com.cjs.biz.impl;

import com.cjs.bean.Dept;
import com.cjs.bean.Job;
import com.cjs.bean.User;
import com.cjs.biz.DeptBiz;
import com.cjs.dao.DeptDao;
import com.cjs.dao.JobDao;
import com.cjs.util.OaException;
import com.cjs.util.pager.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeptBizImpl implements DeptBiz {
    private final JobDao jobDao;
    private final DeptDao deptDao;

    @Autowired
    public DeptBizImpl(DeptDao deptDao, JobDao jobDao) {
        this.deptDao = deptDao;
        this.jobDao = jobDao;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Dept> getAllDepts() {
        try {
            List<Dept> depts = deptDao.getAllDepts();
            depts.forEach(dept -> {
                if (dept.getCreater()!=null) {
                    dept.getCreater().getName();
                }
                if (dept.getModifier() != null) {
                    dept.getModifier().getName();
                }
            });

            return depts;
        } catch (Exception e) {
            throw new OaException("查询部门失败", e);
        }
    }

    @Override
    public Map<String, Object> getAllDeptsAndJobsAjax() {
        try {
            Map<String, Object> deptJobDatas = new HashMap<>();

            List<Dept> depts = deptDao.findDepts();
            List<Map<String, Object>> deptsList = new ArrayList<>();
            depts.forEach(dept -> {
                Map<String, Object> deptMap = new HashMap<>();
                deptMap.put(dept.getId().toString(), dept.getName());
                deptsList.add(deptMap);
            });

            List<Job> jobs = jobDao.findJobs();
            List<Map<String, Object>> jobsList = new ArrayList<>();
            jobs.forEach(job -> {
                Map<String, Object> jobMap = new HashMap<>();
                jobMap.put(job.getCode(), job.getName());
                jobsList.add(jobMap);
            });

            deptJobDatas.put("depts", deptsList);
            deptJobDatas.put("jobs", jobsList);
            return deptJobDatas;
        } catch (Exception e) {
            throw new OaException("查询部门和职位信息异常", e);
        }
    }
}
