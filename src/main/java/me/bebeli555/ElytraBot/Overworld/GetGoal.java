package me.bebeli555.ElytraBot.Overworld;

import java.util.ArrayList;
import java.util.Random;
import com.mojang.realmsclient.gui.ChatFormatting;

import me.bebeli555.ElytraBot.Gui.Gui;
import me.bebeli555.ElytraBot.PathFinding.AStar;
import me.bebeli555.ElytraBot.Settings.Settings;
import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

public class GetGoal {
	static Minecraft mc = Minecraft.getMinecraft();
	static ArrayList<BlockPos> PathsBeenAt = new ArrayList<BlockPos>();
	public static boolean NoPath = false;
	static int Randoms = 0;
	
	public static void CalculatePath() {
		GetPath.LastCalc = 0;
		BlockPos Start = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
		BlockPos Goal = new BlockPos(Settings.getDouble("OverworldX"), mc.player.posY, Settings.getDouble("OverworldZ"));
		
		if (PathsBeenAt.size() > 150) {
			PathsBeenAt.clear();
		}
		
		if (Randoms > 3) {
			NoPath = false;
			Randoms = 0;
		}

		if (NoPath) {
			ArrayList<BlockPos> Check = AStar.GetPath(Start, Goal, 250, false, true);
			Check = AStar.GetPath(Start, AStar.Closest, 350, false, true);
			if (Check.size() > 35) {
				if (PathsBeenAt.contains(Check.get(Check.size() - 1))) {
					GetPath.Path = Check;
					NoPath = false;
					return;
				}
			}
			Randoms++;
			GetPath.Path = RandomPath();
			return;
		}
		
		//Go normally
		ArrayList<BlockPos> Test = new ArrayList<BlockPos>();
		Test = AStar.GetPath(Start, Goal, 350, false, true);
		if (Test.size() > 3) {
			GetPath.Path = Test;
			return;
		}

		ArrayList<BlockPos> Closest = AStar.GetPath(Start, AStar.Closest, 450, false, true);
		if (Closest.size() < 2) {
			if (me.bebeli555.ElytraBot.Highway.GetPath.IsBlockInRenderDistance(Start)) {
				// Stop if goal is in render distance and no path found
				NoPath = true;
				if (IsBlockInRenderDistance(Goal)) {
					if (Test.isEmpty()) {
						TurnOffNoPath();
						return;
					}
				}
			}
		}
		
		PathsBeenAt.add(Closest.get(Closest.size() - 1));
		GetPath.Path = Closest;
	}
	
	public static boolean IsBlockInRenderDistance(BlockPos Pos) {
		return mc.world.getChunkFromBlockCoords(Pos).isLoaded();
	}
	
	public static void TurnOffNoPath() {
		Gui.TurnOff();
		mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.RED + "Goal is in render distance and no path found. Stopping"));
		BlockPos Player = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
		mc.world.playSound(Player, SoundEvents.BLOCK_ANVIL_DESTROY, SoundCategory.AMBIENT, 100.0f, -5.0F, true);
	}
	

	public static ArrayList<BlockPos> RandomPath() {
		BlockPos Player = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
		ArrayList<BlockPos> RandomPath = new ArrayList<BlockPos>();
		
		Random rand = new Random();
		int random = rand.nextInt(4);
		
		if (random == 0) {
			//+X
			RandomPath = AStar.GetPath(Player, new BlockPos(Player.add(350, 0, 0)), 250, false, true);
			RandomPath = AStar.GetPath(Player, AStar.Closest, 350, false, true);
		} else if (random == 1) {
			//-X
			RandomPath = AStar.GetPath(Player, new BlockPos(Player.add(-350, 0, 0)), 250, false, true);
			RandomPath = AStar.GetPath(Player, AStar.Closest, 350, false, true);
		} else if (random == 2) {
			//+Z
			RandomPath = AStar.GetPath(Player, new BlockPos(Player.add(0, 0, 350)), 250, false, true);
			RandomPath = AStar.GetPath(Player, AStar.Closest, 350, false, true);
		} else {
			//-Z
			RandomPath = AStar.GetPath(Player, new BlockPos(Player.add(0, 0, -350)), 250, false, true);
			RandomPath = AStar.GetPath(Player, AStar.Closest, 350, false, true);
		}
		
		return RandomPath;
	}
}
