package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@Slf4j
@Api(tags = "套餐接口" )
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
    @PostMapping
    @ApiOperation("新增套餐")
    public Result addSetmeal(@RequestBody SetmealDTO setmealDTO){
        setmealService.addSetmeal(setmealDTO);
        return Result.success();
    }
    @GetMapping("/page")
    @ApiOperation("套餐分页查询")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO){
        PageResult pageResult = setmealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);

    }
    @DeleteMapping
    @ApiOperation("批量删除套餐")
    public Result deleteByIds(@RequestParam List<Long> ids){
        setmealService.deleteByIds(ids);
        return Result.success();
    }
    @GetMapping("/{id}")
    @ApiOperation("根据id查询套餐")
    public Result<SetmealVO> selectById(@PathVariable Long id){
        SetmealVO setmealVO = setmealService.selectById(id);
        return Result.success(setmealVO);
    }

    @PutMapping
    @ApiOperation(("修改套餐"))
    public Result updateSetmeal(@RequestBody SetmealDTO setmealDTO){
        setmealService.updateSetmeal(setmealDTO);
        return Result.success();
    }

}
