package cn.net.mayh.service;

import cn.net.mayh.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * description: 获取数据库的信息，包括数据时间、数据库序列
 * @author : mayonghui
 * @date : 2020/12/25
 * @version : 1.0
 **/
@Service
@Transactional(readOnly = true)
public class DataBaseService {
    @Autowired
    protected CommonDao dataBaseDao;
    /**
     * 获取数据库时间
     * @return
     */
    public Date getDbSysDate() {
        return dataBaseDao.getDbSysDate();
    }


}
