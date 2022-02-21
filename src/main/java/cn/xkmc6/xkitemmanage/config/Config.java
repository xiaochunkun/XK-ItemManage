package cn.xkmc6.xkitemmanage.config;

import cn.xkmc6.xkitemmanage.ItemManage;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author 小坤
 * @date 2022/02/06 10:34
 */
public class Config {
    private final File file = new File(ItemManage.getInstance().getDataFolder(), "config.yml");
    private final YamlConfiguration yml = new YamlConfiguration();

    /**
     * 加载配置
     */
    public void loadConfig() {
        if (!file.exists()) {
            ItemManage.getInstance().saveDefaultConfig();
            ItemManage.getInstance().getLogger().info("§a保存默认配置");
        }
        try {
            yml.load(file);
            ItemManage.getInstance().getLogger().info("§a读取 config.yml 成功");
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
            ItemManage.getInstance().getLogger().warning("§a读取 config.yml 时发生错误");
        }
        for (String k : yml.getKeys(true)) {
            if (yml.getConfigurationSection(k) == null) {
                Object result = yml.get(k);
                if (result instanceof Boolean) {
                    System.out.println(((Boolean) result).booleanValue());
                }

                if (result instanceof String) {
                    System.out.println(result);
                }

                if (result instanceof Integer) {
                    System.out.println(((Integer) result).intValue());
                }

                if (result instanceof Double) {
                    System.out.println(((Double) result).doubleValue());
                }

                if (result instanceof List<?>) {
                    List<String> list = result.castList(String.class);
                    System.out.println(list);
                }
            }
        }
    }
}
