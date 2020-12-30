package cn.net.mayh.util;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;


/**
 * Description: 字符串处理函数工具类
 * @author mayh
 * @version : 1.0
 * @date 2020/12/26
 **/
public class MyStringUtils {

    private static final char SEPARATOR = '_';

    /**
     * 转换为字节数组
     */
    public static byte[] getBytes(String str) {
        if (str != null) {
            return str.getBytes(Charsets.UTF_8);
        } else {
            return null;
        }
    }

    /**
     * 转换为字节数组
     */
    public static String toString(byte[] bytes) {
        return new String(bytes, Charsets.UTF_8);
    }

    /**
     * 字符串组strs是否包含字符串str,忽略前后空格
     * @param str  验证字符串
     * @param strs 字符串组
     * @return 包含返回true
     */
    public static boolean inString(String str, String... strs) {
        if (str != null) {
            for (String s : strs) {
                if (trim(str).equals(trim(s))) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String trim(String str) {
        return org.apache.commons.lang3.StringUtils.trim(str);
    }

    /**
     * 替换掉HTML标签方法
     */
    public static String replaceHtml(String html) {
        if (isBlank(html)) {
            return "";
        }
        String regEx = "<.+?>";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(html);
        String s = m.replaceAll("");
        return s;
    }

    /**
     * 替换为手机识别的HTML，去掉样式及属性，保留回车。
     *
     * @param html
     * @return
     */
    public static String replaceMobileHtml(String html) {
        if (html == null) {
            return "";
        }
        return html.replaceAll("<([a-z]+?)\\s+?.*?>", "<$1>");
    }


    public static String abbr2(String param, int length) {
        if (param == null) {
            return "";
        }
        StringBuffer result = new StringBuffer();
        int n = 0;
        char temp;
        boolean isCode = false; // 是不是HTML代码
        boolean isHTML = false; // 是不是HTML特殊字符,如&nbsp;
        for (int i = 0; i < param.length(); i++) {
            temp = param.charAt(i);
            if (temp == '<') {
                isCode = true;
            } else if (temp == '&') {
                isHTML = true;
            } else if (temp == '>' && isCode) {
                n = n - 1;
                isCode = false;
            } else if (temp == ';' && isHTML) {
                isHTML = false;
            }
            try {
                if (!isCode && !isHTML) {
                    n += String.valueOf(temp).getBytes("GBK").length;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if (n <= length - 3) {
                result.append(temp);
            } else {
                result.append("...");
                break;
            }
        }
        // 取出截取字符串中的HTML标记
        String temp_result = result.toString().replaceAll("(>)[^<>]*(<?)",
                "$1$2");
        // 去掉不需要结素标记的HTML标记
        temp_result = temp_result
                .replaceAll(
                        "</?(AREA|BASE|BASEFONT|BODY|BR|COL|COLGROUP|DD|DT|FRAME|HEAD|HR|HTML|IMG|INPUT|ISINDEX|LI|LINK|META|OPTION|P|PARAM|TBODY|TD|TFOOT|TH|THEAD|TR|area|base|basefont|body|br|col|colgroup|dd|dt|frame|head|hr|html|img|input|isindex|li|link|meta|option|p|param|tbody|td|tfoot|th|thead|tr)[^<>]*/?>",
                        "");
        // 去掉成对的HTML标记
        temp_result = temp_result.replaceAll("<([a-zA-Z]+)[^<>]*>(.*?)</\\1>",
                "$2");
        // 用正则表达式取出标记
        Pattern p = Pattern.compile("<([a-zA-Z]+)[^<>]*>");
        Matcher m = p.matcher(temp_result);
        List<String> endHTML = Lists.newArrayList();
        while (m.find()) {
            endHTML.add(m.group(1));
        }
        // 补全不成对的HTML标记
        for (int i = endHTML.size() - 1; i >= 0; i--) {
            result.append("</");
            result.append(endHTML.get(i));
            result.append(">");
        }
        return result.toString();
    }

    /**
     * 转换为Double类型
     */
    public static Double toDouble(Object val) {
        if (val == null) {
            return 0D;
        }
        try {
            return Double.valueOf(trim(val.toString()));
        } catch (Exception e) {
            return 0D;
        }
    }

    /**
     * 转换为Float类型
     */
    public static Float toFloat(Object val) {
        return toDouble(val).floatValue();
    }

    /**
     * 转换为Long类型
     */
    public static Long toLong(Object val) {
        return toDouble(val).longValue();
    }

    /**
     * 转换为Integer类型
     */
    public static Integer toInteger(Object val) {
        return toLong(val).intValue();
    }


    /**
     * 获得用户远程地址
     */
    public static String getRemoteAddr(HttpServletRequest request) {
        String remoteAddr = request.getHeader("X-Real-IP");
        if (isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("X-Forwarded-For");
        } else if (isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("Proxy-Client-IP");
        } else if (isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("WL-Proxy-Client-IP");
        }
        return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
    }

    public static boolean isNotBlank(String str) {
        return org.apache.commons.lang3.StringUtils.isNotBlank(str);
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase("hello_world") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }

        s = s.toLowerCase();

        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase("hello_world") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toCapitalizeCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = toCamelCase(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase("hello_world") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toUnderScoreCase(String s) {
        if (s == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            boolean nextUpperCase = true;

            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }

            if ((i > 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    sb.append(SEPARATOR);
                }
                upperCase = true;
            } else {
                upperCase = false;
            }

            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    /**
     * 转换为JS获取对象值，生成三目运算返回结果
     *
     * @param objectString 对象串
     *                     例如：row.user.id
     *                     返回：!row?'':!row.user?'':!row.user.id?'':row.user.id
     */
    public static String jsGetVal(String objectString) {
        StringBuilder result = new StringBuilder();
        StringBuilder val = new StringBuilder();
        String[] vals = split(objectString, ".");
        for (String val1 : vals) {
            val.append(".").append(val1);
            result.append("!").append(val.substring(1)).append("?'':");
        }
        result.append(val.substring(1));
        return result.toString();
    }

    public static String[] split(String str, String separatorChars) {
        return org.apache.commons.lang3.StringUtils.split(str,separatorChars);
    }

    /**
     * cell单元格下标转换
     */
    public static int getCell(String cellStr) {
        char tempChar;
        char [] tempCharArr = cellStr.toCharArray();
        int len = tempCharArr.length;
        double sum = 0;
        double temp = 0;
        for (int i = len, j = 0; i > 0; i--, j++) {
            tempChar = tempCharArr[j];
            temp = Math.pow(26, i - 1);
            temp = (tempChar - 96) * temp;
            sum += temp;
        }
        return (int) sum;
    }


    /**
     * 对象转换为字符串
     * @param o
     * @return
     */
    public static String convertNullToString(Object o)
    {
        if(o == null)
            return "";
        else
            return o.toString().trim();
    }

    /**
     * 获取信息披露季度信息
     * @param date
     * @return
     */
    public static String getQuarterByCurDate(Date date) {
        String quarter = "";
        String curMonthDay = DateUtils.formatDate(date, "MMdd");
        int curMonthDayInt = Integer.parseInt(curMonthDay);
        if (101 <= curMonthDayInt && curMonthDayInt <= 331) { // 第一季度1月1号到3月31号
            quarter = "0";
        } else if (401 <= curMonthDayInt && curMonthDayInt <= 630) {//第二季度
            quarter = "1";
        } else if (701 <= curMonthDayInt && curMonthDayInt <= 930) {//第三季度
            quarter = "2";
        } else if (1001 <= curMonthDayInt && curMonthDayInt <= 1231) {//年度
            quarter = "3";
        }
        return quarter;
    }

    /**
    * Description: 判断是否是数值，包含小数
    * @date 2020/12/26
    * @author  mayh
    * @param str :
    * @return : boolean
    **/
    public static boolean isNum(String str) {
        if (str == null) {
            return false;
        }
        String reg = "[+|-]?[0-9]+(.[0-9]+)?$";
        return str.matches(reg);
    }
    /**
    * Description: 提出数组中null值
    * @date 2020/12/26
    * @author  mayh
    * @param objs :
    * @return : java.lang.String[]
    **/
    public static String[] wipeOfNull(String[] objs) {
        return Stream.of(objs).filter(Objects::nonNull).toArray(String[]::new);
    }

    /**
    * Description: 给字符串前边补0
    * @date 2020/12/26
    * @author  mayh
    * @param str :
	* @param totalSize :
    * @return : java.lang.String
    **/
    public static String addZero(String str, int totalSize) {
        if (str == null) {
            str = "";
        }
        int length = str.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < totalSize - length; i++) {
            sb.append("0");
        }
        return sb.append(str).toString();
    }

    public static boolean isBlank(String str) {
        return org.apache.commons.lang3.StringUtils.isEmpty(str);
    }

    public static boolean isNoneBlank(String begin, String end) {
        return org.apache.commons.lang3.StringUtils.isNoneBlank(begin,end);
    }
}
