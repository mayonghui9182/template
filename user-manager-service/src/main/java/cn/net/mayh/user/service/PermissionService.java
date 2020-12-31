package cn.net.mayh.user.service;


import com.luban.securtiyjdbc.bean.Permission;

import java.util.List;

/**
 * @author Fox
 */
public interface PermissionService  {

    List<Permission> selectByUserId(Long userId);
}
