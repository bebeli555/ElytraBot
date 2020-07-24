package me.bebeli555.ElytraBot.Settings;

import me.bebeli555.ElytraBot.Commands;
import me.bebeli555.ElytraBot.Gui;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class StopAt {
	static Minecraft mc = Minecraft.getMinecraft();
	public static boolean toggle = false;
	static int X = me.bebeli555.ElytraBot.Commands.MaxX;
	static int Z = me.bebeli555.ElytraBot.Commands.MaxZ;

	@SubscribeEvent
	public void onUpdate(LivingUpdateEvent e) {
		if (me.bebeli555.ElytraBot.Main.toggle == true) {
			if (me.bebeli555.ElytraBot.Commands.MaxX != -1) {
				toggle = true;
			} else if (me.bebeli555.ElytraBot.Commands.MaxZ != -1) {
				toggle = true;
			}
		} else if (me.bebeli555.ElytraBot.Settings.Diagonal.toggle == true) {
			if (me.bebeli555.ElytraBot.Commands.MaxX != -1) {
				toggle = true;
			} else if (me.bebeli555.ElytraBot.Commands.MaxZ != -1) {
				toggle = true;
			}
		} else {
			toggle = false;
		}
		if (toggle == true) {
			if (e.getEntity().getName() == mc.player.getName()) {
				X = me.bebeli555.ElytraBot.Commands.MaxX;
				Z = me.bebeli555.ElytraBot.Commands.MaxZ;
				for(int i=1;i<=30;i++){  
					if (X != -1) {
						if ((int) mc.player.posX == X + i) {
							UnCheck();
						}
					}
				}

				for(int i=1;i<=30;i++){  
					if (Z != -1) {
						if ((int) mc.player.posZ == Z + i) {
							UnCheck();
						}
					}
				}

			}
		}
	}
	
	public static void UnCheck () {
		if (Commands.Log == false) {
			BlockPos Player = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
			mc.world.playSound(Player, SoundEvents.BLOCK_ANVIL_DESTROY, SoundCategory.AMBIENT, 100.0f, -5.0F, true);
		}
		mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.RED + "Stopping..." + ChatFormatting.GRAY + " Reached Max Coordinate"));
		Gui.TurnOff();
		if (me.bebeli555.ElytraBot.Commands.Log == true) {
			mc.world.sendQuittingDisconnectingPacket();
		}
		 
	}
}
