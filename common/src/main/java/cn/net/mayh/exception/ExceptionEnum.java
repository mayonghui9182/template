package cn.net.mayh.exception;

import lombok.Getter;

/**
 * 业务异常枚举 便于维护
 *
 * @author duyq06
 */
@Getter
public enum ExceptionEnum {
  /**
   * 成功代码和状态
   **/
  SUCCESS("00", "成功"),
  /**
   * 未知错误
   **/
  UNKONW_ERROR("-1", "未知错误"),
  /**
   * 查询参数为空或null
   **/
  PARAMETER_ISNULL_ERROR("-2", "查询参数为空或null"),
  /**
   * 用户名或密码不正确
   **/
  LOGIN_ERROR("-3", "用户名或密码不正确"),
  QUERY_EMPTY("-4", "查询结果为空"),
  MENU_CHILDREN_EXISTS("-5", "该菜单存在子菜单"),
  NOT_SAVE("-6", "保存结果失败"),
  DATABIND_ERROR("-7", "数据绑定校验错误"),
  GETMETHOD_ERROR("-8", "aop获取执行方法失败"),
  PWD_LOG_DUPLICATION("-9", "密码与前三次密码一致"),
  PWD_ERROR("-10", "密码输入错误"),
  DATA_MISS("-11", "数据操作，参数缺失"),
  INVOKE_FAILED("-12", "方法调用失败，未知错误"),
  RESOURCE_LOCATION_FAILED("-13", "资源定位失败"),
  PARAMETER_ERROR("-14", "参数错误"),
  NOAUTH_ERROR("-98", "无权限"),
  ACCESSDENIED_ERROR("-99", "权限不足");

  private final String code;

  private final String msg;

  ExceptionEnum(String code, String msg) {
    this.code = code;
    this.msg = msg;
  }

}
