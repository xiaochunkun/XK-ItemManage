package cn.xkmc6.xkitemmanage.command;

import cn.xkmc6.xkitemmanage.ItemManage;
import cn.xkmc6.xkitemmanage.internal.ItemData;
import cn.xkmc6.xkitemmanage.internal.meta.Meta;
import cn.xkmc6.xkitemmanage.internal.meta.MetaData;
import cn.xkmc6.xkitemmanage.util.ItemBuilder;
import cn.xkmc6.xkitemmanage.util.PageableInventory;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
        List<ItemStack> list = ItemManage.getInstance().getConfigManage().getItemConfig().getItemMap().values().stream().map(ItemData::getItem).collect(Collectors.toList());
        PageableInventory inv = new PageableInventory(54, "物品列表 --- (第%d页)", list);
        inv.getInventory().setItem(45, new ItemBuilder.Builder(XMaterial.ARROW).name("上一页").build().getItemStack());
        inv.getInventory().setItem(53, new ItemBuilder.Builder(XMaterial.ARROW).name("下一页").build().getItemStack());
        player.openInventory(inv);
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
    public void save(Player player, String path, String key) {
        ItemStack itemStack = player.getItemInHand();
        if (itemStack == null || itemStack.getType().equals(Material.AIR)) {
            return;
        }
        File file = new File(ItemManage.getInstance().getDataFolder(), "item" + File.separator + path.replace(".yml", "") + ".yml");
        if (!ItemManage.getInstance().getConfigManage().getItemConfig().getItemMap().containsKey(itemStack.getItemDataKey())) {
            return;
        }
        YamlConfiguration yml;
        if (file.exists()) {
            yml = new YamlConfiguration();
        } else {
            yml = YamlConfiguration.loadConfiguration(file);
        }
        ItemData itemData = ItemManage.getInstance().getConfigManage().getItemConfig().getItemMap().get(itemStack.getItemDataKey());
        yml.set(key + ".display", itemData.getDisplay());
        yml.set(key + ".material", itemData.getMaterial().name());
        itemData.getName().forEach((k, v) -> yml.set(key + "." + k, v));
        itemData.getLore().forEach((k, v) -> yml.set(key + "." + k, v));
        itemData.getMetas().forEach(meta -> yml.set(key, meta.save()));
        try {
            yml.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Subcommand("show")
    @CommandPermission("xkitemmanage.show")
    @Description("查看手持物品NBT")
    public void show(Player player) {
        ItemStack itemStack = player.getItemInHand();
        itemStack.showItemStack(player);
    }

    @Subcommand("reload")
    @CommandPermission("xkitemmanage.reload")
    @Description("重载配置文件")
    public void doReload(CommandSender sender) {
        ItemManage.getInstance().getConfigManage().registerConfig();
        sender.sendMessage("§a重载完成！");
    }
}
