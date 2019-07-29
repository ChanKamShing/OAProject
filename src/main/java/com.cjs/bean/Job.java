package com.cjs.bean;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Alias("job")
public class Job implements Serializable {
    private String code;
    private String name;
    private String remark;

    public Job() {
    }
    public Job(String code, String name, String remark) {
        this.code = code;
        this.name = name;
        this.remark = remark;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
