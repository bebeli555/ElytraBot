package me.bebeli555.ElytraBot.Games;

import java.util.ArrayList;


public class TetrisNode {
	//Object that is the 1 block of a tetris thing.
	

	public static ArrayList<TetrisNode> Nodes = new ArrayList<>();
	private final ArrayList<TetrisNode> FamilyNodes = new ArrayList<>();
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

	
	public void SetColor(int color) {
		for (TetrisNode familyNode : this.FamilyNodes) {
			familyNode.color = color;
		}
	}
	
	public int GetColor() {
		return color;
	}
	
	public void SetShape(String shape) {
		for (TetrisNode familyNode : this.FamilyNodes) {
			familyNode.shape = shape;
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
		for (TetrisNode Node : this.FamilyNodes) {
			Node.SetY(Node.GetY() + multiplier);
		}
	}
	
	public void MoveRight() {
		for (TetrisNode Node : this.FamilyNodes) {
			Node.SetX(Node.GetX() + multiplier);
		}
	}
	
	public void MoveLeft() {
		for (TetrisNode Node : this.FamilyNodes) {
			Node.SetX(Node.GetX() - multiplier);
		}
	}
	
	public boolean CanMoveRight() {
		for (TetrisNode familyNode : this.FamilyNodes) {
			TetrisNode Node = GetNode(familyNode.GetX() + 10, familyNode.GetY());
			if (Node != null && !IsInFamily(Node)) {
				return false;
			}
			if (familyNode.GetX() > Tetris.toX - 20) {
				return false;
			}
		}
		return true;
	}
	
	public boolean CanMoveLeft() {
		for (TetrisNode familyNode : this.FamilyNodes) {
			TetrisNode Node = GetNode(familyNode.GetX() - 10, familyNode.GetY());
			if (Node != null && !IsInFamily(Node)) {
				return false;
			}
			if (familyNode.GetX() < Tetris.fromX + 10) {
				return false;
			}
		}
		return true;
	}
	
	public ArrayList<TetrisNode> GetFamily(){
		return FamilyNodes;
	}

	
	public boolean IsInFamily(TetrisNode node) { return FamilyNodes.contains(node); }


	public boolean CanGoDown() {
		for (TetrisNode familyNode : this.FamilyNodes) {
			TetrisNode Node = GetNode(familyNode.GetX(), familyNode.GetY() + multiplier);
			if (familyNode.GetY() > Tetris.toY - 10) return false;

			if (Node != null && !IsInFamily(Node)) return false;
		}
		return true;
	}

	public static TetrisNode GetNode(int x, int y) {
		for (TetrisNode Node : Nodes) {
			if (Node.GetX() == x && Node.GetY() == y) return Node;
		}
		return null;
	}
	
	public void ClearFamily() {
		for (TetrisNode familyNode : this.FamilyNodes) {

			if (!familyNode.equals(this)) Nodes.remove(familyNode);
		}
		this.FamilyNodes.clear();
	}
	
	public void SetDownPosition() {
		int OldY = this.FamilyNodes.get(0).GetY();
		int OldY2 = this.FamilyNodes.get(1).GetY();
		int OldY3 = this.FamilyNodes.get(2).GetY();
		int OldY4 = this.FamilyNodes.get(3).GetY();
		
		for (int i2 = 0; i2 < 100; i2++) {
			for (TetrisNode familyNode : this.FamilyNodes) {
				familyNode.SetY(familyNode.GetY() + 10);
			}
			int NodeY = this.y;
			
			if (!this.CanGoDown()) {
				for (TetrisNode familyNode : this.FamilyNodes) {
					familyNode.downpos = (familyNode.GetY() - this.GetY()) + NodeY;
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
