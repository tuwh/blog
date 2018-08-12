package com.uncub.blog.dao.base;

import com.uncub.framework.dao.Pagination;
import com.uncub.blog.condition.ServiceLogConditions;
import com.uncub.blog.dto.base.ServiceLog;
import java.util.List;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface ServiceLogMapper {
    /**
    * 根据主键进行删除
    * @Param id
    * @auth tuwh
    */
    int deleteServiceLogById(Integer id);

    /**
    * 根据主键进行新增,插入所有字段
    * @Param serviceLog
    */
    int insert(ServiceLog serviceLog);

    /**
    * 根据主键进行新增,插入非空字段
    * @Param serviceLog
    */
    int insertSelective(ServiceLog serviceLog);

    /**
    * 根据查询条件进行查找
    * @Param serviceLog
    * @auth tuwh
    */
    List<ServiceLog> queryServiceLogByConditions(ServiceLogConditions serviceLogConditions);

    /**
    * 根据主键进行查找
    * @Param serviceLog
    * @auth tuwh
    */
    ServiceLog selectServiceLogById(Integer id);

    /**
    * 根据主键进行更新，仅更新非主空字段
    * @Param serviceLog
    */
    int updateServiceLogByIdSelective(ServiceLog serviceLog);

    int updateByPrimaryKeyWithBLOBs(ServiceLog record);

    /**
    * 根据主键进行更新，更新所有字段
    * @Param serviceLog
    */
    int updateServiceLogById(ServiceLog serviceLog);

    /**
    * 根据所有不为空条件进行查询，分页。结果将传入@Param pagination 参数中
    */
    List<ServiceLog> queryServiceLog(ServiceLog serviceLog, Pagination pagination);

    /**
    * 根据所有不为空条件进行查询，不分页
    */
    List<ServiceLog> queryServiceLog(ServiceLog serviceLog);

    /**
    * 根据所有不为空条件进行查询，分页。结果将传入@Param pagination 参数中
    */
    List<ServiceLog> queryServiceLogByConditions(ServiceLogConditions serviceLogConditions, Pagination pagination);
}