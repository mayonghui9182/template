package cn.net.mayh.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * description: 系统配置参数类
 * @author : mayonghui
 * @date : 2020/12/25
 * @version : 1.0
 **/
@Configuration
@Data
@Component
public class SystemConfig {
    @Value("${system.file_root}")
    private String fileRoot;
    @Value("${system.dict_cache_time}")
    private String dictCacheTime;
}
