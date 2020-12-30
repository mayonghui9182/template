package cn.net.mayh.dto;

import lombok.Data;

/**
 * description: 前台统一返回结果封装类
 * @author : mayonghui
 * @date : 2020/12/25
 * @version : 1.0
 **/
@Data
public class ResultDto<T> {
    private static final long serialVersionUID = 1L;

    public static final String RESULT_SUCCES_CODE = "0000";

    public static final String RESULT_SUCCES_MSG = "操作成功";

    public static final String RESULT_FAIL_CODE = "0001";

    public static final String RESULT_OVER_CDE = "9999";

    public static final String RESULT_OVER_MSG = "连接超时";
    
    private String code;

    private String msg;

    private T data;

    public ResultDto() {}

    public ResultDto(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultDto(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * Description: 构建请求成功的返回对象，默认的成功代码和信息，不返回数据
     * @date 2020/12/25
     * return ResultDto: 返回对象
     * @author mayonghui
     **/
    public static <T> ResultDto<T> buildSuccess() {
        return new ResultDto<>(RESULT_SUCCES_CODE,RESULT_SUCCES_MSG);
    }
    /**
     * Description: 构建请求成功的返回对象，默认的成功代码和信息，并设置返回数据
     * @date 2020/12/25
     * return ResultDto: 返回对象
     * @author mayonghui
     **/
    public static <T> ResultDto<T> buildSuccess(T data) {
        return new ResultDto<>(RESULT_SUCCES_CODE,RESULT_SUCCES_MSG,data);
    }

    /**
     * Description: 构建请求成功的返回对象，默认的成功代码，设置返回消息和返回数据
     * @date 2020/12/25
     * return ResultDto: 返回对象
     * @author mayonghui
     **/
    public static <T> ResultDto<T> buildSuccessData(String msg, T data) {
        return new ResultDto<>(RESULT_SUCCES_CODE, msg, data);
    }


    /**
     * Description: 构建请求成功的返回对象，默认的成功代码，设置返回消息，不返回数据
     * @date 2020/12/25
     * return ResultDto: 返回对象
     * @author mayonghui
     **/
    public static <T> ResultDto<T> buildSuccess(String msg) {
        return new ResultDto<>(RESULT_SUCCES_CODE, msg);
    }

    /**
     * Description: 构建请求处理失败的返回对象，默认的失败代码，设置返回消息，不返回数据
     * @date 2020/12/25
     * return ResultDto: 返回对象
     * @author mayonghui
     **/
    public static <T> ResultDto<T> buildFailed(String msg) {
        return new ResultDto<>(RESULT_FAIL_CODE, msg);
    }

    /**
     * Description: 构建请求失败的返回对象，默认的失败代码，设置返回消息和返回数据
     * @date 2020/12/25
     * return ResultDto: 返回对象
     * @author mayonghui
     **/
    public static <T> ResultDto<T> buildFailed(String msg, T data) {
        return new ResultDto<>(RESULT_FAIL_CODE, msg, data);
    }

    /**
     * Description: 构建请求超市的返回对象，默认的超时代码，设置返回消息，不返回数据
     * @date 2020/12/25
     * return ResultDto: 返回对象
     * @author mayonghui
     **/
    public static <T> ResultDto<T> buildOverTimeData(String msg) {
        return new ResultDto<>(RESULT_OVER_CDE, msg);
    }

    /**
     * Description: 构建请求超市的返回对象，默认的超时代码，设置返回消息和返回数据
     * @date 2020/12/25
     * return ResultDto: 返回对象
     * @author mayonghui
     **/
    public static <T> ResultDto<T> buildOverTimeInfo(String msg, T data) {
        return new ResultDto<>(RESULT_OVER_CDE, msg, data);
    }

    /**
     * Description: 将其他状态的对象转换为成功状态的对象，默认的成功代码和消息，不重新设置返回数据
     * @date 2020/12/25
     * return ResultDto: 返回对象
     * @author mayonghui
     **/
    public void toSuccess() {
        this.code = RESULT_SUCCES_CODE;
        this.msg = RESULT_SUCCES_MSG;
    }

    /**
     * Description: 将其他状态的对象转换为成功状态的对象，默认的成功代码和消息，重新设置返回数据
     * @date 2020/12/25
     * return ResultDto: 返回对象
     * @author mayonghui
     **/
    public void toSuccess(T data) {
        this.code = RESULT_SUCCES_CODE;
        this.msg = RESULT_SUCCES_MSG;
        this.data = data;
    }
    /**
     * Description: 将其他状态的对象转换为成功状态的对象，默认的成功代码，重新设置返回消息，重新设置返回数据
     * @date 2020/12/25
     * return ResultDto: 返回对象
     * @author mayonghui
     **/
    public void toSuccessData(String msg, T data) {
        this.code = RESULT_SUCCES_CODE;
        this.msg = msg;
        this.data = data;
    }
    /**
     * Description: 将其他状态的对象转换为成功状态的对象，默认的成功代码，重新设置返回消息，不重新设置返回数据
     * @date 2020/12/25
     * return ResultDto: 返回对象
     * @author mayonghui
     **/
    public void toSuccessInfo(String msg) {
        this.code = RESULT_SUCCES_CODE;
        this.msg = msg;
    }

    /**
     * Description: 将其他状态的对象转换为失败状态的对象，默认的失败代码，重新设置返回消息，不重新设置返回数据
     * @date 2020/12/25
     * return ResultDto: 返回对象
     * @author mayonghui
     **/
    public void toFailed(String msg) {
        this.code = RESULT_FAIL_CODE;
        this.msg = msg;
    }

    /**
     * Description: 将其他状态的对象转换为失败状态的对象，默认的失败代码，重新设置返回消息，重新设置返回数据
     * @date 2020/12/25
     * return ResultDto: 返回对象
     * @author mayonghui
     **/
    public void toFailed(String msg, T data) {
        this.code = RESULT_FAIL_CODE;
        this.msg = msg;
        this.data = data;
    }

    /**
     * Description: 将其他状态的对象转换为超时状态的对象，默认的超时代码，重新设置返回消息，不重新设置返回数据
     * @date 2020/12/25
     * return ResultDto: 返回对象
     * @author mayonghui
     **/
    public void toOverTimeData(String msg) {
        this.code = RESULT_OVER_CDE;
        this.msg = msg;
    }

    /**
     * Description: 将其他状态的对象转换为超时状态的对象，默认的超时代码，重新设置返回消息，重新设置返回数据
     * @date 2020/12/25
     * return: ResultDto: 返回对象
     * @author mayonghui
     **/
    public void toOverTimeInfo(String msg, T data) {
        this.code = RESULT_OVER_CDE;
        this.msg = msg;
        this.data = data;
    }


}
