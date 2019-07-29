package com.cjs.bean;


import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

@Alias("popedom")
public class Popedom implements Serializable {
    private Integer id;
    private Module module;
    private Module opera;
    private Role role;
    private User creater;
    private Date createDate;

    public Popedom() {
    }

    public Popedom(Module module, Module opera, Role role, User creater, Date createDate) {
        this.module = module;
        this.opera = opera;
        this.role = role;
        this.creater = creater;
        this.createDate = createDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Module getOpera() {
        return opera;
    }

    public void setOpera(Module opera) {
        this.opera = opera;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
