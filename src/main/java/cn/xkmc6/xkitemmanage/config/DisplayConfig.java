package cn.xkmc6.xkitemmanage.config;

import cn.xkmc6.xkitemmanage.ItemManage;
import cn.xkmc6.xkitemmanage.internal.Display;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;

/**
 * @author 小坤
 * @date 2022/02/27 14:40
 */
public class DisplayConfig {

    private final File file = new File(ItemManage.getInstance().getDataFolder(), "display");

    @Getter
    private final List<File> fileList = new ArrayList<>();
    @Getter
    private final Map<String, Display> displayMap = new HashMap<>();

    public void loadConfig() {
        displayMap.clear();
        if (!file.exists()) {
            file.mkdirs();
            ItemManage.getInstance().saveResource("display/def.yml", true);
        }
        fileList.clear();
        find(file);
        fileList.stream().map(f -> {
            YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
            List<ConfigurationSection> list = new ArrayList<>();
            yml.getKeys(false).forEach(key -> {
                if (yml.getConfigurationSection(key) != null) list.add(yml.getConfigurationSection(key));
            });
            return list;
        }).forEach(list -> list.forEach(section -> displayMap.put(section.getName(),new Display(section))));
    }

    private void find(File file) {
        File[] files = file.listFiles();
        if (files != null) {
            Arrays.stream(files).forEach(f -> {
                if (f.isDirectory()) {
                    find(f);
                }
                if (f.isFile()) {
                    fileList.add(f);
                }
            });
        }
    }
}
