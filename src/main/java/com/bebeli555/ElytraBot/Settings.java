package com.bebeli555.ElytraBot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;

public class Settings {

	static Minecraft mc = Minecraft.getMinecraft();
	static boolean UseBaritone = false;
	static boolean AutoLog = false;
	static boolean AutoRepair = false;
	static boolean Diagonal = false;
	static boolean AutoEat = false;
	static double FlySpeed = 1.8;

	// This stores the setting values in Settings.txt Which is located at
	// .minecraft/ElytraBot/Settings.txt


	//This is called in Commands class
	public static void Default() throws IOException {
		try {
			//Clears the file so it doesnt mess up
			File file = new File(System.getenv("APPDATA"), ".minecraft/ElytraBot/Settings.txt");
			if (file.exists() && file.isFile()) {
				file.delete();
			}
			file.createNewFile();
			File fout = new File(System.getenv("APPDATA"), ".minecraft/ElytraBot/Settings.txt");
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
				bw.close();
			}
		}catch (Exception e) {
			System.out.println("Exception in writing setting file. ELYTRABOT " + e.getCause());
		}
	}
}
