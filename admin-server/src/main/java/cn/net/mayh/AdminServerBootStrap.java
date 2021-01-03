package cn.net.mayh;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author mayh
 * description: 服务监控启动类
 * update：{[author:date:description:]}
 * @date 2021/1/3
 **/
@SpringBootApplication
@EnableAdminServer
public class AdminServerBootStrap {

    public static void main(String[] args) {
        SpringApplication.run(AdminServerBootStrap.class, args);
    }

}
