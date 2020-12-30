package cn.net.mayh.datasource;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Description: 数据库配置属性值
 * @date 2020/12/26
 * @author  mayh
 **/
@Component
@Data
public class DbConfigProperties {

  @Value("${app.jdbc_url}")
  private String jdbc_url;

  @Value("${app.jdbc_username}")
  private String jdbc_username;

  @Value("${app.jdbc_password}")
  private String jdbc_password;

  @Value("${app.jdbc_driverClass}")
  private String jdbc_driverClass;

  @Value("${app.maxPoolPreparedStatementPerConnectionSize}")
  private int maxPoolPreparedStatementPerConnectionSize;

  @Value("${app.testOnBorrow}")
  private boolean testOnBorrow;

  @Value("${app.testOnReturn}")
  private boolean testOnReturn;

  @Value("${app.testWhileIdle}")
  private boolean testWhileIdle;

  @Value("${app.validationQuery}")
  private String validationQuery;

  @Value("${app.initialSize}")
  private int initialSize;

  @Value("${app.maxActive}")
  private int maxActive;
}
