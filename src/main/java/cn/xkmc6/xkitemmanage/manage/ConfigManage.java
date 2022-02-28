package cn.xkmc6.xkitemmanage.manage;

import cn.xkmc6.xkitemmanage.ItemManage;
import cn.xkmc6.xkitemmanage.config.Config;
import cn.xkmc6.xkitemmanage.config.DisplayConfig;
import cn.xkmc6.xkitemmanage.config.ItemConfig;
import lombok.Getter;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author 小坤
 * @date 2022/02/27 14:45
 */
public class ConfigManage {
    @Getter
    private Config config;
    @Getter
    private ItemConfig itemConfig;
    @Getter
    private DisplayConfig displayConfig;

    public ConfigManage(){
        config = new Config();
        itemConfig = new ItemConfig();
        displayConfig = new DisplayConfig();
    }

    public void registerConfig(){
        if (ItemManage.getInstance().isUnitTest()){
            new BukkitRunnable() {
                @Override
                public void run() {
                    config.loadConfig();
                    displayConfig.loadConfig();
                    itemConfig.loadConfig();
                }
            }.runTask(ItemManage.getInstance());
        }else {
            config.loadConfig();
            displayConfig.loadConfig();
            itemConfig.loadConfig();
        }
    }
}
