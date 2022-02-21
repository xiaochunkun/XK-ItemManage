package cn.xkmc6.xkitemmanage.command;

import cn.xkmc6.xkitemmanage.ItemManage;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import org.bukkit.command.CommandSender;

import java.io.File;

/**
 * @author 小坤
 * @date 2022/02/18 23:52
 */
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
    public void list(CommandSender sender) {

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
}
