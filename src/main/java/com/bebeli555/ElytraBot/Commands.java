package com.bebeli555.ElytraBot;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Commands {
	Minecraft mc = Minecraft.getMinecraft();

	@SubscribeEvent
	public void ChatEvent(ClientChatEvent e) {
		if (e.getMessage().equalsIgnoreCase("++Start")) {
			mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.GREEN + "Started!"));
			com.bebeli555.ElytraBot.Main.Check();
			com.bebeli555.ElytraBot.Main.toggle = true;
			e.setCanceled(true);
		} else if (e.getMessage().equalsIgnoreCase("++Help")) {
					String message = ChatFormatting.GREEN + "Elytra Highway bot " + ChatFormatting.RED + "V1.0" + ChatFormatting.GREEN + " Made by:"  + ChatFormatting.RED + " bebeli555";
					mc.player.sendMessage(new TextComponentString(message));
			mc.player.sendMessage(new TextComponentString(""));
			mc.player.sendMessage(new TextComponentString(ChatFormatting.RED + "++Start" + ChatFormatting.GREEN
					+ " = Start Pathfinding to direction you are looking at!"));
			mc.player.sendMessage(new TextComponentString(
					ChatFormatting.RED + "++Stop" + ChatFormatting.GREEN + " = Stop Pathfinding!"));
			mc.player.sendMessage(new TextComponentString(""));
			mc.player.sendMessage(new TextComponentString(ChatFormatting.RED + "Warning: " + ChatFormatting.GREEN + "It is only Recommended to use this in the highways and start it at " + ChatFormatting.RED + "X: 0 " + ChatFormatting.GREEN + "or " + ChatFormatting.RED + "Z: 0"));
			e.setCanceled(true);
		} else if (e.getMessage().equalsIgnoreCase("++Stop")) {
			mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.RED + "Stopped!"));
			com.bebeli555.ElytraBot.Main.toggle = false;
			com.bebeli555.ElytraBot.Main.UnCheck();
			e.setCanceled(true);
		} else if (e.getMessage().startsWith("++")) {
			mc.player.sendMessage(new TextComponentString(ChatFormatting.RED + "Unknown command Type"
					+ ChatFormatting.DARK_RED + " ++Help " + ChatFormatting.RED + "for help"));
			e.setCanceled(true);
		}

	}
}