package com.bebeli555.ElytraBot.PathFinding;

import java.util.ArrayList;

import com.bebeli555.ElytraBot.Main;
import com.bebeli555.ElytraBot.Renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class GetPath {
	static Minecraft mc = Minecraft.getMinecraft();
	public static int LoopAmount = 35; //Amount of blocks it will check sideways
	static ArrayList<BlockPos> BestPaths = new ArrayList<BlockPos>();
	static BlockPos BestPath;
	
	//Checks if the block in the BlockPosition is solid or not
	public static boolean IsSolid(BlockPos pos) {
		if (mc.world.getBlockState(pos).getBlock() != Blocks.STANDING_BANNER && mc.world.getBlockState(pos).getBlock() != Blocks.WALL_BANNER) {
			if (mc.world.getBlockState(pos).getBlock() != Blocks.STANDING_SIGN && mc.world.getBlockState(pos).getBlock() != Blocks.WALL_SIGN) {
				if (mc.world.getBlockState(pos).getMaterial().isSolid()) {
					return true;
				}
			}
		}
		return false;
	}
	
	//Check if path to clear block is possible
	public static boolean CanGoToBlock(BlockPos pos) {
		BlockPos Player = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
		
		for (int i = 0; i < LoopAmount; i++) {
			BlockPos Check;
			if (Main.z == true) {
				if (pos.getZ() < Player.getZ()) {
					Check = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ - i);
				} else {
					Check = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ + i);
				}
			} else {
				if (pos.getX() < Player.getX()) {
					Check = new BlockPos(mc.player.posX - i, mc.player.posY, mc.player.posZ);
				} else {
					Check = new BlockPos(mc.player.posX + i, mc.player.posY, mc.player.posZ);
				}
			}
			
			
			if (IsSolid(Check) == true) {
				return false;
			}
			if (Check.getZ() == pos.getZ() || Check.getX() == pos.getX()) {
				return true;
			}
		}
		return false;
	}
	
	//Get the best path and return it in wierd string formation
	public static String GetBestPath() {
		if (ShouldManuver(false) == true) {
			for (int i = -LoopAmount; i < LoopAmount; i++) {
				BlockPos Player = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
				BlockPos BestPath;

				if (Main.z == true) {
					if (Main.direction.equals(EnumFacing.EAST)) {
						BestPath = new BlockPos(Player.add(1, 0, i));
					} else {
						BestPath = new BlockPos(Player.add(-1, 0, i));
					}
				} else {
					if (Main.direction.equals(EnumFacing.SOUTH)) {
						BestPath = new BlockPos(Player.add(i, 0, 1));
					} else {
						BestPath = new BlockPos(Player.add(i, 0, -1));
					}
				}

				if (IsSolid(BestPath) == false) {
					if (CanGoToBlock(BestPath) == true) {
						if (IsNextBlocksClear(BestPath) == true) {
							BestPaths.add(BestPath);
						}
					}
				}
			}
			
			if (BestPaths.isEmpty()) {
				return "null";
			} else {
				//Get the closest best path
				for (int i = 0; i < BestPaths.size(); i++) {
					if (BestPath == null) {
						BestPath = BestPaths.get(i);
					} else {
						if (IsCloser(BestPaths.get(i), BestPath)) {
							BestPath = BestPaths.get(i);
						}
					}
				}
				
				BlockPos Player = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
				double X = BestPath.getX();
				double Z = BestPath.getZ();
				
				//Render best path as a green something
				Renderer.DrawGreenBox(BestPath);
				
				if (Main.direction.equals(EnumFacing.NORTH)) {
					if (BestPath.getX() > Player.getX()) {
						ResetPath();
						return "left--" + X;
					} else {
						ResetPath();
						return "right-" + X;
					}
				} else if (Main.direction.equals(EnumFacing.WEST)) {
					if (BestPath.getZ() > Player.getZ()) {
						ResetPath();
						return "left--" + Z;
					} else {
						ResetPath();
						return "right-" + Z;
					}
				} else if (Main.direction.equals(EnumFacing.EAST)) {
					if (BestPath.getZ() > Player.getZ()) {
						ResetPath();
						return "left--" + Z;
					} else {
						ResetPath();
						return "right-" + Z;
					}
				} else if (Main.direction.equals(EnumFacing.SOUTH)) {
					if (BestPath.getX() > Player.getX()) {
						ResetPath();
						return "left--" + X;
					} else {
						ResetPath();
						return "right-" + X;
					}
				}
			}
		}
		return "null -No need for manuver";
	}
	
	//Check is there a need for a manuver
	public static boolean ShouldManuver(boolean Straight) {
		BlockPos Player = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
		BlockPos Check;
		
		if (Main.direction.equals(EnumFacing.NORTH)) {
			Check = Player.add(0, 0, -1);
			if (IsSolid(Check)) {
				return true;
			}
		} else if (Main.direction.equals(EnumFacing.WEST)) {
			Check = Player.add(-1, 0, 0);
			if (IsSolid(Check)) {
				return true;
			}
		} else if (Main.direction.equals(EnumFacing.EAST)) {
			Check = Player.add(1, 0, 0);
			if (IsSolid(Check)) {
				return true;
			}
		} else {
			Check = Player.add(0, 0, 1);
			if (IsSolid(Check)) {
				return true;
			}
		}

		if (WillBeTrapped()) {
			return true;
		}
		
		if (Straight == true) {
			if (IsNextBlocksClear(Check)) {
				return false;
			} else {
				return true;
			}
		}
		
		return false;
	}
	
	//Checks if the pos its going to pathfind through is actually clear and can continue its journey
	public static boolean IsNextBlocksClear(BlockPos Pos) {
		BlockPos Forward1;
		BlockPos Forward2;
		BlockPos Side1;
		BlockPos Side2;
		
		if (Main.direction.equals(EnumFacing.NORTH)) {
			Forward1 = Pos.add(0, 0, -1);
			Forward2 = Pos.add(0, 0, -2);
			Side1 = Pos.add   (1, 0, -1);
			Side2 = Pos.add   (-1, 0, -1);
		} else if (Main.direction.equals(EnumFacing.WEST)) {
			Forward1 = Pos.add(-1, 0, 0);
			Forward2 = Pos.add(-2, 0, 0);
			Side1 = Pos.add   (-1, 0, 1);
			Side2 = Pos.add   (-1, 0, -1);
		} else if (Main.direction.equals(EnumFacing.EAST)) {
			Forward1 = Pos.add(1, 0, 0);
			Forward2 = Pos.add(2, 0, 0);
			Side1 = Pos.add   (1, 0, 1);
			Side2 = Pos.add   (1, 0, -1);
		} else {
			Forward1 = Pos.add(0, 0, 1);
			Forward2 = Pos.add(0, 0, 2);
			Side1 = Pos.add	  (1, 0, 1);
			Side2 = Pos.add   (-1, 0, 1);
		}

		//If 2 upcoming blocks are clear we can assume the path is clear
		if (!IsSolid(Forward1) && !IsSolid(Forward2)) {
			return true;
		}
		
		// If only 1 upcoming block is clear but some side is then we going with that
		if (!IsSolid(Forward1)) {
			if (!IsSolid(Side1) || !IsSolid(Side2)) {
				return true;
			}
		}
		
		return false;
	}
	
	//Checks if going straight will lead to a 1x1 trap. and prevent that as it only checks blocks 1 ahead of you
	public static boolean WillBeTrapped() {
		BlockPos Player = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
		BlockPos Forward;
		BlockPos Forward2;
		BlockPos Side;
		BlockPos Side2;
		
		if (Main.direction.equals(EnumFacing.NORTH)) {
			Forward = Player.add(0, 0, -2);
			Forward2 = Player.add(0, 0, -1);
			Side = Player.add(1, 0, -1);
			Side2 = Player.add(-1, 0, -1);
		} else if (Main.direction.equals(EnumFacing.WEST)) {
			Forward = Player.add(-2, 0, 0);
			Forward2 = Player.add(-1, 0, 0);
			Side = Player.add(-1, 0, -1);
			Side2 = Player.add(-1, 0, 1);
		} else if (Main.direction.equals(EnumFacing.EAST)) {
			Forward = Player.add(2, 0, 0);
			Forward2 = Player.add(1, 0, 0);
			Side = Player.add(1, 0, -1);
			Side2 = Player.add(1, 0, 1);
		} else {
			Forward = Player.add(0, 0, 2);
			Forward2 = Player.add(0, 0, 1);
			Side = Player.add(1, 0, 1);
			Side2 = Player.add(-1, 0, 1);
		}
		
		if (GetPath.IsSolid(Forward) && GetPath.IsSolid(Side) && GetPath.IsSolid(Side2) && GetPath.IsSolid(Forward2) == false) {
			return true;
		}
		
		return false;
	}
	
	public static boolean IsCloser(BlockPos Pos1, BlockPos Pos2) {
		double Pos1X = Pos1.getX();
		double Pos1Z = Pos1.getZ();
		double Pos2X = Pos2.getX();
		double Pos2Z = Pos2.getZ();
		double PlayerZ = mc.player.posZ;
		double PlayerX = mc.player.posX;
		
		if (Main.z == true) {
		     if (Math.abs(PlayerZ - Pos1Z) < Math.abs(PlayerZ - Pos2Z)) 
		         return true;
		     else
		        return false;
		} else {
		     if (Math.abs(PlayerX - Pos1X) < Math.abs(PlayerX - Pos2X)) 
		         return true;
		     else
		        return false;
		}
	}
	
	public static void ResetPath() {
		BestPath = null;
		BestPaths.clear();
	}
}

