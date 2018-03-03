package de.dakir.bungeecommandwatcher.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

import de.dakir.bungeecommandwatcher.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class ConfigManager {
		
	public static void checkFiles(){
		if(!Main.instance.getDataFolder().exists()){
			Main.instance.getDataFolder().mkdir();
		}

        File file = new File(Main.instance.getDataFolder(), "config.yml");
     
        if(!file.exists()){
            try(InputStream in = Main.instance.getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath());
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
	}
	
	@SuppressWarnings("unchecked")
	public static void load(){
		File file = new File(Main.instance.getDataFolder(), "config.yml");
	     
        if(!file.exists()){
            try(InputStream in = Main.instance.getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath());
            } catch(IOException e1) {
                e1.printStackTrace();
            }
        }
        
    	try {
			Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Main.instance.getDataFolder(), "config.yml"));
			
			Strings.prefix = ChatColor.translateAlternateColorCodes('&', config.getString("prefix"));
			Strings.format = ChatColor.translateAlternateColorCodes('&', config.getString("format")).replaceAll("%prefix%", Strings.prefix);
			Strings.noPermission = ChatColor.translateAlternateColorCodes('&', config.getString("noPermission"));
			Strings.commandNotExists = ChatColor.translateAlternateColorCodes('&', config.getString("commandNotExists"));
			Strings.playerNotFound = ChatColor.translateAlternateColorCodes('&', config.getString("playerNotFound"));
			Strings.enabledCommandWatcher = ChatColor.translateAlternateColorCodes('&', config.getString("enabledCommandWatcher"));
			Strings.disabledCommandWatcher = ChatColor.translateAlternateColorCodes('&', config.getString("disabledCommandWatcher"));
			Strings.enabledCommandWatcherOther_Target = ChatColor.translateAlternateColorCodes('&', config.getString("enabledCommandWatcherOther-Target"));
			Strings.enabledCommandWatcherOther_Sender = ChatColor.translateAlternateColorCodes('&', config.getString("enabledCommandWatcherOther-Sender"));
			Strings.disabledCommandWatcherOther_Target = ChatColor.translateAlternateColorCodes('&', config.getString("disabledCommandWatcherOther-Target"));
			Strings.disabledCommandWatcherOther_Sender = ChatColor.translateAlternateColorCodes('&', config.getString("disabledCommandWatcherOther-Sender"));
			Strings.noPlayer = ChatColor.translateAlternateColorCodes('&', config.getString("noPlayer"));
			Strings.playersWithCommandWatcher = ChatColor.translateAlternateColorCodes('&', config.getString("playersWithCommandWatcher"));
			Strings.listPlayerWithCommandWatcher = ChatColor.translateAlternateColorCodes('&', config.getString("listPlayerWithCommandWatcher"));
			Strings.reload = ChatColor.translateAlternateColorCodes('&', config.getString("reload"));
			Data.blockedCommands = (List<String>) config.getList("blockedCommands");
            
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}
}
