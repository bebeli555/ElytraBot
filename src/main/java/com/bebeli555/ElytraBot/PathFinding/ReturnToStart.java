package com.bebeli555.ElytraBot.PathFinding;

import com.bebeli555.ElytraBot.Main;
import com.bebeli555.ElytraBot.Renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class ReturnToStart {
	static Minecraft mc = Minecraft.getMinecraft();
	
	//Returns to startup position
	public static void ReturnToStartup(boolean Right) {
		if (CanReturn()) {
			if (IsStartClear()) {
				Main.status = "Returning to Startup Position";
				
				if (ShouldSlow()) {
					Main.ManuverSpeed = 0.08;
				} else {
					Main.ManuverSpeed = 0.45;
				}
				
				Main.MoveOn = true;
				Main.setMove(0, Right, false, 0);
			}
		}
	}
	
	public static void StartReturn() {
		DrawYellow();
		if (Main.z == true) {
			if (Main.StartupPosZ < (int)mc.player.posZ) {
				ReturnToStartup(false);
			} else {
				ReturnToStartup(true);
			}
		} else {
			if (Main.StartupPosX < (int)mc.player.posX) {
				ReturnToStartup(false);
			} else {
				ReturnToStartup(true);
			}
		}
	}
	
	public static void DrawYellow() {
		BlockPos Startup;
		if (Main.direction.equals(EnumFacing.NORTH)) {
			Startup = new BlockPos(Main.StartupPosX ,mc.player.posY ,mc.player.posZ);
		} else if (Main.direction.equals(EnumFacing.WEST)) {
			Startup = new BlockPos(mc.player.posX ,mc.player.posY ,Main.StartupPosZ);
		} else if (Main.direction.equals(EnumFacing.EAST)) {
			Startup = new BlockPos(mc.player.posX ,mc.player.posY ,Main.StartupPosZ);
		} else {
			Startup = new BlockPos(Main.StartupPosX ,mc.player.posY ,mc.player.posZ);
		}
		
		Renderer.DrawYellowBox(Startup);
	}
	
	//Check if returning is possible
	public static boolean CanReturn() {
		BlockPos Start = new BlockPos(Main.StartupPosX, mc.player.posY, Main.StartupPosZ);
		if (GetPath.CanGoToBlock(Start)) {
			if (IsStartClear()) {
				return true;
			}
		}
		
		return false;
	}
	
	//Check if upcoming blocks from startup is clear
	public static boolean IsStartClear() {
		for (int i = 1; i < 5; i++) {
			if (Main.status == "Going Straight") {
				BlockPos Pos;
				BlockPos Check;
				if (Main.direction.equals(EnumFacing.NORTH)) {
					Pos = new BlockPos(Main.StartupPosX ,mc.player.posY ,mc.player.posZ);
				} else if (Main.direction.equals(EnumFacing.WEST)) {
					Pos = new BlockPos(mc.player.posX ,mc.player.posY ,Main.StartupPosZ);
				} else if (Main.direction.equals(EnumFacing.EAST)) {
					Pos = new BlockPos(mc.player.posX ,mc.player.posY ,Main.StartupPosZ);
				} else {
					Pos = new BlockPos(Main.StartupPosX ,mc.player.posY ,mc.player.posZ);
				}
				
				if (Main.direction.equals(EnumFacing.NORTH)) {
					Check = Pos.add(0, 0, -i);
				} else if (Main.direction.equals(EnumFacing.WEST)) {
					Check = Pos.add(-i, 0, 0);
				} else if (Main.direction.equals(EnumFacing.EAST)) {
					Check = Pos.add(i, 0, 0);
				} else {
					Check = Pos.add(0, 0, i);
				}

				if (GetPath.IsSolid(Check)) {
					return false;
				}
			}
		}
		return true;
	}
	
	//If were inside the block then slow down the manuver speed
	public static boolean ShouldSlow() {
		if (Main.z == true) {
			if (Main.StartupPosZ == (int)mc.player.posZ) {
				return true;
			}
		} else {
			if (Main.StartupPosX == (int)mc.player.posX) {
				return true;
			}
		}
		
		return false;
	}
	
	//Check if player is not on startup position
	public static boolean IsNotAtStartup() {
		if (Main.z == true) {
			if ((int)mc.player.posZ != Main.StartupPosZ) {
				return true;
			}
		} else {
			if ((int)mc.player.posX != Main.StartupPosX) {
				return true;
			}
		}
		
		return false;
	}
	
	//Checks if its flown past the obstacle
	public static boolean HasPassedManuver() {
		BlockPos Player = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
		BlockPos Check = Renderer.GreenPos;
		
		
		if (Main.direction.equals(EnumFacing.NORTH)) {
			if ((int)Player.getZ() < (int)Check.getZ()) {
				return true;
			}
		} else if (Main.direction.equals(EnumFacing.WEST)) {
			if ((int)Player.getX() < (int)Check.getX()) {
				return true;
			}
		} else if (Main.direction.equals(EnumFacing.EAST)) {
			if ((int)Player.getX() > (int)Check.getX()) {
				return true;
			}
		} else if (Main.direction.equals(EnumFacing.SOUTH)) {
			if ((int)Player.getZ() > (int)Check.getZ()) {
				return true;
			}
		}
		
		return false;
	}
}
