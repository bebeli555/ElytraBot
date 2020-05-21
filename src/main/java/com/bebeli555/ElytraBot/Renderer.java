package com.bebeli555.ElytraBot;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Renderer {
	private Minecraft mc = Minecraft.getMinecraft();

	@SubscribeEvent
	public void render(RenderGameOverlayEvent.Text e) {
		if (com.bebeli555.ElytraBot.Main.toggle == true) {
			mc.fontRenderer.drawString(ChatFormatting.RED + "ElytraBot Status: " + ChatFormatting.BLUE + com.bebeli555.ElytraBot.Main.status, 390, 475, 0xffff);
		}
	}
}
