package cn.xkmc6.xkitemmanage;

import cn.xkmc6.xkitemmanage.command.CommandHandler;
import cn.xkmc6.xkitemmanage.config.Config;
import cn.xkmc6.xkitemmanage.config.ItemConfig;
import co.aikar.commands.BukkitCommandManager;
import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;

/**
 * @author 小坤
 * @date 2022/02/06 10:15
 */
public final class ItemManage extends JavaPlugin {

    @Getter
    private static ItemManage instance;

    private String version;

    @Getter
    PaperCommandManager commandManager;


    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
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

    public Class getNmsClass(String name) throws ClassNotFoundException {
        return Class.forName("net.minecraft.server." + version + "." + name);
    }

    public Class getCbClass(String name) throws ClassNotFoundException {
        return Class.forName("org.bukkit.craftbukkit." + version + "." + name);
    }

    private void registerLoadConfig(){
        new Config().loadConfig();
        new ItemConfig().loadConfig();
    }

    private void registerListener(){

    }

    private void unRegisterCommand(){
        commandManager.unregisterCommands();
    }

}
