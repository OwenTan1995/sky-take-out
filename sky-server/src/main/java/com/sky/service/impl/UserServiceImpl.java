package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.User;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.FlavorMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.PrivateKey;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户业务层
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private WeChatProperties weChatProperties;
    public static  final  String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";
    @Autowired
    private UserMapper userMapper;
    /**
     * 微信登录
     * @param userLoginDTO
     * @return
     */
    @Override
    public User wxLogin(UserLoginDTO userLoginDTO) {

        String openid = getOpenid(userLoginDTO.getCode());

        if(openid==null){
            throw  new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }

        //判断用户是否存在
        User user = userMapper.selectByOpneId(openid);
        //不存在，创建新用户
        if(user==null){
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.addUser(user);
        }

        return user;
    }

    private String getOpenid(String code){
        //获取openid
        Map<String, String> Map = new HashMap<>();
        Map.put("appid",weChatProperties.getAppid());
        Map.put("secret",weChatProperties.getSecret());
        Map.put("js_code",code);
        Map.put("grant_type","authorization_code");
        String json = HttpClientUtil.doGet(WX_LOGIN, Map);

        JSONObject jsonObject = JSON.parseObject(json);
        String openid = jsonObject.getString("openid");
        return openid;
    }

}
