package cn.xkmc6.xkitemmanage;

import cn.xkmc6.xkitemmanage.command.CommandHandler;
import cn.xkmc6.xkitemmanage.config.Config;
import cn.xkmc6.xkitemmanage.config.ItemConfig;
import co.aikar.commands.BukkitCommandManager;
import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.scheduler.BukkitRunnable;

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
    PaperCommandManager commandManager;

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

        registerListener();
        registerLoadConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        unRegisterCommand();
    }

    private void registerLoadConfig() {
        new BukkitRunnable() {
            @Override
            public void run() {
                new Config().loadConfig();
                new ItemConfig().loadConfig();
                this.cancel();
            }
        }.runTask(this);
    }

    private void registerListener() {

    }

    private void unRegisterCommand() {
        commandManager.unregisterCommands();
    }

}
