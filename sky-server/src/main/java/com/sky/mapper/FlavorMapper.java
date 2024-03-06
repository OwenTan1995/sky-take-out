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
}
