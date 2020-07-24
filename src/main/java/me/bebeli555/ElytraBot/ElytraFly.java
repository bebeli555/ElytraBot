package me.bebeli555.ElytraBot;

import me.bebeli555.ElytraBot.Settings.Diagonal;
import me.bebeli555.ElytraBot.Settings.Settings;

import net.minecraft.client.Minecraft;

public class ElytraFly {
	static Minecraft mc = Minecraft.getMinecraft();
	public static double FlyMinus = 0;
	public static boolean YCenter = false;
	public static double YMinus = 0;
	
	
	//This controls the elytraflight.
	public static void ElytraFlight() {
		if (Main.toggle == true) {
			if (Main.MoveOn == true) {
				Main.Flight((Settings.FlySpeed) - FlyMinus, Main.MoveRight, Main.MoveStraight, -(Settings.GlideSpeed / 10000f));
			}
		}
		
		if (Diagonal.toggle == true) {
			if (Diagonal.MoveOn == true) {
				Diagonal.Flight(Diagonal.MoveRight, Diagonal.MoveStraight, -(Settings.GlideSpeed / 10000f));
			}
		}
		
		if (me.bebeli555.ElytraBot.Overworld.Main.toggle == true) {
			if (me.bebeli555.ElytraBot.Overworld.Main.MoveOn == true) {
				me.bebeli555.ElytraBot.Overworld.Main.Flight((Settings.FlySpeed) - FlyMinus, me.bebeli555.ElytraBot.Overworld.Main.MoveRight, me.bebeli555.ElytraBot.Overworld.Main.MoveStraight, -(Settings.GlideSpeed / 10000f));
			}
		}
	}
	
	public static boolean IsElytrabotOn() {
		if (Main.toggle == true) {
			return true;
		}
		
		if (Diagonal.toggle == true) {
			return true;
		}
		
		if (me.bebeli555.ElytraBot.Overworld.Main.toggle == true) {
			return true;
		}
		
		return false;
	}
}
