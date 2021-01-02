package cn.net.mayh.user.filter;

import cn.net.mayh.dto.ResultDto;
import cn.net.mayh.exception.ExceptionEnum;
import cn.net.mayh.user.utils.JwtTokenUtil;
import cn.net.mayh.util.JSONUtils;
import cn.net.mayh.util.ResponseUtils;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * @author mayh
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                request.getParameter("username"), request.getParameter("password"));
        return authenticationManager.authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        // 从User中获取权限信息
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        // 创建Token
        String token = JwtTokenUtil.createToken(user.getUsername(), authorities.toString());

        // 在请求头里返回创建成功的token
        // 设置请求头为带有"Bearer "前缀的token字符串
        response.setHeader("token", JwtTokenUtil.TOKEN_PREFIX + token);
        ResponseUtils.response(response, ResultDto.buildFailed(ExceptionEnum.SUCCESS,"登录成功"));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        String returnData="";
        if (failed instanceof AccountExpiredException) {
            returnData="账号过期";
        }else if (failed instanceof BadCredentialsException) {
            returnData="密码错误";
        }else if (failed instanceof CredentialsExpiredException) {
            returnData="密码过期";
        }else if (failed instanceof DisabledException) {
            returnData="账号不可用";
        }else if (failed instanceof LockedException) {
            returnData="账号锁定";
        }else if (failed instanceof InternalAuthenticationServiceException) {
            returnData="用户不存在";
        }else{
            returnData="未知异常";
        }
        ResponseUtils.response(response, ResultDto.buildFailed(ExceptionEnum.ACCESSDENIED_ERROR,returnData));

    }
}
