package com.cjs.bean;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

@Alias("dept")
public class Dept implements Serializable {
    private Integer id;
    private String name;
    private String remark;
    private User modifier;
    private Date modifyDate;
    private User creater;
    private Date createDate;

    public Dept() {
    }

    public Dept(String name, String remark, User modifier, Date modifyDate, User creater, Date createDate) {
        this.name = name;
        this.remark = remark;
        this.modifier = modifier;
        this.modifyDate = modifyDate;
        this.creater = creater;
        this.createDate = createDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
