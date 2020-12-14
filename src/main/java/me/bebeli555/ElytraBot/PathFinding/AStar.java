package me.bebeli555.ElytraBot.PathFinding;

import java.util.ArrayList;
import me.bebeli555.ElytraBot.Renderer;
import me.bebeli555.ElytraBot.Highway.GetPath;
import me.bebeli555.ElytraBot.Highway.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class AStar {
	static Minecraft mc = Minecraft.getMinecraft();

	static ArrayList<BlockPos> Open = new ArrayList<BlockPos>();
	public static ArrayList<BlockPos> Closed = new ArrayList<BlockPos>();
	public static ArrayList<BlockPos> FinalPath = new ArrayList<BlockPos>();
	public static ArrayList<BlockPos> LeftGaps = new ArrayList<BlockPos>();
	public static BlockPos Closest;
	public static BlockPos Furthest;
	static BlockPos Start;
	static BlockPos Goal;
	static BlockPos Current;
	static BlockPos Final;
	public static boolean LeaveGap = false;

	public static ArrayList<BlockPos> GetPath(BlockPos start, BlockPos goal, int LoopAmount, boolean HighwayMode, boolean IgnoreAirNextToSolid) {
		Reset();
		Main.status = "Calculating Path";
		Start = start;
		Goal = goal;
		BlockPos Player = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);

		// Add startup pos to Open list if its empty
		if (Open.isEmpty()) {
			Closed.add(Player);
			AddToOpen(Player, IgnoreAirNextToSolid, false);
		}

		if (Current == null) {
			Current = Player;
		}
		
		// Get the best path using A*
		for (int i2 = 0; i2 < LoopAmount; i2++) {
			// Check if were in the goal
			if (Current.getX() == Goal.getX()) {
				if (Current.getZ() == Goal.getZ()) {
					SetPath();
					return FinalPath;
				}
			}
			// Get the lowest F cost in open list and put that to closed list
			int LowestF = Integer.MAX_VALUE;
			for (BlockPos blockPos : Open) {
				if (FCost(blockPos) < LowestF) {
					LowestF = FCost(blockPos);
					Current = blockPos;
				}
			}
			LowestF = Integer.MAX_VALUE;
 
			// Add them to the list and stuff
			Closed.add(Current);
			Open.remove(Current);
			AddToOpen(Current, IgnoreAirNextToSolid, HighwayMode);
			
			//Closest
			if (Closest == null) {
				Closest = Current;
			} else {
				if (FCost(Current) < FCost(Closest)) {
					Closest = Current;
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
	public static void AddToOpen(BlockPos Pos, boolean IgnoreAirNextToSolid, boolean Highway) {
		ArrayList<BlockPos> OpenPositions = new ArrayList<BlockPos>();
		OpenPositions.add(new BlockPos(Pos.add(1, 0, 0)));
		OpenPositions.add(new BlockPos(Pos.add(-1, 0, 0)));
		OpenPositions.add(new BlockPos(Pos.add(0, 0, 1)));
		OpenPositions.add(new BlockPos(Pos.add(0, 0, -1)));

		for (BlockPos openPosition : OpenPositions) {
			if (!GetPath.IsSolid(openPosition)) {
				if (!Closed.contains(openPosition)) {
					if (GetPath.IsBlockInRenderDistance(openPosition)) {
						//Set parents
						double value = FCost(openPosition);
						Node n = Node.GetNodeFromBlockpos(openPosition);
						if (n == null) {
							n = new Node(openPosition);
						}

						if (!Open.contains(openPosition)) {
							n.SetCost(value);
							n.SetParent(Current);

							if (Highway && LeaveGap) {
								if (ShouldLeaveAsGap(openPosition)) {
									if (!LeftGaps.contains(openPosition)) {
										LeftGaps.add(openPosition);
									}
								}

								if (LeftGaps.size() > 1) {
									LeaveGap = false;
								}
							}

							if (IgnoreAirNextToSolid) {
								if (!HasBlockAround(openPosition)) {
									Open.add(openPosition);
								}
							}

							if (Highway) {
								if (!LeftGaps.contains(openPosition)) {
									Open.add(openPosition);
								}
							}
						} else {
							if (value > n.GetCost()) {
								n.SetCost(value);
								n.SetParent(Current);
							}
						}
						Node.Nodes.add(n);
					}
				}
			}
		}
	}
	
	public static boolean ShouldLeaveAsGap (BlockPos Pos) {
		BlockPos Check;
		if (Main.direction.equals(EnumFacing.NORTH)) {
			Check = new BlockPos(Pos.add(0, 0, -1));
		} else if (Main.direction.equals(EnumFacing.WEST)) {
			Check = new BlockPos(Pos.add(-1, 0, 0));
		} else if (Main.direction.equals(EnumFacing.EAST)) {
			Check = new BlockPos(Pos.add(1, 0, 0));
		} else {
			Check = new BlockPos(Pos.add(0, 0, 1));
		}

		return GetPath.IsSolid(Check);
	}
	
	//Checks if this not solid block has any solid blocks around it.
	public static boolean HasBlockAround(BlockPos Pos) {
		ArrayList<BlockPos> Positions = new ArrayList<BlockPos>();
		Positions.add(new BlockPos(Pos.add(1, 0, 0)));
		Positions.add(new BlockPos(Pos.add(-1, 0, 0)));
		Positions.add(new BlockPos(Pos.add(0, 0, 1)));
		Positions.add(new BlockPos(Pos.add(0, 0, -1)));
		Positions.add(new BlockPos(Pos.add(1, 0, 1)));
		Positions.add(new BlockPos(Pos.add(-1, 0, -1)));
		Positions.add(new BlockPos(Pos.add(-1, 0, 1)));
		Positions.add(new BlockPos(Pos.add(1, 0, -1)));
		
		Positions.add(new BlockPos(Pos.add(1, 1, 0)));
		Positions.add(new BlockPos(Pos.add(-1, 1, 0)));
		Positions.add(new BlockPos(Pos.add(0, 1, 1)));
		Positions.add(new BlockPos(Pos.add(0, 1, -1)));
		Positions.add(new BlockPos(Pos.add(1, 1, 1)));
		Positions.add(new BlockPos(Pos.add(-1, 1, -1)));
		Positions.add(new BlockPos(Pos.add(-1, 1, 1)));
		Positions.add(new BlockPos(Pos.add(1, 1, -1)));
		Positions.add(new BlockPos(Pos.add(0, 1, 0)));
		
		Positions.add(new BlockPos(Pos.add(1, -1, 0)));
		Positions.add(new BlockPos(Pos.add(-1, -1, 0)));
		Positions.add(new BlockPos(Pos.add(0, -1, 1)));
		Positions.add(new BlockPos(Pos.add(0, -1, -1)));
		Positions.add(new BlockPos(Pos.add(1, -1, 1)));
		Positions.add(new BlockPos(Pos.add(-1, -1, -1)));
		Positions.add(new BlockPos(Pos.add(-1, -1, 1)));
		Positions.add(new BlockPos(Pos.add(1, -1, -1)));
		Positions.add(new BlockPos(Pos.add(0, -1, 0)));

		for (BlockPos position : Positions) {
			if (GetPath.IsSolid(position)) return true;
		}
		return false;
	}

	public static void SetPath() {
		Renderer.PositionsGreen = Closed;
		try {
			// Backtrace path.
			Node n = Node.GetNodeFromBlockpos(Current);
			if (n == null) {
				n = Node.Nodes.get(Node.Nodes.size() - 1);
			}
			FinalPath.add(n.GetNode());

			while (n.GetParent() != null) {
				FinalPath.add(n.GetParent());
				n = Node.GetNodeFromBlockpos(n.GetParent());
			}
		} catch (NullPointerException e) {}
	}
	
	public static void Reset() {
		Open.clear();
		Closed = new ArrayList<BlockPos>();
		FinalPath = new ArrayList<BlockPos>();
		LeftGaps.clear();
		Node.Nodes.clear();
		Current = null;
		Final = null;
		Closest = null;
		Furthest = null;
	}
}
