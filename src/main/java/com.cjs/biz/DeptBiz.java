package com.cjs.biz;

import com.cjs.bean.Dept;
import com.cjs.bean.User;
import com.cjs.util.pager.PageModel;

import java.util.*;

public interface DeptBiz {
    List<Dept> getAllDepts();

    Map<String, Object> getAllDeptsAndJobsAjax();
}
