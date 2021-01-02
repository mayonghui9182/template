package cn.net.mayh.util;

import cn.net.mayh.dto.ResultDto;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseUtils {
    public static void response(HttpServletResponse response, ResultDto resultDto) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/javascript;charset=utf-8");
        response.getWriter().print(JSONUtils.toJSONString(resultDto));
        response.flushBuffer();
    }
}
