package com.myproject.yygh.hosp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myproject.yygh.model.hosp.HospitalSet;
import com.myproject.yygh.vo.order.SignInfoVo;

public interface HospitalSetService extends IService<HospitalSet> {
    //2.根据传递过来医院编号，查询数据库，查询签名
    String getSignKey(Object hoscode);

    //获取医院签名信息
    SignInfoVo getSignInfoVo(String hoscode);
}
