package cn.net.mayh.user.dao;

import cn.net.mayh.user.bean.SysUser;
import org.springframework.stereotype.Repository;

/**
 * @author mayh
 */
@Repository
public interface IUserDao {
    SysUser getByUsername(String username);
}
