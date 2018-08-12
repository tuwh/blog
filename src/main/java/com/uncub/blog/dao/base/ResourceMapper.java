package com.uncub.blog.dao.base;

import com.uncub.framework.dao.Pagination;
import com.uncub.blog.condition.ResourceConditions;
import com.uncub.blog.dto.base.Resource;
import java.util.List;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface ResourceMapper {
    /**
    * 根据主键进行删除
    * @Param id
    * @auth tuwh
    */
    int deleteResourceById(Integer id);

    /**
    * 根据主键进行新增,插入所有字段
    * @Param resource
    */
    int insert(Resource resource);

    /**
    * 根据主键进行新增,插入非空字段
    * @Param resource
    */
    int insertSelective(Resource resource);

    /**
    * 根据查询条件进行查找
    * @Param resource
    * @auth tuwh
    */
    List<Resource> queryResourceByConditions(ResourceConditions resourceConditions);

    /**
    * 根据主键进行查找
    * @Param resource
    * @auth tuwh
    */
    Resource selectResourceById(Integer id);

    /**
    * 根据主键进行更新，仅更新非主空字段
    * @Param resource
    */
    int updateResourceByIdSelective(Resource resource);

    /**
    * 根据主键进行更新，更新所有字段
    * @Param resource
    */
    int updateResourceById(Resource resource);

    /**
    * 根据所有不为空条件进行查询，分页。结果将传入@Param pagination 参数中
    */
    List<Resource> queryResource(Resource resource, Pagination pagination);

    /**
    * 根据所有不为空条件进行查询，不分页
    */
    List<Resource> queryResource(Resource resource);

    /**
    * 根据所有不为空条件进行查询，分页。结果将传入@Param pagination 参数中
    */
    List<Resource> queryResourceByConditions(ResourceConditions resourceConditions, Pagination pagination);
}