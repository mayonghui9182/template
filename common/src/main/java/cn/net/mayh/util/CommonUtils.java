package cn.net.mayh.util;

import brave.Span;
import brave.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class CommonUtils {

  private static PathMatcher matcher = new AntPathMatcher();

  public static boolean checkUrl(List<String> urlPatterns, String currentUri) {
    for (String urlPattern : urlPatterns) {
      if (matcher.match(urlPattern, currentUri)) {
        return true;
      }
    }
    return false;
  }

  public static String extractTraceId(Tracer tracer) {
    Span span = tracer.currentSpan();
    String traceId = (span == null ? null : String.valueOf(span.context().parentId()));
    if (traceId == null || traceId.isEmpty()) {
      traceId = "[NoClientTraceId]" + UUID.randomUUID().toString();
    }
    return traceId;
  }

  public static String getBindErrorMsg(BindingResult result) {
    String errorMsg = "";
    if (result != null && result.hasErrors()) {
      ObjectError objectError;
      List<ObjectError> list = result.getAllErrors();
      Iterator<ObjectError> it = list.iterator();
      while (it.hasNext()) {
        objectError = it.next();
        log.error(
            objectError.getCode()
                + "---"
                + objectError.getArguments()
                + "----"
                + objectError.getDefaultMessage());
        errorMsg = objectError.getDefaultMessage() + "|" + errorMsg;
      }
    }
    return errorMsg;
  }

  /**
   * 获取请求来源地址
   */
  public static String getRemoteHost(HttpServletRequest request) {
    String ip = request.getHeader("x-forwarded-for");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
  }


  /**
   * 获取rest post请求头
   */
  public static HttpEntity getRestTempHttpEntity (Map<String, Object> param) {
    HttpHeaders headers = new HttpHeaders();
    MediaType type = MediaType.parseMediaType("application/json;charset=utf-8");
    headers.setContentType(type);
    headers.add("Accept", MediaType.APPLICATION_JSON.toString());
    HttpEntity<String> formEntity = new HttpEntity<String>(JSONUtils.toJSONString(param), headers);
    return formEntity;
  }


}
