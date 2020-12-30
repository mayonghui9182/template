package cn.net.mayh.dao;

import cn.net.mayh.dto.IBaseDto;
import cn.net.mayh.po.IBasePO;
import cn.net.mayh.qo.IBaseQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description: 基础Dao接口，封装了常用的数据库查询方法
 *
 * @author mayh
 * @date 2020/12/26
 **/
public interface IBaseDao<P extends IBasePO, Q extends IBaseQO, T extends IBaseDto> {

    /**
     * Description: 根据id获取单条数据
     *
     * @param id : 唯一标识，不能为null或者空字符串
     * @return : T 查询结果
     * @date 2020/12/26
     * @author mayh
     **/
    public T getById(String id);

    /**
     * Description: 根据查询对象获取单条数据
     *
     * @param qo : 查询条件
     * @return : T
     * @date 2020/12/26
     * @author mayh
     **/
    public T get(@Param("qo") Q qo);

    /**
     * Description: 根据查询对象获取多条数据，分页查询
     *
     * @param qo : 查询条件
     * @return : java.util.List<T> 查询结果
     * @date 2020/12/26
     * @author mayh
     **/
    public List<T> findList(@Param("qo") Q qo);

    /**
     * Description: 根据查询对象获取所有数据
     *
     * @param qo : 查询条件
     * @return : java.util.List<T> 查询结果
     * @date 2020/12/26
     * @author mayh
     **/
    public List<T> findAllList(@Param("qo") Q qo);

    /**
     * Description: 插入数据
     *
     * @param po : 要插入的对象
     * @return : int 插入影响的条数,成功为1，失败为0
     * @date 2020/12/26
     * @author mayh
     **/
    public int insert(@Param("po") P po);

    /**
     * Description: 批量插入数据
     *
     * @param poList : 要插入的对象集合
     * @return : int 插入影响的条数,失败为0
     * @date 2020/12/26
     * @author mayh
     **/
    public int bulkInsert(@Param("poList") List<P> poList);

    /**
     * Description: 根据id更新数据
     *
     * @param po : 要更新的对象，会更新不为null的所有属性字段，id不会更新，id不能为空
     * @return : int 更新影响的行数，成功为1，失败为0
     * @date 2020/12/26
     * @author mayh
     **/
    public int updateById(@Param("po") P po);

    /**
     * Description: 根据查询条件更新对象
     *
     * @param po : 不为null的属性都会更新，id不会更新
     * @param qo : 更新条件
     * @return : int 更新影响的行数，更新失败为0
     * @date 2020/12/26
     * @author mayh
     **/
    public int update(@Param("po") P po, @Param("qo") Q qo);

    /**
     * Description: 根据id更新数据,部分为null的字段可更新，根据不同的需求调整各自的mapper即可，id不会更新，id不能为空
     *
     * @param po : 要更新的对象
     * @return : int 更新影响的行数，成功为1，失败为0
     * @date 2020/12/26
     * @author mayh
     **/
    public int updateNullAbleById(@Param("po") P po);

    /**
     * Description: 根据查询条件更新对象,部分为null的字段可更新，根据不同的需求调整各自的mapper即可，id不会更新，id不能为空
     *
     * @param po : 要更新的对象
     * @param qo : 更新条件
     * @return : int 更新影响的行数，更新失败为0
     * @date 2020/12/26
     * @author mayh
     **/
    public int updateNullAble(@Param("po") P po, @Param("qo") Q qo);

    /**
     * Description: 逻辑删除，更新del_flag字段为1
     *
     * @param id : 删除记录的唯一标识
     * @return : int 删除影响的行数
     * @date 2020/12/26
     * @author mayh
     **/
    public int deleteLogincById(String id);

    /**
     * Description: 逻辑删除，更新del_flag字段为1
     *
     * @param qo : 删除条件
     * @return : int 删除影响的行数
     * @date 2020/12/26
     * @author mayh
     **/
    public int deleteLoginc(@Param("qo") Q qo);

}
