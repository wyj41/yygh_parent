package com.myproject.yygh.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myproject.yygh.model.user.UserInfo;
import com.myproject.yygh.vo.user.LoginVo;

import java.util.Map;

public interface UserInfoService extends IService<UserInfo> {
    //用户手机号登录接口
    Map<String, Object> loginUser(LoginVo loginVo);

    //根据openid获取userinfo
    UserInfo selectWxInfoOpenId(String openid);
}
