package cn.xkmc6.xkitemmanage.config;

import be.seeseemelk.mockbukkit.MockBukkit;
import cn.xkmc6.xkitemmanage.ItemManage;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

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
        System.out.println("遍历item文件夹所有文件");
        ItemManage.getInstance().getItemConfig().getFileList().forEach(file -> {
                    System.out.println(file.getPath());
                }
        );
    }
}
