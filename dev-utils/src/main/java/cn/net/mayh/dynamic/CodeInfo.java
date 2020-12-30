package cn.net.mayh.dynamic;

import lombok.Data;

/**
 * Description:
 *
 * @author mayh
 * @version : 1.0
 * @date 2020/12/27
 **/
@Data
public class CodeInfo {
    private String author;
    private String tableName;
    private String tableComment;

    public CodeInfo(String author, String tableName, String tableComment) {
        this.author = author;
        this.tableName = tableName;
        this.tableComment = tableComment;
    }
}
