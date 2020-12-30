package cn.net.mayh.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.taikang.sys.mybatis.dao.TmsGroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;
/**
 * 提供资源对应权限定义.此类在初始化时,获取所有的资源和对应角色
 *
 * @author itw_liuxy01 2017年11月6日
 */
@Component
@Slf4j
public class DomainSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

  @Autowired private TmsGroupDao tmsGroupDao;
  Map<String, Collection<ConfigAttribute>> resourceMap = null;

  //改为通过定时任务 TimedTriggerTasks.java 30 分钟刷新一次数据库配置的 Resource
  //@Cacheable(value = "getAuthSourceMap") 方法参数为空时缓存的 key 也为空所以这块没有缓存效果
  public void loadResourceDefine() {
    List<Map<String, Object>> list = tmsGroupDao.findAllGroupMenus();

    String key;
    String value;
    if (list != null && list.size() > 0) {
      resourceMap = new HashMap<>();
      for (Map<String, Object> map : list) {
        key = String.valueOf(map.get("groupId"));
        value = String.valueOf(map.get("menuId"));
        if (resourceMap.containsKey(key)) {
          Collection<ConfigAttribute> menuValue = resourceMap.get(key);
          menuValue.add(new SecurityConfig(value));
          resourceMap.put(key, menuValue);
        } else {
          Collection<ConfigAttribute> menuValue = new ArrayList<>();
          menuValue.add(new SecurityConfig(value));
          resourceMap.put(key, menuValue);
        }
      }
    }
    log.info("加载好的所有权限：" + JSONObject.toJSONString(resourceMap));
  }

  @Override
  public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
    log.info("获取对应请求所需权限配置");
    HttpServletRequest request = ((FilterInvocation) object).getRequest();
    log.info("请求的 url 路径：" + request.getRequestURI());

    return resourceMap.get(request.getRequestURI());
  }

  @Override
  public Collection<ConfigAttribute> getAllConfigAttributes() {
    log.info("获取所有的权限配置");
    Set<ConfigAttribute> allAttribute = new HashSet<>();
    for (Map.Entry<String, Collection<ConfigAttribute>> entry : resourceMap.entrySet()) {
      allAttribute.addAll(entry.getValue());
    }
    log.info("获取所有的权限配置={}", JSONObject.toJSONString(allAttribute));
    return allAttribute;
  }

  /**
   * Indicates whether the {@code SecurityMetadataSource} implementation is able to provide {@code
   * ConfigAttribute}s for the indicated secure object type.
   *
   * @param clazz the class that is being queried
   * @return true if the implementation can process the indicated class
   */
  @Override
  public boolean supports(Class<?> clazz) {
    log.info("supports");
    return FilterInvocation.class.isAssignableFrom(clazz);
  }
}
