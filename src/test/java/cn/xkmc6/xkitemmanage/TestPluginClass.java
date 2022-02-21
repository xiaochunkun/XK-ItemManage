package cn.xkmc6.xkitemmanage;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author 小坤
 * @date 2022/02/21 00:36
 */
public class TestPluginClass {
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
    @DisplayName("Verify that we are in a UNIT_TEST environment")
    void verifyTestEnvironment() {

    }

}
