package cn.xkmc6.xkitemmanage.internal.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 小坤
 * @date 2022/02/06 10:12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cmd {
    String value();

    boolean IgnoreCase() default true;

    String permission() default "";

    CmdSender cmdSender() default CmdSender.BOTH;

    enum CmdSender {
        //控制台
        CONSOLE,
        // 玩家
        PLAYER,
        // 二者均可
        BOTH
    }
}
