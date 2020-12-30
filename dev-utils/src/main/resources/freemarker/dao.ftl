package ${tableInfo.packageName}.dao;

import cn.net.mayh.dao.IBaseDao;
import ${tableInfo.packageName}.dto.${tableInfo.beanName}${tableInfo.DTO};
import ${tableInfo.packageName}.po.${tableInfo.beanName}${tableInfo.PO};
import ${tableInfo.packageName}.qo.${tableInfo.beanName}${tableInfo.QO};
import org.apache.ibatis.annotations.Mapper;

/**
* ${tableInfo.beanName}的持久层
* @author ${tableInfo.author}
* @version 1.0
*/
@Mapper
public interface I${tableInfo.beanName}Dao extends IBaseDao<${tableInfo.beanName}${tableInfo.PO}, ${tableInfo.beanName}${tableInfo.QO}, ${tableInfo.beanName}${tableInfo.DTO}>{
}