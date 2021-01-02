package cn.net.mayh.user.service.impl;


import cn.net.mayh.user.bean.SysPermission;
import cn.net.mayh.user.bean.SysUser;
import cn.net.mayh.user.dao.IPermissionDao;
import cn.net.mayh.user.dao.IUserDao;
import cn.net.mayh.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mayh
 */
@Service("userDetailsService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private IPermissionDao permissionDao;
    @Autowired
    private IUserDao userDao;

    @Override
    public SysUser getByUsername(String username) {
        return userDao.getByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = getByUsername(username);
        List<GrantedAuthority> authorities = new ArrayList<>();
        if(user!=null){
            List<SysPermission> permissions = permissionDao.selectByUserId(user.getId());

            permissions.forEach(permission -> {
                if (permission!=null && !StringUtils.isEmpty(permission.getEnname())){
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getUrl());
                    authorities.add(grantedAuthority);
                }
            });
            return new User(
                    user.getUsername(),user.getPassword(),authorities);
        }else {
            throw new UsernameNotFoundException("用户名不存在");
        }

    }
}
