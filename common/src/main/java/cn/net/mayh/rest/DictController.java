package cn.net.mayh.rest;

import cn.net.mayh.dto.DictDto;
import cn.net.mayh.service.DictService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * description: 字典类型的查询接口
 * @author : mayonghui
 * @date : 2020/12/25
 * @version : 1.0
 **/
@Slf4j
@CrossOrigin
@RestController
@RequestMapping(path = "/api/v1/comm/dict", produces = "application/json;charset=utf-8")
public class DictController {

    @Autowired
    private DictService dictService;

    /**
     * @author mayonghui
     * Description:
     * @date 2020/12/25
     * @param condition : 字典参数
     * @return : DictDto
     * throws:
     **/
    @ApiOperation(value = "获取字典信息", notes = "")
    @PostMapping("getDict")
    public List<DictDto> getDict(@RequestBody DictDto condition) {
        return dictService.getDict(condition);
    }


}
