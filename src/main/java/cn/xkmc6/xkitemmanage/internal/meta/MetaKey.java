package cn.xkmc6.xkitemmanage.internal.meta;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 小坤
 * @date 2022/02/21 12:34
 */
@Target(value = {ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MetaKey {
    public String key() default "";
}
