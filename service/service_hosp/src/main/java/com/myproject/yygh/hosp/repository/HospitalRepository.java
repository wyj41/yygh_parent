package com.myproject.yygh.hosp.repository;

import com.myproject.yygh.model.hosp.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalRepository extends MongoRepository<Hospital,String> {
    //判断是否存在数据
    Hospital getHospitalByHoscode(String hoscode);
}
