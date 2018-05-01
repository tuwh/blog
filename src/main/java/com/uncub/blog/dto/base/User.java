package com.uncub.blog.dto.base;

import java.io.Serializable;

public class User implements Serializable {
    /**
    * 主键(自增长)
    */
    private Integer id;

    /**
    * 用户号
    */
    private String userNo;

    /**
    * 用户名
    */
    private String userName;

    /**
    * 密码
    */
    private String password;

    private String salt;

    /**
    * 状态
    */
    private String status;

    /**
    * 邮箱
    */
    private String email;

    /**
    * 手机号
    */
    private String phoneNo;

    private String createTm;

    private String createDt;

    private String updateDt;

    private String updateTm;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo == null ? null : userNo.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo == null ? null : phoneNo.trim();
    }

    public String getCreateTm() {
        return createTm;
    }

    public void setCreateTm(String createTm) {
        this.createTm = createTm == null ? null : createTm.trim();
    }

    public String getCreateDt() {
        return createDt;
    }

    public void setCreateDt(String createDt) {
        this.createDt = createDt == null ? null : createDt.trim();
    }

    public String getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt == null ? null : updateDt.trim();
    }

    public String getUpdateTm() {
        return updateTm;
    }

    public void setUpdateTm(String updateTm) {
        this.updateTm = updateTm == null ? null : updateTm.trim();
    }
}