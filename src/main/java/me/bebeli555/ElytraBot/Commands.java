package me.bebeli555.ElytraBot;

import java.io.IOException;

import me.bebeli555.ElytraBot.PathFinding.AStar;
import me.bebeli555.ElytraBot.PathFinding.GetPath;
import me.bebeli555.ElytraBot.Settings.Settings;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.ClickType;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Commands {
	Minecraft mc = Minecraft.getMinecraft();
	String Baritone;
	String AutoRepair;
	String Diagonal;
	String AutoEat;
	String ezzed = ChatFormatting.WHITE + "=";
	boolean confirm = false;
	public static boolean stop = false;
	public static int MaxX = -1;
	public static boolean MaxX2 = false;
	public static int MaxZ = -1;
	public static boolean MaxZ2 = false;
	public static boolean Log = false;
	public static boolean Log2 = false;
	public static boolean GuiON = false;
	
	public static boolean noBaritone = false;

	//Clean code 101
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

		if (me.bebeli555.ElytraBot.Settings.Settings.UseBaritone == true) {
			Baritone = ChatFormatting.GREEN + "True";
		} else {
			Baritone = ChatFormatting.RED + "False";
		}

		if (me.bebeli555.ElytraBot.Settings.Settings.AutoRepair == true) {
			AutoRepair = ChatFormatting.GREEN + "True";
		} else {
			AutoRepair = ChatFormatting.RED + "False";
		}

		if (me.bebeli555.ElytraBot.Settings.Settings.Diagonal == true) {
			Diagonal = ChatFormatting.GREEN + "True";
		} else {
			Diagonal = ChatFormatting.RED + "False";
		}

		if (me.bebeli555.ElytraBot.Settings.Settings.AutoEat == true) {
			AutoEat = ChatFormatting.GREEN + "True";
		} else {
			AutoEat = ChatFormatting.RED + "False";
		}

		if (e.getMessage().equalsIgnoreCase("++Start")) {
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
			me.bebeli555.ElytraBot.Init.SetVar();
			mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.GREEN + "Started!"));
			Gui.TurnOn();
			e.setCanceled(true);

		} else if (e.getMessage().equalsIgnoreCase("++Help")) {			
			String message = ChatFormatting.GREEN + "Elytra Highway bot " + ChatFormatting.RED + "V2.0"
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
			Gui.TurnOff();
			mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.RED + "Stopped!"));
			e.setCanceled(true);
		} else if (e.getMessage().toLowerCase().contains("++gui")) {
			GuiON = true;
			e.setCanceled(true);
		} else if (e.getMessage().equalsIgnoreCase("++Open")) {
			GuiON = true;
			e.setCanceled(true);
		} else if (e.getMessage().equalsIgnoreCase("++Toggle UseBaritone")) {
			me.bebeli555.ElytraBot.Settings.StopAt.toggle = false;
			if (noBaritone == false) {
			if (me.bebeli555.ElytraBot.Settings.Settings.UseBaritone == true) {
				me.bebeli555.ElytraBot.Settings.Settings.UseBaritone = false;
			} else {
				me.bebeli555.ElytraBot.Settings.Settings.UseBaritone = true;
			}
			if (me.bebeli555.ElytraBot.Settings.Settings.UseBaritone == true) {
				mc.player.sendMessage(new TextComponentString(ChatFormatting.BLUE + "UseBaritone: "
						+ ChatFormatting.GREEN + me.bebeli555.ElytraBot.Settings.Settings.UseBaritone));
			} else {
				mc.player.sendMessage(new TextComponentString(ChatFormatting.BLUE + "UseBaritone: " + ChatFormatting.RED
						+ me.bebeli555.ElytraBot.Settings.Settings.UseBaritone));
			}
			} else {
				Settings.UseBaritone = false;
				this.bebeli(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.RED + "This setting requires baritone. You dont have it");
			}
			
			e.setCanceled(true);
			try {
				me.bebeli555.ElytraBot.Settings.Settings.Default();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else if (e.getMessage().equalsIgnoreCase("++Toggle AutoRepair")) {
			if (me.bebeli555.ElytraBot.Settings.Settings.AutoRepair == true) {
				me.bebeli555.ElytraBot.Settings.Settings.AutoRepair = false;
			} else {
				me.bebeli555.ElytraBot.Settings.Settings.AutoRepair = true;
			}
			if (me.bebeli555.ElytraBot.Settings.Settings.AutoRepair == true) {
				mc.player.sendMessage(new TextComponentString(ChatFormatting.BLUE + "AutoRepair: "
						+ ChatFormatting.GREEN + me.bebeli555.ElytraBot.Settings.Settings.AutoRepair));
			} else {
				mc.player.sendMessage(new TextComponentString(ChatFormatting.BLUE + "AutoRepair: " + ChatFormatting.RED
						+ me.bebeli555.ElytraBot.Settings.Settings.AutoRepair));
			}

			e.setCanceled(true);
			try {
				me.bebeli555.ElytraBot.Settings.Settings.Default();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else if (e.getMessage().equalsIgnoreCase("++Toggle Diagonal")) {
			if (me.bebeli555.ElytraBot.Settings.Settings.Diagonal == true) {
				me.bebeli555.ElytraBot.Settings.Settings.Diagonal = false;
			} else {
				me.bebeli555.ElytraBot.Settings.Settings.Diagonal = true;
			}
			if (me.bebeli555.ElytraBot.Settings.Settings.Diagonal == true) {
				mc.player.sendMessage(new TextComponentString(ChatFormatting.BLUE + "Diagonal: " + ChatFormatting.GREEN
						+ me.bebeli555.ElytraBot.Settings.Settings.Diagonal));
			} else {
				mc.player.sendMessage(new TextComponentString(ChatFormatting.BLUE + "Diagonal: " + ChatFormatting.RED
						+ me.bebeli555.ElytraBot.Settings.Settings.Diagonal));
			}

			e.setCanceled(true);
			try {
				me.bebeli555.ElytraBot.Settings.Settings.Default();
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
			if (me.bebeli555.ElytraBot.Settings.Settings.AutoEat == true) {
				Settings.AutoEat = false;
				mc.player.sendMessage(new TextComponentString(ChatFormatting.BLUE + "AutoEat: " + ChatFormatting.RED
						+ me.bebeli555.ElytraBot.Settings.Settings.AutoEat));
			} else {
				Settings.AutoEat = true;
				mc.player.sendMessage(new TextComponentString(ChatFormatting.BLUE + "AutoEat: " + ChatFormatting.GREEN
						+ me.bebeli555.ElytraBot.Settings.Settings.AutoEat));
			}

			try {
				me.bebeli555.ElytraBot.Settings.Settings.Default();
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