package com.uncub.blog.dto.base;

public class RoleResource {
    /**
    * 主键
    */
    private Integer id;

    /**
    * 角色id
    */
    private Integer roleId;

    /**
    * 资源id
    */
    private Integer resourceId;

    /**
    * 状态： 0无效 1有效
    */
    private String status;

    /**
    * 操作权限
    */
    private String permission;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission == null ? null : permission.trim();
    }
}