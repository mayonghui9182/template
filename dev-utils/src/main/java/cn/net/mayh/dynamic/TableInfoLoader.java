package cn.net.mayh.dynamic;

import cn.net.mayh.datasource.DataSourceUtils;
import cn.net.mayh.util.MyStringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author mayh
 * @version : 1.0
 * @date 2020/12/27
 **/
public class TableInfoLoader {
    private static Connection connection = DataSourceUtils.getConnection();

    public static TableInfo loadTableInfo(TableInfo tableInfo){
        //查询所有字段的信息
        String sql = "SELECT column_name, DATA_TYPE, column_comment FROM information_schema.COLUMNS t WHERE t.TABLE_NAME = ?";
        //存储字段信息查询结果
        List<ColumnInfo> columnInfos = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            //执行查询获取结果
            statement.setString(1, tableInfo.getTableName());
            ResultSet rs = statement.executeQuery();
            //遍历数据集，构建ColumnInfo信息
            while (rs.next()) {
                ColumnInfo columnInfo = new ColumnInfo();
                columnInfo.setColumnName(rs.getString("column_name"));
                columnInfo.setDataType(rs.getString("DATA_TYPE").toUpperCase());
                columnInfo.setColumnComment(rs.getString("column_comment").replace("\r\n", ""));
                columnInfos.add(columnInfo);
            }
        } catch (SQLException e) {
            throw new RuntimeException("sql错误：" + tableInfo.getTableName());
        }
        if(CollectionUtils.isEmpty(columnInfos)){
            throw new RuntimeException("查询不到数据库表的相关信息：" + tableInfo.getTableName());
        }
        tableInfo.setColumnInfoList(columnInfos);
        return tableInfo;
    }


}
