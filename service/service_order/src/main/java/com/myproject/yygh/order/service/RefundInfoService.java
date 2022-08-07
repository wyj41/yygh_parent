package com.myproject.yygh.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myproject.yygh.model.order.PaymentInfo;
import com.myproject.yygh.model.order.RefundInfo;

public interface RefundInfoService extends IService<RefundInfo> {
    //保存退款记录
    RefundInfo saveRefundInfo(PaymentInfo paymentInfo);
}
