package cn.net.mayh.user.service.impl;

import cn.net.mayh.user.bean.SysPermission;
import cn.net.mayh.user.dao.IPermissionDao;
import cn.net.mayh.user.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mayh
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private IPermissionDao permissionDao;
    @Override
    public List<SysPermission> selectByUserId(Long userId) {

        return permissionDao.selectByUserId(userId);
    }
}
