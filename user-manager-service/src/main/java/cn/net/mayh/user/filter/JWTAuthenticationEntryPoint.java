package cn.net.mayh.user.filter;

import cn.net.mayh.dto.ResultDto;
import cn.net.mayh.exception.ExceptionEnum;
import cn.net.mayh.util.ResponseUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 匿名用户访问资源时无权限的处理
 */
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        ResponseUtils.response(response, ResultDto.buildFailed(ExceptionEnum.ACCESSDENIED_ERROR));
    }
}