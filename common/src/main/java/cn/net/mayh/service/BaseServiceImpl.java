package cn.net.mayh.service;

import cn.net.mayh.dao.IBaseDao;
import cn.net.mayh.dto.IBaseDto;
import cn.net.mayh.exception.BizException;
import cn.net.mayh.exception.ExceptionEnum;
import cn.net.mayh.po.IBasePO;
import cn.net.mayh.qo.IBaseQO;
import cn.net.mayh.util.IdGenerate;
import cn.net.mayh.util.MyStringUtils;
import cn.net.mayh.util.UserUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

/**
 * Description: 基础service实现类，定义了service实现类的基本方法
 *
 * @author mayh
 * @date 2020/12/26
 **/
@Slf4j
public class BaseServiceImpl<D extends IBaseDao<P, Q, T>, P extends IBasePO, Q extends IBaseQO, T extends IBaseDto> implements IBaseService<P, Q, T> {
    protected D dao;

    public BaseServiceImpl(D dao) {
        this.dao = dao;
    }

    @Override
    public T get(String id) {
        return dao.getById(id);
    }

    @Override
    public T get(Q qo) {
        return dao.get(qo);
    }

    @Override
    public Page<T> findPage(int pageNo,int pageSize, Q qo) {
        PageHelper.startPage(pageNo, pageSize);
        Page<T> listDtoPage = (Page<T>) dao.findList(qo);
        PageHelper.clearPage();
        return listDtoPage;
    }

    @Override
    public Page<P> findList2Page(int pageNo,int pageSize, Supplier<Page<P>> getList) {
        PageHelper.startPage(pageNo, pageSize);
        Page<P> listDtoPage = getList.get();
        PageHelper.clearPage();
        return listDtoPage;
    }

    @Override
    public List<T> findAllList(Q qo) {
        return dao.findAllList(qo);
    }

    @Override
    public int insert(P po) {
        return dao.insert(po);
    }



    @Override
    public int bulkInsert(List<P> poList) {
        return dao.bulkInsert(poList);
    }

    @Override
    public int updateById(P po) {
        if(StringUtils.isBlank(po.getId())){
            throw BizException.builBizException(ExceptionEnum.PARAMETER_ERROR,"主键不能为空！");
        }
        return dao.updateById(po);
    }

    @Override
    public int update(P po, Q qo) {
        return dao.update(po, qo);
    }

    @Override
    public int updateNullAbleById(P po) {
        return dao.updateNullAbleById(po);
    }

    @Override
    public int updateNullAble(P po, Q qo) {
        return dao.updateNullAble(po, qo);
    }

    @Override
    public int deleteLoginc(Q qo) {
        return dao.deleteLoginc(qo);
    }

    @Override
    public int deleteLogincById(String id) {
        return dao.deleteLogincById(id);
    }

    @Override
    public int save(P po,Object... args) {
        String userId = null;
        userId = UserUtils.getUserId();
        Date curDate = new Date();
        po.setLcd(curDate);
        po.setLcu(userId);
        if (MyStringUtils.isBlank(po.getId())) {
            po.setId(IdGenerate.uuid());
            if(args.length > 0){
                //解决需要记录保留任务第一次生成时间的业务需求场景
                po.setFcd((Date)args[0]);
                po.setFcu((String) args[1]);
            }else{
                po.setFcd(curDate);
                po.setFcu(userId);
            }
            return dao.insert(po);
        } else {
            return dao.updateById(po);
        }
    }
}
