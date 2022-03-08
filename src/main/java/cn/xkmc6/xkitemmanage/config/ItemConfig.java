package cn.xkmc6.xkitemmanage.config;

import cn.xkmc6.xkitemmanage.ItemManage;
import cn.xkmc6.xkitemmanage.internal.ItemData;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;

/**
 * @author 小坤
 * @date 2022/02/06 11:19
 */
public class ItemConfig {

    private final File file = new File(ItemManage.getInstance().getDataFolder(), "item");

    @Getter
    private final List<File> fileList = new ArrayList<>();
    @Getter
    private final Map<String, ItemData> itemMap = new HashMap<>();

    public void loadConfig() {
        itemMap.clear();
        if (!file.exists()) {
            file.mkdirs();
            ItemManage.getInstance().saveResource("item/default.yml", true);
            ItemManage.getInstance().saveResource("item/test/test.yml", true);
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
        }).forEach(list -> list.forEach(section -> itemMap.put(section.getName(),new ItemData(section))));
    }

    private void find(File file) {
        File[] files = file.listFiles();
        if (files != null){
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
