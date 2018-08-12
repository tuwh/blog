package com.uncub.blog.dao.base;

import com.uncub.framework.dao.Pagination;
import com.uncub.blog.condition.RoleConditions;
import com.uncub.blog.dto.base.Role;
import java.util.List;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface RoleMapper {
    /**
    * 根据主键进行删除
    * @Param id
    * @auth tuwh
    */
    int deleteRoleById(Integer id);

    /**
    * 根据主键进行新增,插入所有字段
    * @Param role
    */
    int insert(Role role);

    /**
    * 根据主键进行新增,插入非空字段
    * @Param role
    */
    int insertSelective(Role role);

    /**
    * 根据查询条件进行查找
    * @Param role
    * @auth tuwh
    */
    List<Role> queryRoleByConditions(RoleConditions roleConditions);

    /**
    * 根据主键进行查找
    * @Param role
    * @auth tuwh
    */
    Role selectRoleById(Integer id);

    /**
    * 根据主键进行更新，仅更新非主空字段
    * @Param role
    */
    int updateRoleByIdSelective(Role role);

    /**
    * 根据主键进行更新，更新所有字段
    * @Param role
    */
    int updateRoleById(Role role);

    /**
    * 根据所有不为空条件进行查询，分页。结果将传入@Param pagination 参数中
    */
    List<Role> queryRole(Role role, Pagination pagination);

    /**
    * 根据所有不为空条件进行查询，不分页
    */
    List<Role> queryRole(Role role);

    /**
    * 根据所有不为空条件进行查询，分页。结果将传入@Param pagination 参数中
    */
    List<Role> queryRoleByConditions(RoleConditions roleConditions, Pagination pagination);
}