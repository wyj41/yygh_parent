package com.myproject.yygh.hosp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myproject.yygh.hosp.mapper.HospitalSetMapper;
import com.myproject.yygh.hosp.service.HospitalSetService;
import com.myproject.yygh.model.hosp.HospitalSet;
import org.springframework.stereotype.Service;

@Service
public class HospitalSetServiceImpl extends ServiceImpl<HospitalSetMapper, HospitalSet> implements HospitalSetService {

    //2.根据传递过来医院编号，查询数据库，查询签名
    @Override
    public String getSignKey(Object hoscode) {
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        wrapper.eq("hoscode",hoscode);
        HospitalSet hospitalSet = baseMapper.selectOne(wrapper);

        return hospitalSet.getSignKey();
    }
}
