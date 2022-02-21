package cn.xkmc6.xkitemmanage;

import cn.xkmc6.xkitemmanage.command.CommandHandler;
import cn.xkmc6.xkitemmanage.config.Config;
import cn.xkmc6.xkitemmanage.config.ItemConfig;
import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.jetbrains.annotations.NotNull;

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
    private PaperCommandManager commandManager;

    @Getter
    private Config conf;
    @Getter
    private ItemConfig itemConfig;


    private ItemManage(JavaPluginLoader loader, PluginDescriptionFile descriptionFile, File dataFolder, File file) {
        super(loader, descriptionFile, dataFolder, file);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        commandManager = new PaperCommandManager(this);
        commandManager.registerCommand(new CommandHandler());
        commandManager.getLocales().setDefaultLocale(Locale.CHINA);
        commandManager.enableUnstableAPI("help");
        conf = new Config();
        itemConfig = new ItemConfig();
        registerListener();
        registerLoadConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        unRegisterCommand();
    }

    private void registerLoadConfig() {
        conf.loadConfig();
        itemConfig.loadConfig();
    }

    private void registerListener() {

    }

    private void unRegisterCommand() {
        commandManager.unregisterCommands();
    }

}
