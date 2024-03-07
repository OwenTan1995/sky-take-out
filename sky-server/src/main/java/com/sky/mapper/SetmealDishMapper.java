package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    /**
     * 查询菜品对应套餐
     * @param dishIds
     * @return
     */

    List<Long> selectByDishId(List<Long> dishIds);

}
