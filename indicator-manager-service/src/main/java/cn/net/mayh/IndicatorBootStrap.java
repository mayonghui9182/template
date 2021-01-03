package cn.net.mayh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Description: springboot启动类
 * @author mayh
 * @version : 1.0
 * @date 2020/12/28
 **/
@SpringBootApplication
@EnableTransactionManagement
@EnableConfigurationProperties
public class IndicatorBootStrap {
    public static void main(String[] args) {
        SpringApplication.run(IndicatorBootStrap.class, args);
    }

}
