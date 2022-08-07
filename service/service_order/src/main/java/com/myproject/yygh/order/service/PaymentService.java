package com.myproject.yygh.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myproject.yygh.model.order.OrderInfo;
import com.myproject.yygh.model.order.PaymentInfo;

import java.util.Map;

public interface PaymentService extends IService<PaymentInfo> {
    //向支付记录表添加信息
    void savePaymentInfo(OrderInfo orderInfo, Integer status);

    //更新订单状态
    void paySuccess(String out_trade_no, Map<String, String> resultMap);

    //获取支付记录
    PaymentInfo getPaymentInfo(Long orderId, Integer paymentType);
}
