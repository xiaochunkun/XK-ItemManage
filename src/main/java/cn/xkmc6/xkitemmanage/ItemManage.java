package cn.xkmc6.xkitemmanage;

import cn.xkmc6.xkitemmanage.command.CommandHandler;
import cn.xkmc6.xkitemmanage.internal.ItemData;
import cn.xkmc6.xkitemmanage.manage.ClassManage;
import cn.xkmc6.xkitemmanage.manage.ConfigManage;
import cn.xkmc6.xkitemmanage.util.Events;
import cn.xkmc6.xkitemmanage.util.PageableInventory;
import co.aikar.commands.InvalidCommandArgument;
import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
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

    private List<Events<? extends Event>> eventsList = new ArrayList<>();

    public ItemManage() {
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

        registerClass();
        registerConfig();
        registerCommand();
        registerListener();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        unRegisterCommand();
        eventsList.forEach(Events::unregister);
    }

    private void registerClass() {
        classManage = new ClassManage();
        classManage.registerClass();
    }

    private void registerConfig() {
        configManage = new ConfigManage();
        configManage.registerConfig();
    }

    private void registerCommand() {
        commandManager = new PaperCommandManager(this);
        commandManager.getCommandCompletions().registerCompletion("itemData", c -> this.getConfigManage().getItemConfig().getItemMap().keySet());
        commandManager.getCommandContexts().registerContext(ItemData.class, c -> {
            ItemData itemData = c.popFirstArg().asItemData();
            if (itemData == null) {
                throw new InvalidCommandArgument("指定物品不存在");
            }
            return itemData;
        });

        commandManager.getLocales().setDefaultLocale(Locale.CHINA);
        commandManager.enableUnstableAPI("help");
        commandManager.registerCommand(new CommandHandler());
    }

    private void registerListener() {
        eventsList.add(Events.subscribe(InventoryClickEvent.class, event -> {
            Inventory inv = event.getClickedInventory();
            if (inv == null) return;
            if (inv.getHolder() instanceof PageableInventory) {
                event.setCancelled(true);
                Player player = (Player) event.getWhoClicked();
                PageableInventory pageableInventory = (PageableInventory) inv.getHolder();
                switch (event.getRawSlot()) {
                    case 45:
                        pageableInventory.prevPage();
                        player.openInventory(pageableInventory);
                        break;
                    case 53:
                        pageableInventory.nextPage();
                        player.openInventory(pageableInventory);
                        break;
                }
                ItemStack itemStack = event.getCurrentItem();
                if (event.getRawSlot() < 45) {
                    player.addItem(itemStack);
                }
            }
        }));
    }

    private void unRegisterCommand() {
        commandManager.unregisterCommands();
    }

}
