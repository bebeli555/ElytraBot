package me.bebeli555.ElytraBot.Overworld;

import java.util.ArrayList;
import java.util.Objects;

import com.mojang.realmsclient.gui.ChatFormatting;

import me.bebeli555.ElytraBot.ElytraFly;
import me.bebeli555.ElytraBot.Gui;
import me.bebeli555.ElytraBot.TakeOff;
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
	
	public static boolean MoveStraight = false;
	public static boolean MoveRight = false;
	
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

				if (Settings.OpenTerrainX == -1) {
					mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.RED + "You need to set the coordinates correctly Set them in the GUI!"));
					Gui.TurnOff();
					return;
				}

				if (Settings.OpenTerrainZ == -1) {
					mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.RED + "You need to set the coordinates correctly Set them in the GUI!"));
					Gui.TurnOff();
					return;
				}

				// Remove blocks and slow down on manuvers
				SlowDown();

				// Calculate Estimated Time
				EstimatedTime();

				// Stay still if calculating path
				String message = me.bebeli555.ElytraBot.Overworld.GetPath.WhereToGo();
				if (toggle == false) {
					return;
				}
				status = message;
				if (message.contains("Calculating Path")) {
					MoveOn = false;
					me.bebeli555.ElytraBot.Main.SetMotion(0, -(Settings.GlideSpeed / 10000f), 0);
					return;
				}

				// Control elytrafly
				MoveOn = true;
				ElytraFly.FlyMinus = 0;
				ManuverSpeed = Settings.FlySpeed;
				if (message.contains("Forward")) {
					SetMove(false, true);
				} else if (message.contains("Backwards")) {
					ElytraFly.FlyMinus = Settings.FlySpeed * 2;
					SetMove(false, true);
				} else if (message.contains("Right")) {
					SetMove(true, false);
				} else if (message.contains("Left")) {
					SetMove(false, false);
				}
			}
		} catch (Exception e1) {
			System.out.println("Exception in Overworld main class Elytrabot");
			System.out.println(e1.getLocalizedMessage());
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
	
	public static void SetMove(boolean Right, boolean Straight) {
		MoveRight = Right;
		MoveStraight = Straight;
	}
	
	//Flight method
	public static void Flight(double speed, boolean right, boolean straight, double y) {
		if (mc.player.isElytraFlying()) {
			if (straight == true) {
				if (direction.equals(EnumFacing.NORTH)) {
					me.bebeli555.ElytraBot.Main.SetMotion(0, y, -speed);
				} else if (direction.equals(EnumFacing.WEST)) {
					me.bebeli555.ElytraBot.Main.SetMotion(-speed, y, 0);
				} else if (direction.equals(EnumFacing.EAST)) {
					me.bebeli555.ElytraBot.Main.SetMotion(speed, y, 0);
				} else if (direction.equals(EnumFacing.SOUTH)) {
					me.bebeli555.ElytraBot.Main.SetMotion(0, y, speed);
				}
			} else if (right == true) {
				if (direction.equals(EnumFacing.NORTH)) {
					me.bebeli555.ElytraBot.Main.SetMotion(ManuverSpeed, y, 0);
				} else if (direction.equals(EnumFacing.WEST)) {
					me.bebeli555.ElytraBot.Main.SetMotion(0, y, ManuverSpeed);
				} else if (direction.equals(EnumFacing.EAST)) {
					me.bebeli555.ElytraBot.Main.SetMotion(0, y, ManuverSpeed);
				} else if (direction.equals(EnumFacing.SOUTH)) {
					me.bebeli555.ElytraBot.Main.SetMotion(ManuverSpeed, y, 0);
				}
			} else {
				if (direction.equals(EnumFacing.NORTH)) {
					me.bebeli555.ElytraBot.Main.SetMotion(-ManuverSpeed, y, 0);
				} else if (direction.equals(EnumFacing.WEST)) {
					me.bebeli555.ElytraBot.Main.SetMotion(0, y, -ManuverSpeed);
				} else if (direction.equals(EnumFacing.EAST)) {
					me.bebeli555.ElytraBot.Main.SetMotion(0, y, -ManuverSpeed);
				} else if (direction.equals(EnumFacing.SOUTH)) {
					me.bebeli555.ElytraBot.Main.SetMotion(-ManuverSpeed, y, 0);
				}
			}
		}
	}
	
	public static void SlowDown() {
		if (ShouldSlow()) {
			Settings.FlySpeed = 1;
		} else {
			Settings.FlySpeed = me.bebeli555.ElytraBot.Main.FlySpeed;
			RemoveFromPath();
		}
	}
	
	public static boolean ShouldSlow() {
		try {
			BlockPos Test = null;
			for (int i = 0; i < 10; i++) {
				if (Test == null) {
					Test = me.bebeli555.ElytraBot.Overworld.GetPath.Path.get(0);
				}
				
				//Slow down
				if (me.bebeli555.ElytraBot.Overworld.GetPath.Path.get(i).getX() != Test.getX()) {
					if (me.bebeli555.ElytraBot.Overworld.GetPath.Path.get(i).getZ() != Test.getZ()) {
						return true;
					}
				}
			}
			return false;
		} catch (IndexOutOfBoundsException e) {
			return true;
		}
	}
	
	public static void RemoveFromPath() {
		BlockPos Pos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
		ArrayList<BlockPos> OpenPositions = new ArrayList<BlockPos>();
		OpenPositions.add(new BlockPos(Pos.add(1, 0, 0)));
		OpenPositions.add(new BlockPos(Pos.add(-1, 0, 0)));
		OpenPositions.add(new BlockPos(Pos.add(0, 0, 1)));
		OpenPositions.add(new BlockPos(Pos.add(0, 0, -1)));

		for (int i = 0; i < OpenPositions.size(); i++) {
			if (me.bebeli555.ElytraBot.Overworld.GetPath.Path.contains(OpenPositions.get(i))) {
				if (Settings.FlySpeed > 1) {
					me.bebeli555.ElytraBot.Overworld.GetPath.Path.remove(OpenPositions.get(i));
					
				}
				return;
			}
		}
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
			if (BlocksASec < 2) {
				Settings.FlySpeed = 1;
			}
			
			Latest = Player;
		}
		
		//Set time
		if (BlocksASec != 0) {
			int X = (int) mc.player.posX - Settings.OpenTerrainX;
			int Z = (int) mc.player.posZ - Settings.OpenTerrainZ;
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
