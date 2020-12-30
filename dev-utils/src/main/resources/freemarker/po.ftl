package ${tableInfo.packageName}.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import cn.net.mayh.entity.BaseEntity;
import cn.net.mayh.po.IBasePO;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* ${tableInfo.beanName}持久化对象
* @author ${tableInfo.author}
* @version 1.0
*/
@ApiModel("${tableInfo.desc}持久化对象")
@Data
@EqualsAndHashCode(callSuper = true)
public class ${tableInfo.beanName}${tableInfo.PO} extends BaseEntity implements IBasePO {
    private static final long serialVersionUID = 1L;
<#list tableInfo.columnInfoList as column>
    /**
    * ${column.columnComment}
    */
    @ApiModelProperty("${column.columnComment}")
    private ${column.javaType} ${column.fieldName};
 </#list>
}
