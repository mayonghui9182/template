package cn.net.mayh;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <li>创建人：mayonghui
 * <li>创建时间：2018/12/29 16:46
 * <li>创建目的：【SpringBoot测试基类】
 * <li>修改目的：【修改人：修改目的，修改时间】
 **/
//加载spring运行环境
@RunWith(SpringJUnit4ClassRunner.class)
//加载springBoot运行环境
@SpringBootTest(
        classes = DevBootStrap.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("local")
public class BaseTestClass {
    public final static boolean START_TEST = true;

    @Before
    public void before(){
        System.out.println("-----------开始测试-----------");
    }
    @After
    public void after(){
        System.out.println("-----------结束测试-----------");
    }
}
