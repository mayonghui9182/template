package cn.net.mayh.util;

import java.util.Collection;
import java.util.Map;

/**
 * Description: 集合工具包
 * @date 2020/12/26
 * @author  mayh
 **/
public class CollectionUtils {
    /**
    *  判断集合是否为null或空
    **/
    public static boolean isEmpty(Collection collection){
        return collection == null || collection.size() == 0;
    }
    /**
     *  判断map是否为null或空
     **/
    public static boolean isEmpty(Map map){
        return map == null || map.size() == 0;
    }
}
