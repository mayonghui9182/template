${tableInfo.tableName}
<#list tableInfo.columnInfoList as column>
    ${column.columnName}/${column.dataType}
</#list>