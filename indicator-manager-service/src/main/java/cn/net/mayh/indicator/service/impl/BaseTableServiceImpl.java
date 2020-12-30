package cn.net.mayh.indicator.service.impl;


import cn.net.mayh.indicator.dao.IBaseTableDao;
import cn.net.mayh.indicator.dto.BaseTableDto;
import cn.net.mayh.indicator.po.BaseTablePO;
import cn.net.mayh.indicator.qo.BaseTableQO;
import cn.net.mayh.indicator.service.IBaseTableService;
import cn.net.mayh.service.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


/**
* BaseTable的service实现类
* @author mayh
* @version 1.0
*/
@Service
public class BaseTableServiceImpl extends BaseServiceImpl<IBaseTableDao,BaseTablePO, BaseTableQO, BaseTableDto> implements IBaseTableService {
    @Autowired
    public BaseTableServiceImpl(IBaseTableDao dao) {
        super(dao);
    }
}
