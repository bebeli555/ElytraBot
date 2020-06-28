package com.bebeli555.ElytraBot;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Renderer {
	private Minecraft mc = Minecraft.getMinecraft();

	//This renders the Status above your hotbar
	@SubscribeEvent
	public void render(RenderGameOverlayEvent.Text e) {
		if (com.bebeli555.ElytraBot.Main.toggle == true) {
			mc.fontRenderer.drawString(ChatFormatting.RED + "ElytraBot Status: " + ChatFormatting.BLUE + com.bebeli555.ElytraBot.Main.status, 390, 475, 0xffff);
		} else if (com.bebeli555.ElytraBot.Main.baritonetoggle == true) {
			mc.fontRenderer.drawString(ChatFormatting.RED + "ElytraBot Status: " + ChatFormatting.BLUE + "Using baritone to overcome obstacle", 390, 475, 0xffff);
		} else if (com.bebeli555.ElytraBot.AutoRepair.AutoRepair == true) {
			mc.fontRenderer.drawString(ChatFormatting.RED + "ElytraBot Status: " + ChatFormatting.BLUE + "Repairing Elytra", 390, 475, 0xffff);
		} else if (com.bebeli555.ElytraBot.Diagonal.toggle == true) {
			mc.fontRenderer.drawString(ChatFormatting.RED + "ElytraBot Status: " + ChatFormatting.BLUE + com.bebeli555.ElytraBot.Diagonal.status, 390, 475, 0xffff);
		} else if (com.bebeli555.ElytraBot.Diagonal.baritonetoggle == true) {
			mc.fontRenderer.drawString(ChatFormatting.RED + "ElytraBot Status: " + ChatFormatting.BLUE + "Using baritone to overcome obstacle", 390, 475, 0xffff);
		}
	}
}
