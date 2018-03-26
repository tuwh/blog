package com.uncub.blog.dao.base;

import com.uncub.blog.common.dao.Pagination;
import com.uncub.blog.condition.UserConditions;
import com.uncub.blog.dto.base.User;
import java.util.List;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface UserMapper {
    /**
    * 根据主键进行删除
    * @Param id
    * @auth tuwh
    */
    int deleteUserById(Integer id);

    /**
    * 根据主键进行新增,插入所有字段
    * @Param user
    */
    int insert(User user);

    /**
    * 根据主键进行新增,插入非空字段
    * @Param user
    */
    int insertSelective(User user);

    /**
    * 根据查询条件进行查找
    * @Param user
    * @auth tuwh
    */
    List<User> queryUserByConditions(UserConditions userConditions);

    /**
    * 根据主键进行查找
    * @Param user
    * @auth tuwh
    */
    User selectUserById(Integer id);

    /**
    * 根据主键进行更新，仅更新非主空字段
    * @Param user
    */
    int updateUserByIdSelective(User user);

    /**
    * 根据主键进行更新，更新所有字段
    * @Param user
    */
    int updateUserById(User user);

    /**
    * 根据所有不为空条件进行查询，分页。结果将传入@Param pagination 参数中
    */
    List<User> queryUser(User user, Pagination pagination);

    /**
    * 根据所有不为空条件进行查询，不分页
    */
    List<User> queryUser(User user);

    /**
    * 根据所有不为空条件进行查询，分页。结果将传入@Param pagination 参数中
    */
    List<User> queryUserByConditions(UserConditions userConditions, Pagination pagination);
}