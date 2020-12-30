package cn.net.mayh.indicator.dao;

import cn.net.mayh.dao.IBaseDao;
import cn.net.mayh.indicator.dto.BaseTableDto;
import cn.net.mayh.indicator.po.BaseTablePO;
import cn.net.mayh.indicator.qo.BaseTableQO;
import org.apache.ibatis.annotations.Mapper;

/**
* BaseTable的持久层
* @author mayh
* @version 1.0
*/
@Mapper
public interface IBaseTableDao extends IBaseDao<BaseTablePO, BaseTableQO, BaseTableDto>{
}