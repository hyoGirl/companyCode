package com.business.log.aop;


import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BuinessLog {

    /**
     * 业务名称
     * @return
     */
    String name() default "";

    /**
     * 被修改的实体类的主键
     * @return
     */
    String key() default "";


}
