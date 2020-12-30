package cn.net.mayh.indicator.rest;

import cn.net.mayh.dto.ResultDto;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import cn.net.mayh.indicator.service.IBaseTableService;
import cn.net.mayh.indicator.dto.BaseTableDto;
import cn.net.mayh.indicator.po.BaseTablePO;
import cn.net.mayh.indicator.qo.BaseTableQO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * BaseTable的接口
 *
 * @author mayh
 * @version 1.0
 */
@Api(value = "测试表接口", tags = {"测试表接口"})
@RestController
@RequestMapping(value = "/BaseTable",produces = { "application/json;charset=UTF-8"})
public class BaseTableController {

    @Autowired
    private IBaseTableService baseTableService;

    @ApiOperation(value = "根据条件查询单个测试表")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultDto get(BaseTableQO baseTableQO) {
        BaseTableDto baseTableDto = baseTableService.get(baseTableQO);
        return ResultDto.buildSuccess(baseTableDto);
    }

    @ApiOperation(value = "根据id查询测试表")
    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    public ResultDto getById(@RequestParam String id) {
        BaseTableDto baseTableDto = baseTableService.get(id);
        return ResultDto.buildSuccess(baseTableDto);
    }

    @ApiOperation(value = "根据测试表查询条件查询,分页查询")
    @RequestMapping(value = "/findPage", method = RequestMethod.POST)
    public ResultDto findPage(@RequestParam(required = false,defaultValue = "1") int pageNo, @RequestParam(required = false,defaultValue = "20") int pageSize, @RequestBody @ApiParam("查询条件") BaseTableQO baseTableQO) {
        Page<BaseTableDto> baseTableDtoPage = baseTableService.findPage(pageNo, pageSize, baseTableQO);
        return ResultDto.buildSuccess(baseTableDtoPage);
    }

    @ApiOperation(value = "根据测试表查询条件查询全部数据")
    @RequestMapping(value = "/findAllList", method = RequestMethod.POST)
    public ResultDto find(@RequestBody @ApiParam("查询条件") BaseTableQO baseTableQO) {
    List<BaseTableDto> baseTableDtos = baseTableService.findAllList(baseTableQO);
        return ResultDto.buildSuccess(baseTableDtos);
    }

    @ApiOperation(value = "插入测试表")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResultDto insert(@RequestBody @ApiParam("要插入的测试表对象") BaseTablePO baseTablePO) {
       baseTableService.insert(baseTablePO);
       return ResultDto.buildSuccess();
    }

    @ApiOperation(value = "批量插入测试表")
    @RequestMapping(value = "/insertBatch", method = RequestMethod.POST)
    public ResultDto insertBatch(@RequestBody @ApiParam("要插入的测试表对象数组") List<BaseTablePO> baseTablePOs) {
        baseTableService.bulkInsert(baseTablePOs);
        return ResultDto.buildSuccess();
    }

    @ApiOperation(value = "根据id更新测试表")
    @RequestMapping(value = "/updateById", method = RequestMethod.POST)
    public ResultDto updateById(@RequestBody @ApiParam("要更新的测试表对象，主键不能为空") BaseTablePO baseTablePO) {
        baseTableService.updateById(baseTablePO);
        return ResultDto.buildSuccess();
    }

    @ApiOperation(value = "根据条件更新测试表")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultDto update(@RequestBody @ApiParam("要更新的测试表对象") BaseTablePO baseTablePO, @ApiParam("更新条件") BaseTableQO baseTableQO) {
        baseTableService.update(baseTablePO, baseTableQO);
        return ResultDto.buildSuccess();
    }

    @ApiOperation(value = "根据id动态更新测试表")
    @RequestMapping(value = "/updateNullAbleById", method = RequestMethod.POST)
    public ResultDto updateNullAbleById(@RequestBody @ApiParam("要更新的测试表对象") BaseTablePO baseTablePO) {
        baseTableService.updateNullAbleById(baseTablePO);
        return ResultDto.buildSuccess();
    }

    @ApiOperation(value = "根据id动态更新测试表")
    @RequestMapping(value = "/updateNullAble", method = RequestMethod.POST)
    public ResultDto updateNullAble(@RequestBody @ApiParam("要更新的测试表对象") BaseTablePO baseTablePO, @ApiParam("更新条件") BaseTableQO baseTableQO) {
        baseTableService.updateNullAble(baseTablePO, baseTableQO);
        return ResultDto.buildSuccess();
    }

    @ApiOperation(value = "根据id删除测试表")
    @RequestMapping(value = "/deleteLogincById", method = RequestMethod.POST)
    public ResultDto deleteLogincById(@RequestParam @ApiParam("要更新的测试表对象，带着主键") String id) {
        baseTableService.deleteLogincById(id);
        return ResultDto.buildSuccess();
    }

    @ApiOperation(value = "根据id动态删除测试表")
    @RequestMapping(value = "/deleteLoginc", method = RequestMethod.POST)
    public ResultDto deleteLoginc(@RequestBody @ApiParam("要更新的测试表对象") BaseTableQO baseTableQO) {
        baseTableService.deleteLoginc(baseTableQO);
        return ResultDto.buildSuccess();
    }
}
