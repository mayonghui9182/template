package cn.net.mayh.user.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author mayh
 */
@Data
public class SysRole implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long parentId;

    private String name;

    private String enname;

    private String description;

    private Date created;

    private Date updated;

}
