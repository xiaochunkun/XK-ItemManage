package cn.xkmc6.xkitemmanage.internal.meta;

import org.bukkit.configuration.ConfigurationSection;

/**
 * @author 小坤
 * @date 2022/02/06 13:11
 */
public class AttributeMeta extends Meta {



    public AttributeMeta(ConfigurationSection root) {
        super(root);
        root.getKeys(false).forEach(key -> {

        });
    }
}
