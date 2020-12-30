package ${tableInfo.packageName}.rest;

import cn.net.mayh.BaseTest;
import ${tableInfo.packageName}.po.${tableInfo.beanName}PO;
import ${tableInfo.packageName}.qo.${tableInfo.beanName}QO;
import ${tableInfo.packageName}.rest.${tableInfo.beanName}Controller;
import cn.net.mayh.util.JSONUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ${tableInfo.beanName}接口测试类
 *
 * @author mayh
 * @version 1.0
 */
public class ${tableInfo.beanName}ControllerTest extends BaseTest {
    @Autowired
    private ${tableInfo.beanName}Controller apiController;
    private String url = "/${tableInfo.beanName}";

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(apiController).build();
    }

    @Test
    public void getById() {
        Map<String, String> params = new HashMap<>();
        params.put("id", "id");
        testGetSequence(url + "/getById", params);
    }

    @Test
    public void get(){
        ${tableInfo.beanName}QO ${tableInfo.beanNameOfLowerFirst}QO = genBean(${tableInfo.beanName}QO.class);
        String jsonString = JSONUtils.toJSONString(${tableInfo.beanNameOfLowerFirst}QO);
        Map<String, String> params = JSONUtils.parseObject(jsonString, Map.class);
        testGetSequence(url + "/get", params);
    }

    @Test
    public void find(){
        ${tableInfo.beanName}QO ${tableInfo.beanNameOfLowerFirst}QO = genBean(${tableInfo.beanName}QO.class);
        testPostSequence(url + "/findPage", ${tableInfo.beanNameOfLowerFirst}QO, null);
    }
    @Test
    public void findAllList(){
        ${tableInfo.beanName}QO ${tableInfo.beanNameOfLowerFirst}QO = genBean(${tableInfo.beanName}QO.class);
        testPostSequence(url + "/findAllList", ${tableInfo.beanNameOfLowerFirst}QO, null);
    }

    @Test
    public void insert(){
        ${tableInfo.beanName}PO ${tableInfo.beanNameOfLowerFirst}PO = genBean(${tableInfo.beanName}PO.class);
        testPostSequence(url + "/insert", ${tableInfo.beanNameOfLowerFirst}PO, null);
    }

    @Test
    public void insertBatch(){
        ${tableInfo.beanName}PO ${tableInfo.beanNameOfLowerFirst}PO = genBean(${tableInfo.beanName}PO.class);
        List<${tableInfo.beanName}PO> ${tableInfo.beanNameOfLowerFirst}POS = new ArrayList<>();
        ${tableInfo.beanNameOfLowerFirst}POS.add(${tableInfo.beanNameOfLowerFirst}PO);
        testPostSequence(url + "/insertBatch", ${tableInfo.beanNameOfLowerFirst}POS, null);
    }

    @Test
    public void updateById(){
        ${tableInfo.beanName}PO ${tableInfo.beanNameOfLowerFirst}PO = genBean(${tableInfo.beanName}PO.class);
        testPostSequence(url + "/updateById", ${tableInfo.beanNameOfLowerFirst}PO, null);
    }

    @Test
    public void update(){
        ${tableInfo.beanName}PO ${tableInfo.beanNameOfLowerFirst}PO = genBean(${tableInfo.beanName}PO.class);
        ${tableInfo.beanName}QO ${tableInfo.beanNameOfLowerFirst}QO = genBean(${tableInfo.beanName}QO.class);
        String jsonString = JSONUtils.toJSONString(${tableInfo.beanNameOfLowerFirst}QO);
        Map<String, String> params = JSONUtils.parseObject(jsonString, Map.class);
        testPostSequence(url + "/update", ${tableInfo.beanNameOfLowerFirst}PO, params);
    }

    @Test
    public void updateNullAbleById(){
        ${tableInfo.beanName}PO ${tableInfo.beanNameOfLowerFirst}PO = genBean(${tableInfo.beanName}PO.class);
        testPostSequence(url + "/updateNullAbleById", ${tableInfo.beanNameOfLowerFirst}PO, null);
    }

    @Test
    public void updateNullAble(){
        ${tableInfo.beanName}PO ${tableInfo.beanNameOfLowerFirst}PO = genBean(${tableInfo.beanName}PO.class);
        ${tableInfo.beanName}QO ${tableInfo.beanNameOfLowerFirst}QO = genBean(${tableInfo.beanName}QO.class);
        String jsonString = JSONUtils.toJSONString(${tableInfo.beanNameOfLowerFirst}QO);
        Map<String, String> params = JSONUtils.parseObject(jsonString, Map.class);
        testPostSequence(url + "/updateNullAble", ${tableInfo.beanNameOfLowerFirst}PO, params);
    }

    @Test
    public void deleteLogincById() {
        Map<String, String> params = new HashMap<>();
        params.put("id", "id");
        testPostSequence(url + "/deleteLogincById", null, params);
    }

    @Test
    public void deleteLoginc(){
        ${tableInfo.beanName}QO ${tableInfo.beanNameOfLowerFirst}QO = genBean(${tableInfo.beanName}QO.class);
        testPostSequence(url + "/deleteLoginc", ${tableInfo.beanNameOfLowerFirst}QO, null);
    }
}