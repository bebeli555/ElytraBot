package me.bebeli555.ElytraBot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import me.bebeli555.ElytraBot.OpenTerrain.GetGoal;
import me.bebeli555.ElytraBot.PathFinding.Center;
import me.bebeli555.ElytraBot.PathFinding.GetPath;
import me.bebeli555.ElytraBot.Settings.AutoEat;
import me.bebeli555.ElytraBot.Settings.AutoRepair;
import me.bebeli555.ElytraBot.Settings.Diagonal;
import me.bebeli555.ElytraBot.Settings.KeyBind;
import me.bebeli555.ElytraBot.Settings.Settings;
import me.bebeli555.ElytraBot.Settings.StopAt;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = "eb", name = "ElytraBot", version = "2.0")
public class Init {
	static boolean test2 = false;
	static Minecraft mc = Minecraft.getMinecraft();
	static String Path2 = mc.mcDataDir.getPath();
	public static String Path = Path2 + "/ElytraBot/Settings.txt";
	public static java.nio.file.Path FinalPath = Paths.get(Path);
	// Initialization needed for the mod to work

	@EventHandler
	public void init(FMLInitializationEvent event) {
		init();
		GenerateFile();
	}

	public void init() {		
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
		MinecraftForge.EVENT_BUS.register(new me.bebeli555.ElytraBot.OpenTerrain.Main());
		MinecraftForge.EVENT_BUS.register(new Snake());
		Main.FlySpeed = Settings.FlySpeed;
		SetVar();
	}

	//Checks from Settings.txt file and sets them to values they were before
	//Aww Jeez
	public static void SetVar() {
		if (test2 == false) {
			if (Path2.equals(".")) {
				
			}
			
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
				String BaritoneBlocks = "";
				String DropItems = "";
				
				File tmpDir = new File(Path);
				boolean exists = tmpDir.exists();
				if (exists == true) {
						UseBaritone = Files.readAllLines(FinalPath).get(0);
						AutoRepair = Files.readAllLines(FinalPath).get(1);
						Diagonal = Files.readAllLines(FinalPath).get(2);
						AutoEat = Files.readAllLines(FinalPath).get(3);
						GUI = Files.readAllLines(FinalPath).get(4);
						START = Files.readAllLines(FinalPath).get(5);
						STOP = Files.readAllLines(FinalPath).get(6);
						GLIDE = Files.readAllLines(FinalPath).get(7);
						PacketFly = Files.readAllLines(FinalPath).get(8);
						SlowGlide = Files.readAllLines(FinalPath).get(9);
						BaritoneBlocks = Files.readAllLines(FinalPath).get(10);
						DropItems = Files.readAllLines(FinalPath).get(11);
						System.out.println("Elytrabot Path: " + Path);
				}

				if (UseBaritone.contains("true")) {
					me.bebeli555.ElytraBot.Settings.Settings.UseBaritone = true;
				} else {
					me.bebeli555.ElytraBot.Settings.Settings.UseBaritone = false;
				}

				if (AutoRepair.contains("true")) {
					me.bebeli555.ElytraBot.Settings.Settings.AutoRepair = true;
				} else {
					me.bebeli555.ElytraBot.Settings.Settings.AutoRepair = false;
				}

				if (Diagonal.contains("true")) {
					me.bebeli555.ElytraBot.Settings.Settings.Diagonal = true;
				} else {
					me.bebeli555.ElytraBot.Settings.Settings.Diagonal = false;
				}
				
				if (AutoEat.contains("true")) {
					me.bebeli555.ElytraBot.Settings.Settings.AutoEat = true;
				} else {
					me.bebeli555.ElytraBot.Settings.Settings.AutoEat = false;
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
				
				if (DropItems.contains("true")) {
					Settings.DropItems = true;
				} else {
					Settings.DropItems = false;
				}
				
				KeyBind.BindGUI = GUI;
				KeyBind.BindSTART = START;
				KeyBind.BindSTOP = STOP;
				
				double GlideSpeed = Double.parseDouble(GLIDE);
				Settings.GlideSpeed = GlideSpeed;
				
				int BlocksAmount = Integer.parseInt(BaritoneBlocks);
				Settings.UseBaritoneBlocks = BlocksAmount;
			} catch (Exception e) {
				System.out.println("Exception in Reading Settings file" + e.getCause());
			}
		}
	}
	
	//Generates Settings file
	public static void GenerateFile() {
		// Create Settings Folder
		File file = new File(mc.mcDataDir.getPath() + "/ElytraBot");
		if (file.mkdir()) {
			System.out.println("ElytraBot: Directory Created");
		} else {
			System.out.println("ElytraBot: Couldnt Create Directory");
		}

		// Create Settings file inside ElytaBot Directory
		File file2 = new File(Path);
		try {
			if (file2.createNewFile()) {
				System.out.println("ElytraBot: Settings Created");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
