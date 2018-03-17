package de.dakir.bungeecommandwatcher.utils;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class HexxAPI {
	
	public static void sendCommandWatcherMessage(ProxiedPlayer player, String command){
		for(ProxiedPlayer p : BungeeCord.getInstance().getPlayers()){
			if(Data.commandWatcher.contains(p.getName())){
				if(p.getName() != player.getName()){
					String[] args = command.split(" ");
		        	String msg = "";
		        	for(int i = 0; i < args.length; i++){
		        		msg = msg + " §" + Strings.commandColor + args[i];
		        	}
					
					p.sendMessage(new TextComponent(Strings.format.replace("%player%", player.getName()).replace("%command%", msg).replace("%server%", player.getServer().getInfo().getName())));
				}
			}
		}
	}

}
