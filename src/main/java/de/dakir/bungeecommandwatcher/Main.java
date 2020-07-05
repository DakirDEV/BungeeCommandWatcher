package de.dakir.bungeecommandwatcher;

import de.dakir.bungeecommandwatcher.metrics.Metrics;
import de.dakir.bungeecommandwatcher.utils.ConfigManager;
import de.dakir.bungeecommandwatcher.utils.Strings;
import net.md_5.bungee.api.plugin.Plugin;

public class Main extends Plugin {

    public static Plugin instance;

    @Override
    public void onEnable() {
        Main.instance = this;

        PluginManager.load();

        ConfigManager.checkFiles();
        ConfigManager.load();

        @SuppressWarnings("unused")
        Metrics metrics = new Metrics(Main.instance);

        System.out.println(Strings.cprefix + "Plugin wurde aktiviert!");
    }

    @Override
    public void onDisable() {
        System.out.println(Strings.cprefix + "Plugin wurde deaktiviert!");
    }

}
