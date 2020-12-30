package cn.net.mayh.indicator.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import cn.net.mayh.entity.BaseEntity;
import cn.net.mayh.po.IBasePO;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* BaseTable持久化对象
* @author mayh
* @version 1.0
*/
@ApiModel("测试表持久化对象")
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseTablePO extends BaseEntity implements IBasePO {
    private static final long serialVersionUID = 1L;
    /**
    * 名称
    */
    @ApiModelProperty("名称")
    private String name;
}
