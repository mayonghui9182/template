package cn.net.mayh.dynamic;

import cn.net.mayh.util.MyStringUtils;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Data
public class ColumnInfo {
    private String columnName;
    private String fieldName;
    private String fieldNameOfFirstUpper;
    private String dataType;
    private String javaType;
    private String columnComment;

    public void setColumnName(String columnName) {
        this.columnName = columnName;
        this.fieldName = MyStringUtils.toCamelCase(columnName);
        this.fieldNameOfFirstUpper = MyStringUtils.toCapitalizeCamelCase(columnName);
    }

    public void setDataType(String dataType) {
        this.dataType = getJDBCType(dataType);
        String javaType = typeMap.get(this.dataType);
        if(StringUtils.isEmpty(javaType)){
            throw new RuntimeException(dataType+"无对应的javaType");
        }
        this.javaType = javaType;
    }

    private static String getJDBCType(String dataType) {
        if("TEXT".equals(dataType)){
            dataType = "LONGVARCHAR";
        }else if("INT".equals(dataType)){
            dataType = "INTEGER";
        }else if("DATETIME".equals(dataType)){
            dataType = "TIMESTAMP";
        }
        return dataType;
    }

    public static Map<String, String> typeMap = new HashMap<>();

    static {
        typeMap.put("CHAR", "String");
        typeMap.put("VARCHAR", "String");
        typeMap.put("LONGVARCHAR", "String");
        typeMap.put("NUMERIC", "BigDecimal");
        typeMap.put("DECIMAL", "BigDecimal");
        typeMap.put("BIT", "boolean");
        typeMap.put("BOOLEAN", "boolean");
        typeMap.put("TINYINT", "byte");
        typeMap.put("SMALLINT", "short");
        typeMap.put("INTEGER", "Integer");
        typeMap.put("BIGINT", "long");
        typeMap.put("REAL", "float");
        typeMap.put("FLOAT", "double");
        typeMap.put("DOUBLE", "double");
        typeMap.put("BINARY", "byte[]");
        typeMap.put("VARBINARY", "byte[]");
        typeMap.put("DATE", "Date");
        typeMap.put("TIME", "Time");
        typeMap.put("TIMESTAMP", "Date");
    }
}
