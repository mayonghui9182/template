package cn.net.mayh.user.service;

import cn.net.mayh.user.bean.SysPermission;

import java.util.List;

/**
 * @author mayh
 */
public interface PermissionService  {

    List<SysPermission> selectByUserId(Long userId);
}
