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

    public Meta(ConfigurationSection root) {
        this.root = root;
    }

}
