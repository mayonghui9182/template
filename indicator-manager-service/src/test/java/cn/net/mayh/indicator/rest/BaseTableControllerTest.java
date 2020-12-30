package cn.net.mayh.indicator.rest;

import cn.net.mayh.BaseTest;
import cn.net.mayh.indicator.po.BaseTablePO;
import cn.net.mayh.indicator.qo.BaseTableQO;
import cn.net.mayh.indicator.rest.BaseTableController;
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
 * BaseTable接口测试类
 *
 * @author mayh
 * @version 1.0
 */
public class BaseTableControllerTest extends BaseTest {
    @Autowired
    private BaseTableController apiController;
    private String url = "/BaseTable";

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
        BaseTableQO baseTableQO = genBean(BaseTableQO.class);
        String jsonString = JSONUtils.toJSONString(baseTableQO);
        Map<String, String> params = JSONUtils.parseObject(jsonString, Map.class);
        testGetSequence(url + "/get", params);
    }

    @Test
    public void find(){
        BaseTableQO baseTableQO = genBean(BaseTableQO.class);
        testPostSequence(url + "/findPage", baseTableQO, null);
    }
    @Test
    public void findAllList(){
        BaseTableQO baseTableQO = genBean(BaseTableQO.class);
        testPostSequence(url + "/findAllList", baseTableQO, null);
    }

    @Test
    public void insert(){
        BaseTablePO baseTablePO = genBean(BaseTablePO.class);
        testPostSequence(url + "/insert", baseTablePO, null);
    }

    @Test
    public void insertBatch(){
        BaseTablePO baseTablePO = genBean(BaseTablePO.class);
        List<BaseTablePO> baseTablePOS = new ArrayList<>();
        baseTablePOS.add(baseTablePO);
        testPostSequence(url + "/insertBatch", baseTablePOS, null);
    }

    @Test
    public void updateById(){
        BaseTablePO baseTablePO = genBean(BaseTablePO.class);
        testPostSequence(url + "/updateById", baseTablePO, null);
    }

    @Test
    public void update(){
        BaseTablePO baseTablePO = genBean(BaseTablePO.class);
        BaseTableQO baseTableQO = genBean(BaseTableQO.class);
        String jsonString = JSONUtils.toJSONString(baseTableQO);
        Map<String, String> params = JSONUtils.parseObject(jsonString, Map.class);
        testPostSequence(url + "/update", baseTablePO, params);
    }

    @Test
    public void updateNullAbleById(){
        BaseTablePO baseTablePO = genBean(BaseTablePO.class);
        testPostSequence(url + "/updateNullAbleById", baseTablePO, null);
    }

    @Test
    public void updateNullAble(){
        BaseTablePO baseTablePO = genBean(BaseTablePO.class);
        BaseTableQO baseTableQO = genBean(BaseTableQO.class);
        String jsonString = JSONUtils.toJSONString(baseTableQO);
        Map<String, String> params = JSONUtils.parseObject(jsonString, Map.class);
        testPostSequence(url + "/updateNullAble", baseTablePO, params);
    }

    @Test
    public void deleteLogincById() {
        Map<String, String> params = new HashMap<>();
        params.put("id", "id");
        testPostSequence(url + "/deleteLogincById", null, params);
    }

    @Test
    public void deleteLoginc(){
        BaseTableQO baseTableQO = genBean(BaseTableQO.class);
        testPostSequence(url + "/deleteLoginc", baseTableQO, null);
    }
}