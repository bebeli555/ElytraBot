package me.bebeli555.ElytraBot.Overworld;

import java.util.ArrayList;

import com.mojang.realmsclient.gui.ChatFormatting;

import me.bebeli555.ElytraBot.Gui;
import me.bebeli555.ElytraBot.Renderer;
import me.bebeli555.ElytraBot.Settings.Settings;
import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

public class GetPath {
	static Minecraft mc = Minecraft.getMinecraft();
	public static ArrayList<BlockPos> Path = new ArrayList<BlockPos>();
	public static int LastCalc = 0;
	public static int delay = 0;
	public static int LastSize = 0;
	
	public static String WhereToGo() {
		LastCalc++;
		if (GetGoal.Calculating == true) {
			LastCalc = 0;
		}
		String BestPath = "Forward";
		BlockPos Player = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
		
		//Remove player from path
		if (Path.contains(Player)) {
			Path.remove(Player);
		}
		
		//Calculate new path if empty
		if (Path.isEmpty()) {
			if (LastCalc > 10) {
				GetGoal.CalculatePath();
			}
			ReachedDestination();
			return "Calculating Path";
		}
		
		//Calculate new path if not same Y
		if ((int)mc.player.posY != Path.get(0).getY()) {
			if (LastCalc > 15) {
				GetGoal.CalculatePath();
			}
			return "Calculating Path";
		}
		
		//Calculate new path if stuck
		delay++;
		if (delay > 10) {
			delay = 0;
			if (Path.size() == LastSize) {
				GetGoal.CalculatePath();
				return "Calculating Path";
			}
			LastSize = Path.size();
		}
		
		Renderer.IsRendering = true;
		Renderer.PositionsGreen = Path;
		
		BlockPos Next = Path.get(0);
		if (Main.direction.equals(EnumFacing.NORTH)) {
			if (Player.getX() - Next.getX() > 0) {
				BestPath = "Left";
			} else if (Player.getX() - Next.getX() < 0) {
				BestPath = "Right";

			}

			if (Player.getZ() - Next.getZ() > 0) {
				BestPath = "Forward";

			} else if (Player.getZ() - Next.getZ() < 0) {
				BestPath = "Backwards";

			}
		} else if (Main.direction.equals(EnumFacing.WEST)) {
			if (Player.getZ() - Next.getZ() > 0) {
				BestPath = "Left";
			} else if (Player.getZ() - Next.getZ() < 0) {
				BestPath = "Right";
			}

			if (Player.getX() - Next.getX() > 0) {
				BestPath = "Forward";
			} else if (Player.getX() - Next.getX() < 0) {
				BestPath = "Backwards";
			}
		} else if (Main.direction.equals(EnumFacing.EAST)) {
			if (Player.getZ() - Next.getZ() > 0) {
				BestPath = "Left";
			} else if (Player.getZ() - Next.getZ() < 0) {
				BestPath = "Right";
			}

			if (Player.getX() - Next.getX() > 0) {
				BestPath = "Backwards";
			} else if (Player.getX() - Next.getX() < 0) {
				BestPath = "Forward";
			}
		} else {
			if (Player.getX() - Next.getX() > 0) {
				BestPath = "Left";
			} else if (Player.getX() - Next.getX() < 0) {
				BestPath = "Right";
			}

			if (Player.getZ() - Next.getZ() > 0) {
				BestPath = "Backwards";
			} else if (Player.getZ() - Next.getZ() < 0) {
				BestPath = "Forward";
			}
		}

		return BestPath;
 	}
	
	public static void ReachedDestination() {
		int X = (int)mc.player.posX - Settings.OpenTerrainX;
		int Z = (int)mc.player.posX - Settings.OpenTerrainX;
		int Final = Math.abs(X) + Math.abs(Z);
		
		if (Final < 5) {
			Gui.TurnOff();
			mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.RED + "Yay we reached the destination!"));
			//Play epic anvil destroy sound!
			if (Settings.OpenLogout == false) {
				BlockPos Player = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
				mc.world.playSound(Player, SoundEvents.BLOCK_ANVIL_DESTROY, SoundCategory.AMBIENT, 100.0f, -5.0F, true);
			}
			
			if (Settings.OpenLogout) {
				mc.world.sendQuittingDisconnectingPacket();
			}
		}
		
	}
}
