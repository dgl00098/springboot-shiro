package com.dgl.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName ESConfig
 * @Description TODO
 * @Author DGL
 * @Date 2021/5/28  16:43
 **/
@Configuration
public class ESConfig {

    @Value(value = "${spring.data.elasticsearch.host}")
    private String host;
    @Value(value = "${spring.data.elasticsearch.port}")
    private Integer port;

    @Bean(destroyMethod = "close")
    public RestHighLevelClient restHighLevelClient() {
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(RestClient.builder(new HttpHost(host, port)));
        return restHighLevelClient;
    }

}