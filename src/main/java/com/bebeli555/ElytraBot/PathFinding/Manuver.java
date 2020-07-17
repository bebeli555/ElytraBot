package com.bebeli555.ElytraBot.PathFinding;

import com.bebeli555.ElytraBot.ElytraFly;
import com.bebeli555.ElytraBot.Main;
import com.bebeli555.ElytraBot.Renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class Manuver {
	static Minecraft mc = Minecraft.getMinecraft();
	public static boolean IsManuvering = false;
	public static boolean HasCentered = false;
	
	public static void StartManuver (boolean right) {
		IsManuvering = true;
		Main.status = "Avoiding Collision";
		ElytraFly.FlyMinus = 0;
		
		//Start going straight again
		if (GetPath.ShouldManuver(true) == false) {
			if (IsCentered(Main.z) == true) {
				GoStraight();
			}
		}
		
		if (GetPath.ShouldManuver(true) == false) {
			Main.ManuverSpeed = 0.1;
		} else {
			Main.ManuverSpeed = 0.45;
		}
		
		if (right == true) {
			Main.MoveOn = true;
			Main.setMove(0, false, false, 0);
		} else {
			Main.MoveOn = true;
			Main.setMove(0, true, false, 0);
		}
	}
	
	public static void GoStraight () {
		HasCentered = false;
		IsManuvering = false;
		Main.MoveOn = true;
		Main.setMove(0, false, true, 0);
		Main.status = "Going Straight";
	}
	
	public static boolean IsCentered(boolean Z) {
		if (Z == true) {
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
	
	//Checks if its gonna hit a block soon if so then it slows down a bit
	public static boolean WillHitSoon() {
		for (int i = 1; i < 4; i++) {
			if (Main.status == "Going Straight") {
				BlockPos Player = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
				BlockPos Check;
				
				if (Main.direction.equals(EnumFacing.NORTH)) {
					Check = Player.add(0, 0, -i);
				} else if (Main.direction.equals(EnumFacing.WEST)) {
					Check = Player.add(-i, 0, 0);
				} else if (Main.direction.equals(EnumFacing.EAST)) {
					Check = Player.add(i, 0, 0);
				} else {
					Check = Player.add(0, 0, i);
				}
				
				if (GetPath.IsSolid(Check)) {
					Renderer.DrawRedBox(Check);
					return true;
				}
			}
		}
		
		return false;
	}
}
