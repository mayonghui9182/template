package cn.net.mayh.util;

import cn.net.mayh.exception.BizException;
import cn.net.mayh.exception.ExceptionEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author mayh
 * @version : 1.0
 * @date 2020/12/26
 **/
@Slf4j
public class JSONUtils {
    private static ObjectMapper objectMapper;
    public static void setObjectMapper(ObjectMapper objectMapper){
        JSONUtils.objectMapper = objectMapper;
    }
    public static String toJSONString(Object o){
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw BizException.builBizException(ExceptionEnum.PARAMETER_ERROR,e);
        }
    }

    public static <T> T parseObject(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw BizException.builBizException(ExceptionEnum.PARAMETER_ERROR,e);
        } catch (IOException e) {
            throw BizException.builBizException(ExceptionEnum.PARAMETER_ERROR,e);
        }
    }

    public static <T> List<T> parseArray(String json, Class<T> clazz) {
        try {
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            return objectMapper.readValue(json,typeFactory.constructCollectionType(List.class,clazz));
        } catch (JsonProcessingException e) {
            throw BizException.builBizException(ExceptionEnum.PARAMETER_ERROR,e);
        } catch (IOException e) {
            throw BizException.builBizException(ExceptionEnum.PARAMETER_ERROR,e);
        }
    }
    public static <T> T parseObject(Resource resource, Class<T> clazz) {
        try{
            String json = FileUtils.readFileToString(resource.getFile());
            return parseObject(json,clazz);
        } catch (IOException e) {
            throw BizException.builBizException(ExceptionEnum.PARAMETER_ERROR,e);
        }
    }
    public static <T> List<T> parseArray(Resource resource, Class<T> clazz) {
        try{
            String json = FileUtils.readFileToString(resource.getFile());
            return parseArray(json, clazz);
        } catch (IOException e) {
            throw BizException.builBizException(ExceptionEnum.PARAMETER_ERROR,e);
        }
    }
}
