package cn.net.mayh.jackson.date;

import cn.net.mayh.util.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Description:
 *
 * @author mayh
 * @version : 1.0
 * @date 2020/12/29
 **/
@JsonComponent
public class DateSerializer extends JsonSerializer<Date> implements ContextualSerializer {
    private String format = "yyyy-MM-dd HH:mm:ss";

    @Override
    public void serialize(Date date, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(DateUtils.format(date, format));
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty beanProperty) throws JsonMappingException {
        if(beanProperty !=null ){
            if(Objects.equals(beanProperty.getType().getRawClass(),Date.class)){
                JsonFormat jsonFormat = beanProperty.getAnnotation((JsonFormat.class));
                if(jsonFormat == null){
                    jsonFormat = beanProperty.getContextAnnotation(JsonFormat.class);
                }
                DateSerializer dateSerializer = new DateSerializer();
                if(jsonFormat != null){
                    dateSerializer.format = jsonFormat.pattern();
                }
                return dateSerializer;
            }
            return prov.findValueSerializer(beanProperty.getType(),beanProperty);
        }
        return prov.findNullValueSerializer(beanProperty);
    }
}
