package com.bebeli555.ElytraBot;
import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.sound.SoundEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class StopAt {
	static Minecraft mc = Minecraft.getMinecraft();
	static boolean toggle = false;
	static int X = com.bebeli555.ElytraBot.Commands.MaxX;
	static int Z = com.bebeli555.ElytraBot.Commands.MaxZ;

	@SubscribeEvent
	public void onUpdate(LivingUpdateEvent e) {
		if (com.bebeli555.ElytraBot.Main.toggle == true) {
			if (com.bebeli555.ElytraBot.Commands.MaxX != -1) {
				toggle = true;
			} else if (com.bebeli555.ElytraBot.Commands.MaxZ != -1) {
				toggle = true;
			}
		} else if (com.bebeli555.ElytraBot.Diagonal.toggle == true) {
			if (com.bebeli555.ElytraBot.Commands.MaxX != -1) {
				toggle = true;
			} else if (com.bebeli555.ElytraBot.Commands.MaxZ != -1) {
				toggle = true;
			}
		} else {
			toggle = false;
		}
		if (toggle == true) {
			if (e.getEntity().getName() == mc.player.getName()) {
				X = com.bebeli555.ElytraBot.Commands.MaxX;
				Z = com.bebeli555.ElytraBot.Commands.MaxZ;
				for(int i=1;i<=30;i++){  
					if (X != -1) {
						if ((int) mc.player.posX == X + i) {
							this.UnCheck();
						}
					}
				}

				for(int i=1;i<=30;i++){  
					if (Z != -1) {
						if ((int) mc.player.posZ == Z + i) {
							this.UnCheck();
						}
					}
				}

			}
		}
	}
	
	public void UnCheck () {
		//play epic enderdragon death sound
		if (Commands.Log == false) {
			BlockPos Player = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
			mc.world.playSound(Player, SoundEvents.ENTITY_ENDERDRAGON_DEATH, SoundCategory.AMBIENT, 2.5f, 1.0F, true);
		}
		
		mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.RED + "Stopping..." + ChatFormatting.GRAY + " Reached Max Coordinate"));
		toggle = false;
		com.bebeli555.ElytraBot.Main.UnCheck();
		com.bebeli555.ElytraBot.Main.toggle = false;
		com.bebeli555.ElytraBot.Diagonal.UnCheck();
		com.bebeli555.ElytraBot.Diagonal.toggle = false;
		Gui.TurnOff();
		if (com.bebeli555.ElytraBot.Commands.Log == true) {
			mc.world.sendQuittingDisconnectingPacket();
		}
	}
}
