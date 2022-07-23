package com.myproject.yygh.hosp.service;

import com.myproject.yygh.model.hosp.Hospital;

import java.util.Map;

public interface HospitalService {
    //上传医院接口
    void save(Map<String, Object> paramMap);

    //根据医院编号进行查询
    Hospital getByHoscode(String hoscode);
}
