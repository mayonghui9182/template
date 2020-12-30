package cn.net.mayh;

/**
 * Created by itw_liuzhou02 on 2018/12/11.
 */
public class Constants {



    private Constants() {
    }

    // 字符编码
    public static final String CHARSET_UTF8 = "utf-8";
    /**
     * 删除标记（0：正常；1：删除；2：审核；）
     */
    public static final String DEL_FLAG_NORMAL = "0";
    public static final String DEL_FLAG_DELETE = "1";
    public static final String DEL_FLAG_AUDIT = "2";

    //Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_'.@A-Za-z0-9-]*$";

    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String UPLOAD_IMAGE_PATH = "/upload/image";
    public static final String REQUEST_METHOD_OPTIONS = "OPTIONS";
    //请求时token还差不到5min时刷新token
    public static final long TOKEN_REFRESH_TIME = 5 * 60 * 1000;
}
