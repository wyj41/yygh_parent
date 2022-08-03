package com.myproject.yygh.user.controller;

import com.myproject.yygh.common.result.Result;
import com.myproject.yygh.common.utils.AuthContextHolder;
import com.myproject.yygh.model.user.Patient;
import com.myproject.yygh.user.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/user/patient")
public class PatientApiController {
    @Autowired
    private PatientService patientService;

    //获取就诊人列表
    @GetMapping("auth/findAll")
    public Result findAll(HttpServletRequest request){
        //获取当前登录用户id
        Long userId = AuthContextHolder.getUserId(request);
        List<Patient> list = patientService.findAllUserId(userId);
        return Result.ok(list);
    }

    //添加就诊人
    @PostMapping("auth/save")
    public Result savePatient(@RequestBody Patient patient,HttpServletRequest request){
        //获取当前登录用户id
        Long userId = AuthContextHolder.getUserId(request);
        patient.setUserId(userId);
        patientService.save(patient);
        return Result.ok();
    }

    //根据id获取就诊人信息
    @GetMapping("auth/get/{id}")
    public Result getPatient(@PathVariable Long id){
        Patient patient = patientService.getPatientById(id);
        return Result.ok(patient);
    }

    //修改就诊人
    @PostMapping("auth/update")
    public Result updatePatient(@RequestBody Patient patient){
        patientService.updateById(patient);
        return Result.ok();
    }

    //删除就诊人
    @DeleteMapping("auth/remove/{id}")
    public Result removePatient(@PathVariable Long id){
        patientService.removeById(id);
        return Result.ok();
    }
}
