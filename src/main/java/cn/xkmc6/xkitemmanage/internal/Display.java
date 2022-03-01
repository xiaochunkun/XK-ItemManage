package cn.xkmc6.xkitemmanage.internal;

import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 小坤
 * @date 2022/02/27 15:17
 */
public class Display {
    @Getter
    private final Map<String, Object> display = new HashMap<>();

    public Display(ConfigurationSection root) {
        root.getKeys(false).forEach(key -> display.put(key, root.get(key)));
        display.put("root", root);
    }
}