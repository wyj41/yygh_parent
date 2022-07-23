package com.myproject.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.myproject.yygh.hosp.repository.ScheduleRepository;
import com.myproject.yygh.hosp.service.ScheduleService;
import com.myproject.yygh.model.hosp.Department;
import com.myproject.yygh.model.hosp.Schedule;
import com.myproject.yygh.vo.hosp.ScheduleQueryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    //上传排班
    @Override
    public void save(Map<String, Object> paramMap) {
        //paramMap 转换schedule对象
        String paramMapString = JSONObject.toJSONString(paramMap);
        Schedule schedule = JSONObject.parseObject(paramMapString, Schedule.class);

        //根据医院编号和排班编号查询
        Schedule scheduleExist = scheduleRepository.
                getScheduleByHoscodeAndHosScheduleId(schedule.getHoscode(),schedule.getHosScheduleId());

        if(scheduleExist != null){
            schedule.setId(scheduleExist.getId());
            schedule.setCreateTime(scheduleExist.getCreateTime());
            schedule.setUpdateTime(new Date());
            schedule.setIsDeleted(0);
            schedule.setStatus(1);
            scheduleRepository.save(schedule);
        }else{
            schedule.setCreateTime(new Date());
            schedule.setUpdateTime(new Date());
            schedule.setIsDeleted(0);
            schedule.setStatus(1);
            scheduleRepository.save(schedule);
        }
    }

    //查询排班
    @Override
    public Page<Schedule> findPageSchedule(int page, int limit, ScheduleQueryVo scheduleQueryVo) {
        //创建Pageable对象，设置当前页和每页记录数
        //0是第一页
        Pageable pageable = PageRequest.of(page-1,limit);
        //创建Example对象
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true);

        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleQueryVo,schedule);
        schedule.setIsDeleted(0);
        schedule.setStatus(1);

        Example<Schedule> example = Example.of(schedule,matcher);

        Page<Schedule> all = scheduleRepository.findAll(example,pageable);

        return all;
    }

    //删除排班
    @Override
    public void remove(String hoscode, String hosScheduleId) {
        //根据医院编号和排班编号查询信息
        Schedule schedule = scheduleRepository.getScheduleByHoscodeAndHosScheduleId(hoscode,hosScheduleId);
        if(schedule != null){
            //逻辑删除
            schedule.setIsDeleted(1);
            scheduleRepository.save(schedule);
        }
    }
}
