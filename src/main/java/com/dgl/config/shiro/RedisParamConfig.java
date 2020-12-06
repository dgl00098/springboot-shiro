package com.dgl.config.shiro;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;


/**
 * @ClassName RedisParamConfig
 * @Description: redis服务器参数
 * @Author RedisParamConfig
 * @Date 2020/11/29
 * @Version V1.0
 */

@Slf4j
@Data
public class RedisParamConfig {

    @Value(value = "${spring.redis.host}")
    private String host;
//    @Value(value = "${spring.redis.password}")
//    private String password;
    @Value(value = "${spring.redis.port}")
    private int port;

}




