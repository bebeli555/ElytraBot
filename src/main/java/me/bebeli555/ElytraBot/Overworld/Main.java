package me.bebeli555.ElytraBot.Overworld;

import java.util.ArrayList;
import java.util.Objects;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.bebeli555.ElytraBot.Gui.Gui;
import me.bebeli555.ElytraBot.Highway.TakeOff;
import me.bebeli555.ElytraBot.Settings.Settings;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class Main {
	static Minecraft mc = Minecraft.getMinecraft();
	public static boolean toggle = false;
	public static boolean MoveOn = false;
	public static EnumFacing direction;
	public static boolean z = false;
	public static boolean x = false;
	public static String status;
	public static double ManuverSpeed = 0;
	static int delay = 0;
	static int delay2 = 0;
	static int delay3 = 0;
	static BlockPos Latest;
	static int BlocksASec = 0;
	
	public static String MoveDirection;
	
	public static int EstimatedHours = 0;
	public static int EstimatedMinutes = 0;
	public static int EstimatedSeconds = 0;
	
	@SubscribeEvent
	public void onUpdate(ClientTickEvent e) {
		try {
			if (toggle == true) {
				if (TakeOff.HasElytraEquipped()) {
					if (!mc.player.onGround) {
						if (!mc.player.isElytraFlying()) {
							Objects.requireNonNull(mc.getConnection()).sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
							return;
						}
					}
				}

				// Warning messages
				if (!mc.player.isElytraFlying()) {
					mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.RED + "You need to be flying... Read the Guide how to use this mode in the GUI!"));
					Gui.TurnOff();
					return;
				}

				if (Settings.getDouble("OverworldX") == -1) {
					mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.RED + "You need to set the coordinates correctly Set them in the GUI!"));
					Gui.TurnOff();
					return;
				}

				if (Settings.getDouble("OverworldZ") == -1) {
					mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.RED + "You need to set the coordinates correctly Set them in the GUI!"));
					Gui.TurnOff();
					return;
				}
				
				//Set max speed on manuvers.
				Settings.setValue("Speed", GetPath.SpeedToManuver());
				
				// Calculate Estimated Time
				EstimatedTime();

				RemoveFromPath();
				String message = me.bebeli555.ElytraBot.Overworld.GetPath.WhereToGo();
				status = message;
				if (toggle == false) {
					return;
				}

				// Stay still if calculating path
				if (GetPath.Path.isEmpty() || message.contains("Calculating Path")) {
					MoveOn = false;
					me.bebeli555.ElytraBot.Highway.Main.SetMotion(0, -(Settings.getDouble("GlideSpeed") / 10000f), 0);
					return;
				}
				
				// Control elytrafly
				MoveOn = true;
				if (message.contains("X") || message.contains("Z")) {
					MoveDirection = message;
				}
				
				if (GetPath.LastCalc < 5) {
					Settings.setValue("Speed", 1);
				}
			}
		} catch (Exception e1) {
			System.out.println("Exception in Overworld main class Elytrabot");
			e1.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void TurnOff() {
		toggle = false;
	}
	
	public static void Check() {
		toggle = true;
		direction = mc.player.getHorizontalFacing();
		if (direction.equals(EnumFacing.NORTH)) {
			x = true;
			z = false;
		} else if (direction.equals(EnumFacing.WEST)) {
			z = true;
			x = false;
		} else if (direction.equals(EnumFacing.EAST)) {
			z = true;
			x = false;
		} else if (direction.equals(EnumFacing.SOUTH)) {
			x = true;
			z = false;
		}
	}
	
	//Flight method
	public static void Flight(double speed, double y, String Move) {
		if (mc.player.isElytraFlying()) {
			if (Move.equals("+X")) {
				me.bebeli555.ElytraBot.Highway.Main.SetMotion(speed, y, 0);
			} else if (Move.equals("-X")) {
				me.bebeli555.ElytraBot.Highway.Main.SetMotion(-speed, y, 0);
			} else if (Move.equals("+Z")) {
				me.bebeli555.ElytraBot.Highway.Main.SetMotion(0, y, speed);
			} else if (Move.equals("-Z")) {
				me.bebeli555.ElytraBot.Highway.Main.SetMotion(0, y, -speed);
			}
		}
	}
	
	public static void RemoveFromPath() {
		for (int i = 0; i < 10; i++) {
			try {
				BlockPos Player = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
				
				if (!IsConnectedToPath(GetPath.Path.get(i))) {
					GetPath.Path.remove(GetPath.Path.get(i));
				}
				
				//Remove other already went past spots
				if (status.equals("+X")) {
					BlockPos Check = new BlockPos(Player.add(-1, 0, 0));
					GetPath.Path.remove(Check);
				} else if (status.equals("-X")) {
					BlockPos Check = new BlockPos(Player.add(1, 0, 0));
					GetPath.Path.remove(Check);
				} else if (status.equals("+Z")) {
					BlockPos Check = new BlockPos(Player.add(0, 0, -1));
					GetPath.Path.remove(Check);
				} else if (status.equals("-Z")) {
					BlockPos Check = new BlockPos(Player.add(0, 0, 1));
					GetPath.Path.remove(Check);
				}
				
			} catch (IndexOutOfBoundsException e) {
				
			}
		}
	}
	
	public static boolean IsConnectedToPath(BlockPos Pos) {
		ArrayList<BlockPos> Check = new ArrayList<BlockPos>();
		Check.add(new BlockPos(Pos.add(1, 0, 0)));
		Check.add(new BlockPos(Pos.add(-1, 0, 0)));
		Check.add(new BlockPos(Pos.add(0, 0, 1)));
		Check.add(new BlockPos(Pos.add(0, 0, -1)));
		
		for (int i = 0; i < Check.size(); i++) {
			if (GetPath.Path.contains(Check.get(i))) {
				return true;
			}
		}
		return false;
	}
	
	public static void EstimatedTime() {
		//Get Blocks a second player is travelling
		BlockPos Player = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
		delay++;
		if (delay > 19) {
			delay = 0;
			if (Latest == null) {
				Latest = Player;
				return;
			}
			int X = Player.getX() - Latest.getX();
			int Z = Player.getZ() - Latest.getZ();
			BlocksASec = Math.abs(X) + Math.abs(Z);
			Latest = Player;
		}
		
		//Set time
		if (BlocksASec != 0) {
			int X = (int) ((int) mc.player.posX - Settings.getDouble("OverworldX"));
			int Z = (int) ((int) mc.player.posZ - Settings.getDouble("OverworldZ"));
			int Blocks = Math.abs(X) + Math.abs(Z);
			Blocks = Math.abs(Blocks);

			int seconds = 0;
			seconds = (Blocks / BlocksASec) / 2;
			int p1 = seconds % 60;
			int p2 = seconds / 60;
			int p3 = p2 % 60;
			p2 = p2 / 60;

			EstimatedHours = p2;
			EstimatedMinutes = p3;
			EstimatedSeconds = p1;
		}
	}
}
