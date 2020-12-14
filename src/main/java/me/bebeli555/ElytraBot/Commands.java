package me.bebeli555.ElytraBot;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.bebeli555.ElytraBot.Gui.Gui;
import me.bebeli555.ElytraBot.Gui.Node;
import me.bebeli555.ElytraBot.Gui.SetNodes;
import me.bebeli555.ElytraBot.Settings.Settings;
import me.bebeli555.ElytraBot.Settings.SettingsInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Commands {
	Minecraft mc = Minecraft.getMinecraft();
	public static boolean GuiON = false;

	//Ingame commands.
	@SubscribeEvent
	public void ChatEvent(ClientChatEvent e) {
		String prefix = "++";
		if (!e.getMessage().startsWith(prefix)) {
			return;
		}
		String message = e.getMessage().replace(prefix, "");
		String lowercase = message.toLowerCase();
		e.setCanceled(true);
		
		//Open GUI on ++GUI Command
		if (lowercase.startsWith("gui") || lowercase.startsWith("open")) {
			GuiON = true;
		}
		
		//Help
		else if (lowercase.startsWith("help")) {
			SetNodes.SetAllNodes();
			mc.player.sendMessage(new TextComponentString(ChatFormatting.RED+ "ElytraBot " + ChatFormatting.GOLD + "V" + Init.version + ChatFormatting.RED + " Made by: " + ChatFormatting.GREEN + "bebeli555"));
			mc.player.sendMessage(new TextComponentString(""));
			mc.player.sendMessage(new TextComponentString(ChatFormatting.GREEN + "++GUI = " + ChatFormatting.DARK_AQUA + "Open the GUI"));
			mc.player.sendMessage(new TextComponentString(ChatFormatting.GREEN + "++Commands = " + ChatFormatting.DARK_AQUA + "Get ingame Command help page"));
		}
		
		//Command Help Page
		else if (lowercase.startsWith("commands")) {
			mc.player.sendMessage(new TextComponentString(ChatFormatting.GOLD + "Elytrabot Chat Commands Help Page."));
			mc.player.sendMessage(new TextComponentString(ChatFormatting.RED + "(You should use the GUI Instead of commands tho)"));
			mc.player.sendMessage(new TextComponentString(""));
			mc.player.sendMessage(new TextComponentString(ChatFormatting.GREEN + "++Start = " + ChatFormatting.DARK_AQUA + "Start elytrabot"));
			mc.player.sendMessage(new TextComponentString(ChatFormatting.GREEN + "++Stop = " + ChatFormatting.DARK_AQUA + "Stop elytrabot"));
			mc.player.sendMessage(new TextComponentString(ChatFormatting.GREEN + "++Set <Setting> <Value> = " + ChatFormatting.DARK_AQUA + "Sets Setting"));
			mc.player.sendMessage(new TextComponentString(ChatFormatting.GREEN + "++Mode <Mode> = " + ChatFormatting.DARK_AQUA + "Sets the Mode"));
			mc.player.sendMessage(new TextComponentString(ChatFormatting.GREEN + "++Settings = " + ChatFormatting.DARK_AQUA + "Displays all the settings available"));
			mc.player.sendMessage(new TextComponentString(ChatFormatting.GREEN + "++Info <Setting> = " + ChatFormatting.DARK_AQUA + "Displays info about setting."));
		}
		
		//Start
		else if (lowercase.startsWith("start")) {
			Gui.TurnOn();
			mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.GREEN + "Started!"));
		}
		
		//Stop
		else if (lowercase.startsWith("stop")) {
			Gui.TurnOff();
			mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.RED + "Stopped!"));
		}
		
		//Display settings
		else if (lowercase.startsWith("settings")) {
			for (int i = 0; i < SettingsInfo.Settings.size(); i++) {
				try {
					if (Node.getNodeFromID(SettingsInfo.Settings.get(i).getName()) != null) {
						mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_AQUA + SettingsInfo.Settings.get(i).getName()));
					}
				} catch (Exception bebeli555) {}
			}
		}
		
		//Display info about settings.
		else if (lowercase.startsWith("info")) {
			try {
				String[] split = message.split(" ");
				for (int i = 0; i < SettingsInfo.Settings.size(); i++) {
					SettingsInfo s = SettingsInfo.Settings.get(i);
					if (split[1].equals(s.getName().toLowerCase())) {
						mc.player.sendMessage(new TextComponentString(""));
						mc.player.sendMessage(new TextComponentString(ChatFormatting.GREEN + "Info about: " + ChatFormatting.GOLD + split[1]));
						for (int i2 = 0; i2 < Node.getNodeFromID(s.getName()).Tips.size(); i2++) {
							mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_AQUA + Node.getNodeFromID(split[1]).Tips.get(i2)));
						}
						return;
					}
				}
				mc.player.sendMessage(new TextComponentString(ChatFormatting.RED + "No Setting found!"));
			}catch (Exception e2) {
				mc.player.sendMessage(new TextComponentString(ChatFormatting.RED + "No info available!"));
			}
		}
		
		//Commands for setting values to the settings
		else if (lowercase.startsWith("set") || lowercase.startsWith("toggle")) {
			try {
				String[] split = message.split(" ");
				String settingName = split[1];
				Object settingValue = split[2];
				for (int i = 0; i < SettingsInfo.Settings.size(); i++) {
					SettingsInfo s = SettingsInfo.Settings.get(i);
					if (s.getName().toLowerCase().equals(settingName)) {
						Settings.setValue(settingName, settingValue);
						mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.GREEN + "Set " + ChatFormatting.GOLD + settingName + ChatFormatting.GREEN + "'s value to " + ChatFormatting.GOLD + String.valueOf(settingValue)));
						return;
					}
				}
				mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.RED + "No Setting Found! Type" + ChatFormatting.GREEN + " ++Settings " + ChatFormatting.RED + "to display them"));
			}catch (Exception bebeli555IsACoolParson) {
				mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.RED + "Wrong input! Type" + ChatFormatting.GREEN + " ++Commands " + ChatFormatting.RED + "for help!"));
			}
		} else {
			mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.RED + "Unknown command. Type" + ChatFormatting.GREEN + " ++Help " + ChatFormatting.RED + "for help!"));
		}
	}
}