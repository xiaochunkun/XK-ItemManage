package cn.xkmc6.xkitemmanage.config;

import cn.xkmc6.xkitemmanage.ItemManage;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author 小坤
 * @date 2022/02/06 11:19
 */
public class ItemConfig {

    public void loadConfig() {
        File file = new File(ItemManage.getInstance().getDataFolder(), "item");
        if (!file.exists()) {
            file.mkdirs();
            ItemManage.getInstance().saveResource("item/default.yml",true);
        }
        find(file).forEach(f -> {
            YamlConfiguration yml = new YamlConfiguration();
            try {
                yml.load(f);

            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
        });
    }

    private List<File> find(File file) {
        List<File> list = new ArrayList<>();
        Arrays.stream(Objects.requireNonNull(file.listFiles())).forEach(f -> {
            if (f.isDirectory()) {
                find(f);
            }
            if (f.isFile()) {
                list.add(f);
            }
        });
        return list;
    }
}
