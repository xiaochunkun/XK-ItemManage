package cn.xkmc6.xkitemmanage.manage;

import cn.xkmc6.xkitemmanage.internal.meta.Meta;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author 小坤
 * @date 2022/02/26 19:02
 */
public class ItemManage {

    public static List<Meta> readMeta(ConfigurationSection root) {
        List<Meta> metas = new ArrayList<>();
        ConfigurationSection metaSection = root.getConfigurationSection("meta");
        if (metaSection != null) {
            metaSection.getKeys(false).stream().filter(Objects::nonNull).forEach(key -> {

            });
        }
        return metas;
    }
}
