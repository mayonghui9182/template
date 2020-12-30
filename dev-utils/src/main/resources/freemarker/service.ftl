package ${tableInfo.packageName}.service;

import ${tableInfo.packageName}.dto.${tableInfo.beanName}${tableInfo.DTO};
import ${tableInfo.packageName}.po.${tableInfo.beanName}${tableInfo.PO};
import ${tableInfo.packageName}.qo.${tableInfo.beanName}${tableInfo.QO};
import cn.net.mayh.service.IBaseService;

/**
* ${tableInfo.beanName}的service接口
* @author ${tableInfo.author}
* @version 1.0
*/
public interface I${tableInfo.beanName}${tableInfo.SERVICE} extends IBaseService<${tableInfo.beanName}${tableInfo.PO},${tableInfo.beanName}${tableInfo.QO}, ${tableInfo.beanName}${tableInfo.DTO}>{
}
