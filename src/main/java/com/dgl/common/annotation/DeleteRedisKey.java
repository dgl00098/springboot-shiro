package com.dgl.common.annotation;

import java.lang.annotation.*;

/**
 * @Description: 自定义注解-删除redis缓存
 * @author dgl
 * @Version: 1.0
 * @create 2021/01/22
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DeleteRedisKey {

    /**
     * To delete redis key name
     */
    String key();

}
