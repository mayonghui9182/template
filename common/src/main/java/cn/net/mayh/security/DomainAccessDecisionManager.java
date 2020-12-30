package cn.net.mayh.security;

import java.util.Collection;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;
/**
 * 重写资源访问验证
 *
 * @author itw_liuxy01 2017年11月6日
 */
@Component
@Slf4j
public class DomainAccessDecisionManager implements AccessDecisionManager {

  protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

  @Override
  public void decide(
      Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
      throws AccessDeniedException, InsufficientAuthenticationException {
    log.info("MyAccessDecisionManager decide 验证用户是否允许访问");
    log.info(
        "MyAccessDecisionManager decide Authorities:"
            + JSONObject.toJSONString(authentication.getAuthorities()));
    log.info(
        "MyAccessDecisionManager decide configAttributes:"
            + JSONObject.toJSONString(configAttributes));
    if (configAttributes == null) {
      return;
    }
    for (ConfigAttribute configAttribute : configAttributes) {
      String needRole = ((SecurityConfig) configAttribute).getAttribute();
      //ga为用户所被赋予的权限，needRole为访问响应资源应具有的权限
      for (GrantedAuthority ga : authentication.getAuthorities()) {
        if (needRole.trim().equals(ga.getAuthority().trim())) {
          return;
        }
      }
    }

    throw new AccessDeniedException(
        messages.getMessage("AbstractAccessDecisionManager.accessDenied", "Access is denied"));
  }

  @Override
  public boolean supports(ConfigAttribute attribute) {
    // TODO Auto-generated method stub
    return true;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    // TODO Auto-generated method stub
    return true;
  }
}
