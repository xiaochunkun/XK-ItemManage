package cn.xkmc6.xkitemmanage.config;

import be.seeseemelk.mockbukkit.MockBukkit;
import cn.xkmc6.xkitemmanage.ItemManage;
import cn.xkmc6.xkitemmanage.internal.meta.Meta;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author 小坤
 * @date 2022/02/21 11:53
 */
public class TestItemConfigClass {
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
    @DisplayName("测试item文件夹")
    void verifyTestItemConfig() {
        new BukkitRunnable() {
            @Override
            public void run() {
                testConfig();
            }
        }.runTaskLater(plugin,20L);
    }

    void testConfig(){
        "-----------------------".print();
        "遍历item文件夹所有文件".print();

        /*
        ItemManage.getInstance().getConfigManage().getItemConfig().getFileList().forEach(file -> {
                    YamlConfiguration yml = new YamlConfiguration();
                    try {
                        yml.load(file);
                        yml.getKeys(false).stream().filter(key -> yml.getConfigurationSection(key) != null).forEach(key -> {
                            ConfigurationSection sectionMeta = yml.getConfigurationSection(key + ".meta");
                            Material material = yml.getString(key + ".material", "stone").asMaterial();

                            if (sectionMeta != null) {
                                sectionMeta.getKeys(false).forEach(k -> {
                                    if (ItemManage.getInstance().getClassManage().getClasses().containsKey(k)) {
                                        Class<? extends Meta> clazz = ItemManage.getInstance().getClassManage().getClasses().get(k);
                                        try {
                                            Constructor<? extends Meta> constructor = clazz.getConstructor(ConfigurationSection.class);
                                            Meta o = constructor.newInstance(sectionMeta);
                                            System.out.println(o.build(new ItemStack(Material.WATER)));
                                        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        });
                    } catch (IOException | InvalidConfigurationException e) {
                        e.printStackTrace();
                    }
                }
        );
         */
    }
}
