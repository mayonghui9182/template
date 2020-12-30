package cn.net.mayh.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

/**
 * description: 获取序列以及db时间
 * @author : mayonghui
 * @date : 2020/12/25
 * @version : 1.0
 **/
@Mapper
public interface CommonDao {
    /**
     * Description: 获取db时间
     * @date 2020/12/25
     * @return Date: 数据库系统时间
     * throws:
     * @author mayonghui
     **/
    Date getDbSysDate();
}
