package cn.net.mayh.po;

import cn.net.mayh.entity.BaseEntity;
import cn.net.mayh.entity.IEntity;
import lombok.Data;

/**
 * description: 数据库字典表实体类
 * @author : mayonghui
 * @version : 1.0
 * @date : 2020/12/25
 **/
@Data
public class Dict extends BaseEntity implements IEntity {
    private String systemCode;
    private String type;
    private String value;
    private String label;
}
