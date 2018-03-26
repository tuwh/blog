package com.uncub.blog.dao;

import com.uncub.blog.dto.ResourcePermission;
import com.uncub.blog.dto.base.Role;
import com.uncub.blog.dto.base.User;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan
public interface CustomUserMapper {

    List<Role> queryRoleByUserId(Integer id);

    List<ResourcePermission> queryPermissionByUserId(Integer id);

}
