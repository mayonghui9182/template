package cn.net.mayh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Description: 开发工具包启动
 * @date 2021/1/1
 * @author  mayh
 **/

@SpringBootApplication(scanBasePackages = "cn.net.mayh")
@EnableCircuitBreaker
@EnableTransactionManagement
public class DevBootStrap {
  public static void main(String[] args) {
    SpringApplication.run(DevBootStrap.class, args);
  }
}
