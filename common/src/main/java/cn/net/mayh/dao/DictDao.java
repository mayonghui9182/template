package cn.net.mayh.dao;

import cn.net.mayh.dto.DictDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * description: 字典Dao，用来查询字典信息
 * @author : mayonghui
 * @date : 2020/12/25
 * @version : 1.0
 **/
@Mapper
public interface DictDao {
    /**
     * 根据条件查询字典值列表
     * @param condition
     * return List:字典值列表
     */
    List<DictDto> getDict(DictDto condition);

}
