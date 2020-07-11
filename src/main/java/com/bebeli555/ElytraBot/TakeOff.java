package com.bebeli555.ElytraBot;

import java.util.Objects;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.text.TextComponentString;

public class TakeOff {
	static Minecraft mc = Minecraft.getMinecraft();
	
	static int delay = 0;
	static boolean Jumped = false;
	static boolean diagonal;
	static boolean ActivatePacketFly = false;
	static double StartPos = -69;
	
	static boolean WarningMessage = false;

	public static void TakeOffMethod (boolean Diagonal, boolean PacketFly, boolean SlowGlide) {
		try {
			diagonal = Diagonal;
			
			//Warning message
			if ((int)mc.player.posX != 0 && (int)mc.player.posZ != 0) {
				if (Diagonal == false) {
					if (WarningMessage == false) {
						WarningMessage = true;
						mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.RED + "You dont seem to be standing on the middle of the highway?"));
					}
				}
			}
			//Jump
			if (Jumped == false) {
				if (mc.player.onGround) {
					Jumped = true;
					mc.player.jump();
					StartPos = mc.player.posY;
				}
			}
			
			//Reset takeoff
			if (Jumped == true) {
				if (mc.player.onGround) {
					ResetTakeOff();
				}
			}
			
			//Activate packetfly or slowglide and send elytra activation packet
			if (mc.player.posY < mc.player.lastTickPosY) {
				//Activate elytra
				if (!mc.player.isElytraFlying()) {
					Objects.requireNonNull(mc.getConnection()).sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
				}
				
				//This prevents packetfly from phasing through ground (Hopefully)
				double PreventPhase = (StartPos + 0.6);
				//Activate PacketFly
				if (PacketFly == true) {
					if (mc.player.posY > PreventPhase) {
						ActivatePacketFly = true;
					} else {
						ResetTakeOff();
					}
				} else if (SlowGlide == true) {
					if (!mc.player.isElytraFlying()) {
						mc.player.setVelocity(0, -0.025, 0);
					}
				}
			}
			
			//Wait a bit then start moving when takeoff is successfull
			if (mc.player.isElytraFlying()) {
				Main.SetMotion(0, -(Settings.GlideSpeed / 10000f), 0);
				
				delay++;
				if (delay > 20) {
					delay = 0;
					ActivateElytraBot();
				}
			}
			
		} catch (Exception e) {
			System.out.println("Exception while taking off Elytrabot");
			System.out.println(e.getStackTrace());
		}
	}
	
	public static void ResetTakeOff() {
		delay = 0;
		Jumped = false;
		ActivatePacketFly = false;
	}
	
	public static void ActivateElytraBot() {
		ActivatePacketFly = false;
		if (diagonal == true) {
			Diagonal.cancelled = false;
			Diagonal.manuver = false;
			Diagonal.MoveOn = true;
			Diagonal.setMove(false, true);
			Diagonal.delay = 0;
			Diagonal.takeoff = true;
			Diagonal.cancelled = false;
			Diagonal.mess = false;
		} else {
			Main.manuver = false;
			Main.manuverdelay = 0;
			Main.isgoing = false;
			Main.delay2 = 0;
			Main.delay3 = 0;
			Main.manuver1 = false;
			Main.manuver2 = false;
			Main.manuver3 = false;
			Main.manuverM1 = false;
			Main.manuverM2 = false;
			Main.manuverM3 = false;
			Main.bebeli555 = false;
			Main.MoveOn = true;
			Main.setMove(1.8, false, true, 0);
			Main.takeoff = true;
		}
	}
	
	public static boolean ShouldActivatePacketFly() {
		return ActivatePacketFly;
	}
}
