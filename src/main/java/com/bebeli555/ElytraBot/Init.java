package com.bebeli555.ElytraBot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.bebeli555.ElytraBot.proxy.CommonProxy;
import com.bebeli555.ElytraBot.util.Reference;
import com.bebeli555.ElytraBot.PathFinding.Center;
import com.bebeli555.ElytraBot.PathFinding.GetPath;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class Init {
	static boolean test2 = false;
	// Initialization needed for the mod to work
	public static final Logger LOGGER = LogManager.getLogger(Reference.MOD_ID);
	@Instance
	public static Init instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;

	@EventHandler
	public void PreInit(FMLPreInitializationEvent event) {

	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		init();

		// Create Settings Folder
		File file = new File(System.getenv("APPDATA"), ".minecraft/ElytraBot");
		if (file.mkdir()) {
			System.out.println("ElytraBot: Directory Created");
		} else {
			System.out.println("ElytraBot: Couldnt Create Directory");
		}

		// Create Settings file inside ElytaBot Directory
		File file2 = new File(System.getenv("APPDATA"), ".minecraft/ElytraBot/Settings.txt");
		try {
			if (file2.createNewFile()) {
				System.out.println("ElytraBot: Settings Created");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@EventHandler
	public void Postinit(FMLPostInitializationEvent event) {

	}

	public void init() {		
		SetVar();
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
		MinecraftForge.EVENT_BUS.register(new GetPath());
		MinecraftForge.EVENT_BUS.register(new Center());
		MinecraftForge.EVENT_BUS.register(new ElytraFly());
	}

	//Checks from Settings.txt file and sets them to values they were before
	//Aww Jeez
	public static void SetVar() {
		if (test2 == false) {
			test2 = true;
			try {
				String UseBaritone = "";
				String AutoRepair = "";
				String Diagonal = "";
				String AutoEat = "";
				String GUI = "NONE";
				String START = "NONE";
				String STOP = "NONE";
				String GLIDE = "";
				String PacketFly = "";
				String SlowGlide = "";
				
				File tmpDir = new File(System.getenv("APPDATA"), ".minecraft/ElytraBot/Settings.txt");
				boolean exists = tmpDir.exists();
				if (exists == true) {
						UseBaritone = Files.readAllLines(Paths.get(System.getenv("APPDATA"), ".minecraft/ElytraBot/Settings.txt")).get(0);
						AutoRepair = Files.readAllLines(Paths.get(System.getenv("APPDATA"), ".minecraft/ElytraBot/Settings.txt")).get(1);
						Diagonal = Files.readAllLines(Paths.get(System.getenv("APPDATA"), ".minecraft/ElytraBot/Settings.txt")).get(2);
						AutoEat = Files.readAllLines(Paths.get(System.getenv("APPDATA"), ".minecraft/ElytraBot/Settings.txt")).get(3);
						
						GUI = Files.readAllLines(Paths.get(System.getenv("APPDATA"), ".minecraft/ElytraBot/Settings.txt")).get(4);
						START = Files.readAllLines(Paths.get(System.getenv("APPDATA"), ".minecraft/ElytraBot/Settings.txt")).get(5);
						STOP = Files.readAllLines(Paths.get(System.getenv("APPDATA"), ".minecraft/ElytraBot/Settings.txt")).get(6);
						
						GLIDE = Files.readAllLines(Paths.get(System.getenv("APPDATA"), ".minecraft/ElytraBot/Settings.txt")).get(7);
						
						PacketFly = Files.readAllLines(Paths.get(System.getenv("APPDATA"), ".minecraft/ElytraBot/Settings.txt")).get(8);
						SlowGlide = Files.readAllLines(Paths.get(System.getenv("APPDATA"), ".minecraft/ElytraBot/Settings.txt")).get(9);
				}

				if (UseBaritone.contains("true")) {
					com.bebeli555.ElytraBot.Settings.UseBaritone = true;
				} else {
					com.bebeli555.ElytraBot.Settings.UseBaritone = false;
				}

				if (AutoRepair.contains("true")) {
					com.bebeli555.ElytraBot.Settings.AutoRepair = true;
				} else {
					com.bebeli555.ElytraBot.Settings.AutoRepair = false;
				}

				if (Diagonal.contains("true")) {
					com.bebeli555.ElytraBot.Settings.Diagonal = true;
				} else {
					com.bebeli555.ElytraBot.Settings.Diagonal = false;
				}
				
				if (AutoEat.contains("true")) {
					com.bebeli555.ElytraBot.Settings.AutoEat = true;
				} else {
					com.bebeli555.ElytraBot.Settings.AutoEat = false;
				}
				
				if (PacketFly.contains("true")) {
					Settings.PacketFly = true;
				} else {
					Settings.PacketFly = false;
				}
				
				if (SlowGlide.contains("true")) {
					Settings.SlowGlide = true;
				} else {
					Settings.SlowGlide = false;
				}
				
				KeyBind.BindGUI = GUI;
				KeyBind.BindSTART = START;
				KeyBind.BindSTOP = STOP;
				
				double GlideSpeed = Double.parseDouble(GLIDE);
				Settings.GlideSpeed = GlideSpeed;

			} catch (Exception e) {
				System.out.println("Exception in Settings file" + e.getCause());
			}
		}
	}

}
