package cn.net.mayh.service;

import com.github.pagehelper.Page;

import java.util.List;
import java.util.function.Supplier;

/**
 * Description: 基本service接口，定义了service的基本通用方法
 * @author mayh
 * @version : 1.0
 * @date 2020/12/26
 **/
public interface IBaseService<P,Q,T> {

    /**
     * Description: 根据id获取单条数据
     * @date 2020/12/26
     * @author  mayh
     * @param id : 唯一标识，不能为null或者空字符串
     * @return : T 查询结果
     **/
    T get(String id);

    /**
     * Description: 根据查询对象获取单条数据
     * @date 2020/12/26
     * @author  mayh
     * @param qo : 查询条件
     * @return : T
     **/
    T get(Q qo);

    /**
    * Description: 根据查询对象获取多条数据，分页查询
    * @date 2020/12/26
    * @author  mayh
    * @param pageNo :
    * @param pageSize :
    * @param qo : 查询条件
    * @return : cn.net.mayh.comm.Page<T> 分页查询结果
    **/
    Page<T> findPage(int pageNo,int pageSize, Q qo);

    /**
    * Description: 根据查询对象获取多条数据，分页查询,但是数据来源不在是findList，而是getList
    * @date 2020/12/26
    * @author  mayh
    * @param pageNo :
    * @param pageSize :
	* @param getList : 提供的数据源，是一个函数式接口
    * @return : cn.net.mayh.comm.Page<P> 分页查询结果
    **/
    Page<P> findList2Page(int pageNo,int pageSize,Supplier<Page<P>> getList);

    /**
     * Description: 根据查询对象获取所有数据
     * @date 2020/12/26
     * @author  mayh
     * @param qo : 查询条件
     * @return : java.util.List<T> 查询结果
     **/
    List<T> findAllList(Q qo);

    /**
     * Description: 插入数据
     * @date 2020/12/26
     * @author  mayh
     * @param po : 要插入的对象
     * @return : int 插入影响的条数,成功为1，失败为0
     **/
    int insert(P po);


    /**
     * Description: 插入数据
     * @date 2020/12/26
     * @author  mayh
     * @param poList : 要插入的对象集合
     * @return : int 插入影响的条数,成功为1，失败为0
     **/
    int bulkInsert(List<P> poList);

    /**
     * Description: 根据id更新数据
     * @date 2020/12/26
     * @author  mayh
     * @param po : 要更新的对象，会更新不为null的所有属性字段，id不会更新，id不能为空
     * @return : int 更新影响的行数，成功为1，失败为0
     **/
    int updateById(P po);
    /**
     * Description: 根据查询条件更新对象
     * @date 2020/12/26
     * @author  mayh
     * @param po : 不为null的属性都会更新，id不会更新
     * @param qo : 更新条件
     * @return : int 更新影响的行数，更新失败为0
     **/
    int update(P po,Q qo);
    /**
     * Description: 根据id更新数据,部分为null的字段可更新，根据不同的需求调整各自的mapper即可，id不会更新，id不能为空
     * @date 2020/12/26
     * @author  mayh
     * @param po : 要更新的对象
     * @return : int 更新影响的行数，成功为1，失败为0
     **/
    int updateNullAbleById(P po);
    /**
     * Description: 根据查询条件更新对象,部分为null的字段可更新，根据不同的需求调整各自的mapper即可，id不会更新，id不能为空
     * @date 2020/12/26
     * @author  mayh
     * @param po : 要更新的对象
     * @param qo : 更新条件
     * @return : int 更新影响的行数，更新失败为0
     **/
    int updateNullAble(P po,Q qo);

    /**
     * Description: 逻辑删除，更新del_flag字段为1
     * @date 2020/12/26
     * @author  mayh
     * @param qo : 删除条件
     * @return : int 删除影响的行数
     **/
    int deleteLoginc(Q qo);

    /**
     * Description: 物理删除数据
     * @date 2020/12/26
     * @author  mayh
     * @param id : 删除记录唯一标识
     * @return : int 删除影响的行数
     **/
    int deleteLogincById(String id);


    /**
    * Description: 保存或者更新对象，根据po的id判断，为空插入，否则更新
    * @date 2020/12/26
    * @author  mayh
    * @param po : 保存的对象
    * @return : int 影响的行数
    **/
    int save( P po,Object... args);
}
