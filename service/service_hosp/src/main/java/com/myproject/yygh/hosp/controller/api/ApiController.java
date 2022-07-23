package com.myproject.yygh.hosp.controller.api;

import com.myproject.yygh.common.exception.YyghException;
import com.myproject.yygh.common.helper.HttpRequestHelper;
import com.myproject.yygh.common.result.Result;
import com.myproject.yygh.common.result.ResultCodeEnum;
import com.myproject.yygh.common.utils.MD5;
import com.myproject.yygh.hosp.service.DepartmentService;
import com.myproject.yygh.hosp.service.HospitalService;
import com.myproject.yygh.hosp.service.HospitalSetService;
import com.myproject.yygh.hosp.service.ScheduleService;
import com.myproject.yygh.model.hosp.Department;
import com.myproject.yygh.model.hosp.Hospital;
import com.myproject.yygh.model.hosp.Schedule;
import com.myproject.yygh.vo.hosp.DepartmentQueryVo;
import com.myproject.yygh.vo.hosp.ScheduleQueryVo;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/hosp")
public class ApiController {
    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private HospitalSetService hospitalSetService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ScheduleService scheduleService;

    //删除排班
    @PostMapping("schedule/remove")
    public Result remove(HttpServletRequest request){
        //获取传递过来的医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //签名校验
        isSignEquals(paramMap);

        //获取医院编号和排班编号
        String hoscode = (String) paramMap.get("hoscode");
        String hosScheduleId = (String) paramMap.get("hosScheduleId");

        scheduleService.remove(hoscode,hosScheduleId);
        return Result.ok();
    }

    //查询排班
    @PostMapping("schedule/list")
    public Result findSchedule(HttpServletRequest request){
        //获取传递过来的医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //签名校验
        isSignEquals(paramMap);

        //医院编号 和 科室编号
        String hoscode = (String) paramMap.get("hoscode");
        String depcode = (String) paramMap.get("depcode");

        int page = StringUtils.isEmpty(paramMap.get("page")) ?
                1 : Integer.parseInt((String) paramMap.get("page"));

        int limit = StringUtils.isEmpty(paramMap.get("limit")) ?
                20 : Integer.parseInt((String) paramMap.get("limit"));

        ScheduleQueryVo scheduleQueryVo = new ScheduleQueryVo();
        scheduleQueryVo.setHoscode(hoscode);
        scheduleQueryVo.setDepcode(depcode);

        //调用service方法
        Page<Schedule> pageModel = scheduleService.findPageSchedule(page,limit,scheduleQueryVo);

        return Result.ok(pageModel);
    }

    //上传排班
    @PostMapping("saveSchedule")
    public Result saveSchedule(HttpServletRequest request){
        //获取传递过来的医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //签名校验
        isSignEquals(paramMap);

        scheduleService.save(paramMap);
        return Result.ok();
    }

    //删除科室接口
    @PostMapping("department/remove")
    public Result removeDepartment(HttpServletRequest request){
        //获取传递过来的医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //签名校验
        isSignEquals(paramMap);

        //医院编号 和 科室编号
        String hoscode = (String) paramMap.get("hoscode");
        String depcode = (String) paramMap.get("depcode");

        departmentService.remove(hoscode,depcode);
        return Result.ok();
    }

    //查询科室接口
    @PostMapping("department/list")
    public Result findDepartment(HttpServletRequest request){
        //获取传递过来的医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //签名校验
        isSignEquals(paramMap);

        //医院编号
        String hoscode = (String) paramMap.get("hoscode");

        int page = StringUtils.isEmpty(paramMap.get("page")) ?
                1 : Integer.parseInt((String) paramMap.get("page"));

        int limit = StringUtils.isEmpty(paramMap.get("limit")) ?
                20 : Integer.parseInt((String) paramMap.get("limit"));

        DepartmentQueryVo departmentQueryVo = new DepartmentQueryVo();
        departmentQueryVo.setHoscode(hoscode);
        //调用service方法
        Page<Department> pageModel = departmentService.findPageDepartment(page,limit,departmentQueryVo);

        return Result.ok(pageModel);
    }

    //上传科室接口
    @PostMapping("saveDepartment")
    public Result saveDepartment(HttpServletRequest request){
        //获取传递过来的医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //签名校验
        isSignEquals(paramMap);

        //调用service的方法
        departmentService.save(paramMap);
        return Result.ok();
    }

    //查询医院
    @PostMapping("hospital/show")
    public Result getHospital(HttpServletRequest request){
        //获取传递过来的医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //签名校验
        isSignEquals(paramMap);

        //获取医院编号
        String hoscode = (String) paramMap.get("hoscode");

        //调用service方法实现根据医院编号查询
        Hospital hospital = hospitalService.getByHoscode(hoscode);
        return Result.ok(hospital);
    }

    //上传医院接口
    @PostMapping("saveHospital")
    public Result saveHosp(HttpServletRequest request){
        //获取传递过来的医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //签名校验
        isSignEquals(paramMap);

        //传输过程中“+”转换为“ ”，因此我们要转换回来
        String logoData = (String) paramMap.get("logoData");
        logoData = logoData.replaceAll(" ","+");
        paramMap.put("logoData",logoData);

        //调用service的方法
        hospitalService.save(paramMap);
        return Result.ok();
    }

    public void isSignEquals(Map<String, Object> paramMap){
        //1.获取医院系统传递过来的签名，签名进行MD5加密
        String hospSign = (String)paramMap.get("sign");

        //2.根据传递过来医院编号，查询数据库，查询签名
        String hoscode = (String) paramMap.get("hoscode");
        String signKey = hospitalSetService.getSignKey(hoscode);

        //3.把数据库查询签名进行MD5加密
        String signKeyMd5 = MD5.encrypt(signKey);

        //4.判断签名是否一致
        if(!hospSign.equals(signKeyMd5)){
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
    }
}
