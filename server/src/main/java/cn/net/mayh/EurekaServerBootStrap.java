package cn.net.mayh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
/**
 * @date 2021/1/3
 * @author  mayh
 * description: eureka server启动类
 * update：{[author:date:description:]}
 **/
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerBootStrap {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerBootStrap.class, args);
    }

}
