package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FlavorMapper {
    /**
     * 新增菜品口味
     * @param flavors
     */
    void addFlaver(List<DishFlavor> flavors);

    /**
     * 根据菜品名称删除口味
     * @param dishIds
     */
    void deleteByDishId(List<Long> dishIds);
}
