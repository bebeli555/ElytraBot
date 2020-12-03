package me.bebeli555.ElytraBot;

import java.io.File;
import java.io.IOException;
import me.bebeli555.ElytraBot.Games.Snake;
import me.bebeli555.ElytraBot.Games.Tetris;
import me.bebeli555.ElytraBot.Gui.Gui;
import me.bebeli555.ElytraBot.Gui.SetNodes;
import me.bebeli555.ElytraBot.Highway.Main;
import me.bebeli555.ElytraBot.Settings.AutoEat;
import me.bebeli555.ElytraBot.Settings.AutoRepair;
import me.bebeli555.ElytraBot.Settings.AutoSwitch;
import me.bebeli555.ElytraBot.Settings.Diagonal;
import me.bebeli555.ElytraBot.Settings.KeyBind;
import me.bebeli555.ElytraBot.Settings.Settings;
import me.bebeli555.ElytraBot.Settings.StopAt;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = "eb", name = "ElytraBot", version = "2.2")
public class Init{
	public static double version = 2.2;
	static Minecraft mc = Minecraft.getMinecraft();
	public static String Path = mc.mcDataDir.getPath() + "/ElytraBot/Settings.txt";
	// Initialization needed for the mod to work

	@EventHandler
	public void init(FMLInitializationEvent event) {
		//Register event listeners
		MinecraftForge.EVENT_BUS.register(new Init());
		MinecraftForge.EVENT_BUS.register(new Main());
		MinecraftForge.EVENT_BUS.register(new Commands());
		MinecraftForge.EVENT_BUS.register(new Renderer());
		MinecraftForge.EVENT_BUS.register(new Settings());
		MinecraftForge.EVENT_BUS.register(new AutoRepair());
		MinecraftForge.EVENT_BUS.register(new Diagonal());
		MinecraftForge.EVENT_BUS.register(new StopAt());
		MinecraftForge.EVENT_BUS.register(new Gui());
		MinecraftForge.EVENT_BUS.register(new AutoEat());
		MinecraftForge.EVENT_BUS.register(new KeyBind());
		MinecraftForge.EVENT_BUS.register(new me.bebeli555.ElytraBot.Overworld.Main());
		MinecraftForge.EVENT_BUS.register(new Snake());
		MinecraftForge.EVENT_BUS.register(new Tetris());
		MinecraftForge.EVENT_BUS.register(new AutoSwitch());
		
		Main.FlySpeed = Settings.getDouble("Speed");
		GenerateFile();
		SetNodes.SetAllNodes();
	}
	
	
	//Generates Settings file
	public static void GenerateFile() {
		// Create Settings Folder
		File file = new File(mc.mcDataDir.getPath() + "/ElytraBot");
		file.mkdir();

		// Create Settings file inside ElytaBot Directory
		File file2 = new File(Path);
		try {
			if (file2.createNewFile()) {
				System.out.println("ElytraBot: Settings File Created");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
