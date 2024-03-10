package com.sky.mapper;

import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Insert;
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

    /**
     * 添加套餐菜品关联信息
     * @param setmealDishes
     */

    void addSetmealDishs(List<SetmealDish> setmealDishes);

    /**
     * 根据套餐名称删除套餐菜品关联信息
     * @param ids
     */
    void deleteBySetmealIds(List<Long> ids);

    /**
     * 根据套餐id查询关联信息
     * @param id
     * @return
     */
    @Select("select * from setmeal_dish where setmeal_id = #{id}")
    List<SetmealDish> selectBySetmealId(Long id);
}
