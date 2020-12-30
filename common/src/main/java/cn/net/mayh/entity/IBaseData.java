package cn.net.mayh.entity;

import java.util.Date;

/**
 * Description: 数据基本接口
 * @author mayh
 * @version : 1.0
 * @date 2020/12/27
 **/
public interface IBaseData {

    String getId();

    void setId(String id);

    String getFcu();

    void setFcu(String fcu) ;

    String getFcuName();

    void setFcuName(String fcuName);

    Date getFcd();

    void setFcd(Date fcd);

    String getLcu();

    void setLcu(String lcu);

    String getLcuName();

    void setLcuName(String lcuName);

    Date getLcd();

    void setLcd(Date lcd);

    String getDelFlag();

    void setDelFlag(String delFlag);
}
