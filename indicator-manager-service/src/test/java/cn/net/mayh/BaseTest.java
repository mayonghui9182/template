package cn.net.mayh;

import cn.net.mayh.dto.ResultDto;
import cn.net.mayh.exception.BizException;
import cn.net.mayh.exception.ExceptionEnum;
import cn.net.mayh  .IndicatorBootStrap;
import cn.net.mayh.util.JSONUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
//加载springBoot运行环境
@SpringBootTest(
        classes = IndicatorBootStrap.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("local")
public class BaseTest {
    protected MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    public static <T> T genBean(Class<T> clazz) {
        T o = null;
        try {
            o = clazz.newInstance();
            Class<?> supperClass = clazz;
            while (supperClass !=null ) {
                Field[] fields = supperClass.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (field.getType() == String.class) {
                        field.set(o, field.getName());
                    } else if (field.getType() == BigDecimal.class) {
                        field.set(o, new BigDecimal("234.234"));
                    } else if (field.getType() == Date.class) {
                        field.set(o, new Date());
                    }
                }
                supperClass = supperClass.getSuperclass();
            }
        } catch (InstantiationException e) {
            throw BizException.builBizException(ExceptionEnum.INVOKE_FAILED,e);
        } catch (IllegalAccessException e) {
            throw BizException.builBizException(ExceptionEnum.INVOKE_FAILED,e);
        }
        return o;
    }
    protected void testGetSequence(String url, Map<String,String> params) {
        try {
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(url);
            if(!CollectionUtils.isEmpty(params)) {
                params.forEach(requestBuilder::param);
            }
            MvcResult mvcResult = mockMvc.perform(requestBuilder)
                    .andExpect(MockMvcResultMatchers.status().is(200))
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
            int status = mvcResult.getResponse().getStatus();
            System.out.println("请求状态码：" + status);
            String result = mvcResult.getResponse().getContentAsString();
            System.out.println("接口返回结果：" + result);
            ResultDto<?> resultObj = JSONUtils.parseObject(result,ResultDto.class);
            Assert.assertNotNull(resultObj);
            Assert.assertEquals(ResultDto.RESULT_SUCCES_CODE, resultObj.getCode());
        } catch (Exception e) {
            throw BizException.builBizException(ExceptionEnum.INVOKE_FAILED,e);
        }
    }
    protected void testPostSequence(String url,Object body,Map<String,String> params) {
        try {
            String content = JSONUtils.toJSONString(body);
            System.out.println(content);
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON)
                    .content(content).accept(MediaType.APPLICATION_JSON);
            if(!CollectionUtils.isEmpty(params)) {
                params.forEach(requestBuilder::param);
            }
            MvcResult mvcResult = mockMvc.perform(requestBuilder)
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().is(200))
                    .andReturn();
            String result = mvcResult.getResponse().getContentAsString();
            System.out.println("接口返回结果：" + result);
            ResultDto<?> resultObj = JSONUtils.parseObject(result,ResultDto.class);
            Assert.assertNotNull(resultObj);
            Assert.assertEquals(ResultDto.RESULT_SUCCES_CODE, resultObj.getCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
