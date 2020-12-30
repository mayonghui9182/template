package cn.net.mayh.dto;

import cn.net.mayh.po.Dict;
import lombok.Data;
import lombok.ToString;

/**
 * description: todo
 *
 * @author : mayonghui
 * @version : 1.0
 * @date : 2020/12/25
 **/
@Data
@ToString(callSuper = true)
public class DictDto extends Dict {
    private String serviceName;
}
