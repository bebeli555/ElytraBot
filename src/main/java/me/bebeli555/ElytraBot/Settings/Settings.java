package me.bebeli555.ElytraBot.Settings;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import me.bebeli555.ElytraBot.Init;
import me.bebeli555.ElytraBot.Main;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;

public class Settings {
	public static boolean UseBaritone = false;
	public static int UseBaritoneBlocks = 15;
	public static boolean AutoLog = false;
	public static boolean AutoRepair = false;
	public static boolean DropItems = true;
	public static boolean Diagonal = false;
	public static boolean AutoEat = false;
	public static double FlySpeed = 1.8;
	public static double GlideSpeed = 0.1;
	public static boolean PacketFly = true;
	public static boolean SlowGlide = false;
	
	public static int OpenTerrainX = -1;
	public static int OpenTerrainZ = -1;
	public static boolean OpenLogout = false;
	// This stores the setting values in Settings.txt Which is located at
	// .minecraft/ElytraBot/Settings.txt


	//This is called in Commands class
	public static void Default() throws IOException {
		try {
			//Clears the file so it doesnt mess up
			File file = new File(Init.Path);
			if (file.exists() && file.isFile()) {
				file.delete();
			}
			file.createNewFile();
			File fout = new File(Init.Path);
			FileOutputStream fos = new FileOutputStream(fout);
			
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

			// Sets value of your settings to Settings.txt
			if (fout.exists()) {
				bw.write(UseBaritone + " = UseBaritone");
				bw.newLine();
				bw.write(AutoRepair + " = AutoRepair");
				bw.newLine();
				bw.write(Diagonal + " = Diagonal");
				bw.newLine();
				bw.write(AutoEat + " = AutoEat");
				bw.newLine();
				bw.write(KeyBind.BindGUI);
				bw.newLine();
				bw.write(KeyBind.BindSTART);
				bw.newLine();
				bw.write(KeyBind.BindSTOP);
				bw.newLine();
				bw.write("" + Settings.GlideSpeed);
				bw.newLine();
				bw.write("" + Settings.PacketFly);
				bw.newLine();
				bw.write("" + Settings.SlowGlide);
				bw.newLine();
				bw.write("" + Settings.UseBaritoneBlocks);
				bw.newLine();
				bw.write("" + Settings.DropItems);
				bw.close();
			}
		}catch (Exception e) {
			System.out.println("Exception in writing setting file. ELYTRABOT " + e.getCause());
		}
	}
}
