package com.cjs.bean;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.*;

@Alias("role")
public class Role implements Serializable {
    private Integer id;
    private String name;
    private String remark;
    private User creater;
    private Date createDate;
    private User modifier;
    private Date modifyDate;
    private List<User> users;

    public Role() {
    }

    public Role(Integer id, String name, String remark, User creater, Date createDate, User modifier, Date modifyDate, List<User> users) {
        this.id = id;
        this.name = name;
        this.remark = remark;
        this.creater = creater;
        this.createDate = createDate;
        this.modifier = modifier;
        this.modifyDate = modifyDate;
        this.users = users;
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
