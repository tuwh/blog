package com.uncub.blog.dto.base;

public class UserRole {
    /**
    * 主键
    */
    private Integer id;

    /**
    * 角色id
    */
    private Integer userId;

    /**
    * 角色id
    */
    private Integer roleId;

    /**
    * 状态： 0无效 1有效
    */
    private String status;

    /**
    * 角色名
    */
    private String roleName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }
}