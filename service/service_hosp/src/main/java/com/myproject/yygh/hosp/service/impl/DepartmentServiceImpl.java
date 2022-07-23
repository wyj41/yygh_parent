package com.myproject.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.domain.*;
import com.myproject.yygh.hosp.repository.DepartmentRepository;
import com.myproject.yygh.hosp.service.DepartmentService;
import com.myproject.yygh.model.hosp.Department;
import com.myproject.yygh.vo.hosp.DepartmentQueryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    //添加科室信息
    @Override
    public void save(Map<String, Object> paramMap) {
        //paramMap 转换department对象
        String paramMapString = JSONObject.toJSONString(paramMap);
        Department department = JSONObject.parseObject(paramMapString, Department.class);

        //根据医院编号和科室编号查询
        Department departmentExist = departmentRepository.
                getDepartmentByHoscodeAndDepcode(department.getHoscode(),department.getDepcode());

        if(departmentExist != null){
            department.setId(departmentExist.getId());
            department.setCreateTime(departmentExist.getCreateTime());
            department.setUpdateTime(new Date());
            department.setIsDeleted(0);
            departmentRepository.save(department);
        }else{
            department.setCreateTime(new Date());
            department.setUpdateTime(new Date());
            department.setIsDeleted(0);
            departmentRepository.save(department);
        }
    }

    //查询科室信息
    @Override
    public Page<Department> findPageDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo) {
        //创建Pageable对象，设置当前页和每页记录数
        //0是第一页
        Pageable pageable = PageRequest.of(page-1,limit);
        //创建Example对象
        ExampleMatcher matcher = ExampleMatcher.matching()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                        .withIgnoreCase(true);

        Department department = new Department();
        BeanUtils.copyProperties(departmentQueryVo,department);
        department.setIsDeleted(0);

        Example<Department> example = Example.of(department,matcher);

        Page<Department> all = departmentRepository.findAll(example,pageable);

        return all;
    }

    //删除科室
    @Override
    public void remove(String hoscode, String depcode) {
        //根据医院编号和科室编号查询
        Department department = departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
        if(department != null){
            //调用方法删除(逻辑删除)
            department.setIsDeleted(1);
            department.setUpdateTime(new Date());
            departmentRepository.save(department);
        }
    }
}
