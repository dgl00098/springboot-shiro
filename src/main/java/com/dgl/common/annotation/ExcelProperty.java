package com.dgl.common.annotation;

import java.lang.annotation.*;

/**
 * @Description: 自定义注解-匹配excel表格的列值字段
 *  注意:该注解不能用于Map上,不能用户集合嵌套map中,例如List<Map> / Map
 * @author dgl
 * @Version: 1.0
 * @create 2021/01/22
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExcelProperty {
    /**
     * 映射excel的第几列 默认从0开始
     * @return
     */
    int index() default 0;

    /**
     * 映射的列值
     * @return
     */
    String value() default "";

}
