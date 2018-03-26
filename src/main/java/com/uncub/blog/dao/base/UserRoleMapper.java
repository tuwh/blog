package com.uncub.blog.dao.base;

import com.uncub.blog.common.dao.Pagination;
import com.uncub.blog.condition.UserRoleConditions;
import com.uncub.blog.dto.base.UserRole;
import java.util.List;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface UserRoleMapper {
    /**
    * 根据主键进行删除
    * @Param id
    * @auth tuwh
    */
    int deleteUserRoleById(Integer id);

    /**
    * 根据主键进行新增,插入所有字段
    * @Param userRole
    */
    int insert(UserRole userRole);

    /**
    * 根据主键进行新增,插入非空字段
    * @Param userRole
    */
    int insertSelective(UserRole userRole);

    /**
    * 根据查询条件进行查找
    * @Param userRole
    * @auth tuwh
    */
    List<UserRole> queryUserRoleByConditions(UserRoleConditions userRoleConditions);

    /**
    * 根据主键进行查找
    * @Param userRole
    * @auth tuwh
    */
    UserRole selectUserRoleById(Integer id);

    /**
    * 根据主键进行更新，仅更新非主空字段
    * @Param userRole
    */
    int updateUserRoleByIdSelective(UserRole userRole);

    /**
    * 根据主键进行更新，更新所有字段
    * @Param userRole
    */
    int updateUserRoleById(UserRole userRole);

    /**
    * 根据所有不为空条件进行查询，分页。结果将传入@Param pagination 参数中
    */
    List<UserRole> queryUserRole(UserRole userRole, Pagination pagination);

    /**
    * 根据所有不为空条件进行查询，不分页
    */
    List<UserRole> queryUserRole(UserRole userRole);

    /**
    * 根据所有不为空条件进行查询，分页。结果将传入@Param pagination 参数中
    */
    List<UserRole> queryUserRoleByConditions(UserRoleConditions userRoleConditions, Pagination pagination);
}