package de.dakir.bungeecommandwatcher.utils;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class HexxAPI {
	
	public static void sendCommandWatcherMessage(ProxiedPlayer player, String command){
		for(ProxiedPlayer p : BungeeCord.getInstance().getPlayers()){
			if(Data.commandWatcher.contains(p.getName())){
				if(p.getName() != player.getName()){
					p.sendMessage(new TextComponent(Strings.format.replace("%player%", player.getName()).replace("%command%", command)));
				}
			}
		}
	}

}
