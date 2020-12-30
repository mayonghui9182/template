package cn.net.mayh.service;


import cn.net.mayh.comm.AsDict;
import cn.net.mayh.dao.DictDao;
import cn.net.mayh.dto.DictDto;
import cn.net.mayh.exception.BizException;
import cn.net.mayh.exception.ExceptionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * description: 字典类service
 * @author : mayonghui
 * @date : 2020/12/25
 * @version : 1.0
 **/
@Service
@Transactional(readOnly = true)
public class DictService {

    @Autowired(required = false)
    private Map<String,AsDict> asDictMap;

    @Autowired
    protected DictDao dictDao;

    /**
     * Description: 根据查询条件获取字典列表
     * @date 2020/12/25
     * param condition: 查询条件
     * return List<DictDto>：字典列表
     * @author mayonghui
     **/
    public List<DictDto> getDict(DictDto condition) {
        String type = condition.getType();
        if (StringUtils.isEmpty(type)) {
            throw BizException.builBizException(ExceptionEnum.PARAMETER_ISNULL_ERROR);
        }
        condition.setType(type.toLowerCase());
        return dictDao.getDict(condition);
    }


    /**
     * Description: 根据serviceName查询对应表的对应字段的值，作为字典值
     * @date 2020/12/25
     * param condition: 查询条件
     * return List<DictDto>：字典列表
     * @author mayonghui
     **/
    public List<DictDto> getTableDict(DictDto condition) {
        List<DictDto> dicts = null;
        final String serviceTeam = condition.getServiceName();
        if (StringUtils.isEmpty(serviceTeam)) {
            throw BizException.builBizException(ExceptionEnum.PARAMETER_ISNULL_ERROR);
        }
        //根据type获取相应的service
        final AsDict asDict = asDictMap.get(serviceTeam);
        if(null!=asDict){
            dicts =  asDict.findListAsDict(condition);
        }
        return dicts;
    }
}