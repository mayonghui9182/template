package cn.net.mayh.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * description: 异常处理类
 * @author : mayonghui
 * @version : 1.0
 * @date : 2020/12/25
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class BizException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private String code;

    public BizException() {}

    public BizException(String code, String msg, Throwable cause) {
        super(msg,cause);
        this.code = code;
    }
    public BizException(String code, String msg) {
        super(msg);
        this.code = code;
    }

    public static BizException builBizException(ExceptionEnum exceptionEnum) {
        return new BizException(exceptionEnum.getCode(),exceptionEnum.getMsg());
    }
    public static BizException builBizException(ExceptionEnum exceptionEnum,String msg) {
        return new BizException(exceptionEnum.getCode(),msg);
    }

    public static BizException builBizException(ExceptionEnum exceptionEnum, Throwable cause) {
        return new BizException(exceptionEnum.getCode(),exceptionEnum.getMsg(),cause);
    }
    public static BizException builBizException(ExceptionEnum exceptionEnum, String msg, Throwable cause) {
        return new BizException(exceptionEnum.getCode(),msg,cause);
    }
    public static BizException builBizException(String code, String msg){
        return new BizException(code,msg);
    }
    public static BizException builBizException(String code, String msg, Throwable cause){
        return new BizException(code,msg,cause);
    }
}
