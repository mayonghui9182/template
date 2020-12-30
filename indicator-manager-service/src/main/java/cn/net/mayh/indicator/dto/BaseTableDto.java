package cn.net.mayh.indicator.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import cn.net.mayh.entity.BaseEntity;
import cn.net.mayh.dto.IBaseDto;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* BaseTable的传输对象
* @author mayh
* @version 1.0
*/
@ApiModel("测试表持久化对象")
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseTableDto extends BaseEntity implements IBaseDto {
    private static final long serialVersionUID = 1L;
    /**
    * 名称
    */
    @ApiModelProperty("名称")
    private String name;
}
