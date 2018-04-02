package com.gluten.rpc.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
@Inherited
public @interface RequestParam {
    String value() default "";

    String name() default "";

    boolean required() default false;
}
