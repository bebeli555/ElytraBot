package me.bebeli555.ElytraBot.PathFinding;

import java.util.ArrayList;

import me.bebeli555.ElytraBot.Main;
import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

public class AStar {
	static Minecraft mc = Minecraft.getMinecraft();

	static ArrayList<BlockPos> Open = new ArrayList<BlockPos>();
	public static ArrayList<BlockPos> Closed = new ArrayList<BlockPos>();
	public static ArrayList<BlockPos> FinalPath = new ArrayList<BlockPos>();
	public static BlockPos Closest;
	public static BlockPos Furthest;
	static BlockPos Start;
	static BlockPos Goal;
	static BlockPos Current;
	static BlockPos Final;
	public static int Fails = 0;

	public static ArrayList<BlockPos> GetPath(BlockPos start, BlockPos goal, int LoopAmount, boolean Overworld) {
		Reset();
		Main.status = "Calculating Path";
		Start = start;
		Goal = goal;
		BlockPos Player = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);

		// Add startup pos to Open list if its empty
		if (Open.isEmpty()) {
			Closed.add(Player);
			AddToOpen(Player);
		}

		if (Current == null) {
			Current = Player;
		}

		// Get the best path using A*
		for (int i2 = 0; i2 < LoopAmount; i2++) {
			// Check if were in the goal
			if (Current.getX() == Goal.getX()) {
				if (Current.getZ() == Goal.getZ()) {
					FinalPath = Closed;
					//Just spam this to make sure theres no fucked up spots
					for (int i = 0; i < 100; i++) {
						if (IsPathFixed()) {
							break;
						} else {
							FixPath();
						}
					}
					return FinalPath;
				}
			}
			// Get the lowest F cost in open list and put that to closed list
			int LowestF = Integer.MAX_VALUE;
			for (int i = 0; i < Open.size(); i++) {
				if (FCost(Open.get(i)) < LowestF) {
					if (IsCollided(Open.get(i)) < 2) {
						LowestF = FCost(Open.get(i));
						Current = Open.get(i);
					}
				}
			}
			LowestF = Integer.MAX_VALUE;

			// Add them to the list and stuff
			Closed.add(Current);
			Open.remove(Current);
			AddToOpen(Current);
			
			//Closest
			if (Closest == null) {
				Closest = Current;
			} else {
				if (FCost(Current) < FCost(Closest)) {
					Closest = Current;
					Fails = 0;
				} else {
					Fails++;
				}
			}
			
			if (Overworld == true) {
				if (Fails > 15) {
					return new ArrayList<BlockPos>();
				}
			}
			
			//Furthest
			if (Furthest == null) {
				Furthest = Current;
			} else {
				if (FCost(Current) > FCost(Furthest)) {
					Furthest = Current;
				}
			}
		}
		return new ArrayList<BlockPos>();
	}

	// Calculates the F cost of the put blockposition
	public static int FCost(BlockPos current) {
		// H cost
		double dx = Goal.getX() - current.getX();
		double dy = Goal.getZ() - current.getZ();
		double h = Math.sqrt(dx * dx + dy * dy);

		// G cost
		double dx2 = Start.getX() - current.getX();
		double dy2 = Start.getZ() - current.getZ();
		double g = Math.sqrt(Math.abs(dx2) + Math.abs(dy2));

		return (int) g + (int) h;
	}

	// Add the surrounding blocks to the open list IF their clear
	public static void AddToOpen(BlockPos Pos) {
		ArrayList<BlockPos> OpenPositions = new ArrayList<BlockPos>();
		OpenPositions.add(new BlockPos(Pos.add(1, 0, 0)));
		OpenPositions.add(new BlockPos(Pos.add(-1, 0, 0)));
		OpenPositions.add(new BlockPos(Pos.add(0, 0, 1)));
		OpenPositions.add(new BlockPos(Pos.add(0, 0, -1)));

		for (int i = 0; i < OpenPositions.size(); i++) {
			if (!GetPath.IsSolid(OpenPositions.get(i))) {
				if (!Open.contains(OpenPositions.get(i))) {
					if (GetPath.IsBlockInRenderDistance(OpenPositions.get(i))) {
						Open.add(OpenPositions.get(i));
					}
				}
			}
		}
	}

	// Checks if the closed thing would be collided with other closed things
	public static int IsCollided(BlockPos Pos) {
		int collides = 0;
		BlockPos Side1 = new BlockPos(Pos.add(1, 0, 0));
		BlockPos Side2 = new BlockPos(Pos.add(-1, 0, 0));
		BlockPos Side3 = new BlockPos(Pos.add(0, 0, 1));
		BlockPos Side4 = new BlockPos(Pos.add(0, 0, -1));

		if (Closed.contains(Side1)) {
			collides++;
		}

		if (Closed.contains(Side2)) {
			collides++;
		}

		if (Closed.contains(Side3)) {
			collides++;
		}

		if (Closed.contains(Side4)) {
			collides++;
		}

		return collides;
	}

	// Checks if the Pos is connected to the other pos
	public static boolean IsConnected(BlockPos From, BlockPos To) {
		BlockPos Side1 = new BlockPos(From.add(1, 0, 0));
		BlockPos Side2 = new BlockPos(From.add(-1, 0, 0));
		BlockPos Side3 = new BlockPos(From.add(0, 0, 1));
		BlockPos Side4 = new BlockPos(From.add(0, 0, -1));

		if (From.getX() == To.getX() && From.getZ() == To.getZ()) {
			return true;
		}

		if (Side1.getX() == To.getX() && Side1.getZ() == To.getZ()) {
			return true;
		}

		if (Side2.getX() == To.getX() && Side2.getZ() == To.getZ()) {
			return true;
		}

		if (Side3.getX() == To.getX() && Side3.getZ() == To.getZ()) {
			return true;
		}

		if (Side4.getX() == To.getX() && Side4.getZ() == To.getZ()) {
			return true;
		}

		return false;
	}

	public static BlockPos GetBestBlock(BlockPos Pos) {
		ArrayList<BlockPos> OpenPositions = new ArrayList<BlockPos>();
		OpenPositions.add(new BlockPos(Pos.add(1, 0, 0)));
		OpenPositions.add(new BlockPos(Pos.add(-1, 0, 0)));
		OpenPositions.add(new BlockPos(Pos.add(0, 0, 1)));
		OpenPositions.add(new BlockPos(Pos.add(0, 0, -1)));
		OpenPositions.add(new BlockPos(Pos.add(1, 0, 1)));
		OpenPositions.add(new BlockPos(Pos.add(-1, 0, 1)));
		OpenPositions.add(new BlockPos(Pos.add(1, 0, -1)));
		OpenPositions.add(new BlockPos(Pos.add(-1, 0, -1)));

		int LowestF = Integer.MAX_VALUE;
		BlockPos BestPath = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);

		for (int i = 0; i < OpenPositions.size(); i++) {
			if (FCost(OpenPositions.get(i)) < LowestF) {
				if (FinalPath.contains(OpenPositions.get(i))) {
					LowestF = FCost(OpenPositions.get(i));
					BestPath = OpenPositions.get(i);
				}
			}
		}

		return BestPath;
	}

	public static void FixPath() {
		//Fixes all the unused spots in the path that the A* Algorithm gives you
		//By checking if the block has only 1 block path next to it
		//and if its not the start or goal position then removing that block
		for (int i = 0; i < FinalPath.size(); i++) {
			BlockPos Pos = FinalPath.get(i);
			if (IsNotStart(Pos)) {
				if (IsNotGoal(Pos)) {
					if (IsCollided(Pos) == 1) {
						FinalPath.remove(Pos);
					}
				}
			}
		}
	}
	
	public static boolean IsPathFixed() {
		for (int i = 0; i < FinalPath.size(); i++) {
			BlockPos Pos = FinalPath.get(i);
			if (IsNotStart(Pos)) {
				if (IsNotGoal(Pos)) {
					if (IsCollided(Pos) == 1) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public static ArrayList<BlockPos> GetClosedList() {
		boolean Contains = false;
		ArrayList<BlockPos> Test = Closed;
		for (int i = 0; i < Test.size(); i++) {
			BlockPos Pos = Test.get(i);
			//Fix path
			if (Furthest.getX() != Pos.getX()) {
				if (Furthest.getZ() != Pos.getZ()) {
					if (Closest.getX() != Pos.getX()) {
						if (Closest.getZ() != Pos.getZ()) {
							if (IsCollided(Pos) == 1) {
								Test.remove(Pos);
							}
						}
 					}
				}
			}
			//Fix the other spots in this Closed list
			if (Pos.getX() == Closest.getX()) {
				if (Pos.getZ() == Closest.getZ()) {
					Contains = true;
				}
			}
			if (Contains == true) {
				Test.remove(Pos);
			}
		}
		return Test;
	}
	
	public static boolean IsNotStart(BlockPos Pos) {
		if (Pos.getX() == Start.getX()) {
			if (Pos.getZ() == Start.getZ()) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean IsNotGoal(BlockPos Pos) {
		if (Pos.getX() == Goal.getX()) {
			if (Pos.getZ() == Goal.getZ()) {
				return false;
			}
		}
		return true;
	}

	public static ArrayList<BlockPos> GetPath() {
		return Closed;
	}
	
	public static void Reset() {
		Open.clear();
		Closed.clear();
		Current = null;
		Final = null;
		Closest = null;
		Furthest = null;
		Fails = 0;
	}
}
