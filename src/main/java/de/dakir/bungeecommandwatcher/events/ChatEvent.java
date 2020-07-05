package de.dakir.bungeecommandwatcher.events;

import de.dakir.bungeecommandwatcher.utils.Data;
import de.dakir.bungeecommandwatcher.utils.HexxAPI;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChatEvent implements Listener{
	
	@EventHandler
	public void onChat(net.md_5.bungee.api.event.ChatEvent e){
		if(e.isCommand()){
			if(e.getSender() instanceof ProxiedPlayer){
				ProxiedPlayer p = (ProxiedPlayer) e.getSender();
				if(!(p.hasPermission("commandwatcher.bypass"))){
					String msg = e.getMessage().toLowerCase();
					if(!(Data.blockedCommands.contains(msg.split(" ")[0]))){
						boolean b = false;
						for(String cmd : Data.blockedCommands){
							if(msg.contains(cmd)){
								b = true;
							}
						}
						if(b == false){
							HexxAPI.sendCommandWatcherMessage(p, e.getMessage());
						}
					}
				}
			}
		}
	}
}
