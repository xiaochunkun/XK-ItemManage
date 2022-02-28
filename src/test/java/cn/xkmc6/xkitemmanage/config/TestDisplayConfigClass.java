package cn.xkmc6.xkitemmanage.config;

import be.seeseemelk.mockbukkit.MockBukkit;
import cn.xkmc6.xkitemmanage.ItemManage;
import org.bukkit.scheduler.BukkitRunnable;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author 小坤
 * @date 2022/02/27 14:43
 */
public class TestDisplayConfigClass {
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
    @DisplayName("测试display文件夹")
    void verifyTestItemConfig() {
        new BukkitRunnable() {
            @Override
            public void run() {
                testConfig();
            }
        }.runTaskLater(plugin,2L);
    }

    void testConfig(){
        "-----------------------".print();
        "遍历display文件夹所有文件".print();
        ItemManage.getInstance().getConfigManage().getDisplayConfig().getDisplayMap().forEach((k,v) -> {
            System.out.println(v.getDisplay().get("name"));
            System.out.println(v.getDisplay().get("name").toString().asNameKey());
            v.getDisplay().get("lore").castList(String.class).asLoreKeys().forEach(key ->{
                System.out.println(key.getKey());
                System.out.println(key.getValue());
            });
        });
    }
}
