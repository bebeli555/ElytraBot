package me.bebeli555.ElytraBot.Settings;

import me.bebeli555.ElytraBot.Gui.Gui;
import me.bebeli555.ElytraBot.Highway.Main;

import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;

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

	@SubscribeEvent
	public void onUpdate(LivingUpdateEvent e) {
		double X = Settings.getDouble("StopAtX");
		double Z = Settings.getDouble("StopAtZ");
		if (Main.toggle || Diagonal.toggle) {
			if (X != -1) toggle = true;
			 else if (Z != -1) toggle = true;
		} else toggle = false;

		
		if (toggle) {
			if (e.getEntity().getName().equals(mc.player.getName())) {
				for(int i=1;i<=30;i++){  
					if (X != -1) {
						if ((int) mc.player.posX == X + i) {
							UnCheck();
						}
					}
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
		if (!Settings.getBoolean("StopAtLog")) {
			BlockPos Player = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
			mc.world.playSound(Player, SoundEvents.BLOCK_ANVIL_DESTROY, SoundCategory.AMBIENT, 100.0f, -5.0F, true);
		}
		mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.RED + "Stopping..." + ChatFormatting.GRAY + " Reached Max Coordinate"));
		Gui.TurnOff();
		if (Settings.getBoolean("StopAtLog")) {
			mc.world.sendQuittingDisconnectingPacket();
		}
		
		SendAlert("We reached the destination.", MessageType.INFO);
	}
	
	//Send windows alert for stuff
	public static void SendAlert(String message, MessageType type) {
		try{
		    SystemTray tray = SystemTray.getSystemTray();
		    Image image = Toolkit.getDefaultToolkit().createImage("Elytrabot.png");
		    TrayIcon trayIcon = new TrayIcon(image, "ElytraBot");
		    trayIcon.setImageAutoSize(true);
		    trayIcon.setToolTip("ElytraBot");
		    tray.add(trayIcon);
		    trayIcon.displayMessage("ElytraBot", message, type);
		}catch(Exception e){
			System.out.println("Elytrabot: Exception trying to send an alert.");
			e.getCause();
		}
	}
}
