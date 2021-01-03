package cn.net.mayh.user.dao;

import cn.net.mayh.user.bean.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author mayh
 */
@Mapper
public interface IUserDao {
    SysUser getByUsername(String username);
}
