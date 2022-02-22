package cn.xkmc6.xkitemmanage.internal.meta;

import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

/**
 * @author 小坤
 * @date 2022/02/06 13:14
 */
public abstract class Meta {
    @Getter
    private final ConfigurationSection root;
    @Getter
    private final String id;

    public Meta(ConfigurationSection root) {
        this.root = root;
        if (this.getClass().isAssignableFrom(MetaKey.class)){
            this.id = this.getClass().getAnnotation(MetaKey.class).key();
        }else {
            this.id = this.getClass().getName();
        }
    }

}
