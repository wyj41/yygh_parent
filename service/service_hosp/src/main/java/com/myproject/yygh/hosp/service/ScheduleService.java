package com.myproject.yygh.hosp.service;

import com.myproject.yygh.model.hosp.Schedule;
import com.myproject.yygh.vo.hosp.ScheduleQueryVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface ScheduleService {
    //上传排班
    void save(Map<String, Object> paramMap);

    //查询排班
    Page<Schedule> findPageSchedule(int page, int limit, ScheduleQueryVo scheduleQueryVo);

    //删除排班
    void remove(String hoscode, String hosScheduleId);

    //根据医院编号 和科室编号 ，查询排班规则数据
    Map<String, Object> getScheduleRule(Long page, Long limit, String hoscode, String depcode);

    //根据医院编号，科室编号和工作日期，查询排班详细信息
    List<Schedule> getDetailSchedule(String hoscode, String depcode, String workDate);
}
