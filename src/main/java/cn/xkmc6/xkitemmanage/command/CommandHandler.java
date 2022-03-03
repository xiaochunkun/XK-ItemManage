package cn.xkmc6.xkitemmanage.command;

import cn.xkmc6.xkitemmanage.ItemManage;
import cn.xkmc6.xkitemmanage.internal.ItemData;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;

/**
 * @author 小坤
 * @date 2022/02/18 23:52
 */
@CommandAlias("xkitem|item|xkitemmanage")
public class CommandHandler extends BaseCommand {
    @HelpCommand
    @CommandPermission("xkitemmanage.help")
    @Description("查看帮助信息")
    public void doHelp(CommandSender sender, CommandHelp help) {
        help.showHelp();
    }

    @Subcommand("list")
    @CommandPermission("xkitemmanage.list")
    @Description("查看物品列表")
    public void list(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, "物品列表");
        ItemManage.getInstance().getConfigManage().getItemConfig().getItemMap().forEach((k, v) -> {

        });
    }

    @Subcommand("get")
    @CommandPermission("xkitemmanage.get")
    @Description("查看物品列表")
    @CommandCompletion("@itemData")
    public void get(Player player, ItemData itemData) {
        player.getInventory().addItem(itemData.getItem());
        "§a物品已发送到背包".send(player);
    }

    @Subcommand("save")
    @CommandPermission("xkitemmanage.save")
    @Description("保存物品")
    @Syntax("<文件名> <节点>")
    public void save(CommandSender sender, String[] msg) {
        if (msg.length == 1) {
            File file = new File(ItemManage.getInstance().getDataFolder(), "item" + File.separator + msg[0]);

        }
    }

    @Subcommand("show")
    @CommandPermission("xkitemmanage.show")
    @Description("查看手持物品NBT")
    public void show(Player player) {
        ItemStack itemStack = player.getItemInHand();
        itemStack.showItemStack(player);
    }
}
