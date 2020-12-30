package cn.net.mayh.jackson.date;

import cn.net.mayh.exception.BizException;
import cn.net.mayh.exception.ExceptionEnum;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description:
 *
 * @author mayh
 * @version : 1.0
 * @date 2020/12/29
 **/
@JsonComponent
public class DateDeSerializer extends JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonParser jsonParser,
                            DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = jsonParser.getText();
        try {
            return format.parse(date);
        } catch (ParseException e) {
            throw BizException.builBizException(ExceptionEnum.PARAMETER_ERROR,e);
        }
    }
}