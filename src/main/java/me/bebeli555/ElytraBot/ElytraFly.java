package me.bebeli555.ElytraBot;

import me.bebeli555.ElytraBot.Highway.Main;
import me.bebeli555.ElytraBot.Settings.Diagonal;
import me.bebeli555.ElytraBot.Settings.Settings;

import net.minecraft.client.Minecraft;

public class ElytraFly {
	static Minecraft mc = Minecraft.getMinecraft();
	public static double FlyMinus = 0;
	public static boolean YCenter = false;
	public static double YMinus = 0;
	
	
	//This controls the ElytraFlight.
	public static void ElytraFlight() {
		double flySpeed = Settings.getDouble("Speed");
		double glideSpeed = Settings.getDouble("GlideSpeed");
		
		if (Main.toggle) {
			if (Main.MoveOn) {
				Main.Flight((flySpeed) - FlyMinus, Main.MoveRight, Main.MoveStraight, -(glideSpeed / 10000f));
			}
		}
		
		if (Diagonal.toggle) {
			if (Diagonal.MoveOn) {
				Diagonal.Flight(Diagonal.MoveRight, Diagonal.MoveStraight, -(glideSpeed / 10000f));
			}
		}
		
		if (me.bebeli555.ElytraBot.Overworld.Main.toggle) {
			if (me.bebeli555.ElytraBot.Overworld.Main.MoveOn) {
				me.bebeli555.ElytraBot.Overworld.Main.Flight((flySpeed) - FlyMinus, -(glideSpeed / 10000f), me.bebeli555.ElytraBot.Overworld.Main.MoveDirection);
			}
		}
	}
	
	public static boolean IsElytrabotOn() {

		if (Main.toggle) return true;
		
		if (Diagonal.toggle) return true;

		return me.bebeli555.ElytraBot.Overworld.Main.toggle;
	}
}
