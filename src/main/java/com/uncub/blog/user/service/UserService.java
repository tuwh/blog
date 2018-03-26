package com.uncub.blog.user.service;

import com.uncub.blog.common.exception.ServiceException;
import com.uncub.blog.common.util.MD5Utils;
import com.uncub.blog.condition.UserRoleConditions;
import com.uncub.blog.dao.CustomUserMapper;
import com.uncub.blog.dao.base.UserMapper;
import com.uncub.blog.dao.base.UserRoleMapper;

import com.uncub.blog.dto.ResourcePermission;
import com.uncub.blog.dto.base.Role;
import com.uncub.blog.dto.base.RoleResource;
import com.uncub.blog.dto.base.User;
import com.uncub.blog.dto.base.UserRole;
import org.apache.velocity.runtime.parser.node.MathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service("userService")
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private CustomUserMapper customUserMapper;

    private static final String EFFICTIVE_STATUS = "1";

    public void deleteUserById(Integer id) {
        userMapper.deleteUserById(id);
    }

    /**
     * 根据主键进行查找
     *
     * @Param user
     * @auth tuwh
     */
    public User selectUserById(Integer id) {
        return userMapper.selectUserById(id);
    }

    /**
     * 根据主键进行更新，仅更新非主空字段
     *
     * @Param user
     */
    public int updateUserByIdSelective(User user) {
        return userMapper.updateUserByIdSelective(user);
    }

    /**
     * 根据所有不为空条件进行查询，不分页
     */
    public List<User> queryUser(User user) {
        return userMapper.queryUser(user);
    }

    /**
     * 查询用户的有效角色
     *
     * @param userId
     * @return
     */
    public List<Role> getEffictiveRoleByUserId(Integer userId) {
        return customUserMapper.queryRoleByUserId(userId);
    }

    public Set<String> getEffictiveRolesByUserId(Integer userId) {
        UserRoleConditions userRoleConditions = new UserRoleConditions();
        userRoleConditions.createCriteria().andUserIdEqualTo(userId)
                .andStatusEqualTo(EFFICTIVE_STATUS);
        List<UserRole> userRoles = userRoleMapper.queryUserRoleByConditions(userRoleConditions);
        if (userRoles == null || userRoles.isEmpty()) return new HashSet<String>();
        Set<String> set = new HashSet<String>();
        for (UserRole userRole : userRoles) {
            set.add(String.valueOf(userRole.getRoleName()));
        }
        return set;
    }

    public Set<String> findPermissionByUserId(Integer userId) {
        List<ResourcePermission> resourcePermissions = customUserMapper.queryPermissionByUserId(userId);
        if (resourcePermissions == null || resourcePermissions.isEmpty()) return new HashSet<String>();
        Set<String> set = new HashSet<String>();
        for (ResourcePermission resourcePermission : resourcePermissions) {
            set.add(resourcePermission.getResourceName() + ":" + resourcePermission.getPermission());
        }
        return set;
    }

    public void addUser(User user) {
        if (checkUserExists(user)) throw new ServiceException("用户已存在！");
        user.setSalt(Math.random() * 10000 / 1 + "");
        user.setPassword(MD5Utils.getMD5(user.getPassword() + user.getUserNo() + user.getSalt(),2));
        userMapper.insertSelective(user);
    }

    public boolean checkUserExists(User user) {
        //todo
        return false;
    }
}
