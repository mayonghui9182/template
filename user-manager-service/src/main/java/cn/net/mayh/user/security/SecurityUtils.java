package cn.net.mayh.user.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 * Spring Security 工具类
 *
 * @author duyq06
 */
public final class SecurityUtils {

  private SecurityUtils() {}

  /**
   * 获取当前用户 userCode
   *
   * @return the login of the current userCode
   */
  public static String getCurrentUserCode() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    Authentication authentication = securityContext.getAuthentication();
    String userCode = null;
    if (authentication != null) {
      if (authentication.getPrincipal() instanceof UserDetails) {
        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
        userCode = jwtUser.getUcode();
      } else if (authentication.getPrincipal() instanceof String) {
        userCode = (String) authentication.getPrincipal();
      }
    }
    return userCode;
  }

  /**
   * 获取当前用户 userId
   *
   * @return the login of the current userId
   */
  public static String getCurrentUserId() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    if(securityContext.getAuthentication() == null){
      return null;
    }else{
      JwtUser jwtUser = (JwtUser) securityContext.getAuthentication().getPrincipal();
      return jwtUser.getUid();
    }
  }

  /**
   * 获取当前用户
   *
   * @return the login of the current user 用户
   */
  public static JwtUser getCurrentJwtUser() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    JwtUser jwtUser = (JwtUser) securityContext.getAuthentication().getPrincipal();
    return jwtUser;
  }

  /**
   * 获取当前登录用户 token
   *
   * @return the JWT of the current user token
   */
  public static String getCurrentUserJWT() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    JwtUser jwtUser = (JwtUser) securityContext.getAuthentication().getPrincipal();
    return jwtUser.getToken();
  }

  /**
   * 获取当前登录用户IP
   *
   * @return the JWT of the current userIP
   */
  public static String getCurrentUserIP() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    WebAuthenticationDetails webAuthenticationDetails =
        (WebAuthenticationDetails) securityContext.getAuthentication().getDetails();
    return webAuthenticationDetails.getRemoteAddress();
  }
}
