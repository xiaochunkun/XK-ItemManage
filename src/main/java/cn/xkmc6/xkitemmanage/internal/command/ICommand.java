package cn.xkmc6.xkitemmanage.internal.command;

import cn.xkmc6.xkitemmanage.ItemManage;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.ToString;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

/**
 * @author 小坤
 * @date 2022/02/06 10:13
 */
@ToString
public abstract class ICommand extends Command {

    private final Class SUB_CLASS;

    @Setter
    private String onlyConsole, onlyPlayer, noSubCommand, noPermission;

    public ICommand(Class clz, String name, String... aliases) {
        super(name, "item", "/<command>", Arrays.asList(aliases));
        SUB_CLASS = clz;
        onlyConsole = "§c此命令仅支持后台使用";
        onlyPlayer = "§c此命令仅支持游戏内使用";
        noSubCommand = "§c此命令无子命令";
        noPermission = "§c无权使用此命令";
    }

    /**
     * 执行命令
     *
     * @param sender 发送方
     * @param label  标签
     * @param args   arg游戏
     * @return boolean
     */
    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (args.length >= 1) {
            Method method = getMethodByCommand(args[0]);
            if (method != null) {
                if (check(sender, method.getAnnotation(Cmd.class))) {
                    try {
                        method.invoke(this, sender, label, args);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', noSubCommand));
            }
        } else {
            sendHelpMsg(sender);
        }
        return true;
    }

    /**
     * 检查权限、玩家、控制台
     *
     * @param sender 发送的玩家
     * @param cmd 发送的命令
     * @return 返回发送命令的结果
     */
    private boolean check(CommandSender sender, Cmd cmd) {
        if (!sender.hasPermission(cmd.permission())) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', noPermission));
            return false;
        } else if (cmd.cmdSender() == Cmd.CmdSender.PLAYER && !(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', onlyPlayer));
            return false;
        } else if (cmd.cmdSender() == Cmd.CmdSender.CONSOLE && sender instanceof Player) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', onlyConsole));
            return false;
        }
        return true;
    }

    /**
     * 获取处理子命令的method
     *
     * @param subCmd 请求的子命令
     * @return Method 处理请求命令的方法
     */
    private Method getMethodByCommand(String subCmd) {
        Method[] methods = SUB_CLASS.getMethods();
        for (Method method : methods) {
            Cmd cmd = method.getAnnotation(Cmd.class);
            if (cmd != null) {
                if (cmd.IgnoreCase() ? subCmd.equals(cmd.value()) : subCmd.equalsIgnoreCase(cmd.value())) {
                    Parameter[] parameter = method.getParameters();
                    if (parameter.length == 3 && parameter[0].getType() == CommandSender.class && parameter[1].getType() == String.class && parameter[2].getType() == String[].class) {
                        return method;
                    } else {
                        ItemManage.getInstance().getLogger().warning("found a Illegal sub command method in command " + getName() + " called: " + method.getName() + " in class: " + SUB_CLASS);
                    }
                }
            }
        }
        return null;
    }

    /**
     * 发送帮助信息
     *
     * @Param sender 被发送的对象
     */
    public void sendHelpMsg(CommandSender sender){
        sender.sendMessage("/item help --- §a查看帮助");
    }

    /**
     * 注册命令
     */
    public void register() {
        try {
            Field commandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMap.setAccessible(true);
            CommandMap map = (CommandMap) commandMap.get(Bukkit.getServer());
            map.register("item", this);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            ItemManage.getInstance().getLogger().warning("Error while register Command: " + this + " due to\n");
            ItemManage.getInstance().getLogger().warning(e.getLocalizedMessage());
        }
    }


    /**
     * 卸载命令
     */
    @SneakyThrows
    public void unregister() {
        Field commandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
        commandMap.setAccessible(true);
        CommandMap map = (CommandMap) commandMap.get(Bukkit.getServer());
        unregister(map);
    }

}

