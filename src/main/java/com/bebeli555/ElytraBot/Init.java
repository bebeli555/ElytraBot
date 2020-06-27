package com.bebeli555.ElytraBot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import com.bebeli555.ElytraBot.proxy.CommonProxy;

import com.bebeli555.ElytraBot.util.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.ITextComponent;

import com.bebeli555.ElytraBot.*;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.libraries.ModList;

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
		MinecraftForge.EVENT_BUS.register(this);
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
	}

	//Checks from Settings.txt file and sets them to values they were before
	//Aww Jeez
	public static void SetVar() {
		if (test2 == false) {
			test2 = true;
			try {
				String UseBaritone;
				String AutoRepair;
				String Diagonal;
				String AutoEat;
				File tmpDir = new File(System.getenv("APPDATA"), ".minecraft/ElytraBot/Settings.txt");
				boolean exists = tmpDir.exists();
				if (exists == true) {
					if (tmpDir.length() > 0) {
						UseBaritone = Files
								.readAllLines(Paths.get(System.getenv("APPDATA"), ".minecraft/ElytraBot/Settings.txt"))
								.get(0);
					} else {
						UseBaritone = "IdkMan";
					}

					if (tmpDir.length() > 22) {
						AutoRepair = Files
								.readAllLines(Paths.get(System.getenv("APPDATA"), ".minecraft/ElytraBot/Settings.txt"))
								.get(1);
					} else {
						AutoRepair = "IdkMan";
					}

					if (tmpDir.length() > 37) {
						Diagonal = Files
								.readAllLines(Paths.get(System.getenv("APPDATA"), ".minecraft/ElytraBot/Settings.txt"))
								.get(2);
					} else {
						Diagonal = "IdkMan";
					}
					
					if (tmpDir.length() > 53) {
						AutoEat = Files
								.readAllLines(Paths.get(System.getenv("APPDATA"), ".minecraft/ElytraBot/Settings.txt"))
								.get(3);
					} else {
						AutoEat = "IdkMan";
					}
				} else {
					UseBaritone = "OmgThisCodeIsSoBad";
					AutoRepair = "MySocksAreWet???";
					Diagonal = "AwJeezManImAMortyFromCDimensionSomething";
					AutoEat = "GodDamnThisIsMessyCodeIReallyGottaRedoThisSomedayWhichINeverWillBcsThatsSoMuchFuckingWork";
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

			} catch (IOException e) {
				System.out.println("IOException");
			} catch (IndexOutOfBoundsException e2) {
				System.out.println("IndexOutOfBoundsException");
			} catch (NullPointerException e3) {
				System.out.println("NullPointerException");
			}
		}
	}

}
