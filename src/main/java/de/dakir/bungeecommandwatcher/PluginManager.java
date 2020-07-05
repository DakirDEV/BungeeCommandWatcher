package de.dakir.bungeecommandwatcher;

import de.dakir.bungeecommandwatcher.commands.CommandWatcher;
import de.dakir.bungeecommandwatcher.events.ChatEvent;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public class PluginManager {

    public static void load() {
        registerEvents();
        registerCommands();
    }

    public static void registerEvents() {
        net.md_5.bungee.api.plugin.PluginManager pm = ProxyServer.getInstance().getPluginManager();
        Plugin pl = Main.instance;
        pm.registerListener(pl, new ChatEvent());
    }

    public static void registerCommands() {
        net.md_5.bungee.api.plugin.PluginManager pm = ProxyServer.getInstance().getPluginManager();
        Plugin pl = Main.instance;
        pm.registerCommand(pl, new CommandWatcher("commandwatcher"));
        pm.registerCommand(pl, new CommandWatcher("cw"));
    }

}
