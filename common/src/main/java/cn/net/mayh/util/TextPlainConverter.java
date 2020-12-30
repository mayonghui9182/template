package cn.net.mayh.util;/**
 * Created by itw_lizy12 on 2019/6/10.
 */

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * 解析Feign返回为【text/plain】的数据
 */
public class TextPlainConverter extends MappingJackson2HttpMessageConverter {
	public TextPlainConverter(){
		List<MediaType> mediaTypes = new ArrayList<>();
		mediaTypes.add(MediaType.TEXT_PLAIN);
		setSupportedMediaTypes(mediaTypes);
	}
}
