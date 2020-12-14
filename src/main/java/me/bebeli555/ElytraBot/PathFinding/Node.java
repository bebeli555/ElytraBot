package me.bebeli555.ElytraBot.PathFinding;

import java.util.ArrayList;
import net.minecraft.util.math.BlockPos;

public class Node {
	public static ArrayList<Node> Nodes = new ArrayList<Node>();
	private BlockPos node;
	private BlockPos parent;
	private double cost;
	
	public Node(BlockPos pos) {
		this.node = pos;
	}
	
	public BlockPos GetParent() {
		return parent;
	}
	
	public void SetParent(BlockPos pos) {
		this.parent = pos;
	}
	
	public BlockPos GetNode() {
		return node;
	}
	
	public void SetNode(BlockPos pos) {
		this.node = pos;
	}
	
	public void SetCost(double amount) {
		this.cost = amount;
	}
	
	public double GetCost() {
		return this.cost;
	}
	
	public static Node GetNodeFromBlockpos(BlockPos Pos) {
		for (Node n : Nodes) {
			if (n.GetNode().equals(Pos)) {
				return n;
			}
		}
		return null;
	}
}
