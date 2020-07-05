package de.dakir.bungeecommandwatcher.commands;

import de.dakir.bungeecommandwatcher.utils.ConfigManager;
import de.dakir.bungeecommandwatcher.utils.Data;
import de.dakir.bungeecommandwatcher.utils.Strings;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CommandWatcher extends Command {

    public CommandWatcher(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (sender.hasPermission("commandwatcher.reload")) {
                ConfigManager.load();
                sender.sendMessage(new TextComponent(Strings.prefix + Strings.reload));
                return;
            } else {
                sender.sendMessage(new TextComponent(Strings.prefix + Strings.noPermission));
                return;
            }
        }

        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if (args.length == 0) {
                if (p.hasPermission("commandwatcher.use")) {
                    if (Data.commandWatcher.contains(p.getName())) {
                        Data.commandWatcher.remove(p.getName());
                        p.sendMessage(new TextComponent(Strings.prefix + Strings.disabledCommandWatcher));
                    } else {
                        Data.commandWatcher.add(p.getName());
                        p.sendMessage(new TextComponent(Strings.prefix + Strings.enabledCommandWatcher));
                    }
                } else {
                    p.sendMessage(new TextComponent(Strings.prefix + Strings.noPermission));
                }
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("list")) {
                    if (p.hasPermission("commandwatcher.use")) {
                        if (Data.commandWatcher.size() == 0) {
                            p.sendMessage(new TextComponent(Strings.prefix + Strings.noPlayer));
                        } else {
                            p.sendMessage(new TextComponent(Strings.prefix + Strings.playersWithCommandWatcher));
                            for (String s : Data.commandWatcher) {
                                p.sendMessage(new TextComponent(Strings.prefix + Strings.listPlayerWithCommandWatcher.replace("%player%", s)));
                            }
                        }
                    } else {
                        p.sendMessage(new TextComponent(Strings.prefix + Strings.noPermission));
                    }
                } else {
                    if (p.hasPermission("commandwatcher.other")) {
                        try {
                            ProxiedPlayer other = ProxyServer.getInstance().getPlayer(args[0]);
                            if (Data.commandWatcher.contains(other.getName())) {
                                Data.commandWatcher.remove(other.getName());
                                p.sendMessage(new TextComponent(Strings.prefix + Strings.disabledCommandWatcherOther_Sender.replace("%player%", other.getName())));
                                other.sendMessage(new TextComponent(Strings.prefix + Strings.disabledCommandWatcherOther_Target.replace("%player%", p.getName())));
                            } else {
                                Data.commandWatcher.add(other.getName());
                                p.sendMessage(new TextComponent(Strings.prefix + Strings.enabledCommandWatcherOther_Sender.replace("%player%", other.getName())));
                                other.sendMessage(new TextComponent(Strings.prefix + Strings.enabledCommandWatcherOther_Target.replace("%player%", p.getName())));
                            }
                        } catch (NullPointerException e) {
                            p.sendMessage(new TextComponent(Strings.prefix + Strings.playerNotFound));
                        }
                    } else {
                        p.sendMessage(new TextComponent(Strings.prefix + Strings.noPermission));
                    }
                }
            } else {
                p.sendMessage(new TextComponent(Strings.prefix + Strings.commandNotExists));
            }
        } else {
            sender.sendMessage(new TextComponent(Strings.onlyPlayer));
        }
    }
}
