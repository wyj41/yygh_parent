package com.myproject.yygh.hosp.service;

import org.springframework.data.domain.Page;
import com.myproject.yygh.model.hosp.Department;
import com.myproject.yygh.vo.hosp.DepartmentQueryVo;

import java.util.Map;

public interface DepartmentService {
    //添加科室信息
    void save(Map<String, Object> paramMap);

    //查询科室信息
    Page<Department> findPageDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo);

    //删除科室
    void remove(String hoscode, String depcode);
}
