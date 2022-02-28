package cn.xkmc6.xkitemmanage.config;

import cn.xkmc6.xkitemmanage.ItemManage;
import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * @author 小坤
 * @date 2022/02/06 10:34
 */
public class Config {
    @Getter
    private final File file = new File(ItemManage.getInstance().getDataFolder(), "config.yml");
    @Getter
    private final YamlConfiguration yml = new YamlConfiguration();

    /**
     * 加载配置
     */
    public void loadConfig() {
        if (!file.exists()) {
            ItemManage.getInstance().saveDefaultConfig();
            "§a保存默认配置".print();
        }
        try {
            yml.load(file);
            "§a读取 config.yml 成功".print();
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
            "§a读取 config.yml 时发生错误".print();
        }
    }
}
