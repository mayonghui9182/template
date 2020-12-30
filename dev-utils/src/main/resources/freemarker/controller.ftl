package ${tableInfo.packageName}.rest;

import cn.net.mayh.dto.ResultDto;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import ${tableInfo.packageName}.service.I${tableInfo.beanName}Service;
import ${tableInfo.packageName}.dto.${tableInfo.beanName}Dto;
import ${tableInfo.packageName}.po.${tableInfo.beanName}PO;
import ${tableInfo.packageName}.qo.${tableInfo.beanName}QO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ${tableInfo.beanName}的接口
 *
 * @author mayh
 * @version 1.0
 */
@Api(value = "${tableInfo.desc}接口", tags = {"${tableInfo.desc}接口"})
@RestController
@RequestMapping(value = "/${tableInfo.beanName}",produces = { "application/json;charset=UTF-8"})
public class ${tableInfo.beanName}Controller {

    @Autowired
    private I${tableInfo.beanName}Service ${tableInfo.beanNameOfLowerFirst}Service;

    @ApiOperation(value = "根据条件查询单个${tableInfo.desc}")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultDto get(${tableInfo.beanName}QO ${tableInfo.beanNameOfLowerFirst}QO) {
        ${tableInfo.beanName}Dto ${tableInfo.beanNameOfLowerFirst}Dto = ${tableInfo.beanNameOfLowerFirst}Service.get(${tableInfo.beanNameOfLowerFirst}QO);
        return ResultDto.buildSuccess(${tableInfo.beanNameOfLowerFirst}Dto);
    }

    @ApiOperation(value = "根据id查询${tableInfo.desc}")
    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    public ResultDto getById(@RequestParam String id) {
        ${tableInfo.beanName}Dto ${tableInfo.beanNameOfLowerFirst}Dto = ${tableInfo.beanNameOfLowerFirst}Service.get(id);
        return ResultDto.buildSuccess(${tableInfo.beanNameOfLowerFirst}Dto);
    }

    @ApiOperation(value = "根据${tableInfo.desc}查询条件查询,分页查询")
    @RequestMapping(value = "/findPage", method = RequestMethod.POST)
    public ResultDto findPage(@RequestParam(required = false,defaultValue = "1") int pageNo, @RequestParam(required = false,defaultValue = "20") int pageSize, @RequestBody @ApiParam("查询条件") ${tableInfo.beanName}QO ${tableInfo.beanNameOfLowerFirst}QO) {
        Page<${tableInfo.beanName}Dto> ${tableInfo.beanNameOfLowerFirst}DtoPage = ${tableInfo.beanNameOfLowerFirst}Service.findPage(pageNo, pageSize, ${tableInfo.beanNameOfLowerFirst}QO);
        return ResultDto.buildSuccess(${tableInfo.beanNameOfLowerFirst}DtoPage);
    }

    @ApiOperation(value = "根据${tableInfo.desc}查询条件查询全部数据")
    @RequestMapping(value = "/findAllList", method = RequestMethod.POST)
    public ResultDto find(@RequestBody @ApiParam("查询条件") ${tableInfo.beanName}QO ${tableInfo.beanNameOfLowerFirst}QO) {
    List<${tableInfo.beanName}Dto> ${tableInfo.beanNameOfLowerFirst}Dtos = ${tableInfo.beanNameOfLowerFirst}Service.findAllList(${tableInfo.beanNameOfLowerFirst}QO);
        return ResultDto.buildSuccess(${tableInfo.beanNameOfLowerFirst}Dtos);
    }

    @ApiOperation(value = "插入${tableInfo.desc}")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResultDto insert(@RequestBody @ApiParam("要插入的${tableInfo.desc}对象") ${tableInfo.beanName}PO ${tableInfo.beanNameOfLowerFirst}PO) {
       ${tableInfo.beanNameOfLowerFirst}Service.insert(${tableInfo.beanNameOfLowerFirst}PO);
       return ResultDto.buildSuccess();
    }

    @ApiOperation(value = "批量插入${tableInfo.desc}")
    @RequestMapping(value = "/insertBatch", method = RequestMethod.POST)
    public ResultDto insertBatch(@RequestBody @ApiParam("要插入的${tableInfo.desc}对象数组") List<${tableInfo.beanName}PO> ${tableInfo.beanNameOfLowerFirst}POs) {
        ${tableInfo.beanNameOfLowerFirst}Service.bulkInsert(${tableInfo.beanNameOfLowerFirst}POs);
        return ResultDto.buildSuccess();
    }

    @ApiOperation(value = "根据id更新${tableInfo.desc}")
    @RequestMapping(value = "/updateById", method = RequestMethod.POST)
    public ResultDto updateById(@RequestBody @ApiParam("要更新的${tableInfo.desc}对象，主键不能为空") ${tableInfo.beanName}PO ${tableInfo.beanNameOfLowerFirst}PO) {
        ${tableInfo.beanNameOfLowerFirst}Service.updateById(${tableInfo.beanNameOfLowerFirst}PO);
        return ResultDto.buildSuccess();
    }

    @ApiOperation(value = "根据条件更新${tableInfo.desc}")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultDto update(@RequestBody @ApiParam("要更新的${tableInfo.desc}对象") ${tableInfo.beanName}PO ${tableInfo.beanNameOfLowerFirst}PO, @ApiParam("更新条件") ${tableInfo.beanName}QO ${tableInfo.beanNameOfLowerFirst}QO) {
        ${tableInfo.beanNameOfLowerFirst}Service.update(${tableInfo.beanNameOfLowerFirst}PO, ${tableInfo.beanNameOfLowerFirst}QO);
        return ResultDto.buildSuccess();
    }

    @ApiOperation(value = "根据id动态更新${tableInfo.desc}")
    @RequestMapping(value = "/updateNullAbleById", method = RequestMethod.POST)
    public ResultDto updateNullAbleById(@RequestBody @ApiParam("要更新的${tableInfo.desc}对象") ${tableInfo.beanName}PO ${tableInfo.beanNameOfLowerFirst}PO) {
        ${tableInfo.beanNameOfLowerFirst}Service.updateNullAbleById(${tableInfo.beanNameOfLowerFirst}PO);
        return ResultDto.buildSuccess();
    }

    @ApiOperation(value = "根据id动态更新${tableInfo.desc}")
    @RequestMapping(value = "/updateNullAble", method = RequestMethod.POST)
    public ResultDto updateNullAble(@RequestBody @ApiParam("要更新的${tableInfo.desc}对象") ${tableInfo.beanName}PO ${tableInfo.beanNameOfLowerFirst}PO, @ApiParam("更新条件") ${tableInfo.beanName}QO ${tableInfo.beanNameOfLowerFirst}QO) {
        ${tableInfo.beanNameOfLowerFirst}Service.updateNullAble(${tableInfo.beanNameOfLowerFirst}PO, ${tableInfo.beanNameOfLowerFirst}QO);
        return ResultDto.buildSuccess();
    }

    @ApiOperation(value = "根据id删除${tableInfo.desc}")
    @RequestMapping(value = "/deleteLogincById", method = RequestMethod.POST)
    public ResultDto deleteLogincById(@RequestParam @ApiParam("要更新的${tableInfo.desc}对象，带着主键") String id) {
        ${tableInfo.beanNameOfLowerFirst}Service.deleteLogincById(id);
        return ResultDto.buildSuccess();
    }

    @ApiOperation(value = "根据id动态删除${tableInfo.desc}")
    @RequestMapping(value = "/deleteLoginc", method = RequestMethod.POST)
    public ResultDto deleteLoginc(@RequestBody @ApiParam("要更新的${tableInfo.desc}对象") ${tableInfo.beanName}QO ${tableInfo.beanNameOfLowerFirst}QO) {
        ${tableInfo.beanNameOfLowerFirst}Service.deleteLoginc(${tableInfo.beanNameOfLowerFirst}QO);
        return ResultDto.buildSuccess();
    }
}
