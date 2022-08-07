package com.myproject.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.myproject.yygh.vo.hosp.DepartmentVo;
import org.springframework.data.domain.*;
import com.myproject.yygh.hosp.repository.DepartmentRepository;
import com.myproject.yygh.hosp.service.DepartmentService;
import com.myproject.yygh.model.hosp.Department;
import com.myproject.yygh.vo.hosp.DepartmentQueryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    //根据医院编号，查询医院所有科室列表
    @Override
    public List<DepartmentVo> findDeptTree(String hoscode) {
        //创建list集合，用于最终数据封装
        List<DepartmentVo> result = new ArrayList<>();

        ////根据医院编号，查询医院所有科室信息
        Department departmentQuery = new Department();
        departmentQuery.setHoscode(hoscode);
        Example example = Example.of(departmentQuery);
        //所有科室列表 departmentList
        List<Department> bigList = departmentRepository.findAll(example);

        //根据大科室编号 bigcode 分组，获取每个大科室里面下级子科室
        Map<String, List<Department>> bigMap = bigList.stream().collect(Collectors.groupingBy(Department::getBigcode));
        //遍历map集合
        for(Map.Entry<String,List<Department>> entry : bigMap.entrySet()){
            //大科室的编号
            String bigcode = entry.getKey();
            //大科室标号对应的全部数据
            List<Department> departmentList = entry.getValue();

            //封装大科室
            DepartmentVo bigVo = new DepartmentVo();
            bigVo.setDepcode(bigcode);
            bigVo.setDepname(departmentList.get(0).getBigname());

            //封装小科室
            List<DepartmentVo> children = new ArrayList<>();
            for (Department department : departmentList) {
                DepartmentVo departmentVo = new DepartmentVo();
                departmentVo.setDepcode(department.getDepcode());
                departmentVo.setDepname(department.getDepname());
                //封装到list集合
                children.add(departmentVo);
            }
            //把小科室list集合放到大科室children里面
            bigVo.setChildren(children);
            //放到最终的result中
            result.add(bigVo);
        }
        return result;
    }

    //根据科室编号和医院编号查询科室名称
    @Override
    public String getDepName(String hoscode, String depcode) {
        Department department = departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
        if(department != null){
            return department.getDepname();
        }
        return null;
    }

    //根据科室编号和医院编号，查询科室
    @Override
    public Department getDepartment(String hoscode, String depcode) {
        return departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode,depcode);
    }
}
