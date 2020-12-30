package cn.net.mayh.security;

import com.taikang.sys.comm.AccessDeniedException;
import com.taikang.sys.comm.ExceptionEnum;
import com.taikang.sys.entity.TmsUser;
import com.taikang.sys.service.TmsUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 继承 Spring UserDetailsService 类，自定义实现 loadUserByUsername 方法 Locates the user based on the
 * username. Authenticate a user from the database.
 *
 * @author duyq06
 */
@Service
@Slf4j
public class DomainUserDetailsService implements UserDetailsService {

    @Autowired
    private TmsUserService tmsUserService;

    /**
     * 通过用户编码加载用户信息
     *
     * @param userCode
     */
    @Override
    @Transactional
    // 此处加 cache 后，如果密码被修改后不好刷新，由于此方法在认证时才调用可不加cache
    @Cacheable(value = "getUserInfoByToken", key = "#userCode", sync = true)
    public UserDetails loadUserByUsername(final String userCode) {
        log.info("开始认证 Authenticating {}", userCode);

        TmsUser user = tmsUserService.get(userCode);
        if (Objects.isNull(user)) {
            throw new AccessDeniedException(ExceptionEnum.ACCESSDENIED_ERROR);
        }
        // 如果用户冻结则抛出权限不足
        if (!"1".equals(user.getUserStatus())) {
            throw new AccessDeniedException(ExceptionEnum.ACCESSDENIED_ERROR);
        }

        List<String> usrRoles = new ArrayList<>();
        usrRoles.add(user.getOrgRole().toString());
        // 角色信息
        List<GrantedAuthority> authorities =
                usrRoles.stream()
                        .map(authority -> new SimpleGrantedAuthority(authority))
                        .collect(Collectors.toList());
        JwtUser jwtUser = new JwtUser(userCode,
                user.getUserId(),
                user.getUserName(),
                user.getEmail(),
                authorities,
                user.getPassword(),
                null);
        return jwtUser;
    }
}
