package cn.net.mayh.dynamic;

import cn.net.mayh.util.MyStringUtils;
import lombok.Data;

import java.util.List;
@Data
public class TableInfo {
    public final String DTO = "Dto";
    public final String PO = "PO";
    public final String QO = "QO";
    public final String ENTITY = "Entity";
    public final String DAO = "Dao";
    public final String SERVICE = "Service";
    public final String CONTROLLER = "Controller";
    public final String RESULT = "Result";
    private String tableName;
    private String path;
    private String author;
    private String desc;
    private String beanName;
    private String beanNameOfLowerFirst;
    private String packageName;
    private String idColumn;
    private String idField;
    private String idFieldOfLowerFirst;
    private List<ColumnInfo> columnInfoList;

    public void setBeanName(String beanName) {
        this.beanName = beanName;

    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
        this.beanNameOfLowerFirst = MyStringUtils.toCamelCase(tableName);
        beanName = MyStringUtils.toCapitalizeCamelCase(tableName);
    }

    public void setIdColumn(String idColumn) {
        this.idColumn = idColumn;
        idFieldOfLowerFirst = MyStringUtils.toCamelCase(idColumn);
        idField =  MyStringUtils.toCapitalizeCamelCase(idColumn);
    }
}
