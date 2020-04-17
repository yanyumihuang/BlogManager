package info.yymh.blogmanager.annotation;

import java.lang.annotation.*;

/**
 * @author sikunliang
 * @date 2020/4/15
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ControllerLog {
    /**
     *方法描述
     */
    public String value() default "";

}

