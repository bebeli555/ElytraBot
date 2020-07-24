package me.bebeli555.ElytraBot.Overworld;

import java.util.ArrayList;

import me.bebeli555.ElytraBot.PathFinding.AStar;
import me.bebeli555.ElytraBot.Settings.Settings;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;

public class GetGoal {
	static Minecraft mc = Minecraft.getMinecraft();
	public static boolean Calculating = false;
	
	public static void CalculatePath() {
		Calculating = true;
		GetPath.LastCalc = 0;
		ArrayList<BlockPos> Test = new ArrayList<BlockPos>();
		BlockPos Goal = new BlockPos(Settings.OpenTerrainX, mc.player.posY, Settings.OpenTerrainZ);
		BlockPos Start = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
		Test = AStar.GetPath(Start, Goal, 500, true);
		if (!Test.isEmpty()) {
			GetPath.Path = Test;
			Calculating = false;
			return;
		}
		
		GetPath.Path = AStar.GetPath(Start, AStar.Closest, 550, false);
		Calculating = false;
	}
	
	public static boolean IsBlockInRenderDistance(BlockPos Pos) {
		if (mc.world.getChunkFromBlockCoords(Pos).isLoaded()) {
			return true;
		}
		return false;
	}
}
