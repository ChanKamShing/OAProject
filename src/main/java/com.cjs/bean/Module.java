package com.cjs.bean;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

@Alias("module")
public class Module implements Serializable {
    private String code;
    private String name;
    private String url;
    private String remark;
    private User modifier;
    private Date modifyDate;
    private User creater;
    private Date createDate;

    public Module() {
    }

    public Module(String code, String name, String url, String remark, User modifier, Date modifyDate, User creater, Date createDate) {
        this.code = code;
        this.name = name;
        this.url = url;
        this.remark = remark;
        this.modifier = modifier;
        this.modifyDate = modifyDate;
        this.creater = creater;
        this.createDate = createDate;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public User getModifier() {
        return modifier;
    }

    public void setModifier(User modifier) {
        this.modifier = modifier;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public User getCreater() {
        return creater;
    }

    public void setCreater(User creater) {
        this.creater = creater;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
