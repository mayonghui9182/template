package cn.net.mayh.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Description: 基本的数据类型类
 * @author mayh
 * @version : 1.0
 * @date 2020/12/27
 **/
@Data
public class BaseEntity {
    /**
     * 实体编号（唯一标识）
     */
    protected String id;
    /**
     * 创建者唯一标识
     */
    protected String fcu;
    /**
     * 创建者名称
     */
    protected String fcuName;
    /**
     *创建日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date fcd;
    /**
     * 更新者唯一标识
     */
    protected String lcu;
    /**
     * 更新者中文名
     **/
    protected String lcuName;
    /**
     * 更新日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date lcd;
    /**
     * 删除标记（0:正常,1:删除,2:审核）
     */
    protected String delFlag;
}
