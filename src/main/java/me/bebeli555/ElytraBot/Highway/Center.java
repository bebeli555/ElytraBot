package me.bebeli555.ElytraBot.Highway;

import me.bebeli555.ElytraBot.ElytraFly;
import me.bebeli555.ElytraBot.Settings.Settings;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumFacing;

public class Center {
	//This centers you on the middle of the block when going for a manuver
	//If we don't do this then theres a change it will start clipping into a block
	//It also centers the other way when going straight again in Manuver class
	
	static Minecraft mc = Minecraft.getMinecraft();

	//Centers the player and also activates manuver when done
	public static void ManuverCenter (boolean Right) {
		if (!IsManuverCentered(Main.z)) {
			if (ShouldCenterBackwards()) CenterBackwards();
			else CenterForward();
		}
	}

	public static boolean IsManuverCentered(boolean Z) {
		if (!Z) {
			double Coord = Math.round(mc.player.posZ * 10) / 10.0;
			String StringCoord = String.valueOf(Coord);

			return StringCoord.contains(".5");

		} else {
			double Coord = Math.round(mc.player.posX * 10) / 10.0;
			String StringCoord = String.valueOf(Coord);

			return StringCoord.contains(".5");
		}
	}
	
	//Check if we overlapped the center target and then start centering backwards
	public static boolean ShouldCenterBackwards() {
		double Coord = Math.round(mc.player.posX * 10) / 10.0;
		String CoordX = String.valueOf(Coord);
		
		double Coord2 = Math.round(mc.player.posZ * 10) / 10.0;
		String CoordZ = String.valueOf(Coord2);
		
		if (Main.direction.equals(EnumFacing.NORTH)) {
			if (Coord2 < 0)
				if (CoordZ.contains(".7") || CoordZ.contains(".6") || CoordZ.contains(".8"))
					return true;

			 else return CoordZ.contains(".3") || CoordZ.contains(".2") || CoordZ.contains(".4");
		} else if (Main.direction.equals(EnumFacing.WEST)) {
			if (Coord < 0)
				if (CoordX.contains(".7") || CoordX.contains(".6") || CoordX.contains(".8"))
					return true;
			else return CoordX.contains(".3") || CoordX.contains(".2") || CoordX.contains(".4");

		} else if (Main.direction.equals(EnumFacing.EAST)) {
			if (Coord < 0)
				if (CoordX.contains(".3") || CoordX.contains(".2") || CoordX.contains(".4"))
					return true;

			 else return CoordX.contains(".7") || CoordX.contains(".6") || CoordX.contains(".8");

		} else if (Main.direction.equals(EnumFacing.SOUTH)) {
			if (Coord2 < 0)
				if (CoordZ.contains(".3") || CoordZ.contains(".2") || CoordZ.contains(".4")) return true;
			 else return CoordZ.contains(".7") || CoordZ.contains(".6") || CoordZ.contains(".8");
		}
		return false;
	}
	
	public static boolean IsYCentered() {
		if (!mc.player.isElytraFlying()) {
			return false;
		}
		
		double Coord = Math.round(mc.player.posY * 10) / 10.0;
		String CoordY = String.valueOf(Coord);

		return CoordY.contains(".3") || CoordY.contains(".4");
	}
	
	public static boolean IsCentered(boolean Z) {
		if (Z) {
			double Coord = Math.round(mc.player.posZ * 10) / 10.0;
			String StringCoord = String.valueOf(Coord);

			return StringCoord.contains(".5");

		} else {
			double Coord = Math.round(mc.player.posX * 10) / 10.0;
			String StringCoord = String.valueOf(Coord);

			return StringCoord.contains(".5");
		}
	}
	
	//Start going forward slowly
	public static void CenterForward() {
		Main.MoveOn = true;
		ElytraFly.FlyMinus = Settings.getDouble("Speed") - 0.1;
	}
	
	//Start going backwards slowly
	public static void CenterBackwards() {
		Main.MoveOn = true;
		ElytraFly.FlyMinus = Settings.getDouble("Speed") + 0.1;
	}
}
