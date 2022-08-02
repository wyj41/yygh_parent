package com.myproject.yygh.user.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.myproject.yygh.user.mapper")
public class UserConfig {
}
