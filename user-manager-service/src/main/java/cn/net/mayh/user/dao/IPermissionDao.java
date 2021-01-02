package cn.net.mayh.user.dao;

import cn.net.mayh.user.bean.SysPermission;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author mayh
 */
@Repository
public interface IPermissionDao  {

    List<SysPermission> selectByUserId(Long userId);
}
