package ${tableInfo.packageName}.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import cn.net.mayh.entity.BaseEntity;
import cn.net.mayh.entity.IEntity;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* ${tableInfo.beanName}的传输对象
* @author ${tableInfo.author}
* @version 1.0
*/
@ApiModel("${tableInfo.desc}持久化对象")
@Data
@EqualsAndHashCode(callSuper = true)
public class ${tableInfo.beanName}${tableInfo.ENTITY} extends BaseEntity implements IEntity {
private static final long serialVersionUID = 1L;
<#list tableInfo.columnInfoList as column>
    /**
    * ${column.columnComment}
    */
    @ApiModelProperty("${column.columnComment}")
    private ${column.javaType} ${column.fieldName};
</#list>
}
