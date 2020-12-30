package cn.net.mayh.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

public class DomainFilterSecurityInterceptor extends FilterSecurityInterceptor {

  public DomainFilterSecurityInterceptor(
      FilterInvocationSecurityMetadataSource securityMetadataSource,
      AccessDecisionManager accessDecisionManager,
      AuthenticationManager authenticationManager) {
    this.setSecurityMetadataSource(securityMetadataSource);
    this.setAccessDecisionManager(accessDecisionManager);
    this.setAuthenticationManager(authenticationManager);
  }
}
