package cn.net.mayh.user.service;

import cn.net.mayh.user.bean.SysUser;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author mayh
 */
public interface IUserService extends UserDetailsService {

    SysUser getByUsername(String username);
}
