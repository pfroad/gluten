package com.gluten.rpc.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
@Inherited
public @interface RequestMapping {
    String[] value() default {};

    String[] path() default {};
}
