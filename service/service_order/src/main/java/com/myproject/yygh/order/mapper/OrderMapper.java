package com.myproject.yygh.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myproject.yygh.model.order.OrderInfo;
import com.myproject.yygh.vo.order.OrderCountQueryVo;
import com.myproject.yygh.vo.order.OrderCountVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<OrderInfo> {
    //查询预约统计数据的方法
    List<OrderCountVo> selectOrderCount(@Param("vo") OrderCountQueryVo orderCountQueryVo);
}
