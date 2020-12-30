package cn.net.mayh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "cn.net.mayh")
@EnableCircuitBreaker
@EnableTransactionManagement
public class BootStrap {
  public static void main(String[] args) {
    SpringApplication.run(BootStrap.class, args);
  }
}
