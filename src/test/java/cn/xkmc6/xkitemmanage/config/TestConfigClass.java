package cn.xkmc6.xkitemmanage.config;

import be.seeseemelk.mockbukkit.MockBukkit;
import cn.xkmc6.xkitemmanage.ItemManage;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author 小坤
 * @date 2022/02/21 11:51
 */
public class TestConfigClass {
    private static ItemManage plugin;

    @BeforeAll
    public static void load() {
        MockBukkit.mock();
        plugin = MockBukkit.load(ItemManage.class);
    }

    @AfterAll
    public static void unload() {
        MockBukkit.unmock();
    }

    @Test
    @DisplayName("测试config.yml")
    void verifyTestConfig() {
        new BukkitRunnable() {
            @Override
            public void run() {
                testConfig();
            }
        }.runTaskLater(plugin,2L);
    }

    void testConfig(){
        YamlConfiguration yml = ItemManage.getInstance().getConfigManage().getConfig().getYml();
        yml.getKeys(true).stream().filter(key -> yml.getConfigurationSection(key) == null).forEach(k -> {
                    Object result = yml.get(k);
                    System.out.println(result);
            /*
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

             */
                }
        );
    }
}
