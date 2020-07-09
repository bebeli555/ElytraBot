package com.bebeli555.ElytraBot;

import java.io.IOException;

import com.ibm.icu.text.TimeZoneFormat.Style;
import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ClickType;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Commands {
	Minecraft mc = Minecraft.getMinecraft();
	String Baritone;
	String AutoRepair;
	String Diagonal;
	String AutoEat;
	String ezzed = ChatFormatting.WHITE + "=";
	boolean confirm = false;
	static boolean stop = false;
	static int MaxX = -1;
	static boolean MaxX2 = false;
	static int MaxZ = -1;
	static boolean MaxZ2 = false;
	static boolean Log = false;
	static boolean Log2 = false;
	static boolean GuiON = false;
	
	static boolean noBaritone = false;

	// These are the commands you do ingame
	// I Originally thought there were only 2 commands but now this is a fucking
	// mess lmao
	
	//Dont take any advice from this plz
	@SubscribeEvent
	public void ChatEvent(ClientChatEvent e) {
		if (stop == true) {
			String Mega = e.getMessage().toLowerCase();
			if (MaxX2 == false) {
				MaxX2 = true;
				if (isInteger(Mega)) {
					MaxX = Integer.parseInt(Mega);
					this.bebeli(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.GRAY + "MaxX: "
							+ ChatFormatting.GREEN + Integer.parseInt(Mega));
					this.bebeli(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.RED + "(Number)"
							+ ChatFormatting.GRAY + " MaximumZ: ");
				} else {
					this.bebeli(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.GRAY
							+ "OutPut is Not A Number " + ChatFormatting.RED + "Stopping...");
					MaxX2 = false;
					MaxZ2 = false;
					Log2 = false;
					stop = false;
				}
			} else if (MaxZ2 == false) {
				MaxZ2 = true;
				if (isInteger(Mega)) {
					MaxZ = Integer.parseInt(Mega);
					this.bebeli(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.GRAY + "MaxZ: "
							+ ChatFormatting.GREEN + Integer.parseInt(Mega));
					this.bebeli(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.RED + "(True / False)"
							+ ChatFormatting.GRAY + " LogOutWhenAtCoords: ");
				} else {
					this.bebeli(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.GRAY
							+ "OutPut is Not A Number " + ChatFormatting.RED + "Stopping...");
					MaxX2 = false;
					MaxZ2 = false;
					Log2 = false;
					stop = false;
				}
			} else if (Log2 == false) {
				Log2 = true;
				if (Mega.contains("true")) {
					this.bebeli(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.GRAY + "MaxX: "
							+ ChatFormatting.GREEN + MaxX + ChatFormatting.GRAY + " MaxZ: " + ChatFormatting.GREEN
							+ MaxZ + ChatFormatting.GRAY + " LogOutWhenAtCoords: " + ChatFormatting.GREEN + Log);
					Log = true;
					MaxX2 = false;
					MaxZ2 = false;
					Log2 = false;
					stop = false;
				} else if (Mega.contains("false")) {
					this.bebeli(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.GRAY + "MaxX: "
							+ ChatFormatting.GREEN + MaxX + ChatFormatting.GRAY + " MaxZ: " + ChatFormatting.GREEN
							+ MaxZ + ChatFormatting.GRAY + " LogOutWhenAtCoords: " + ChatFormatting.RED + Log);
					Log = false;
					MaxX2 = false;
					MaxZ2 = false;
					Log2 = false;
					stop = false;
				} else {
					this.bebeli(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.GRAY
							+ "OutPut is Not True / False " + ChatFormatting.RED + "Stopping...");
					MaxX2 = false;
					MaxZ2 = false;
					Log2 = false;
					stop = false;
				}
			}
			e.setCanceled(true);

		} else {
			MaxX2 = false;
			MaxZ2 = false;
			Log2 = false;
		}

		if (com.bebeli555.ElytraBot.Settings.UseBaritone == true) {
			Baritone = ChatFormatting.GREEN + "True";
		} else {
			Baritone = ChatFormatting.RED + "False";
		}

		if (com.bebeli555.ElytraBot.Settings.AutoRepair == true) {
			AutoRepair = ChatFormatting.GREEN + "True";
		} else {
			AutoRepair = ChatFormatting.RED + "False";
		}

		if (com.bebeli555.ElytraBot.Settings.Diagonal == true) {
			Diagonal = ChatFormatting.GREEN + "True";
		} else {
			Diagonal = ChatFormatting.RED + "False";
		}

		if (com.bebeli555.ElytraBot.Settings.AutoEat == true) {
			AutoEat = ChatFormatting.GREEN + "True";
		} else {
			AutoEat = ChatFormatting.RED + "False";
		}

		if (e.getMessage().equalsIgnoreCase("++Start")) {
			Main.PitchOn = false;
			Main.PitchReal = 1F;
			com.bebeli555.ElytraBot.Diagonal.PitchOn = false;
			com.bebeli555.ElytraBot.Diagonal.PitchReal = 1F;
			
			String Log5;
			if (Log == false) {
				Log5 = ChatFormatting.RED + "False";
			} else {
				Log5 = ChatFormatting.GREEN + "True";
			}
			String X;
			String Z;
			if (MaxX == -1) {
				X = ChatFormatting.RED + "NONE";
			} else {
				X = ChatFormatting.GREEN + "" + MaxX;
			}

			if (MaxZ == -1) {
				Z = ChatFormatting.RED + "NONE";
			} else {
				Z = ChatFormatting.GREEN + "" + MaxZ;
			}
			com.bebeli555.ElytraBot.Init.SetVar();
			mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.GREEN + "Started!"));
			if (com.bebeli555.ElytraBot.Settings.Diagonal == true) {
				com.bebeli555.ElytraBot.Diagonal.toggle = true;
				com.bebeli555.ElytraBot.Diagonal.Check();
			} else {
				com.bebeli555.ElytraBot.Main.Check();
				com.bebeli555.ElytraBot.Main.toggle = true;
			}
			e.setCanceled(true);

		} else if (e.getMessage().equalsIgnoreCase("++Help")) {
			String message = ChatFormatting.GREEN + "Elytra Highway bot " + ChatFormatting.RED + "V1.7"
					+ ChatFormatting.GREEN + " Made by:" + ChatFormatting.RED + " bebeli555";
			mc.player.sendMessage(new TextComponentString(message));
			this.bebeli("");
			this.bebeli(ChatFormatting.RED + "++GUI " + ezzed + ChatFormatting.DARK_AQUA + " Opens the GUI");
			this.bebeli(ChatFormatting.RED + "++Start " + ezzed + ChatFormatting.DARK_AQUA
					+ " Start Pathfinding to direction you are looking at");
			this.bebeli(ChatFormatting.RED + "++Stop " + ezzed + ChatFormatting.DARK_AQUA + " Stop Pathfinding");
			this.bebeli(ChatFormatting.RED + "++Toggle <Setting> " + ezzed + ChatFormatting.DARK_AQUA
					+ " Toggles Setting Off or On");
			this.bebeli("");
			this.bebeli(ChatFormatting.AQUA + "Info about the Settings in the GUI open with " + ChatFormatting.RED
					+ "++GUI");
			mc.player.sendMessage(new TextComponentString(ChatFormatting.LIGHT_PURPLE + "Join Our Discord: "
					+ ChatFormatting.RED + "https://discord.gg/QTPWRrV"));
			e.setCanceled(true);

		} else if (e.getMessage().equalsIgnoreCase("++Stop")) {
			com.bebeli555.ElytraBot.AutoRepair.AutoRepair = false;
			com.bebeli555.ElytraBot.AutoRepair.ArmorTakeoff = false;
			if (Main.baritonetoggle == true) {
				mc.player.sendChatMessage("#cancel");
			} else if (com.bebeli555.ElytraBot.Diagonal.baritonetoggle == true) {
				mc.player.sendChatMessage("#cancel");
			}
			mc.player.sendMessage(new TextComponentString(
					ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.RED + "Stopped!"));
			com.bebeli555.ElytraBot.Main.toggle = false;
			com.bebeli555.ElytraBot.Main.UnCheck();
			com.bebeli555.ElytraBot.Diagonal.toggle = false;
			com.bebeli555.ElytraBot.Diagonal.UnCheck();
			com.bebeli555.ElytraBot.StopAt.toggle = false;
			e.setCanceled(true);
		} else if (e.getMessage().toLowerCase().contains("++gui")) {
			GuiON = true;
			e.setCanceled(true);
		} else if (e.getMessage().equalsIgnoreCase("++Open")) {
			GuiON = true;
			e.setCanceled(true);
		} else if (e.getMessage().equalsIgnoreCase("++Toggle UseBaritone")) {
			com.bebeli555.ElytraBot.StopAt.toggle = false;
			if (noBaritone == false) {
			if (com.bebeli555.ElytraBot.Settings.UseBaritone == true) {
				com.bebeli555.ElytraBot.Settings.UseBaritone = false;
			} else {
				com.bebeli555.ElytraBot.Settings.UseBaritone = true;
			}
			if (com.bebeli555.ElytraBot.Settings.UseBaritone == true) {
				mc.player.sendMessage(new TextComponentString(ChatFormatting.BLUE + "UseBaritone: "
						+ ChatFormatting.GREEN + com.bebeli555.ElytraBot.Settings.UseBaritone));
			} else {
				mc.player.sendMessage(new TextComponentString(ChatFormatting.BLUE + "UseBaritone: " + ChatFormatting.RED
						+ com.bebeli555.ElytraBot.Settings.UseBaritone));
			}
			} else {
				Settings.UseBaritone = false;
				this.bebeli(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.RED + "This setting requires baritone. You dont have it");
			}
			
			e.setCanceled(true);
			try {
				com.bebeli555.ElytraBot.Settings.Default();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else if (e.getMessage().equalsIgnoreCase("++Toggle AutoRepair")) {
			if (com.bebeli555.ElytraBot.Settings.AutoRepair == true) {
				com.bebeli555.ElytraBot.Settings.AutoRepair = false;
			} else {
				com.bebeli555.ElytraBot.Settings.AutoRepair = true;
			}
			if (com.bebeli555.ElytraBot.Settings.AutoRepair == true) {
				mc.player.sendMessage(new TextComponentString(ChatFormatting.BLUE + "AutoRepair: "
						+ ChatFormatting.GREEN + com.bebeli555.ElytraBot.Settings.AutoRepair));
			} else {
				mc.player.sendMessage(new TextComponentString(ChatFormatting.BLUE + "AutoRepair: " + ChatFormatting.RED
						+ com.bebeli555.ElytraBot.Settings.AutoRepair));
			}

			e.setCanceled(true);
			try {
				com.bebeli555.ElytraBot.Settings.Default();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else if (e.getMessage().equalsIgnoreCase("++Toggle Diagonal")) {
			if (com.bebeli555.ElytraBot.Settings.Diagonal == true) {
				com.bebeli555.ElytraBot.Settings.Diagonal = false;
			} else {
				com.bebeli555.ElytraBot.Settings.Diagonal = true;
			}
			if (com.bebeli555.ElytraBot.Settings.Diagonal == true) {
				mc.player.sendMessage(new TextComponentString(ChatFormatting.BLUE + "Diagonal: " + ChatFormatting.GREEN
						+ com.bebeli555.ElytraBot.Settings.Diagonal));
			} else {
				mc.player.sendMessage(new TextComponentString(ChatFormatting.BLUE + "Diagonal: " + ChatFormatting.RED
						+ com.bebeli555.ElytraBot.Settings.Diagonal));
			}

			e.setCanceled(true);
			try {
				com.bebeli555.ElytraBot.Settings.Default();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		} else if (e.getMessage().equalsIgnoreCase("++Toggle StopAt")) {
			e.setCanceled(true);
			stop = true;
			this.bebeli(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.GRAY + "Type in " + ChatFormatting.RED
					+ "Chat. " + ChatFormatting.GRAY + "No ++ Needed Set " + ChatFormatting.RED + "-1 "
					+ ChatFormatting.GRAY + "For " + ChatFormatting.RED + "NONE");
			this.bebeli(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.RED + "(Number)" + ChatFormatting.GRAY
					+ " MaximumX: ");

		} else if (e.getMessage().equalsIgnoreCase("++Toggle AutoEat")) {
			e.setCanceled(true);
			if (com.bebeli555.ElytraBot.Settings.AutoEat == true) {
				Settings.AutoEat = false;
				mc.player.sendMessage(new TextComponentString(ChatFormatting.BLUE + "AutoEat: " + ChatFormatting.RED
						+ com.bebeli555.ElytraBot.Settings.AutoEat));
			} else {
				Settings.AutoEat = true;
				mc.player.sendMessage(new TextComponentString(ChatFormatting.BLUE + "AutoEat: " + ChatFormatting.GREEN
						+ com.bebeli555.ElytraBot.Settings.AutoEat));
			}

			try {
				com.bebeli555.ElytraBot.Settings.Default();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else if (e.getMessage().startsWith("++toggle")) {
			mc.player.sendMessage(new TextComponentString(
					ChatFormatting.BLUE + "Usage: " + ChatFormatting.RED + "++Toggle " + ChatFormatting.BLUE
							+ "<Setting> See available settings with " + ChatFormatting.RED + "++Help"));
			mc.playerController.windowClick(2, 2, 2, ClickType.PICKUP, mc.player);
			mc.playerController.windowClick(4, 4, 4, ClickType.PICKUP, mc.player);
			e.setCanceled(true);
		} else if (e.getMessage().startsWith("++")) {
			mc.player.sendMessage(new TextComponentString(ChatFormatting.RED + "Unknown command Type"
					+ ChatFormatting.DARK_RED + " ++Help " + ChatFormatting.RED + "for help"));
			e.setCanceled(true);
		}

	}

	public void bebeli(String String) {
		mc.player.sendMessage(new TextComponentString(String));
	}

	public static boolean isInteger(String s) {
		return isInteger(s, 10);
	}

	public static boolean isInteger(String s, int radix) {
		if (s.isEmpty())
			return false;
		for (int i = 0; i < s.length(); i++) {
			if (i == 0 && s.charAt(i) == '-') {
				if (s.length() == 1)
					return false;
				else
					continue;
			}
			if (Character.digit(s.charAt(i), radix) < 0)
				return false;
		}
		return true;
	}

	public static String extractNumber(final String str) {

		if (str == null || str.isEmpty())
			return "";

		StringBuilder sb = new StringBuilder();
		boolean found = false;
		for (char c : str.toCharArray()) {
			if (Character.isDigit(c)) {
				sb.append(c);
				found = true;
			} else if (found) {
				// If we already found a digit before and this char is not a digit, stop looping
				break;
			}
		}

		return sb.toString();
	}
}