package com.cjs.bean;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.*;

@Alias("user")
public class User implements Serializable {
    private String userId;
    private String password;
    private String name;
    private int sex=1;
    private Dept dept;
    private Job job;
    private String email;
    private String tel;
    private String phone;
    private String qqNum;
    private int question;
    private String answer;
    private int status = 0;//0:新建;1:审核;2:不通过审核;3:冻结;
    private User creater;
    private Date createDate;
    private User modifier;
    private Date modifyDate;
    private User checker;
    private Date checkDate;

    private List<Role> roles;

    public User() {
    }

    public User(String userId, String password, String name, int sex, Dept dept, Job job, String email, String tel, String phone, String qqNum, int question, String answer, int status, User creater, Date createDate, User modifier, Date modifyDate, User checker, Date checkDate, List<Role> roles) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.dept = dept;
        this.job = job;
        this.email = email;
        this.tel = tel;
        this.phone = phone;
        this.qqNum = qqNum;
        this.question = question;
        this.answer = answer;
        this.status = status;
        this.creater = creater;
        this.createDate = createDate;
        this.modifier = modifier;
        this.modifyDate = modifyDate;
        this.checker = checker;
        this.checkDate = checkDate;
        this.roles = roles;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQqNum() {
        return qqNum;
    }

    public void setQqNum(String qqNum) {
        this.qqNum = qqNum;
    }

    public int getQuestion() {
        return question;
    }

    public void setQuestion(int question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public User getChecker() {
        return checker;
    }

    public void setChecker(User checker) {
        this.checker = checker;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
