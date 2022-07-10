package com.myproject.yygh.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.myproject.yygh.hosp.mapper")
public class HospConfig {
}
