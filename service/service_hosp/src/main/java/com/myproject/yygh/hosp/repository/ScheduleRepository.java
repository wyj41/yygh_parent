package com.myproject.yygh.hosp.repository;

import com.myproject.yygh.model.hosp.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends MongoRepository<Schedule,String> {
    //根据医院编号和排班编号查询
    Schedule getScheduleByHoscodeAndHosScheduleId(String hoscode, String depcode);
}
