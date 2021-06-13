package com.wodekit.paste.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author banto
 */
@Configuration
@MapperScan("com.wodekit.paste.mapper")
public class MybatisConfig {
}
