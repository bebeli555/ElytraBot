package me.bebeli555.ElytraBot;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import me.bebeli555.ElytraBot.PathFinding.GetPath;
import me.bebeli555.ElytraBot.Settings.AutoRepair;
import me.bebeli555.ElytraBot.Settings.KeyBind;
import me.bebeli555.ElytraBot.Settings.Settings;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class Gui extends GuiScreen {
	static Minecraft mc = Minecraft.getMinecraft();
	int delay = 0;
	String ElytraFly = ChatFormatting.BOLD + "ElytraFly:";
	String x2 = "Max X: ";
	String z2 = "Max Z: ";
	String log = "LogOut: ";
	static boolean StopX = false;
	static boolean StopZ = false;
	static boolean StopSpeed = false;
	static boolean StopGlideSpeed = false;
	static String numbersX = "";
	static int XReal;
	static String numbersZ = "";
	static String numbersSpeed = "";
	static double NumbersSpeed;
	static String numbersGlideSpeed = "";
	static double NumbersGlideSpeed;
	static int ZReal;
	int delay2 = 0;
	static int NumbersX;
	static int NumbersZ;
	int delay5 = 0;
	String Start = ChatFormatting.BOLD + "START";
	String Stop = ChatFormatting.BOLD + "STOP";
	static boolean on = false;
	static boolean GUI = false;
	static boolean START = false;
	static boolean STOP = false;
	static boolean StopBlock = false, StopOpenX = false, StopOpenZ = false;
	static String Blocks = "", OpenX = "", OpenZ = "";
	static int blocks, openX, openZ, OWY;

	static int YAutoRepair = 0;
	static int YDiagonal = 0;
	static int YAutoEat = 0;
	static int YOthers = 0;
	static boolean UseBaritoneExtended = false;
	static boolean DiagonalExtended = false;
	static boolean AutoRepairExtended = false;
	static boolean AutoEatExtended = false;

	static boolean Highway = false;
	static boolean OpenTerrain = false;
	static int ModeX = 0, ModeY = 0;

	@Override
	public void initGui() {
		super.initGui();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		
		// Some stuff
		OWY = -70;
		int HelpX = -75;
		int HelpY = 15;

		if (YOthers < 0) {
			YAutoRepair = 0;
			YDiagonal = 0;
			YAutoEat = 0;
			YOthers = 0;
		}

		if (Highway == true) {
			ModeX = 120;
			ModeY = 110;
		}

		int x = me.bebeli555.ElytraBot.Commands.MaxX;
		int z = me.bebeli555.ElytraBot.Commands.MaxZ;
		// Draw the GUI Stuff
		if (OpenTerrain == true) {
			YOthers = -110;
			ModeX = 0;
			ModeY = 110;
			
			//GoTo Mark
			drawRect(430, 170 + OWY, 540, 190 + OWY, 0x36393fff);
			mc.fontRenderer.drawStringWithShadow(ChatFormatting.BOLD + "Coordinates:", 452, 175 + OWY, 0xffaa00);
			
			//GoTo X
			drawRect(430, 190 + OWY, 540, 210 + OWY, 0x36393fff);
			if (Settings.OpenTerrainX != -1) {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.DARK_AQUA + "X: " + ChatFormatting.GREEN + Settings.OpenTerrainX, 465, 195 + OWY, 0xffff);
			} else {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.DARK_AQUA + "X: " + ChatFormatting.RED + "NONE", 465, 195 + OWY, 0xffff);
			}
			
			//GoTo Z
			drawRect(430, 210 + OWY, 540, 230 + OWY, 0x36393fff);
			if (Settings.OpenTerrainZ != -1) {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.DARK_AQUA + "Z: " + ChatFormatting.GREEN + Settings.OpenTerrainZ, 465, 215 + OWY, 0xffff);
			} else {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.DARK_AQUA + "Z: " + ChatFormatting.RED + "NONE", 465, 215 + OWY, 0xffff);
			}
			
			//GoTo Logout
			drawRect(430, 230 + OWY, 540, 250 + OWY, 0x36393fff);
			if (Settings.OpenLogout) {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.DARK_AQUA + "Logout: " + ChatFormatting.GREEN + "True", 453, 235 + OWY, 0xffff);
			} else {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.DARK_AQUA + "Logout: " + ChatFormatting.RED + "False", 453, 235 + OWY, 0xffff);
			}
			
			mc.fontRenderer.drawStringWithShadow(ChatFormatting.BLACK + "(" + ChatFormatting.RED + "!" + ChatFormatting.BLACK + ") " + ChatFormatting.GOLD + "How to use" + ChatFormatting.BLACK + " (" + ChatFormatting.RED + "!" + ChatFormatting.BLACK + ")", 250, 70, 0xffff);
			mc.fontRenderer.drawStringWithShadow("Set the GoTo Coordinates to your destination", 180, 80, 0xffff);
			mc.fontRenderer.drawStringWithShadow("When you want to activate it you need", 200, 90, 0xffff);
			mc.fontRenderer.drawStringWithShadow("to be ALREADY FLYING with elytra.", 207, 100, 0xffff);
			mc.fontRenderer.drawStringWithShadow("Then when started it will go to the destination", 183, 110, 0xffff);
			mc.fontRenderer.drawStringWithShadow("So get on elytra flying then start it.", 208, 120, 0xffff);
			mc.fontRenderer.drawStringWithShadow("You should go as high as you can", 210, 130, 0xffff);
			mc.fontRenderer.drawStringWithShadow("So there will be less obstacles on ur way", 194, 140, 0xffff);
		}

		mc.fontRenderer.drawStringWithShadow(ChatFormatting.RED + "ElytraHighwayBot " + ChatFormatting.GREEN + "V2.0", 1, 1, 0xb8352c);
		mc.fontRenderer.drawStringWithShadow(ChatFormatting.RED + "Made By: " + ChatFormatting.GREEN + "bebeli555", 1, 10, 0xb8352c);
		mc.fontRenderer.drawStringWithShadow(ChatFormatting.RED + "Discord: " + ChatFormatting.GREEN + "https://discord.gg/QTPWRrV", 1, 20, 0xb8352c);

		String Mode = ChatFormatting.BOLD + "Mode:";
		// Mode
		drawRect(430 + ModeX, 150 + ModeY, 540 + ModeX, 170 + ModeY, 0x36393fff);
		mc.fontRenderer.drawStringWithShadow(ChatFormatting.GOLD + Mode, 468 + ModeX, 155 + ModeY, 0xffff);

		// Highway
		drawRect(430 + ModeX, 170 + ModeY, 540 + ModeX, 190 + ModeY, 0x36393fff);
		if (Highway == true) {
			mc.fontRenderer.drawStringWithShadow(ChatFormatting.GREEN + "Highway", 465 + ModeX, 175 + ModeY, 0xffff);
		} else {
			mc.fontRenderer.drawStringWithShadow(ChatFormatting.RED + "Highway", 465 + ModeX, 175 + ModeY, 0xffff);
		}

		// Open terrain
		drawRect(430 + ModeX, 190 + ModeY, 540 + ModeX, 210 + ModeY, 0x36393fff);
		if (OpenTerrain == true) {
			mc.fontRenderer.drawStringWithShadow(ChatFormatting.GREEN + "Overworld", 458 + ModeX, 195 + ModeY, 0xffff);
		} else {
			mc.fontRenderer.drawStringWithShadow(ChatFormatting.RED + "Overworld", 458 + ModeX, 195 + ModeY, 0xffff);
		}

		if (Highway == false && OpenTerrain == false) {
			SoundGUI.drawSoundGUI();
			ModeX = 0;
			ModeY = 0;
			mc.fontRenderer.drawStringWithShadow(ChatFormatting.GOLD + "-->", 405, 185, 0xffff);
			mc.fontRenderer.drawStringWithShadow("Please select a Mode!", 290, 185, 0xffff);
			// Info about the Modes
			int InfoX = 25;
			String HighwayMode = ChatFormatting.BOLD + "Highway MODE";
			String OpenTerrainMode = ChatFormatting.BOLD + "Overworld MODE";
			mc.fontRenderer.drawStringWithShadow(ChatFormatting.LIGHT_PURPLE + HighwayMode, 640 + InfoX, 120, 0xffff);
			mc.fontRenderer.drawStringWithShadow("A Mode designed for the Highways", 598 + InfoX, 130, 0xffff);
			mc.fontRenderer.drawStringWithShadow("Just look at the direction you want to go", 585 + InfoX, 140, 0xffff);
			mc.fontRenderer.drawStringWithShadow("And start it. Also has alot of Settings!", 588 + InfoX, 150, 0xffff);

			mc.fontRenderer.drawStringWithShadow(ChatFormatting.LIGHT_PURPLE + OpenTerrainMode, 628 + InfoX, 170, 0xffff);
			mc.fontRenderer.drawStringWithShadow("A Mode designed for Overworld travel", 583 + InfoX, 180, 0xffff);
			mc.fontRenderer.drawStringWithShadow("It will take you to set coordinates.", 587 + InfoX, 190, 0xffff);
		}

		if (Highway == true || OpenTerrain == true) {
			drawRect(430, 70, 540, 90, 0x36393fff);
			if (me.bebeli555.ElytraBot.Main.toggle == true | me.bebeli555.ElytraBot.Settings.Diagonal.toggle == true | me.bebeli555.ElytraBot.Main.baritonetoggle == true | me.bebeli555.ElytraBot.Settings.Diagonal.baritonetoggle == true | me.bebeli555.ElytraBot.Overworld.Main.toggle == true) {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.RED + Stop, 468, 75, 0xffff);
				on = true;
			} else {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.GREEN + Start, 468, 75, 0xffff);
				on = false;
			}
			
			// KeyBinds mark
			drawRect(550, 100, 660, 120, 0x36393fff);
			mc.fontRenderer.drawStringWithShadow(ChatFormatting.BOLD + "KeyBinds:", 578, 105, 0xffaa00);

			// KeyBind GUI
			drawRect(550, 120, 660, 140, 0x36393fff);
			if (KeyBind.BindGUI.contains("NONE")) {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.DARK_AQUA + "GUI: " + ChatFormatting.RED + KeyBind.BindGUI, 582, 125, 0xffaa00);
			} else {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.DARK_AQUA + "GUI: " + ChatFormatting.GREEN + KeyBind.BindGUI, 582, 125, 0xffaa00);
			}

			// KeyBind START
			drawRect(550, 140, 660, 160, 0x36393fff);
			if (KeyBind.BindSTART.contains("NONE")) {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.DARK_AQUA + "Start: " + ChatFormatting.RED + KeyBind.BindSTART, 578, 145, 0xffaa00);
			} else {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.DARK_AQUA + "Start: " + ChatFormatting.GREEN + KeyBind.BindSTART, 578, 145, 0xffaa00);
			}

			// KeyBind STOP
			drawRect(550, 160, 660, 180, 0x36393fff);
			if (KeyBind.BindSTOP.contains("NONE")) {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.DARK_AQUA + "Stop: " + ChatFormatting.RED + KeyBind.BindSTOP, 580, 165, 0xffaa00);
			} else {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.DARK_AQUA + "Stop: " + ChatFormatting.GREEN + KeyBind.BindSTOP, 580, 165, 0xffaa00);
			}
			
			if (GUI == true) {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.GOLD + "Click key in your keyboard to set this!", 665, 125, 0xffff);
			}

			if (START == true) {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.GOLD + "Click key in your keyboard to set this!", 665, 145, 0xffff);
			}

			if (STOP == true) {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.GOLD + "Click key in your keyboard to set this!", 665, 165, 0xffff);
			}
			
			if (StopOpenX == true) {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.GOLD + "Click key in your keyboard to set this!", 547, 195 + OWY, 0xffff);
			}
			
			if (StopOpenZ == true) {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.GOLD + "Click key in your keyboard to set this!", 547, 215 + OWY, 0xffff);
			}
			
			if (StopSpeed == true) {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.GOLD + "Type numbers in your keyboard to set this!", 550, 325 + YOthers, 0xffff);
			}

			if (StopGlideSpeed == true) {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.GOLD + "Type numbers in your keyboard to set this! (Divided by 1000)", 550, 345 + YOthers, 0xffff);
			}
			
			// ElytraFly Mark
			drawRect(430, 300 + YOthers, 540, 320 + YOthers, 0x36393fff);
			mc.fontRenderer.drawStringWithShadow(ChatFormatting.GOLD + ElytraFly, 455, 305 + YOthers, 0xffff);

			// Speed
			drawRect(430, 320 + YOthers, 540, 340 + YOthers, 0x36393fff);
			mc.fontRenderer.drawStringWithShadow(ChatFormatting.DARK_AQUA + "Speed: " + ChatFormatting.GREEN + Settings.FlySpeed, 455, 325 + YOthers, 0xffff);

			// GlideSpeed
			drawRect(430, 340 + YOthers, 540, 360 + YOthers, 0x36393fff);
			mc.fontRenderer.drawStringWithShadow(ChatFormatting.DARK_AQUA + "GlideSpeed: " + ChatFormatting.GREEN + Settings.GlideSpeed, 445, 345 + YOthers, 0xffff);
		}

		if (Highway == true) {
			// Settings mark
			drawRect(430, 100, 540, 120, 0x36393fff);
			mc.fontRenderer.drawStringWithShadow(ChatFormatting.BOLD + "Settings:", 459, 105, 0xffaa00);

			// UseBaritone
			drawRect(430, 120, 540, 140, 0x36393fff);
			if (me.bebeli555.ElytraBot.Settings.Settings.UseBaritone == false) {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.RED + "UseBaritone", 453, 125, 0xffff);
			} else {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.GREEN + "UseBaritone", 453, 125, 0xffff);
			}

			if (UseBaritoneExtended) {
				drawRect(430, 140, 540, 160, 0x32CD32ff);
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.DARK_AQUA + "Blocks: " + ChatFormatting.GREEN + Settings.UseBaritoneBlocks, 460, 145, 0xffff);
			}

			// AutoRepair
			drawRect(430, 140 + YAutoRepair, 540, 160 + YAutoRepair, 0x36393fff);
			if (me.bebeli555.ElytraBot.Settings.Settings.AutoRepair == false) {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.RED + "AutoRepair", 458, 145 + YAutoRepair, 0xffff);
			} else {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.GREEN + "AutoRepair", 458, 145 + YAutoRepair, 0xffff);
			}

			if (AutoRepairExtended) {
				drawRect(430, 160 + YAutoRepair, 540, 180 + YAutoRepair, 0x32CD32ff);
				if (Settings.DropItems == true) {
					mc.fontRenderer.drawStringWithShadow(ChatFormatting.GREEN + "DropItems", 458, 165 + YAutoRepair, 0xffff);
				} else {
					mc.fontRenderer.drawStringWithShadow(ChatFormatting.RED + "DropItems", 458, 165 + YAutoRepair, 0xffff);
				}
			}

			// Diagonal
			drawRect(430, 160 + YDiagonal, 540, 180 + YDiagonal, 0x36393fff);
			if (me.bebeli555.ElytraBot.Settings.Settings.Diagonal == false) {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.RED + "Diagonal", 463, 165 + YDiagonal, 0xffff);
			} else {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.GREEN + "Diagonal", 463, 165 + YDiagonal, 0xffff);
			}

			if (DiagonalExtended) {
				drawRect(430, 180 + YDiagonal, 540, 200 + YDiagonal, 0x32CD32ff);
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.RED + "-", 480, 185 + YDiagonal, 0xffff);
			}

			// AutoEat
			drawRect(430, 180 + YAutoEat, 540, 200 + YAutoEat, 0x36393fff);
			if (me.bebeli555.ElytraBot.Settings.Settings.AutoEat == false) {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.RED + "AutoEat", 463, 185 + YAutoEat, 0xffff);
			} else {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.GREEN + "AutoEat", 463, 185 + YAutoEat, 0xffff);
			}

			if (AutoEatExtended) {
				drawRect(430, 200 + YAutoEat, 540, 220 + YAutoEat, 0x32CD32ff);
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.RED + "-", 480, 205 + YAutoEat, 0xffff);
			}

			// Stopat mark
			drawRect(430, 210 + YOthers, 540, 230 + YOthers, 0x36393fff);
			mc.fontRenderer.drawStringWithShadow(ChatFormatting.BOLD + "Stopat Setting:", 445, 215 + YOthers, 0xffaa00);

			// Stopat X
			drawRect(430, 230 + YOthers, 540, 250 + YOthers, 0x36393fff);
			if (x == -1) {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.DARK_AQUA + x2 + ChatFormatting.RED + "NONE", 455, 235 + YOthers, 0xffff);
			} else {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.DARK_AQUA + x2 + ChatFormatting.GREEN + x, 455, 235 + YOthers, 0xffff);
			}

			// Stopat Z
			drawRect(430, 250 + YOthers, 540, 270 + YOthers, 0x36393fff);
			if (z == -1) {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.DARK_AQUA + z2 + ChatFormatting.RED + "NONE", 455, 255 + YOthers, 0xffff);
			} else {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.DARK_AQUA + z2 + ChatFormatting.GREEN + z, 455, 255 + YOthers, 0xffff);
			}

			// Stopat Log
			drawRect(430, 270 + YOthers, 540, 290 + YOthers, 0x36393fff);
			if (me.bebeli555.ElytraBot.Commands.Log == true) {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.DARK_AQUA + log + ChatFormatting.GREEN + me.bebeli555.ElytraBot.Commands.Log, 450, 275 + YOthers, 0xffff);
			} else {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.DARK_AQUA + log + ChatFormatting.RED + me.bebeli555.ElytraBot.Commands.Log, 450, 275 + YOthers, 0xffff);
			}

			String PacketFly;
			String SlowGlide;

			if (Settings.PacketFly == true) {
				PacketFly = ChatFormatting.GREEN + "PacketFly";
			} else {
				PacketFly = ChatFormatting.RED + "PacketFly";
			}

			if (Settings.SlowGlide == true) {
				SlowGlide = ChatFormatting.GREEN + "SlowGlide";
			} else {
				SlowGlide = ChatFormatting.RED + "SlowGlide";
			}

			// TakeOff Mark
			drawRect(550, 190, 660, 210, 0x36393fff);
			mc.fontRenderer.drawStringWithShadow(ChatFormatting.BOLD + "TakeOff:", 580, 195, 0xffaa00);

			// TakeOff Packetfly
			drawRect(550, 210, 660, 230, 0x36393fff);
			mc.fontRenderer.drawStringWithShadow(PacketFly, 580, 215, 0xffaa00);

			// TakeOff SlowGlide
			drawRect(550, 230, 660, 250, 0x36393fff);
			mc.fontRenderer.drawStringWithShadow(SlowGlide, 580, 235, 0xffaa00);

			// Set guide for yall dummies ;)
			if (StopX == true) {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.GOLD + "Type numbers in your keyboard to set this!", 550, 235 + YOthers, 0xffff);
			}

			if (StopZ == true) {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.GOLD + "Type numbers in your keyboard to set this!", 550, 255 + YOthers, 0xffff);
			}

			if (StopBlock == true) {
				if (UseBaritoneExtended) {
					mc.fontRenderer.drawStringWithShadow(ChatFormatting.GOLD + "Type numbers in your keyboard to set this!", 550, 125 + YOthers, 0xffff);
				}
			}

			// Draw Settings help page to GUI
			String settinginfo = ChatFormatting.BOLD + "Settings INFO";

			mc.fontRenderer.drawStringWithShadow(ChatFormatting.BLACK + "(" + ChatFormatting.RED + "!" + ChatFormatting.BLACK + ") " + ChatFormatting.GOLD + settinginfo + ChatFormatting.BLACK + " (" + ChatFormatting.RED + "!" + ChatFormatting.BLACK + ")", 184 + HelpX, 50 + HelpY, 0xffff);
			// UseBaritone
			mc.fontRenderer.drawStringWithShadow(ChatFormatting.LIGHT_PURPLE + "UseBaritone", 210 + HelpX, 70 + HelpY, 0xffff);
			mc.fontRenderer.drawStringWithShadow("When Ever ElytraBot gets stuck", 165 + HelpX, 80 + HelpY, 0xffff);
			mc.fontRenderer.drawStringWithShadow("It will use baritone to walk forward", 155 + HelpX, 90 + HelpY, 0xffff);
			mc.fontRenderer.drawStringWithShadow("So it can start flying again.", 175 + HelpX, 100 + HelpY, 0xffff);

			// AutoRepair
			mc.fontRenderer.drawStringWithShadow(ChatFormatting.LIGHT_PURPLE + "AutoRepair", 215 + HelpX, 130 + HelpY, 0xffff);
			mc.fontRenderer.drawStringWithShadow("Automatically repairs elytra using", 160 + HelpX, 140 + HelpY, 0xffff);
			mc.fontRenderer.drawStringWithShadow("XP-Bottles in ur inventory if elytra", 155 + HelpX, 150 + HelpY, 0xffff);
			mc.fontRenderer.drawStringWithShadow("Durability gets low", 195 + HelpX, 160 + HelpY, 0xffff);
			mc.fontRenderer.drawStringWithShadow("Also it takes off ur armor if inv has space", 135 + HelpX, 170 + HelpY, 0xffff);

			// Diagonal
			mc.fontRenderer.drawStringWithShadow(ChatFormatting.LIGHT_PURPLE + "Diagonal", 219 + HelpX, 200 + HelpY, 0xffff);
			mc.fontRenderer.drawStringWithShadow("Turns ElytraBot to Diagonal Mode", 162 + HelpX, 210 + HelpY, 0xffff);
			mc.fontRenderer.drawStringWithShadow("So it will travel on the diagonal", 168 + HelpX, 220 + HelpY, 0xffff);
			mc.fontRenderer.drawStringWithShadow("Highway ur looking at when u start it", 157 + HelpX, 230 + HelpY, 0xffff);

			// AutoEat
			mc.fontRenderer.drawStringWithShadow(ChatFormatting.LIGHT_PURPLE + "AutoEat", 221 + HelpX, 260 + HelpY, 0xffff);
			mc.fontRenderer.drawStringWithShadow("Auto Eats When ur low hunger / Health", 148 + HelpX, 270 + HelpY, 0xffff);
			mc.fontRenderer.drawStringWithShadow("Doesn't eat chorus and prefers gaps", 150 + HelpX, 280 + HelpY, 0xffff);
			mc.fontRenderer.drawStringWithShadow("Food has to be in ur HOTBAR", 175 + HelpX, 290 + HelpY, 0xffff);

			// StopAt
			mc.fontRenderer.drawStringWithShadow(ChatFormatting.LIGHT_PURPLE + "StopAt", 223 + HelpX, 320 + HelpY, 0xffff);
			mc.fontRenderer.drawStringWithShadow("When 1 Of the set coords is reached", 150 + HelpX, 330 + HelpY, 0xffff);
			mc.fontRenderer.drawStringWithShadow("ElytraBot Will Stop and LogOut if", 165 + HelpX, 340 + HelpY, 0xffff);
			mc.fontRenderer.drawStringWithShadow("LogOut Setting is set to true", 170 + HelpX, 350 + HelpY, 0xffff);
			mc.fontRenderer.drawStringWithShadow(ChatFormatting.RED + "Note: ", 148 + HelpX, 360 + HelpY, 0xffff);
			mc.fontRenderer.drawStringWithShadow("You have to put NETHER Coords!", 175 + HelpX, 360 + HelpY, 0xffff);

			// PacketFly
			mc.fontRenderer.drawStringWithShadow(ChatFormatting.LIGHT_PURPLE + "PacketFly", 215 + HelpX, 390 + HelpY, 0xffff);
			mc.fontRenderer.drawStringWithShadow("Uses Packetfly after jumping to", 160 + HelpX, 400 + HelpY, 0xffff);
			mc.fontRenderer.drawStringWithShadow("Give more time to activate elytra", 158 + HelpX, 410 + HelpY, 0xffff);
			mc.fontRenderer.drawStringWithShadow(ChatFormatting.GREEN + "Best for 2b2t!", 205 + HelpX, 420 + HelpY, 0xffff);

			// SlowGlide
			mc.fontRenderer.drawStringWithShadow(ChatFormatting.LIGHT_PURPLE + "SlowGlide", 215 + HelpX, 450 + HelpY, 0xffff);
			mc.fontRenderer.drawStringWithShadow("Glides down slow after jumping to", 160 + HelpX, 460 + HelpY, 0xffff);
			mc.fontRenderer.drawStringWithShadow("Give more time to activate elytra", 160 + HelpX, 470 + HelpY, 0xffff);

			mc.fontRenderer.drawStringWithShadow(ChatFormatting.GOLD + "-->", 400, 145, 0xffff);
			mc.fontRenderer.drawStringWithShadow("Right click the", 310, 135, 0xffff);
			mc.fontRenderer.drawStringWithShadow("Settings for more", 300, 145, 0xffff);
			mc.fontRenderer.drawStringWithShadow("Settings for the setting", 290, 155, 0xffff);
		}
	}

	// Useless thingy get the fuck out of here man you have no purpose in this place
	// (I mean the code below this not you <3)
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	// Activate the GUI.
	@SubscribeEvent
	public void onUpdate(ClientTickEvent e) {
		if (me.bebeli555.ElytraBot.Commands.GuiON == true) {
			delay++;
			if (delay > 1) {
				me.bebeli555.ElytraBot.Commands.GuiON = false;
				delay = 0;
				Minecraft.getMinecraft().displayGuiScreen(new Gui());
			}
		}

		// Stop listening to keys when gui is closed
		if (mc.currentScreen == null) {
			StopX = false;
			StopZ = false;
			StopSpeed = false;
			StopGlideSpeed = false;
			GUI = false;
			START = false;
			STOP = false;
			StopBlock = false;
			StopOpenX = false;
			StopOpenZ = false;
		}
	}

	// Mouse click event stuff it activates the modules and stuff when u click the
	// thingys on the screen!
	@Override
	protected void mouseClicked(int i, int j, int k) {
		try {
			super.mouseClicked(i, j, k);
			StopX = false;
			StopZ = false;
			StopSpeed = false;
			StopGlideSpeed = false;
			GUI = false;
			START = false;
			STOP = false;
			StopBlock = false;
			StopOpenX = false;
			StopOpenZ = false;
			
			if (Highway == false && OpenTerrain == false) {
				SoundGUI.ClickEvent(i, j, k);
			}

			if (Highway == true || OpenTerrain == true) {
				// Start and Stop 430, 70, 540, 90
				if (430 < i && 540 > i && 70 < j && 90 > j) {
					if (on == true) {
						TurnOff();
					} else {
						TurnOn();
					}
				}
				
				// Keybind GUI 550, 120, 660, 140
				if (550 < i && 660 > i && 120 < j && 140 > j) {
					GUI = true;
				}

				// Keybind Start 550, 140, 660, 160
				if (550 < i && 660 > i && 140 < j && 160 > j) {
					START = true;
				}

				// Keybind Stop 550, 160, 660, 180
				if (550 < i && 660 > i && 160 < j && 180 > j) {
					STOP = true;
				}
				
				// ElytraFlySpeed 430, 320, 540, 340
				if (430 < i && 540 > i && 320 + YOthers < j && 340 + YOthers > j) {
					StopSpeed = true;
				}

				// ElytraFlyGlideSpeed 430, 340, 540, 360
				if (430 < i && 540 > i && 340 + YOthers < j && 360 + YOthers > j) {
					StopGlideSpeed = true;
				}
			}
			
			//Open terrain stuff
			if (OpenTerrain == true) {
				//Logout 430, 230, 540, 250
				if (430 < i && 540 > i && 230 + OWY < j && 250 + OWY > j) {
					if (Settings.OpenLogout) {
						Settings.OpenLogout = false;
					} else {
						Settings.OpenLogout = true;
					}
				}
				
				//X 430, 190, 540, 210
				if (430 < i && 540 > i && 190 + OWY < j && 210 + OWY > j) {
					StopOpenX = true;
				}
				
				//Z 430, 210, 540, 230
				if (430< i && 540 > i && 210 + OWY < j && 230 + OWY > j) {
					StopOpenZ = true;
				}
			}
			
			if (Highway == true) {				
				// UseBaritone 430, 120, 540, 140
				if (430 < i && 540 > i && 120 < j && 140 > j) {
					if (Mouse.getEventButton() != 1) {
						if (Commands.noBaritone == false) {
							if (me.bebeli555.ElytraBot.Settings.Settings.UseBaritone == false) {
								me.bebeli555.ElytraBot.Settings.Settings.UseBaritone = true;
							} else {
								me.bebeli555.ElytraBot.Settings.Settings.UseBaritone = false;
							}
						} else {
							Settings.UseBaritone = false;
							mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.RED + "This setting requires baritone. You dont have it"));
						}
						SetStuff();
					} else {
						if (UseBaritoneExtended == true) {
							UseBaritoneExtended = false;
							YAutoRepair = YAutoRepair - 20;
							YDiagonal = YDiagonal - 20;
							YAutoEat = YAutoEat - 20;
							YOthers = YOthers - 20;
						} else {
							UseBaritoneExtended = true;
							YAutoRepair = YAutoRepair + 20;
							YDiagonal = YDiagonal + 20;
							YAutoEat = YAutoEat + 20;
							YOthers = YOthers + 20;
						}
					}
				}
				// UseBaritone Blocks 430, 140, 540, 160
				if (430 < i && 540 > i && 140 < j && 160 > j) {
					StopBlock = true;
				}

				// AutoRepair 430, 140, 540, 160
				if (430 < i && 540 > i && 140 + YAutoRepair < j && 160 + YAutoRepair > j) {
					if (Mouse.getEventButton() != 1) {
						if (me.bebeli555.ElytraBot.Settings.Settings.AutoRepair == false) {
							me.bebeli555.ElytraBot.Settings.Settings.AutoRepair = true;
						} else {
							me.bebeli555.ElytraBot.Settings.Settings.AutoRepair = false;
						}
						SetStuff();
					} else {
						if (AutoRepairExtended == true) {
							AutoRepairExtended = false;
							YDiagonal = YDiagonal - 20;
							YAutoEat = YAutoEat - 20;
							YOthers = YOthers - 20;
						} else {
							AutoRepairExtended = true;
							YDiagonal = YDiagonal + 20;
							YAutoEat = YAutoEat + 20;
							YOthers = YOthers + 20;
						}
					}
				}
				// Autorepair DropItems 430, 160 + YAutoRepair, 540, 180 + YAutoRepair
				if (430 < i && 540 > i && 160 + YAutoRepair < j && 180 + YAutoRepair > j) {
					if (AutoRepairExtended) {
						if (Settings.DropItems == true) {
							Settings.DropItems = false;
						} else {
							Settings.DropItems = true;
						}
						SetStuff();
					}
				}

				// Diagonal 430, 160, 540, 180
				if (430 < i && 540 > i && 160 + YDiagonal < j && 180 + YDiagonal > j) {
					if (Mouse.getEventButton() != 1) {
						if (me.bebeli555.ElytraBot.Settings.Settings.Diagonal == false) {
							me.bebeli555.ElytraBot.Settings.Settings.Diagonal = true;
						} else {
							me.bebeli555.ElytraBot.Settings.Settings.Diagonal = false;
						}
						SetStuff();
					} else {
						if (DiagonalExtended == true) {
							DiagonalExtended = false;
							YAutoEat = YAutoEat - 20;
							YOthers = YOthers - 20;
						} else {
							DiagonalExtended = true;
							YAutoEat = YAutoEat + 20;
							YOthers = YOthers + 20;
						}
					}
				}

				// AutoEat 430, 180, 540, 200
				if (430 < i && 540 > i && 180 + YAutoEat < j && 200 + YAutoEat > j) {
					if (Mouse.getEventButton() != 1) {
						if (me.bebeli555.ElytraBot.Settings.Settings.AutoEat == false) {
							me.bebeli555.ElytraBot.Settings.Settings.AutoEat = true;
						} else {
							me.bebeli555.ElytraBot.Settings.Settings.AutoEat = false;
						}
						SetStuff();
					} else {
						if (AutoEatExtended == true) {
							AutoEatExtended = false;
							YOthers = YOthers - 20;
						} else {
							AutoEatExtended = true;
							YOthers = YOthers + 20;
						}
					}
				}

				// Logout 430, 250, 540, 270
				if (430 < i && 540 > i && 270 + YOthers < j && 290 + YOthers > j) {
					if (me.bebeli555.ElytraBot.Commands.Log == false) {
						me.bebeli555.ElytraBot.Commands.Log = true;
					} else {
						me.bebeli555.ElytraBot.Commands.Log = false;
					}
				}

				// Stopat X 430, 210, 540, 230
				if (430 < i && 540 > i && 230 + YOthers < j && 250 + YOthers > j) {
					StopX = true;
				}

				// Stopat Z 430, 230, 540, 250
				if (430 < i && 540 > i && 250 + YOthers < j && 270 + YOthers > j) {
					StopZ = true;
				}

				// Discord link 1, 1, 190, 30
				if (1 < i && 190 > i && 1 < j && 30 > j) {
					GuiConfirmOpenLink LinkMan = new GuiConfirmOpenLink(this, "https://discord.gg/QTPWRrV", 0, true);
					LinkMan.disableSecurityWarning();
					mc.displayGuiScreen(LinkMan);
				}

				// Takeoff Packetfly 550, 210, 660, 230
				if (550 < i && 660 > i && 210 < j && 230 > j) {
					if (Settings.PacketFly == true) {
						Settings.PacketFly = false;
					} else {
						Settings.PacketFly = true;
						if (Settings.SlowGlide == true) {
							Settings.SlowGlide = false;
						}
					}
					SetStuff();
				}

				// Takeoff SlowGlide 550, 230, 660, 250
				if (550 < i && 660 > i && 230 < j && 250 > j) {
					if (Settings.SlowGlide == true) {
						Settings.SlowGlide = false;
					} else {
						Settings.SlowGlide = true;
						if (Settings.PacketFly == true) {
							Settings.PacketFly = false;
						}
					}
					SetStuff();
				}
			}

			// HighwayMode 430 + ModeX, 170 + ModeY, 540 + ModeX, 190 + ModeY
			if (430 + ModeX < i && 540 + ModeX > i && 170 + ModeY < j && 190 + ModeY > j) {
				if (Highway == true) {
					Highway = false;
				} else {
					Highway = true;
				}
				OpenTerrain = false;
			}

			// OpenTerrain 430 + ModeX, 190 + ModeY, 540 + ModeX, 210 + ModeY
			if (430 + ModeX < i && 540 + ModeX > i && 190 + ModeY < j && 210 + ModeY > j) {
				if (OpenTerrain == true) {
					OpenTerrain = false;
				} else {
					OpenTerrain = true;
				}
				Highway = false;
			}

		} catch (IOException e) {
			System.out.println("IOException In ElytraBot GUI");
		}

	}

	public static void SetStuff() {
		try {
			Main.FlySpeed = Settings.FlySpeed;
			me.bebeli555.ElytraBot.Settings.Settings.Default();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	// Keyboard type event
	@SubscribeEvent
	public void onKeyPress(GuiScreenEvent.KeyboardInputEvent.Post e) {
		try {
			if (!Keyboard.isKeyDown(28)) {
				if (Keyboard.isKeyDown(14)) {
					ResetTyping();
				} else if (Keyboard.isKeyDown(Keyboard.getEventKey())) {
					if (Highway == false && OpenTerrain == false) {
						if (SoundGUI.StopPlaySpeed) {
							SoundGUI.playSpeed = SoundGUI.playSpeed + Keyboard.getEventCharacter();
							if (!SoundGUI.playSpeed.equals("-")) {
								SoundGUI.PlaySpeed = Float.parseFloat(SoundGUI.playSpeed);
							}
						}
					}
					
					if (Highway == true) {
						if (StopX == true) {
							numbersX = numbersX + Keyboard.getEventCharacter();
							if (!numbersX.equals("-")) {
								NumbersX = Integer.parseInt(numbersX);
								Commands.MaxX = NumbersX;
							}
							SetStuff();
						} else if (StopZ == true) {
							numbersZ = numbersZ + Keyboard.getEventCharacter();
							if (!numbersZ.equals("-")) {
								NumbersZ = Integer.parseInt(numbersZ);
								Commands.MaxZ = NumbersZ;
							}
							SetStuff();
						} else if (StopBlock == true) {
							Blocks = Blocks + Keyboard.getEventCharacter();
							blocks = Integer.parseInt(Blocks);
							Settings.UseBaritoneBlocks = blocks;
							SetStuff();
						}
					}
					
 					if (Highway == true || OpenTerrain == true) {
 						if (START == true) {
							if (!Keyboard.getKeyName(Keyboard.getEventKey()).contains("ESCAPE")) {
								KeyBind.BindSTART = Keyboard.getKeyName(Keyboard.getEventKey());
							}
							START = false;
							SetStuff();
						} else if (STOP == true) {
							if (!Keyboard.getKeyName(Keyboard.getEventKey()).contains("ESCAPE")) {
								KeyBind.BindSTOP = Keyboard.getKeyName(Keyboard.getEventKey());
							}
							STOP = false;
							SetStuff();
						} else if (GUI == true) {
							if (!Keyboard.getKeyName(Keyboard.getEventKey()).contains("ESCAPE")) {
								KeyBind.BindGUI = Keyboard.getKeyName(Keyboard.getEventKey());
							}
							GUI = false;
							SetStuff();
						} else if (StopSpeed == true) {
							numbersSpeed = numbersSpeed + Keyboard.getEventCharacter();
							NumbersSpeed = Double.parseDouble(numbersSpeed);
							Settings.FlySpeed = NumbersSpeed;
							SetStuff();
						} else if (StopGlideSpeed == true) {
							numbersGlideSpeed = numbersGlideSpeed + Keyboard.getEventCharacter();
							NumbersGlideSpeed = Double.parseDouble(numbersGlideSpeed);
							Settings.GlideSpeed = NumbersGlideSpeed;
							SetStuff();
						}
 					}
 					
 					if (OpenTerrain == true) {
 						if (StopOpenX == true) {
							OpenX = OpenX + Keyboard.getEventCharacter();
							if (!OpenX.equals("-")) {
								openX = Integer.parseInt(OpenX);
								Settings.OpenTerrainX = openX;
							}
 						} else if (StopOpenZ == true) {
							OpenZ = OpenZ + Keyboard.getEventCharacter();
							if (!OpenZ.equals("-")) {
								openZ = Integer.parseInt(OpenZ);
								Settings.OpenTerrainZ = openZ;
							}
 						}
 					}
				}
			} else {
				StopX = false;
				StopZ = false;
				StopSpeed = false;
				StopGlideSpeed = false;
				GUI = false;
				START = false;
				STOP = false;
				StopBlock = false;
			}
			// Gotta catch em all!
		} catch (Exception e8) {
			ResetTyping();
		}
	}

	public static void TurnOff() {
		try {
			GetPath.Path.clear();
		} catch (Exception e) {

		}
		if (Main.FlySpeed != 0) {
			Settings.FlySpeed = Main.FlySpeed;
		}
		me.bebeli555.ElytraBot.Overworld.Main.toggle = false;
		me.bebeli555.ElytraBot.Overworld.Main.TurnOff();
		TakeOff.ActivatePacketFly = false;
		Main.delay5 = 0;
		me.bebeli555.ElytraBot.ElytraFly.YCenter = false;
		Main.delay18 = 0;
		me.bebeli555.ElytraBot.ElytraFly.FlyMinus = 0;
		Renderer.IsRendering = false;
		TakeOff.ActivatePacketFly = false;
		me.bebeli555.ElytraBot.Settings.AutoRepair.AutoRepair = false;
		AutoRepair.ArmorTakeoff = false;
		if (Main.baritonetoggle == true) {
			mc.player.sendChatMessage("#cancel");
		} else if (me.bebeli555.ElytraBot.Settings.Diagonal.baritonetoggle == true) {
			mc.player.sendChatMessage("#cancel");
		}
		me.bebeli555.ElytraBot.Main.toggle = false;
		me.bebeli555.ElytraBot.Main.UnCheck();
		me.bebeli555.ElytraBot.Settings.Diagonal.toggle = false;
		me.bebeli555.ElytraBot.Settings.Diagonal.UnCheck();
		me.bebeli555.ElytraBot.Settings.StopAt.toggle = false;
	}

	public static void TurnOn() {
		if (Highway == true) {
			if (me.bebeli555.ElytraBot.Settings.Settings.Diagonal == true) {
				me.bebeli555.ElytraBot.Settings.Diagonal.toggle = true;
				me.bebeli555.ElytraBot.Settings.Diagonal.Check();
			} else {
				me.bebeli555.ElytraBot.Main.toggle = true;
				me.bebeli555.ElytraBot.Main.Check();
			}
		} else if (OpenTerrain == true) {
			me.bebeli555.ElytraBot.Overworld.Main.Check();
			if ((int)mc.player.posY < 250) {
				mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.RED + "You should use this ABOVE the build limit so theres no obstacles!"));
			}
		} else {
			mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.RED + "No mode selected. Setting to Highway as default."));
			if (me.bebeli555.ElytraBot.Settings.Settings.Diagonal == true) {
				me.bebeli555.ElytraBot.Settings.Diagonal.toggle = true;
				me.bebeli555.ElytraBot.Settings.Diagonal.Check();
			} else {
				me.bebeli555.ElytraBot.Main.toggle = true;
				me.bebeli555.ElytraBot.Main.Check();
			}
		}
	}

	public static void ResetTyping() {
		SoundGUI.PlaySpeed = 1;
		SoundGUI.playSpeed = "";
		if (StopX == true) {
			XReal = -1;
			numbersX = "";
			NumbersX = 0;
			me.bebeli555.ElytraBot.Commands.MaxX = XReal;
		} else if (StopZ == true) {
			ZReal = -1;
			numbersZ = "";
			NumbersZ = 0;
			me.bebeli555.ElytraBot.Commands.MaxZ = ZReal;
		} else if (StopSpeed == true) {
			NumbersSpeed = 1.8;
			numbersSpeed = "";
			Settings.FlySpeed = NumbersSpeed;
			SetStuff();
		} else if (GUI == true) {
			KeyBind.BindGUI = "NONE";
		} else if (START == true) {
			KeyBind.BindSTART = "NONE";
		} else if (STOP == true) {
			KeyBind.BindSTOP = "NONE";
		} else if (StopGlideSpeed == true) {
			numbersGlideSpeed = "";
			NumbersGlideSpeed = 0.1;
			Settings.GlideSpeed = NumbersGlideSpeed;
			SetStuff();
		} else if (StopBlock == true) {
			Blocks = "";
			blocks = 15;
			Settings.UseBaritoneBlocks = blocks;
			SetStuff();
		} else if (StopOpenX == true) {
			OpenX = "";
			openX = -1;
			Settings.OpenTerrainX = openX;
		} else if (StopOpenZ == true) {
			OpenZ = "";
			openZ = -1;
			Settings.OpenTerrainZ = openZ;
		}
	}
}