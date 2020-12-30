package cn.net.mayh.indicator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Description: springboot启动类
 * @author mayh
 * @version : 1.0
 * @date 2020/12/28
 **/
@SpringBootApplication
@EnableTransactionManagement
@ComponentScan("cn.net.mayh")
@EnableConfigurationProperties
public class BootStrap {
    public static void main(String[] args) {
        SpringApplication.run(BootStrap.class, args);
    }

}
