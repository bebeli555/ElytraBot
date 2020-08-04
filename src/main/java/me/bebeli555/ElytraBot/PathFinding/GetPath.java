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
			BlockPos Player = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
			
			//Leave gap when going fast. to prevent rubberbanding by 2b anticheat
			if (ShouldLeaveGap()) {
				AStar.LeaveGap = true;
			}
			
			if (Path.isEmpty()) {
				if (LastCalculation > 5) {
					ReCalculatePath(false);
				}
			}

			if ((int) mc.player.posY != Path.get(Path.size() - 1).getY()) {
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
			int PathX = Math.abs(Path.get(Path.size() - 1).getX());
			int PathZ = Math.abs(Path.get(Path.size() - 1).getZ());

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
			BlockPos Next = Path.get(Path.size() - 1);
			Renderer.IsRendering = true;
			Renderer.PositionsGreen = Path;
			if (Main.direction.equals(EnumFacing.NORTH)) {
				if (Player.getX() - Next.getX() > 0) {
					return "Left";
				} else if (Player.getX() - Next.getX() < 0) {
					return "Right";

				}

				if (Player.getZ() - Next.getZ() > 0) {
					return "Forward";

				} else if (Player.getZ() - Next.getZ() < 0) {
					return "Backwards";

				}
			} else if (Main.direction.equals(EnumFacing.WEST)) {
				if (Player.getZ() - Next.getZ() > 0) {
					return "Left";
				} else if (Player.getZ() - Next.getZ() < 0) {
					return "Right";
				}

				if (Player.getX() - Next.getX() > 0) {
					return "Forward";
				} else if (Player.getX() - Next.getX() < 0) {
					return "Backwards";
				}
			} else if (Main.direction.equals(EnumFacing.EAST)) {
				if (Player.getZ() - Next.getZ() > 0) {
					return "Left";
				} else if (Player.getZ() - Next.getZ() < 0) {
					return "Right";
				}

				if (Player.getX() - Next.getX() > 0) {
					return "Backwards";
				} else if (Player.getX() - Next.getX() < 0) {
					return "Forward";
				}
			} else {
				if (Player.getX() - Next.getX() > 0) {
					return "Left";
				} else if (Player.getX() - Next.getX() < 0) {
					return "Right";
				}

				if (Player.getZ() - Next.getZ() > 0) {
					return "Backwards";
				} else if (Player.getZ() - Next.getZ() < 0) {
					return "Forward";
				}
			}

			return "Calculating Path";
		}catch (IndexOutOfBoundsException e) {
			return "Calculating Path";
		}
 	}
	
	public static BlockPos GetGoal() {
		BlockPos Player = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
		BlockPos Goal = null;

		if (Main.direction.equals(EnumFacing.NORTH)) {
			Goal = new BlockPos(Main.StartupPosX, mc.player.posY, mc.player.posZ - 200);
		} else if (Main.direction.equals(EnumFacing.WEST)) {
			Goal = new BlockPos(mc.player.posX - 200, mc.player.posY, Main.StartupPosZ);
		} else if (Main.direction.equals(EnumFacing.EAST)) {
			Goal = new BlockPos(mc.player.posX + 200, mc.player.posY, Main.StartupPosZ);
		} else {
			Goal = new BlockPos(Main.StartupPosX, mc.player.posY, mc.player.posZ + 200);
		}
		
		ArrayList<BlockPos> Test = AStar.GetPath(Player, Goal, 250, true, false);
		
		return AStar.Closest;
	}
	
	public static void ReCalculatePath(boolean Continue) {
		BlockPos Player = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
		
		ArrayList<BlockPos> Array = new ArrayList<BlockPos>();
		LastCalculation = 0;
		BlockPos Goal = GetGoal();
		Array = AStar.GetPath(Player, Goal, 300, true, false);
		if (Array.isEmpty()) {
			BlockPos Goal2 = AStar.Closest;
			Array = AStar.GetPath(Player, Goal2, 300, true, false);

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
				return;
			}
		}
		
		Path = Array;
		Main.CantContinue = false;
		Path.remove(0);
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
					RemoveAll(0, 1);
				} else {
					BlockPos Remove = new BlockPos(Player.add(0, 0, -1));
					GetPath.Path.remove(Remove);
					RemoveAll(0, -1);
				}
			} else if (Main.direction.equals(EnumFacing.WEST)) {
				if (Main.status.equals("Forward")) {
					BlockPos Remove = new BlockPos(Player.add(1, 0, 0));
					GetPath.Path.remove(Remove);
					RemoveAll(1, 0);
				} else {
					BlockPos Remove = new BlockPos(Player.add(-1, 0, 0));
					GetPath.Path.remove(Remove);
					RemoveAll(-1, 0);
				}
			} else if (Main.direction.equals(EnumFacing.EAST)) {
				if (Main.status.equals("Forward")) {
					BlockPos Remove = new BlockPos(Player.add(-1, 0, 0));
					GetPath.Path.remove(Remove);
					RemoveAll(-1, 0);
				} else {
					BlockPos Remove = new BlockPos(Player.add(1, 0, 0));
					GetPath.Path.remove(Remove);
					RemoveAll(1, 0);
				}
			} else if (Main.direction.equals(EnumFacing.SOUTH)) {
				if (Main.status.equals("Forward")) {
					BlockPos Remove = new BlockPos(Player.add(0, 0, -1));
					GetPath.Path.remove(Remove);
					RemoveAll(0, -1);
				} else {
					BlockPos Remove = new BlockPos(Player.add(0, 0, 1));
					GetPath.Path.remove(Remove);
					RemoveAll(0, 1);
				}
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
			if (Path.contains(Check.get(i))) {
				return true;
			}
		}
		return false;
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
	
	public static boolean ShouldLeaveGap() {
		try {
			int X = -1;
			int Z = -1;
			
			for (int i = 0; i < 10; i++) {
				X = GetPath.Path.get(0).getX();
				Z = GetPath.Path.get(0).getZ();
				
				if (GetPath.Path.get(i).getX() != X) {
					if (GetPath.Path.get(i).getZ() != Z) {
						return false;
					}
				}
			}
			return true;
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
	}
	
	public static boolean ShouldGoMaxSpeed() {
		try {
			int X = -1;
			int Z = -1;
			
			for (int i = 0; i < 10; i++) {
				X = GetPath.Path.get(0).getX();
				Z = GetPath.Path.get(0).getZ();
				
				if (GetPath.Path.get(i).getX() != X) {
					if (GetPath.Path.get(i).getZ() != Z) {
						return false;
					}
				}
			}
			
			if (Path.size() > 10) {
				return true;
			}
			return false;
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
	}
	
	public static void RemoveAll(int X, int Z) {
		if (Main.FlySpeed > 1.99) {
			if (ShouldGoMaxSpeed()) {
				BlockPos Player = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
				for (int i = 0; i < 50; i++) {
					if (X == -1) {
						BlockPos Remove = new BlockPos(Player.add(-i, 0, 0));
						Path.remove(Remove);
					} else if (X == 1) {
						BlockPos Remove = new BlockPos(Player.add(i, 0, 0));
						Path.remove(Remove);
					} else if (Z == -1) {
						BlockPos Remove = new BlockPos(Player.add(0, 0, -i));
						Path.remove(Remove);
					} else if (Z == 1) {
						BlockPos Remove = new BlockPos(Player.add(0, 0, i));
						Path.remove(Remove);
					}
				}
			} else {
				Settings.FlySpeed = 1;
			}
		}
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

