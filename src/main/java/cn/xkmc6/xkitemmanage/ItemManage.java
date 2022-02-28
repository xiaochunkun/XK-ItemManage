package cn.xkmc6.xkitemmanage;

import cn.xkmc6.xkitemmanage.command.CommandHandler;
import cn.xkmc6.xkitemmanage.config.Config;
import cn.xkmc6.xkitemmanage.config.ItemConfig;
import cn.xkmc6.xkitemmanage.manage.ClassManage;
import cn.xkmc6.xkitemmanage.manage.ConfigManage;
import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.File;
import java.util.Locale;

/**
 * @author 小坤
 * @date 2022/02/06 10:15
 */
public final class ItemManage extends JavaPlugin {

    @Getter
    private static ItemManage instance;
    @Getter
    private boolean unitTest;

    @Getter
    private PaperCommandManager commandManager;

    @Getter
    private ClassManage classManage;
    @Getter
    private ConfigManage configManage;

    public ItemManage(){
        super();
    }

    @ParametersAreNonnullByDefault
    public ItemManage(JavaPluginLoader loader, PluginDescriptionFile descriptionFile, File dataFolder, File file) {
        super(loader, descriptionFile, dataFolder, file);
        unitTest = true;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        commandManager = new PaperCommandManager(this);
        commandManager.registerCommand(new CommandHandler());
        commandManager.getLocales().setDefaultLocale(Locale.CHINA);
        commandManager.enableUnstableAPI("help");
        classManage = new ClassManage();
        classManage.registerClass();
        configManage = new ConfigManage();
        configManage.registerConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        unRegisterCommand();
    }


    private void unRegisterCommand() {
        commandManager.unregisterCommands();
    }

}
