package cn.net.mayh.security;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.taikang.sys.jpa.domain.BscUserT;

import lombok.Getter;
import lombok.Setter;

/**
 * 客户化用户登录信息
 *
 * @author duyq06
 */
@Getter
@Setter
public class JwtUser extends User {
  private static final long serialVersionUID = 779745203288607710L;
  // 用户的相关信息
  private String uid;
  private String ucode;
  private String utpy;
  private String uname;
  private String password;
  private String company;
  private String email;
  private String headImage;
  private String token;
  private Collection<GrantedAuthority> authorities;
  private Date lastPasswordResetDate;

  public JwtUser(
      String id,
      String userCode,
      String username,
      String email,
      Collection<GrantedAuthority> authorities,
      String password,
      String headImage) {

    super(id, password, authorities);
    this.uid = id;
    this.ucode = userCode;
    this.uname = username;
    this.email = email;
    this.authorities = authorities;
    this.password = password;
    this.headImage = headImage;
  }
  //返回分配给用户的角色列表
  @Override
  public Collection<GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @JsonIgnore
  public String getId() {
    return uid;
  }

  @JsonIgnore
  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return uname;
  }
  // 账户是否未过期
  @JsonIgnore
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }
  // 账户是否未锁定
  @JsonIgnore
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }
  // 密码是否未过期
  @JsonIgnore
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }
  // 账户是否激活
  @JsonIgnore
  @Override
  public boolean isEnabled() {
    return true;
  }

  public static JwtUser convertToJwtUser(BscUserT user, Collection<GrantedAuthority> authorities) {
    return new JwtUser(
        user.getUserId(),
        user.getUserCode(),
        user.getUserName(),
        "",
        authorities,
        user.getUserPwd(),
        user.getHeadImage());
  }
}
