package me.bebeli555.ElytraBot.Games;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;

public class TetrisNode {
	//Object that is the 1 block of a tetris thing.
	
	static Minecraft mc = Minecraft.getMinecraft();
	public static ArrayList<TetrisNode> Nodes = new ArrayList<TetrisNode>();
	private ArrayList<TetrisNode> FamilyNodes = new ArrayList<TetrisNode>();
	public static int multiplier = 10;
	private int x, y;
	private int color;
	private String shape;
	private int rotation;
	private int downpos;
	
	public TetrisNode(int x, int y) {
		this.x = x;
		this.y = y;
		this.rotation = 1;
		Nodes.add(this);
	}
	
	public void RemoveFromList() {
		Nodes.remove(this);
	}
	
	public void SetColor(int color) {
		for (int i = 0; i < this.FamilyNodes.size(); i++) {
			this.FamilyNodes.get(i).color = color;
		}
	}
	
	public int GetColor() {
		return color;
	}
	
	public void SetShape(String shape) {
		for (int i = 0; i < this.FamilyNodes.size(); i++) {
			this.FamilyNodes.get(i).shape = shape;
		}
	}
	
	public String GetShape() {
		return shape;
	}
	
	public void AddToFamily(TetrisNode Node) {
		FamilyNodes.add(Node);
	}
	
	public void SetX(int x) {
		this.x = x;
	}
	
	public void SetY(int y) {
		this.y = y;
	}
	
	public int GetX () {
		return x;
	}
	
	public int GetY () {
		return y;
	}
	
	public void MoveDown() {
		for (int i = 0; i < this.FamilyNodes.size(); i++) {
			TetrisNode Node = this.FamilyNodes.get(i);
			Node.SetY(this.FamilyNodes.get(i).GetY() + multiplier);
		}
	}
	
	public void MoveRight() {
		for (int i = 0; i < this.FamilyNodes.size(); i++) {
			TetrisNode Node = this.FamilyNodes.get(i);
			Node.SetX(Node.GetX() + multiplier);
		}
	}
	
	public void MoveLeft() {
		for (int i = 0; i < this.FamilyNodes.size(); i++) {
			TetrisNode Node = this.FamilyNodes.get(i);
			Node.SetX(Node.GetX() - multiplier);
		}
	}
	
	public boolean CanMoveRight() {
		for (int i = 0; i < this.FamilyNodes.size(); i++) {
			TetrisNode Node = GetNode(this.FamilyNodes.get(i).GetX() + 10, this.FamilyNodes.get(i).GetY());
			if (Node != null && !IsInFamily(Node)) {
				return false;
			}
			if (this.FamilyNodes.get(i).GetX() > Tetris.toX - 20) {
				return false;
			}	
		}
		return true;
	}
	
	public boolean CanMoveLeft() {
		for (int i = 0; i < this.FamilyNodes.size(); i++) {
			TetrisNode Node = GetNode(this.FamilyNodes.get(i).GetX() - 10, this.FamilyNodes.get(i).GetY());
			if (Node != null && !IsInFamily(Node)) {
				return false;
			}
			if (this.FamilyNodes.get(i).GetX() < Tetris.fromX + 10) {
				return false;
			}	
		}
		return true;
	}
	
	public ArrayList<TetrisNode> GetFamily(){
		return FamilyNodes;
	}
	
	public void MoveCompletelyDown() {
		for (int i = 0; i < 150; i++) {
			if (this.CanGoDown()) {
				this.MoveDown();
			}
		}
	}
	
	public boolean IsInFamily(TetrisNode node) {
		if (FamilyNodes.contains(node)) return true;

		return false;
	}
	
	public boolean CanGoDown() {		
		for (int i = 0; i < this.FamilyNodes.size(); i++) {
			TetrisNode Node = GetNode(this.FamilyNodes.get(i).GetX(), this.FamilyNodes.get(i).GetY() + multiplier);		
			if (this.FamilyNodes.get(i).GetY() > Tetris.toY - 10) return false;
			
			if (Node != null && !IsInFamily(Node)) return false;
		}
		return true;
	}

	public static TetrisNode GetNode(int x, int y) {
		for (int i = 0; i < Nodes.size(); i++) {
			TetrisNode Node = Nodes.get(i);
			if (Node.GetX() == x && Node.GetY() == y) return Node;
		}
		return null;
	}
	
	public void ClearFamily() {
		for (int i = 0; i < this.FamilyNodes.size(); i++) {

			if (!this.FamilyNodes.get(i).equals(this)) Nodes.remove(this.FamilyNodes.get(i));
		}
		this.FamilyNodes.clear();
	}
	
	public void SetDownPosition() {
		int OldY = this.FamilyNodes.get(0).GetY();
		int OldY2 = this.FamilyNodes.get(1).GetY();
		int OldY3 = this.FamilyNodes.get(2).GetY();
		int OldY4 = this.FamilyNodes.get(3).GetY();
		
		for (int i2 = 0; i2 < 100; i2++) {
			for (int i3 = 0; i3 < this.FamilyNodes.size(); i3++) {
				this.FamilyNodes.get(i3).SetY(this.FamilyNodes.get(i3).GetY() + 10);
			}
			int NodeY = this.y;
			
			if (!this.CanGoDown()) {
				for (int i = 0; i < this.FamilyNodes.size(); i++) {
					this.FamilyNodes.get(i).downpos = (this.FamilyNodes.get(i).GetY() - this.GetY()) + NodeY;
				}
				this.FamilyNodes.get(0).SetY(OldY);
				this.FamilyNodes.get(1).SetY(OldY2);
				this.FamilyNodes.get(2).SetY(OldY3);
				this.FamilyNodes.get(3).SetY(OldY4);
				return;
			}
		}
	}
	
	public int GetDownPosition() {
		return downpos;
	}
	
 	//Rotate tetris block
	public void Rotate() {
		String rot = this.GetShape();
		if (rot.equals("O")) {
			return;
		}
		this.rotation++;
		this.ClearFamily();
		this.FamilyNodes.add(this);

		//I
		if (rot.equals("I")) {
			if (this.rotation > 2) {
				this.rotation = 1;
			}
			
			if (rotation == 1) {
				this.FamilyNodes.add(new TetrisNode(x, y + 10));
				this.FamilyNodes.add(new TetrisNode(x, y + 20));
				this.FamilyNodes.add(new TetrisNode(x, y + 30));
			} else {
				this.FamilyNodes.add(new TetrisNode(x + 10, y));
				this.FamilyNodes.add(new TetrisNode(x + 20, y));
				this.FamilyNodes.add(new TetrisNode(x - 10, y));
			}
			this.SetColor(0xFF36EAFF);
		}
		
		//S
		if (rot.equals("S")) {
			if (this.rotation > 2) {
				this.rotation = 1;
			}
			
			if (rotation == 1) {
				this.FamilyNodes.add(new TetrisNode(x + 10, y));
				this.FamilyNodes.add(new TetrisNode(x, y + 10));
				this.FamilyNodes.add(new TetrisNode(x - 10, y + 10));
			} else {
				this.FamilyNodes.add(new TetrisNode(x, y + 10));
				this.FamilyNodes.add(new TetrisNode(x - 10, y));
				this.FamilyNodes.add(new TetrisNode(x - 10, y - 10));
			}
			this.SetColor(0xFFFF0009);
		}
		
		//Z
		if (rot.equals("Z")) {
			if (this.rotation > 2) {
				this.rotation = 1;
			}
			
			if (rotation == 1) {
				this.FamilyNodes.add(new TetrisNode(x, y + 10));
				this.FamilyNodes.add(new TetrisNode(x + 10, y + 10));
				this.FamilyNodes.add(new TetrisNode(x - 10, y));
			} else {
				this.FamilyNodes.add(new TetrisNode(x + 10, y));
				this.FamilyNodes.add(new TetrisNode(x + 10, y - 10));
				this.FamilyNodes.add(new TetrisNode(x, y + 10));
			}
			this.SetColor(0xFF00FF2B);
		}
		
		//L
		if (rot.equals("L")) {
			if (this.rotation > 4) {
				this.rotation = 1;
			}
			
			if (rotation == 1) {
				this.FamilyNodes.add(new TetrisNode(x, y + 10));
				this.FamilyNodes.add(new TetrisNode(x, y + 20));
				this.FamilyNodes.add(new TetrisNode(x + 10, y + 20));
			} else if (rotation == 2){
				this.FamilyNodes.add(new TetrisNode(x + 10, y));
				this.FamilyNodes.add(new TetrisNode(x - 10, y));
				this.FamilyNodes.add(new TetrisNode(x - 10, y + 10));
			} else if (rotation == 3){
				this.FamilyNodes.add(new TetrisNode(x - 10, y));
				this.FamilyNodes.add(new TetrisNode(x, y + 10));
				this.FamilyNodes.add(new TetrisNode(x, y + 20));
			} else{
				this.FamilyNodes.add(new TetrisNode(x + 10, y));
				this.FamilyNodes.add(new TetrisNode(x + 10, y - 10));
				this.FamilyNodes.add(new TetrisNode(x - 10, y));
			}
			this.SetColor(0xFFEC830C);
		}
		
		//J
		if (rot.equals("J")) {
			if (this.rotation > 4) {
				this.rotation = 1;
			}
			
			if (rotation == 1) {
				this.FamilyNodes.add(new TetrisNode(x, y + 10));
				this.FamilyNodes.add(new TetrisNode(x, y + 20));
				this.FamilyNodes.add(new TetrisNode(x - 10, y + 20));
			} else if (rotation == 2){
				this.FamilyNodes.add(new TetrisNode(x, y + 10));
				this.FamilyNodes.add(new TetrisNode(x + 10, y + 10));
				this.FamilyNodes.add(new TetrisNode(x + 20, y + 10));
			} else if (rotation == 3){
				this.FamilyNodes.add(new TetrisNode(x, y + 10));
				this.FamilyNodes.add(new TetrisNode(x, y - 10));
				this.FamilyNodes.add(new TetrisNode(x + 10, y - 10));
			} else{
				this.FamilyNodes.add(new TetrisNode(x + 10, y));
				this.FamilyNodes.add(new TetrisNode(x - 10, y));
				this.FamilyNodes.add(new TetrisNode(x + 10, y + 10));
			}
			this.SetColor(0xFFFF19EF);
		}
		
		//T
		if (rot.equals("T")) {
			if (this.rotation > 4) {
				this.rotation = 1;
			}
			
			if (rotation == 1) {
				this.FamilyNodes.add(new TetrisNode(x + 10, y));
				this.FamilyNodes.add(new TetrisNode(x - 10, y));
				this.FamilyNodes.add(new TetrisNode(x, y + 10));
			} else if (rotation == 2){
				this.FamilyNodes.add(new TetrisNode(x, y + 10));
				this.FamilyNodes.add(new TetrisNode(x, y - 10));
				this.FamilyNodes.add(new TetrisNode(x - 10, y));
			} else if (rotation == 3){
				this.FamilyNodes.add(new TetrisNode(x, y - 10));
				this.FamilyNodes.add(new TetrisNode(x + 10, y));
				this.FamilyNodes.add(new TetrisNode(x - 10, y));
			} else{
				this.FamilyNodes.add(new TetrisNode(x + 10, y));
				this.FamilyNodes.add(new TetrisNode(x, y + 10));
				this.FamilyNodes.add(new TetrisNode(x, y - 10));
			}
			this.SetColor(0xFF9100FF);
		}
	}
}
