package com.bebeli555.ElytraBot;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import scala.Char;


public class Gui extends GuiScreen{
	// ElytraBot GUI
	// epikk helped with some colors & chatformatting :D
	static Minecraft mc = Minecraft.getMinecraft();
	int delay = 0;
	int x;
	int z;
	String baritone = "UseBaritone";
	String Repair = "AutoRepair";
	String Diagonal = "Diagonal";
	String AutoEat = "AutoEat";
	String ElytraFly = ChatFormatting.BOLD + "ElytraFly:";
	String x2 = "Max X: ";
	String z2 = "Max Z: ";
	String log = "LogOut: ";
	static boolean StopX = false;
	static boolean StopZ = false;
	static boolean StopSpeed = false;
	static boolean StopGlideSpeed = false;
	String numbersX = "";
	int XReal;
	String numbersZ = "";
	String numbersSpeed = "";
	double NumbersSpeed;
	String numbersGlideSpeed = "";
	double NumbersGlideSpeed;
	int ZReal;
	int delay2 = 0;
	int NumbersX;
	int NumbersZ;
	int delay5 = 0;
	String Start = ChatFormatting.BOLD + "START";
	String Stop = ChatFormatting.BOLD + "STOP";
	static boolean on = false;
	static boolean GUI = false;
	static boolean START = false;
	static boolean STOP = false;
	
    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    	//Set the X and Z StopAt Setting stuff yay

    	//Some stuff
    	x = com.bebeli555.ElytraBot.Commands.MaxX;
    	z = com.bebeli555.ElytraBot.Commands.MaxZ;
        //Draw the GUI Stuff
    	this.drawDefaultBackground();

    	mc.fontRenderer.drawStringWithShadow(ChatFormatting.RED + "ElytraHighwayBot " + ChatFormatting.GREEN + "V1.8", 1, 1, 0xb8352c);
    	mc.fontRenderer.drawStringWithShadow(ChatFormatting.RED + "Made By: " + ChatFormatting.GREEN + "bebeli555", 1, 10, 0xb8352c);
    	mc.fontRenderer.drawStringWithShadow(ChatFormatting.RED + "Discord: " + ChatFormatting.GREEN + "https://discord.gg/QTPWRrV", 1, 20, 0xb8352c);

    	//Start and Stop
        drawRect(430, 70, 540, 90, 0x36393fff);
        if (com.bebeli555.ElytraBot.Main.toggle == true | com.bebeli555.ElytraBot.Diagonal.toggle == true | com.bebeli555.ElytraBot.Main.baritonetoggle == true | com.bebeli555.ElytraBot.Diagonal.baritonetoggle == true) {
        	mc.fontRenderer.drawStringWithShadow(ChatFormatting.RED + Stop, 468, 75, 0xffff);
        	on = true;
        } else {
        	mc.fontRenderer.drawStringWithShadow(ChatFormatting.GREEN + Start, 468, 75, 0xffff);
        	on = false;
        }
        
        //Settings mark
        drawRect(430, 100, 540, 120, 0x36393fff);
        mc.fontRenderer.drawStringWithShadow(ChatFormatting.BOLD + "Settings:", 459, 105, 0xffaa00);
        
        //UseBaritone
        drawRect(430, 120, 540, 140, 0x36393fff);
        if (com.bebeli555.ElytraBot.Settings.UseBaritone == false) {
        	mc.fontRenderer.drawStringWithShadow(ChatFormatting.RED + baritone, 453, 125, 0xffff);
        } else {
        	mc.fontRenderer.drawStringWithShadow(ChatFormatting.GREEN + baritone, 453, 125, 0xffff);
        }
        
        //AutoRepair
        drawRect(430, 140, 540, 160, 0x36393fff);
        if (com.bebeli555.ElytraBot.Settings.AutoRepair == false) {
        	mc.fontRenderer.drawStringWithShadow(ChatFormatting.RED + Repair, 458, 145, 0xffff);
        } else {
        	mc.fontRenderer.drawStringWithShadow(ChatFormatting.GREEN + Repair, 458, 145, 0xffff);
        }
        
        //Diagonal
        drawRect(430, 160, 540, 180, 0x36393fff);
        if (com.bebeli555.ElytraBot.Settings.Diagonal == false) {
        	mc.fontRenderer.drawStringWithShadow(ChatFormatting.RED + Diagonal, 463, 165, 0xffff);
        } else {
        	mc.fontRenderer.drawStringWithShadow(ChatFormatting.GREEN + Diagonal, 463, 165, 0xffff);
        }
        
        //AutoEat
        drawRect(430, 180, 540, 200, 0x36393fff);
        if (com.bebeli555.ElytraBot.Settings.AutoEat == false) {
        	mc.fontRenderer.drawStringWithShadow(ChatFormatting.RED + AutoEat, 463, 185, 0xffff);
        } else {
        	mc.fontRenderer.drawStringWithShadow(ChatFormatting.GREEN + AutoEat, 463, 185, 0xffff);
        }
        
        //Stopat mark
        drawRect(430, 210, 540, 230, 0x36393fff);
        mc.fontRenderer.drawStringWithShadow(ChatFormatting.BOLD + "Stopat Setting:", 445, 215, 0xffaa00);
        
        //Stopat X
        drawRect(430, 230, 540, 250, 0x36393fff);
        if (x == -1) {
        	mc.fontRenderer.drawStringWithShadow(ChatFormatting.DARK_AQUA + x2 + ChatFormatting.RED + "NONE", 455, 235, 0xffff);
        } else {
        	mc.fontRenderer.drawStringWithShadow(ChatFormatting.DARK_AQUA + x2 + ChatFormatting.GREEN + x, 455, 235, 0xffff);
        }
        
        //Stopat Z
        drawRect(430, 250, 540, 270, 0x36393fff);
        if (z == -1) {
        	mc.fontRenderer.drawStringWithShadow(ChatFormatting.DARK_AQUA + z2 + ChatFormatting.RED + "NONE", 455, 255, 0xffff);
        } else {
        	mc.fontRenderer.drawStringWithShadow(ChatFormatting.DARK_AQUA + z2 + ChatFormatting.GREEN + z, 455, 255, 0xffff);
        }
        
        //Stopat Log
        drawRect(430, 270, 540, 290, 0x36393fff);
        if (com.bebeli555.ElytraBot.Commands.Log == true) {
        	mc.fontRenderer.drawStringWithShadow(ChatFormatting.DARK_AQUA + log + ChatFormatting.GREEN + com.bebeli555.ElytraBot.Commands.Log, 450, 275, 0xffff);
        } else {
        	mc.fontRenderer.drawStringWithShadow(ChatFormatting.DARK_AQUA + log + ChatFormatting.RED + com.bebeli555.ElytraBot.Commands.Log, 450, 275, 0xffff);
        }
        
        //ElytraFly Mark
        drawRect(430, 300, 540, 320, 0x36393fff);
        mc.fontRenderer.drawStringWithShadow(ChatFormatting.GOLD + ElytraFly , 455, 305, 0xffff);
                
        //Speed
        drawRect(430, 320, 540, 340, 0x36393fff);
        mc.fontRenderer.drawStringWithShadow(ChatFormatting.DARK_AQUA + "Speed: " + ChatFormatting.GREEN + Settings.FlySpeed, 455, 325, 0xffff);
        
        //GlideSpeed
        drawRect(430, 340, 540, 360, 0x36393fff);
        mc.fontRenderer.drawStringWithShadow(ChatFormatting.DARK_AQUA + "GlideSpeed: " + ChatFormatting.GREEN + Settings.GlideSpeed, 445, 345, 0xffff);
        
        
        //KeyBinds mark
        drawRect(550, 100, 660, 120, 0x36393fff);
        mc.fontRenderer.drawStringWithShadow(ChatFormatting.BOLD + "KeyBinds:", 578, 105, 0xffaa00);
        
        //KeyBind GUI
        drawRect(550, 120, 660, 140, 0x36393fff);
        if (KeyBind.BindGUI.contains("NONE")) {
        	mc.fontRenderer.drawStringWithShadow(ChatFormatting.DARK_AQUA + "GUI: " + ChatFormatting.RED + KeyBind.BindGUI, 582, 125, 0xffaa00);
        } else {
        	mc.fontRenderer.drawStringWithShadow(ChatFormatting.DARK_AQUA + "GUI: " + ChatFormatting.GREEN + KeyBind.BindGUI, 582, 125, 0xffaa00);
        }
        
        //KeyBind START
        drawRect(550, 140, 660, 160, 0x36393fff);
        if (KeyBind.BindSTART.contains("NONE")) {
        	mc.fontRenderer.drawStringWithShadow(ChatFormatting.DARK_AQUA + "Start: " + ChatFormatting.RED + KeyBind.BindSTART, 578, 145, 0xffaa00);
        } else {
        	mc.fontRenderer.drawStringWithShadow(ChatFormatting.DARK_AQUA + "Start: " + ChatFormatting.GREEN + KeyBind.BindSTART, 578, 145, 0xffaa00);
        }
        
        //KeyBind STOP
        drawRect(550, 160, 660, 180, 0x36393fff);
        if (KeyBind.BindSTOP.contains("NONE")) {
        	mc.fontRenderer.drawStringWithShadow(ChatFormatting.DARK_AQUA + "Stop: " + ChatFormatting.RED + KeyBind.BindSTOP, 580, 165, 0xffaa00);
        } else {
        	mc.fontRenderer.drawStringWithShadow(ChatFormatting.DARK_AQUA + "Stop: " + ChatFormatting.GREEN + KeyBind.BindSTOP, 580, 165, 0xffaa00);
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
        
        //TakeOff Mark
        drawRect(550, 190, 660, 210, 0x36393fff);
        mc.fontRenderer.drawStringWithShadow(ChatFormatting.BOLD + "TakeOff:", 580, 195, 0xffaa00);
      
        //TakeOff Packetfly
        drawRect(550, 210, 660, 230, 0x36393fff);
        mc.fontRenderer.drawStringWithShadow(PacketFly, 580, 215, 0xffaa00);
      
        //TakeOff SlowGlide
        drawRect(550, 230, 660, 250, 0x36393fff);
        mc.fontRenderer.drawStringWithShadow(SlowGlide, 580, 235, 0xffaa00);

        
        //Set guide for yall dummies ;)
        if (StopX == true) {
        	mc.fontRenderer.drawStringWithShadow(ChatFormatting.GOLD + "Type numbers in your keyboard to set this!", 550, 235, 0xffff);
        }
        
        if (StopZ == true) {
        	mc.fontRenderer.drawStringWithShadow(ChatFormatting.GOLD + "Type numbers in your keyboard to set this!", 550, 255, 0xffff);
        }
        
        if (StopSpeed == true) {
        	mc.fontRenderer.drawStringWithShadow(ChatFormatting.GOLD + "Type numbers in your keyboard to set this!", 550, 325, 0xffff);
        }
        
        if (StopGlideSpeed == true) {
        	mc.fontRenderer.drawStringWithShadow(ChatFormatting.GOLD + "Type numbers in your keyboard to set this! (Divided by 1000)", 550, 345, 0xffff);
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
        
        //Draw Settings help page to GUI
        String settinginfo = ChatFormatting.BOLD + "Settings INFO";
        
        mc.fontRenderer.drawStringWithShadow(ChatFormatting.BLACK + "(" + ChatFormatting.RED + "!" + ChatFormatting.BLACK  + ") " + ChatFormatting.GOLD + settinginfo + ChatFormatting.BLACK + " (" + ChatFormatting.RED + "!" + ChatFormatting.BLACK  + ")", 184, 50, 0xffff);
        //UseBaritone
        mc.fontRenderer.drawStringWithShadow(ChatFormatting.LIGHT_PURPLE + "UseBaritone", 210, 70, 0xffff);
        mc.fontRenderer.drawStringWithShadow("When Ever ElytraBot gets stuck", 165, 80, 0xffff);
        mc.fontRenderer.drawStringWithShadow("It will use baritone to walk forward", 155, 90, 0xffff);
        mc.fontRenderer.drawStringWithShadow("So it can start flying again.", 175, 100, 0xffff);
        
        //AutoRepair
        mc.fontRenderer.drawStringWithShadow(ChatFormatting.LIGHT_PURPLE + "AutoRepair", 215, 130, 0xffff);
        mc.fontRenderer.drawStringWithShadow("Automatically repairs elytra using", 160, 140, 0xffff);
        mc.fontRenderer.drawStringWithShadow("XP-Bottles in ur inventory if elytra", 155, 150, 0xffff);
        mc.fontRenderer.drawStringWithShadow("Durability gets low", 195, 160, 0xffff);
        mc.fontRenderer.drawStringWithShadow("Also it takes off ur armor if inv has space", 135, 170, 0xffff);
        
        //Diagonal
        mc.fontRenderer.drawStringWithShadow(ChatFormatting.LIGHT_PURPLE + "Diagonal", 219, 200, 0xffff);
        mc.fontRenderer.drawStringWithShadow("Turns ElytraBot to Diagonal Mode", 162, 210, 0xffff);
        mc.fontRenderer.drawStringWithShadow("So it will travel on the diagonal", 168, 220, 0xffff);
        mc.fontRenderer.drawStringWithShadow("Highway ur looking at when u start it", 157, 230, 0xffff);
        
        //AutoEat
        mc.fontRenderer.drawStringWithShadow(ChatFormatting.LIGHT_PURPLE + "AutoEat", 221, 260, 0xffff);
        mc.fontRenderer.drawStringWithShadow("Auto Eats When ur low hunger / Health", 148, 270, 0xffff);
        mc.fontRenderer.drawStringWithShadow("Doesn't eat chorus and prefers gaps", 150, 280, 0xffff);
        mc.fontRenderer.drawStringWithShadow("Food has to be in ur HOTBAR", 175, 290, 0xffff);
        
        //StopAt
        mc.fontRenderer.drawStringWithShadow(ChatFormatting.LIGHT_PURPLE + "StopAt", 223, 320, 0xffff);
        mc.fontRenderer.drawStringWithShadow("When 1 Of the set coords is reached", 150, 330, 0xffff);
        mc.fontRenderer.drawStringWithShadow("ElytraBot Will Stop and LogOut if", 165, 340, 0xffff);
        mc.fontRenderer.drawStringWithShadow("LogOut Setting is set to true", 170, 350, 0xffff);
        mc.fontRenderer.drawStringWithShadow(ChatFormatting.RED + "Note: ", 148, 360, 0xffff);
        mc.fontRenderer.drawStringWithShadow("You have to put NETHER Coords!", 175, 360, 0xffff);
        
        
        //Takeoff help page
        String takeoffhelp = ChatFormatting.BOLD + "TakeOff INFO";
        mc.fontRenderer.drawStringWithShadow(ChatFormatting.BLACK + "(" + ChatFormatting.RED + "!" + ChatFormatting.BLACK  + ") " + ChatFormatting.GOLD + takeoffhelp + ChatFormatting.BLACK + " (" + ChatFormatting.RED + "!" + ChatFormatting.BLACK  + ")", 740, 50, 0xffff);
        mc.fontRenderer.drawStringWithShadow("TakeOff settings are settings that elytrabot", 685, 70, 0xffff);
        mc.fontRenderer.drawStringWithShadow("Will use after the Jump in takeoff", 715, 80, 0xffff);
        mc.fontRenderer.drawStringWithShadow("To get it more time to activate elytra", 708, 90, 0xffff);
        mc.fontRenderer.drawStringWithShadow("If you got none enabled then it will just jump", 683, 100, 0xffff);
        mc.fontRenderer.drawStringWithShadow("And try to takeoff only from that", 720, 110, 0xffff);
        
        //PacketFly
        mc.fontRenderer.drawStringWithShadow(ChatFormatting.LIGHT_PURPLE + "PacketFly", 775, 140, 0xffff);
        mc.fontRenderer.drawStringWithShadow("Uses Packetfly after jumping to", 720, 150, 0xffff);
        mc.fontRenderer.drawStringWithShadow("Give more time to activate elytra", 720, 160, 0xffff);
        mc.fontRenderer.drawStringWithShadow(ChatFormatting.GREEN + "Best for 2b2t!", 762, 170, 0xffff);
        
        //SlowGlide
        mc.fontRenderer.drawStringWithShadow(ChatFormatting.LIGHT_PURPLE + "SlowGlide", 775, 200, 0xffff);
        mc.fontRenderer.drawStringWithShadow("Glides down slow after jumping to", 720, 210, 0xffff);
        mc.fontRenderer.drawStringWithShadow("Give more time to activate elytra", 720, 220, 0xffff);
    }

    //Useless thingy get the fuck out of here man you have no purpose in this place (I mean the code below this not you <3)
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    
    //Activate the GUI.
    @SubscribeEvent
    public void onUpdate(ClientTickEvent e) {
    	if (com.bebeli555.ElytraBot.Commands.GuiON == true) {
    		delay++;
    		if (delay > 1) {
    			com.bebeli555.ElytraBot.Commands.GuiON = false;
    			delay = 0;
    			Minecraft.getMinecraft().displayGuiScreen(new Gui());
    		}
    	}
    	
    	//Stop listening to keys when gui is closed
    	if (mc.currentScreen == null) {
    		StopX = false;
    		StopZ = false;
    		StopSpeed = false;
    		StopGlideSpeed = false;
    		GUI = false;
    		START = false;
    		STOP = false;
    	}
    }
    
    
    //Mouse click event stuff it activates the modules and stuff when u click the thingys on the screen!
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
			
			// Start and Stop 430, 70, 540, 90
			if (430 < i && 540 > i && 70 < j && 90 > j) {
				if (on == true) {
					TurnOff();
				} else {
					TurnOn();
				}
			}
			
			
			//UseBaritone 430, 120, 540, 140
			if (430 < i && 540 > i && 120 < j && 140 > j) {
				if (Commands.noBaritone == false) {
				if (com.bebeli555.ElytraBot.Settings.UseBaritone == false) {
					com.bebeli555.ElytraBot.Settings.UseBaritone = true;
				} else {
					com.bebeli555.ElytraBot.Settings.UseBaritone = false;
				}
				} else {
					Settings.UseBaritone = false;
					mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.RED + "This setting requires baritone. You dont have it"));
				}
				SetStuff();
			}
			
			
			//AutoRepair 430, 140, 540, 160
			if (430 < i && 540 > i && 140 < j && 160 > j) {
				if (com.bebeli555.ElytraBot.Settings.AutoRepair == false) {
					com.bebeli555.ElytraBot.Settings.AutoRepair = true;
				} else {
					com.bebeli555.ElytraBot.Settings.AutoRepair = false;
				}
				SetStuff();
			}
			
			
			//Diagonal 430, 160, 540, 180
			if (430 < i && 540 > i && 160 < j && 180 > j) {
				if (com.bebeli555.ElytraBot.Settings.Diagonal == false) {
					com.bebeli555.ElytraBot.Settings.Diagonal = true;
				} else {
					com.bebeli555.ElytraBot.Settings.Diagonal = false;
				}
				SetStuff();
			}
			
			//AutoEat 430, 180, 540, 200
			if (430 < i && 540 > i && 180 < j && 200 > j) {
				if (com.bebeli555.ElytraBot.Settings.AutoEat == false) {
					com.bebeli555.ElytraBot.Settings.AutoEat = true;
				} else {
					com.bebeli555.ElytraBot.Settings.AutoEat = false;
				}
				SetStuff();
			}
			
			//Logout 430, 250, 540, 270
			if (430 < i && 540 > i && 270 < j && 290 > j) {
				if (com.bebeli555.ElytraBot.Commands.Log == false) {
					com.bebeli555.ElytraBot.Commands.Log = true;
				} else {
					com.bebeli555.ElytraBot.Commands.Log = false;
				}
			}
			
			
			//Stopat X 430, 210, 540, 230
			if (430 < i && 540 > i && 230 < j && 250 > j) {
				StopX = true;
			}
			
			
			//Stopat Z 430, 230, 540, 250
			if (430 < i && 540 > i && 250 < j && 270 > j) {
				StopZ = true;
			}
			
			//ElytraFlySpeed 430, 320, 540, 340
			if (430 < i && 540 > i && 320 < j && 340 > j) {
				StopSpeed = true;
			}
			
			//ElytraFlyGlideSpeed 430, 340, 540, 360
			if (430 < i && 540 > i && 340 < j && 360 > j) {
				StopGlideSpeed = true;
			}
			
			//Discord link 1, 1, 190, 30
			if (1 < i && 190 > i && 1 < j && 30 > j) {
				GuiConfirmOpenLink LinkMan = new GuiConfirmOpenLink(this, "https://discord.gg/QTPWRrV", 0, true);
				LinkMan.disableSecurityWarning();
				mc.displayGuiScreen(LinkMan);
			}
			
			//Keybind GUI 550, 120, 660, 140
			if (550 < i && 660 > i && 120 < j && 140 > j) {
				GUI = true;
			}
			
			//Keybind Start 550, 140, 660, 160
			if (550 < i && 660 > i && 140 < j && 160 > j) {
				START = true;
			}
			
			//Keybind Stop 550, 160, 660, 180
			if (550 < i && 660 > i && 160 < j && 180 > j) {
				STOP = true;
			}
			
			//Takeoff Packetfly 550, 210, 660, 230
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
			
			//Takeoff SlowGlide 550, 230, 660, 250
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
			
			
		} catch (IOException e) {
			System.out.println("IOException In ElytraBot GUI");
		}
    	
    }
    
    public static void SetStuff() {
		try {
			com.bebeli555.ElytraBot.Settings.Default();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }
    
	// Keyboard event for the StopAt X and Z
	@SubscribeEvent
	public void onKeyPress(GuiScreenEvent.KeyboardInputEvent e) {
		try {
		if (!Keyboard.isKeyDown(28)) {
		if (Keyboard.isKeyDown(14)) {
			if (StopX == true) {
				XReal = -1;
				numbersX = "";
				NumbersX = 0;
				com.bebeli555.ElytraBot.Commands.MaxX = XReal;
			} else if (StopZ == true) {
				ZReal = -1;
				numbersZ = "";
				NumbersZ = 0;
				com.bebeli555.ElytraBot.Commands.MaxZ = ZReal;
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
				NumbersGlideSpeed = 1;
				Settings.GlideSpeed = NumbersGlideSpeed;
				SetStuff();
			}
		} else if (Keyboard.isKeyDown(Keyboard.getEventKey())) {
			if (StopX == true) {
				try {
					delay2++;
					if (delay2 == 1) {
						if (Keyboard.isKeyDown(74) | Keyboard.isKeyDown(12)) {
							numbersX = "-";
							NumbersX = Integer.parseInt(numbersX);
							numbersX = Integer.toString(NumbersX);
							XReal = NumbersX;
							com.bebeli555.ElytraBot.Commands.MaxX = XReal;
						} else {
							if (Keyboard.isKeyDown(11)) {
								numbersX = numbersX + 1;
							} else if (Keyboard.isKeyDown(10)) {
								numbersX = numbersX + 9;
							} else {
								numbersX = numbersX + Keyboard.getEventKey();
							}
							NumbersX = Integer.parseInt(numbersX);
							if (Keyboard.isKeyDown(10)) {
								NumbersX = NumbersX;
							} else {
								if (numbersX.contains("-")) {
									NumbersX = NumbersX + 1;
								} else {
									NumbersX = NumbersX - 1;
								}
							}
							numbersX = Integer.toString(NumbersX);
							XReal = NumbersX;
							com.bebeli555.ElytraBot.Commands.MaxX = XReal;
							
						}
					} else {
						delay2 = 0;
					}
				} catch (NumberFormatException e5) {

				}
			}else if (StopZ == true) {
				try {
					delay2++;
					if (delay2 == 1) {
						if (Keyboard.isKeyDown(74) | Keyboard.isKeyDown(12)) {
							numbersZ = "-";
							int NumbersZ = Integer.parseInt(numbersZ);
							numbersZ = Integer.toString(NumbersZ);
							ZReal = NumbersZ;
							com.bebeli555.ElytraBot.Commands.MaxZ = ZReal;
						} else {
							if (Keyboard.isKeyDown(11)) {
								numbersZ = numbersZ + 1;
							} else if (Keyboard.isKeyDown(10)) {
								numbersZ = numbersZ + 9;
							} else {
								numbersZ = numbersZ + Keyboard.getEventKey();
							}
							int NumbersZ = Integer.parseInt(numbersZ);
							if (Keyboard.isKeyDown(10)) {
								NumbersZ = NumbersZ;
							} else {
								if (numbersZ.contains("-")) {
									NumbersZ = NumbersZ + 1;
								} else {
									NumbersZ = NumbersZ - 1;
								}
							}
							numbersZ = Integer.toString(NumbersZ);
							ZReal = NumbersZ;
							com.bebeli555.ElytraBot.Commands.MaxZ = ZReal;
						}
					} else {
						delay2 = 0;
					}
				} catch (NumberFormatException e5) {

				}
			} else if (StopSpeed == true) {
				delay2++;
				if (delay2 == 1) {
					numbersSpeed = numbersSpeed + Keyboard.getEventCharacter();
					NumbersSpeed = Double.parseDouble(numbersSpeed);
					Settings.FlySpeed = NumbersSpeed;
				} else {
					delay2 = 0;
				}
			} else if (GUI == true) {
				if (!Keyboard.getKeyName(Keyboard.getEventKey()).contains("ESCAPE")) {
					KeyBind.BindGUI = Keyboard.getKeyName(Keyboard.getEventKey());
				}
				GUI = false;
				SetStuff();
			} else if (START == true) {
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
			} else if (StopGlideSpeed == true) {
				delay2++;
				if (delay2 == 1) {
					numbersGlideSpeed = numbersGlideSpeed + Keyboard.getEventCharacter();
					NumbersGlideSpeed = Double.parseDouble(numbersGlideSpeed);
					Settings.GlideSpeed = NumbersGlideSpeed;
					SetStuff();
				} else {
					delay2 = 0;
				}
			}
		}
		} else {
			StopX = false;
			StopZ = false;
			StopSpeed = false;
			GUI = false;
			START = false;
			STOP = false;
		}
		//Gotta catch em all!
		} catch (Exception e8) {
			numbersSpeed = "";
		}
	}
	
	public static void TurnOff() {
		TakeOff.WarningMessage = false;
		TakeOff.ActivatePacketFly = false;
		com.bebeli555.ElytraBot.AutoRepair.AutoRepair = false;
		AutoRepair.ArmorTakeoff = false;
		if (Main.baritonetoggle == true) {
			mc.player.sendChatMessage("#cancel");
		} else if (com.bebeli555.ElytraBot.Diagonal.baritonetoggle == true) {
			mc.player.sendChatMessage("#cancel");
		}
		com.bebeli555.ElytraBot.Main.toggle = false;
		com.bebeli555.ElytraBot.Main.UnCheck();
		com.bebeli555.ElytraBot.Diagonal.toggle = false;
		com.bebeli555.ElytraBot.Diagonal.UnCheck();
		com.bebeli555.ElytraBot.StopAt.toggle = false;
	}
	
	public static void TurnOn() {
		if (com.bebeli555.ElytraBot.Settings.Diagonal == true) {
			com.bebeli555.ElytraBot.Diagonal.toggle = true;
			com.bebeli555.ElytraBot.Diagonal.Check();
		} else {
			com.bebeli555.ElytraBot.Main.toggle = true;
			com.bebeli555.ElytraBot.Main.Check();
		}
	}
}