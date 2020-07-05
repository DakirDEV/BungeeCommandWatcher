package de.dakir.bungeecommandwatcher.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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
			
			Strings.configversion = config.getString("configversion");
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
            
			String args[] = Strings.format.split("%command%");
			Strings.commandColor = "" + args[0].charAt(args[0].length()-1);
			
			//Updatechecker
			try {
				double version = Double.parseDouble(Main.instance.getDescription().getVersion());
				if(config.getDouble("configversion") < version){
					loadConfigBackup();
					update();
				}
			} catch (NullPointerException e) {
				System.out.println(Strings.cprefix + "The version number in the plugin.yml file is incorrect!");
			}
			
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void loadConfigBackup(){
		try {
			Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Main.instance.getDataFolder(), "config.yml"));
			
			ConfigBackup.configversion = Double.parseDouble(Main.instance.getDescription().getVersion());
			
			ConfigBackup.prefix = config.getString("prefix");
			ConfigBackup.format = config.getString("format");
			ConfigBackup.noPermission = config.getString("noPermission");
			ConfigBackup.commandNotExists = config.getString("commandNotExists");
			ConfigBackup.playerNotFound = config.getString("playerNotFound");
			ConfigBackup.enabledCommandWatcher = config.getString("enabledCommandWatcher");
			ConfigBackup.disabledCommandWatcher = config.getString("disabledCommandWatcher");
			ConfigBackup.enabledCommandWatcherOther_Target = config.getString("enabledCommandWatcherOther-Target");
			ConfigBackup.enabledCommandWatcherOther_Sender = config.getString("enabledCommandWatcherOther-Sender");
			ConfigBackup.disabledCommandWatcherOther_Target = config.getString("disabledCommandWatcherOther-Target");
			ConfigBackup.disabledCommandWatcherOther_Sender = config.getString("disabledCommandWatcherOther-Sender");
			ConfigBackup.noPlayer = config.getString("noPlayer");
			ConfigBackup.playersWithCommandWatcher = config.getString("playersWithCommandWatcher");
			ConfigBackup.listPlayerWithCommandWatcher = config.getString("listPlayerWithCommandWatcher");
			ConfigBackup.reload = config.getString("reload");
			
			ConfigBackup.blockedCommands = (List<String>) config.getList("blockedCommands");
		} catch (IOException e2) {
			e2.printStackTrace();
		}	
	}
	
	public static void update(){
		PrintWriter writer;
		try {
			writer = new PrintWriter(new FileWriter(Main.instance.getDataFolder() + "/config.yml", false));
			writer.write("#BungeeCommandWatcher by Dakir" + System.lineSeparator());
			writer.write("#Website: http://hexxcraft.net/" + System.lineSeparator());
			writer.write("" + System.lineSeparator());
			writer.write("#Please don't edit this" + System.lineSeparator());
			writer.write("configversion: " + ConfigBackup.configversion + System.lineSeparator());
			writer.write("" + System.lineSeparator());
			writer.write("#Prefix of all messages" + System.lineSeparator());
			writer.write("prefix: '" + ConfigBackup.prefix + "'" + System.lineSeparator());
			writer.write("" + System.lineSeparator());
			writer.write("#Format of CommandWatcher messages" + System.lineSeparator());
			writer.write("#Placeholders:" + System.lineSeparator());
			writer.write("# - %prefix% = plugin prefix" + System.lineSeparator());
			writer.write("# - %player% = player name" + System.lineSeparator());
			writer.write("# - %server% = players server name" + System.lineSeparator());
			writer.write("# - %command% = command which is sent" + System.lineSeparator());
			writer.write("format: '" + ConfigBackup.format + "'" + System.lineSeparator());
			writer.write("" + System.lineSeparator());
			writer.write("#Commands which are not displayed" + System.lineSeparator());
			writer.write("blockedCommands:" + System.lineSeparator());
			for(int i = 0; i < ConfigBackup.blockedCommands.size(); i++){
				writer.write("- '" + ConfigBackup.blockedCommands.get(i) + "'" + System.lineSeparator());
			}
			writer.write("" + System.lineSeparator());
			writer.write("#Messages" + System.lineSeparator());
			writer.write("noPermission: '" + ConfigBackup.noPermission + "'" + System.lineSeparator());
			writer.write("commandNotExists: '" + ConfigBackup.commandNotExists + "'" + System.lineSeparator());
			writer.write("playerNotFound: '" + ConfigBackup.playerNotFound + "'" + System.lineSeparator());
			writer.write("enabledCommandWatcher: '" + ConfigBackup.enabledCommandWatcher + "'" + System.lineSeparator());
			writer.write("disabledCommandWatcher: '" + ConfigBackup.disabledCommandWatcher + "'" + System.lineSeparator());
			writer.write("enabledCommandWatcherOther-Target: '" + ConfigBackup.enabledCommandWatcherOther_Target + "'" + System.lineSeparator());
			writer.write("enabledCommandWatcherOther-Sender: '" + ConfigBackup.enabledCommandWatcherOther_Sender + "'" + System.lineSeparator());
			writer.write("disabledCommandWatcherOther-Target: '" + ConfigBackup.disabledCommandWatcherOther_Target + "'" + System.lineSeparator());
			writer.write("disabledCommandWatcherOther-Sender: '" + ConfigBackup.disabledCommandWatcherOther_Sender + "'" + System.lineSeparator());
			writer.write("noPlayer: '" + ConfigBackup.noPlayer + "'" + System.lineSeparator());
			writer.write("playersWithCommandWatcher: '" + ConfigBackup.playersWithCommandWatcher + "'" + System.lineSeparator());
			writer.write("listPlayerWithCommandWatcher: '" + ConfigBackup.listPlayerWithCommandWatcher + "'" + System.lineSeparator());
			writer.write("reload: '" + ConfigBackup.reload + "'" + System.lineSeparator());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		load();
	}
}
