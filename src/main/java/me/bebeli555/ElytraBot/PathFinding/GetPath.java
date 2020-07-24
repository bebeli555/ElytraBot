package me.bebeli555.ElytraBot.PathFinding;

import java.util.ArrayList;

import me.bebeli555.ElytraBot.Commands;
import me.bebeli555.ElytraBot.Main;
import me.bebeli555.ElytraBot.Renderer;
import me.bebeli555.ElytraBot.Settings.Settings;
import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

public class GetPath {
	static Minecraft mc = Minecraft.getMinecraft();
	public static ArrayList<BlockPos> Path = new ArrayList<BlockPos>();
	static int LastCalculation = 0;
	static boolean FixPath = false;
	static int delay = 0;
	static int delay2 = 0;
	static double X = 0;
	static double Z = 0;
	
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
	
	//Tells elytrabot what to do
	public static String WhereToGo() {
		try {
			LastCalculation++;
			String BestPath = "Null";
			BlockPos Player = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
			BlockPos Start = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
			if (Path.isEmpty()) {
				if (LastCalculation > 5) {
					ReCalculatePath(false);
				}
			}

			if ((int) mc.player.posY != Path.get(0).getY()) {
				if (LastCalculation > 5) {
					ReCalculatePath(false);
				}
			}

			// Calculate more path
			if (Path.size() < 20) {
				if (LastCalculation > 10) {
					if (IsPathStraight()) {
						ReCalculatePath(false);
					}
				}
			}

			if (!IsPlayerMoving()) {
				ReCalculatePath(false);
			}

			// If player is far from path then recalculate it
			int PlayerX = Math.abs((int) mc.player.posX);
			int PlayerZ = Math.abs((int) mc.player.posZ);
			int PathX = Math.abs(Path.get(0).getX());
			int PathZ = Math.abs(Path.get(0).getZ());

			if (Math.abs(PlayerX - PathX) > 10) {
				if (LastCalculation > 15) {
					ReCalculatePath(false);
				}
			}
			if (Math.abs(PlayerZ - PathZ) > 10) {
				if (LastCalculation > 15) {
					ReCalculatePath(false);
				}
			}

			if (Path.contains(Player)) {
				Path.remove(Player);
			}

			GetPath.RemovePassedSpots();
			BlockPos Next = Path.get(0);
			Renderer.IsRendering = true;
			Renderer.PositionsGreen = Path;
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
		}catch (IndexOutOfBoundsException e) {
			return "Calculating Path";
		}
 	}
	
	public static BlockPos GetGoal() {
		BlockPos Player = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
		BlockPos Goal = null;
		for (int i = 1; i < 170; i++) {
			//Gets the best goal to use in the pathfinding.
			if (Main.direction.equals(EnumFacing.NORTH)) {
				if (i == 1) {
					Goal = new BlockPos(Main.StartupPosX, mc.player.posY, mc.player.posZ - 200);
				}
				if (!IsBlockInRenderDistance(Goal)) {
					Goal = new BlockPos(Main.StartupPosX, mc.player.posY, mc.player.posZ - (200 - i));
				} else {
					Goal = new BlockPos(Goal.add(0, 0, -3));
					break;
				}
			} else if (Main.direction.equals(EnumFacing.WEST)) {
				if (i == 1) {
					Goal = new BlockPos(mc.player.posX - 200, mc.player.posY, Main.StartupPosZ);
				}
				if (!IsBlockInRenderDistance(Goal)) {
					Goal = new BlockPos(mc.player.posX - (200 - i), mc.player.posY, Main.StartupPosZ);
				} else {
					Goal = new BlockPos(Goal.add(-3, 0, 0));
					break;
				}
			} else if (Main.direction.equals(EnumFacing.EAST)) {
				if (i == 1) {
					Goal = new BlockPos(mc.player.posX + 200, mc.player.posY, Main.StartupPosZ);
				}
				if (!IsBlockInRenderDistance(Goal)) {
					Goal = new BlockPos(mc.player.posX + (200 - i), mc.player.posY, Main.StartupPosZ);
				} else {
					Goal = new BlockPos(Goal.add(3, 0, 0));
					break;
				}
			} else {
				if (i == 1) {
					Goal = new BlockPos(Main.StartupPosX, mc.player.posY, mc.player.posZ + 200);
				}
				if (!IsBlockInRenderDistance(Goal)) {
					Goal = new BlockPos(Main.StartupPosX, mc.player.posY, mc.player.posZ + (200 - i));
				} else {
					Goal = new BlockPos(Goal.add(0, 0, 3));
					break;
				}
			}
		}
		
		if (GetPath.IsSolid(Goal)) {
			for (int i = 0; i < 40; i++) {
				if (GetPath.IsSolid(Goal)) {
					if (i < 10) {
						Goal = new BlockPos(Goal.add(i, 0, 0));
					} else if (i < 20) {
						Goal = new BlockPos(Goal.add(0, 0, i - 10));
					} else if (i < 30) {
						Goal = new BlockPos(Goal.add(0, 0, -(i - 20)));
					} else {
						Goal = new BlockPos(Goal.add(-(i - 30), 0, 0));
					}
				} else {
					break;
				}
			}
		}
		
		return Goal;
	}
	
	public static void ReCalculatePath(boolean Continue) {
		ArrayList<BlockPos> Array = new ArrayList<BlockPos>();
		LastCalculation = 0;
		BlockPos Goal = GetGoal();
		BlockPos Player = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
		Array = AStar.GetPath(Player, Goal, 300, false);
		if (Array.isEmpty()) {
			BlockPos Goal2 = AStar.Closest;
			Array = AStar.GetPath(Player, Goal2, 300, false);

			// Activate baritone
			if (Player.getX() == Goal2.getX()) {
				if (Player.getZ() == Goal2.getZ()) {
					if (IsBlockInRenderDistance(Player)) {
						if (Settings.UseBaritone) {
							Main.ActivateUseBaritone();
						} else {
							Main.NoUsebaritoneMessage();
						}
						return;
					}
				}
			}
		}
		
		if (Array.isEmpty()) {
			if (IsBlockInRenderDistance(Player)) {
				if (Settings.UseBaritone) {
					Main.ActivateUseBaritone();
				} else {
					Main.NoUsebaritoneMessage();
				}
			}
		}
		
		Path = Array;
		Main.CantContinue = false;
	}
	
	public static boolean ShouldSlowDown(ArrayList<BlockPos> Path) {
		try {
			BlockPos Player = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
			BlockPos Current = Path.get(0);
			for (int i = 1; i < 5; i++) {
				BlockPos Next = Path.get(i);
				if (Current.getX() != Next.getX()) {
					if (Current.getZ() != Next.getZ()) {
						return true;
					}
				}
			}

			if ((int) mc.player.posX != Current.getX()) {
				if ((int) mc.player.posZ != Current.getZ()) {
					return true;
				}
			}
			
			return false;
		}catch (Exception e) {
			return false;
		}
	}
	
	public static void RemovePassedSpots() {
		//Remove already passed spots from the path
		BlockPos Player = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
		if (Main.status == "Forward" || Main.status == "Backwards") {
			if (Main.direction.equals(EnumFacing.NORTH)) {
				if (Main.status.equals("Forward")) {
					BlockPos Remove = new BlockPos(Player.add(0, 0, 1));
					GetPath.Path.remove(Remove);
				} else {
					BlockPos Remove = new BlockPos(Player.add(0, 0, -1));
					GetPath.Path.remove(Remove);
				}
			} else if (Main.direction.equals(EnumFacing.WEST)) {
				if (Main.status.equals("Forward")) {
					BlockPos Remove = new BlockPos(Player.add(1, 0, 0));
					GetPath.Path.remove(Remove);
				} else {
					BlockPos Remove = new BlockPos(Player.add(-1, 0, 0));
					GetPath.Path.remove(Remove);
				}
			} else if (Main.direction.equals(EnumFacing.EAST)) {
				if (Main.status.equals("Forward")) {
					BlockPos Remove = new BlockPos(Player.add(-1, 0, 0));
					GetPath.Path.remove(Remove);
				} else {
					BlockPos Remove = new BlockPos(Player.add(1, 0, 0));
					GetPath.Path.remove(Remove);
				}
			} else if (Main.direction.equals(EnumFacing.SOUTH)) {
				if (Main.status.equals("Forward")) {
					BlockPos Remove = new BlockPos(Player.add(0, 0, -1));
					GetPath.Path.remove(Remove);
				} else {
					BlockPos Remove = new BlockPos(Player.add(0, 0, 1));
					GetPath.Path.remove(Remove);
				}
			}
		}
	}
	
	public static boolean IsBlockInRenderDistance(BlockPos Pos) {
		if (mc.world.getChunkFromBlockCoords(Pos).isLoaded()) {
			return true;
		}
		return false;
	}
	
	public static boolean IsPlayerMoving() {
		if (mc.player.posX == X && mc.player.posZ == Z) {
			delay++;
			if (delay > 10) {
				delay = 0;
				return false;
			}
		} else {
			delay = 0;
		}
		
		X = mc.player.posX;
		Z = mc.player.posZ;
		return true;
	}
	
	public static boolean IsPathStraight() {
		int X = -1;
		int Z = -1;
		
		for (int i = 0; i < Path.size(); i++) {
			X = Path.get(0).getX();
			Z = Path.get(0).getZ();
			
			if (Path.get(i).getX() != X) {
				if (Path.get(i).getZ() != Z) {
					return false;
				}
			}
		}
		
		if (Path.size() < 10) {
			return false;
		}
		
		return true;
	}
}

