package com.bebeli555.ElytraBot.PathFinding;

import com.bebeli555.ElytraBot.ElytraFly;
import com.bebeli555.ElytraBot.Main;
import com.bebeli555.ElytraBot.Settings;
import com.bebeli555.ElytraBot.TakeOff;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class Center {
	//This centers you on the middle of the block when going for a manuver
	//If we dont do this then theres a change it will start clipping into a block
	//It also centers the other way when going straight again in Manuver class
	
	static Minecraft mc = Minecraft.getMinecraft();
	
	//Centers the player and also activates manuver when done
	public static void ManuverCenter (boolean Right) {
		Manuver.IsManuvering = true;
		if (IsManuverCentered(Main.z)) {
			Manuver.StartManuver(Right);
			Manuver.HasCentered = true;
		} else {
			if (ShouldCenterBackwards()) {
				CenterBackwards();
			} else {
				CenterForward();
			}
		}
	}
	
	public static boolean IsManuverCentered(boolean Z) {
		if (Z == false) {
			double Coord = Math.round(mc.player.posZ * 10) / 10.0;
			String StringCoord = String.valueOf(Coord);
			
			if (StringCoord.contains(".5")) {
				return true;
			}
		} else {
			double Coord = Math.round(mc.player.posX * 10) / 10.0;
			String StringCoord = String.valueOf(Coord);
			
			if (StringCoord.contains(".5")) {
				return true;
			}
		}
		return false;
	}
	
	//Check if we overlapped the center target and then start centering backwards
	public static boolean ShouldCenterBackwards() {
		double Coord = Math.round(mc.player.posX * 10) / 10.0;
		String CoordX = String.valueOf(Coord);
		
		double Coord2 = Math.round(mc.player.posZ * 10) / 10.0;
		String CoordZ = String.valueOf(Coord2);
		
		if (Main.direction.equals(EnumFacing.NORTH)) {
			if (Coord2 < 0) {
				if (CoordZ.contains(".7") || CoordZ.contains(".6") || CoordZ.contains(".8")) {
					return true;
				}
			} else {
				if (CoordZ.contains(".3") || CoordZ.contains(".2") || CoordZ.contains(".4")) {
					return true;
				}
			}
		} else if (Main.direction.equals(EnumFacing.WEST)) {
			if (Coord < 0) {
				if (CoordX.contains(".7") || CoordX.contains(".6") || CoordX.contains(".8")) {
					return true;
				}
			} else {
				if (CoordX.contains(".3") || CoordX.contains(".2") || CoordX.contains(".4")) {
					return true;
				}
			}
		} else if (Main.direction.equals(EnumFacing.EAST)) {
			if (Coord < 0) {
				if (CoordX.contains(".3") || CoordX.contains(".2") || CoordX.contains(".4")) {
					return true;
				}
			} else {
				if (CoordX.contains(".7") || CoordX.contains(".6") || CoordX.contains(".8")) {
					return true;
				}
			}
		} else if (Main.direction.equals(EnumFacing.SOUTH)) {
			if (Coord2 < 0) {
				if (CoordZ.contains(".3") || CoordZ.contains(".2") || CoordZ.contains(".4")) {
					return true;
				}
			} else {
				if (CoordZ.contains(".7") || CoordZ.contains(".6") || CoordZ.contains(".8")) {
					return true;
				}	
			}
		}
		
		return false;
	}
	
	public static boolean IsYCentered() {
		if (!mc.player.isElytraFlying()) {
			return false;
		}
		
		double Coord = Math.round(mc.player.posY * 10) / 10.0;
		String CoordY = String.valueOf(Coord);
		
		if (CoordY.contains(".3") || CoordY.contains(".2")) {
			return true;
		}
		return false;
	}
	
	//Start going forward slowly
	public static void CenterForward() {
		Main.MoveOn = true;
		ElytraFly.FlyMinus = Settings.FlySpeed - 0.1;
	}
	
	//Start going backwards slowly
	public static void CenterBackwards() {
		Main.MoveOn = true;
		ElytraFly.FlyMinus = Settings.FlySpeed + 0.1;
	}
}
