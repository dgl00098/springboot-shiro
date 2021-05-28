package com.dgl.common.annotation;

import java.lang.annotation.*;

/**
 * @Title: SameUrlData
 * @Description: 自定义注解防止表单重复提交
 * @author dgl
 * @Version: 1.0
 * @create 2020/11/12
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DoubleSubmit {

}
