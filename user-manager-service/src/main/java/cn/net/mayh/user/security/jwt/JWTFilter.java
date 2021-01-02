package cn.net.mayh.user.security.jwt;

import cn.net.mayh.Constants;
import cn.net.mayh.exception.BizException;
import cn.net.mayh.exception.ExceptionEnum;
import cn.net.mayh.user.security.DomainUserDetailsService;
import cn.net.mayh.user.security.JwtUser;
import cn.net.mayh.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * filter 拦截请求，获取用户、权限信息 Filter base class that aims to guarantee a single execution per request
 * dispatch, on any servlet container. It provides a {@link #doFilterInternal} method with
 * HttpServletRequest and HttpServletResponse arguments.
 *
 * @author duyq06
 */
@Slf4j
public class JWTFilter extends OncePerRequestFilter {

  @Autowired private TokenProvider tokenProvider;
  @Autowired private DomainUserDetailsService userDetailsService;

  @Value("${ikfa.security.authentication.jwt.header}")
  private String tokenHeader;

  @Value("${ikfa.security.swagger-ui.test.enable}")
  private boolean enableTest;

  @Value("${ikfa.security.swagger-ui.test.mocktoken}")
  private String mocktoken;

  @Value("${ikfa.security.authentication.permiturl}")
  private String permiturl;

  private List<String> permitUrlList = new ArrayList<String>();

  @PostConstruct
  public void initPermitUrlList() {
    String[] strArr = permiturl.split(",");

    Collections.addAll(permitUrlList, strArr);
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {
    log.info("请求路径uri：" + request.getRequestURI());
    if (!request.getMethod().equals(Constants.REQUEST_METHOD_OPTIONS) && !CommonUtils.checkUrl(permitUrlList, request.getRequestURI())) {
      String authToken = this.tokenProvider.resolveToken(request);
      String userCode = null;
      if (authToken != null) {
        //此方法同时会验证token是否过期
        userCode = tokenProvider.getUserCodeFromToken(authToken);
      } else {
        log.debug("没有 token 信息存在！");
        throw BizException.builBizException(ExceptionEnum.ACCESSDENIED_ERROR);
        // 用于mock token信息
        /*if (enableTest) {
          // 需要模拟测试时设置 token 为固定值
          log.debug("mock token 测试！");
          authToken = mocktoken.substring(7, mocktoken.length());
          userCode = tokenProvider.getUserCodeFromToken(authToken);
        }*/
      }
      log.info("校验用户认证信息 authentication for user " + userCode);
      if (userCode != null && SecurityContextHolder.getContext().getAuthentication() == null) {

        // It is not compelling necessary to load the use details from the
        // database. You could also store the information
        // in the token and read it from it. It's up to you
        JwtUser userDetails = (JwtUser) this.userDetailsService.loadUserByUsername(userCode);

        //如果不足五分钟过期，则刷新token
        long expireTime = tokenProvider.getExpirationDateFromToken(authToken).getTime();
        long nowTime = System.currentTimeMillis();
        if ((expireTime - nowTime) < Constants.TOKEN_REFRESH_TIME && !enableTest) {
          if (tokenProvider.canTokenBeRefreshed(
              authToken, userDetails.getLastPasswordResetDate())) {
            String refreshedToken = tokenProvider.refreshToken(authToken, false);
            response.addHeader(Constants.REFRESH_TOKEN, "Bearer " + refreshedToken);
          }
        }

        userDetails.setToken(authToken);
        // For simple validation it is completely sufficient to just check
        // the token integrity. You don't have to call
        // the database compellingly. Again it's up to you
        if (tokenProvider.validateToken(authToken, userDetails)) {
          UsernamePasswordAuthenticationToken authentication =
              new UsernamePasswordAuthenticationToken(
                  userDetails, null, userDetails.getAuthorities());
          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          log.info("authenticated user " + userCode + ", setting security context");
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      }
    }
    chain.doFilter(request, response);
  }
}
