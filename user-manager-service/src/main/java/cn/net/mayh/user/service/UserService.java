package cn.net.mayh.user.service;

import com.luban.securtiyjdbc.bean.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Fox
 */
public interface UserService extends UserDetailsService {

    User getByUsername(String username);
}
