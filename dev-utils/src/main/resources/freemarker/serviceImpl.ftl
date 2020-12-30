package ${tableInfo.packageName}.service.impl;


import ${tableInfo.packageName}.dao.I${tableInfo.beanName}${tableInfo.DAO};
import ${tableInfo.packageName}.dto.${tableInfo.beanName}${tableInfo.DTO};
import ${tableInfo.packageName}.po.${tableInfo.beanName}${tableInfo.PO};
import ${tableInfo.packageName}.qo.${tableInfo.beanName}${tableInfo.QO};
import ${tableInfo.packageName}.service.I${tableInfo.beanName}${tableInfo.SERVICE};
import cn.net.mayh.service.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


/**
* ${tableInfo.beanName}的service实现类
* @author ${tableInfo.author}
* @version 1.0
*/
@Service
public class ${tableInfo.beanName}ServiceImpl extends BaseServiceImpl<I${tableInfo.beanName}${tableInfo.DAO},${tableInfo.beanName}${tableInfo.PO}, ${tableInfo.beanName}${tableInfo.QO}, ${tableInfo.beanName}${tableInfo.DTO}> implements I${tableInfo.beanName}${tableInfo.SERVICE} {
    @Autowired
    public ${tableInfo.beanName}ServiceImpl(I${tableInfo.beanName}${tableInfo.DAO} dao) {
        super(dao);
    }
}
