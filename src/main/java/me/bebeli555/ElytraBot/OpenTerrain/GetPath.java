package me.bebeli555.ElytraBot.OpenTerrain;

import java.util.ArrayList;

import com.mojang.realmsclient.gui.ChatFormatting;

import me.bebeli555.ElytraBot.ElytraFly;
import me.bebeli555.ElytraBot.Gui;
import me.bebeli555.ElytraBot.Main;
import me.bebeli555.ElytraBot.Renderer;
import me.bebeli555.ElytraBot.Settings.Settings;
import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.chunk.Chunk;

public class GetPath {
	static Minecraft mc = Minecraft.getMinecraft();
	public static ArrayList<BlockPos> Path = new ArrayList<BlockPos>();
	public static int LastCalc = 0;
	public static int delay = 0;
	public static String lastmove = "";
	
	public static String WhereToGo() {
		Renderer.IsRendering = true;
		Renderer.PositionsGreen = Path;
		LastCalc++;
		BlockPos Player = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
		
		//Remove player from path
		if (Path.contains(Player)) {
			Path.remove(Player);
		}
		
		//Calculate new path if empty
		if (Path.isEmpty()) {
			if (!IsReachedDestination()) {
				if (LastCalc > 10) {
					GetGoal.CalculatePath();
				}
			} else {
				ReachedDestination();
			}
			return "Calculating Path";
		}
		
		if (Path.size() < 15) {
			if (LastCalc > 65) {
				GetGoal.CalculatePath();
				return "Calculating Path";
			}
		}
		
		//Calculate new path if not same Y
		if ((int)mc.player.posY != Path.get(0).getY()) {
			if (LastCalc > 15) {
				GetGoal.CalculatePath();
			}
			return "Calculating Path";
		}
		
		BlockPos Next = Path.get(Path.size() - 1);
		BlockPos Side1 = new BlockPos(Player.add(1, 0, 0));
		BlockPos Side2 = new BlockPos(Player.add(-1, 0, 0));
		BlockPos Side3 = new BlockPos(Player.add(0, 0, 1));
		BlockPos Side4 = new BlockPos(Player.add(0, 0, -1));
		
		if (Side1.getX() == Next.getX()) {
			if (Side1.getZ() == Next.getZ()) {
				lastmove = "+X";
				return "+X";
			}
		}
		
		if (Side2.getX() == Next.getX()) {
			if (Side2.getZ() == Next.getZ()) {
				lastmove = "-X";
				return "-X";
			}
		}
		
		if (Side3.getX() == Next.getX()) {
			if (Side3.getZ() == Next.getZ()) {
				lastmove = "+Z";
				return "+Z";
			}
		}
		
		if (Side4.getX() == Next.getX()) {
			if (Side4.getZ() == Next.getZ()) {
				lastmove = "-Z";
				return "-Z";
			}
		}
		
		if (LastCalc > 20) {
			GetGoal.CalculatePath();
		}
		return lastmove;
 	}
	
	public static void ReachedDestination() {
		Gui.TurnOff();
		mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.GREEN + "Yay we reached the destination!"));
		//Play epic anvil destroy sound!
		if (Settings.OpenLogout == false) {
			BlockPos Player = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
			mc.world.playSound(Player, SoundEvents.BLOCK_ANVIL_DESTROY, SoundCategory.AMBIENT, 100.0f, -5.0F, true);
		}
		
		if (Settings.OpenLogout) {
			mc.world.sendQuittingDisconnectingPacket();
		}
	}
	
	public static boolean IsReachedDestination() {
		int X = (int)mc.player.posX - Settings.OpenTerrainX;
		int Z = (int)mc.player.posX - Settings.OpenTerrainX;
		int Final = Math.abs(X) + Math.abs(Z);
		
		if (Final < 5) {
			return true;
		}
		return false;
	}
	
	//Returns the best speed to reach the next block in 1 tick.
	//This is bcs if u go too fast u would overlap the block and then ur fucked.
	public static double SpeedToManuver() {
		try {
			BlockPos Player = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
			
			//Full speed if the next path is straight.
			if (Path.get(Path.size() - 1).getX() == Path.get(Path.size() - 2).getX() && Player.getX() == Path.get(Path.size() - 1).getX()) {
				return Main.FlySpeed;
			}
			
			if (Path.get(Path.size() - 1).getZ() == Path.get(Path.size() - 2).getZ() && Player.getZ() == Path.get(Path.size() - 1).getZ()) {
				return Main.FlySpeed;
			}
			
			//Otherwise return 1. Its the max speed u can go without overlapping.
			return 1;
		} catch (IndexOutOfBoundsException e) {
			return 1;
		}
	}
}
