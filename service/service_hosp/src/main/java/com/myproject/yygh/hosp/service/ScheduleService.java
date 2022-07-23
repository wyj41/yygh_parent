package com.myproject.yygh.hosp.service;

import com.myproject.yygh.model.hosp.Schedule;
import com.myproject.yygh.vo.hosp.ScheduleQueryVo;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface ScheduleService {
    //上传排班
    void save(Map<String, Object> paramMap);

    //查询排班
    Page<Schedule> findPageSchedule(int page, int limit, ScheduleQueryVo scheduleQueryVo);

    //删除排班
    void remove(String hoscode, String hosScheduleId);
}
