package me.bebeli555.ElytraBot.Gui;

import java.util.ArrayList;
import org.lwjgl.input.Mouse;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.bebeli555.ElytraBot.Settings.SettingsInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;

public class Node {
	//Object containing information of the GUI Shits.
	
	public int x;
	public int y;
	public int x2;
	public int y2;
	public int textX;
	public int textY;
	public int[] coordMultiplier = new int[2];
	public String text;
	public float textScale;
	public int color;
	public boolean isClickable;
	public boolean parent;
	public String id;
	public String mode;
	public double value;
	public boolean allowDoubleValue;
	public boolean isKeybind;
	public boolean isExtendable;
	public boolean isExtended;
	public boolean isAnExtend;
	public String stringValue = "";
	public boolean shouldResetStringValue;
	public boolean typeable;
	public ChatFormatting textColor;
	public ArrayList<String> Tips = new ArrayList<String>();
	public ArrayList<String> Group = new ArrayList<String>();
	public ArrayList<Node> Extends = new ArrayList<Node>();
	
	public static Minecraft mc = Minecraft.getMinecraft();
	public static ArrayList<Node> Nodes = new ArrayList<Node>();
	
 	public Node(double x, double y, boolean clickable, String mode) {
 		this.x = (int)x;
 		this.y = (int)y;
 		this.x2 = this.x + 110;
 		this.y2 = this.y + 20;
 		this.isClickable = clickable;
 		this.color = 0x36393fff;
 		this.textX = this.x;
 		this.textY = this.y + 5;
 		this.mode = mode;
 		Nodes.add(this);
 	}
 	
 	public void setCoordMultiplier(int x, int y) {
 		coordMultiplier[0] = x;
 		coordMultiplier[1] = y;
 	}
 	
  	public void extend(int times) {			
 		isExtended = !isExtended;
 		for (int i = 0; i < Nodes.size(); i++) {
 			Node n = Nodes.get(i);
 			if (this.getX() == n.getX() && n.getY() > this.getY()) {
 				if (n.mode.contains(Gui.currentMode) || n.mode.equals("null")) {
					if (n.isAnExtend) {
						if (this.Extends.contains(n)) {
							continue;
						}
					}
					if (isExtended) {
						n.setCoordMultiplier(n.coordMultiplier[0], n.coordMultiplier[1] + ((20 * times) + 3));
					} else {
						n.setCoordMultiplier(n.coordMultiplier[0], n.coordMultiplier[1] + -((20 * times) + 3));
					}
 				}
 			}
 		}
 	}
 	
 	public void resetValue() {
 		shouldResetStringValue = true;
 		try {
 			value = 0;
 			stringValue = String.valueOf(SettingsInfo.getSetting(id).defaultValue);
 		} catch (Exception e) {
 			value = 0;
 			stringValue = "";
 		}
 		Gui.setValue(this);
 	}
 	
 	public void setTypeAble(boolean type) {
 		this.typeable = type;
 	}
 	
 	public void setStringValue(String val) {
 		this.stringValue = val.trim();
 	}
 	
 	public void addTip(String tip) {
 		Tips.add(tip);
 	}
 	
 	public void addToGroup(String id) {
 		Group.add(id);
 		getNodeFromID(id).Group.add(this.getID());
 	}
 	
 	public void setParent(boolean parent) {
 		this.parent = parent;
 	}
 	
 	public void setX2(int x) {
 		this.x2 = x;
 	}
 	
 	public void setY2(int y) {
 		this.y2 = y;
 	}
 	
 	public void setColor(int color) {
 		this.color = color;
 	}
 	
 	public void setText(String text, ChatFormatting color, float scale) {
 		this.text = text;
 		this.id = text;
 		this.textScale = scale;
 		this.textColor = color;
 		if (scale != 1F) {
 			textY = (int) (textY / scale);
 		}
 	}
 	
 	public void centerText(String text, float scale) {
 		//Automatically put it in the center of the block
 		int stringWidth = mc.fontRenderer.getStringWidth(text);
 		int boxWidth = this.getX2() - this.getX();
 		
 		//Yeah math!
 		this.textX = (int) ((((boxWidth / 2) + this.x) - (stringWidth / 2)) / scale);
 	}
 	
 	public String getRealText() {
 		if (this.isTypeable()) {
 			if (this.stringValue.isEmpty() && this.isKeybind || !this.isKeybind && this.stringValue.equals("-1")) {
 				return this.text + "NONE";
 			} else {
 				return this.text + this.getStringValue();
 			}
 		}
 		
 		return this.text;
 	}
 	
 	public int getX() {
 		return x + coordMultiplier[0];
 	}
 	
 	public int getY() {
 		return y + coordMultiplier[1];
 	}
 	
 	public int getX2() {
 		return x2 + coordMultiplier[0];
 	}
 	
 	public int getY2() {
 		return y2 + coordMultiplier[1];
 	}
 	
 	public String getText() {
 		return text;
 	}
 	
 	public int getColor() {
 		return color;
 	}
 	
 	public int getTextX() {
 		return (int) (textX + (coordMultiplier[0] / this.getScale()));
 	}
 	
 	public int getTextY() {
 		return (int) (textY + (coordMultiplier[1] / this.getScale()));
 	}
 	
 	public float getScale() {
 		return textScale;
 	}
 	
	public boolean isClickable() {
 		return isClickable;
 	}
	
	public ChatFormatting getTextColor() {
		return textColor;
	}
	
	public String getMode() {
		return mode;
	}
	
 	public String getStringValue() {
 		if (this.allowDoubleValue || this.isKeybind) {
 			return this.stringValue;
 		} else {
 			try {
 				return String.valueOf((int)Double.parseDouble(stringValue));
 			}catch (Exception e) {
 				return stringValue;
 			}
 		}
 	}
	
	public boolean isTypeable() {
		return typeable;
	}
	
	public boolean isInRightMode() {
		if (this.mode.equals("null")) {
			return true;
		}
		
		if (this.mode.contains(Gui.currentMode) && isAnyModeActivated()) {
			return true;
		}
		return false;
	}
	
	public boolean isClickInArea(int x, int y) {
		if (this.getX() < x && this.getX2() > x && this.getY() < y && this.getY2() > y) {
			return true;
		}
		return false;
	}
	
	public String getID() {
		return id;
	}
	
	public void setClicked() {
		for (int i = 0; i < Nodes.size(); i++) {
			if (Nodes.get(i).isTypeable()) {
				Nodes.get(i).parent = false;
			}
		}
		
 		if (isClickable) {
 			if (Mouse.isButtonDown(1) && !this.isAnExtend) {
 				if (this.isExtendable) {
 					this.extend(this.Extends.size());
 				} else {
 					mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.RED + "This Setting cannot be extended!"));
 				}
 				return;
 			}
 			
			if (parent) {
				parent = false;
				if (isMode(id)) {
					setModesCoordinate(id);
					Gui.currentMode = "";
				}
			} else {
				for (int i = 0; i < Group.size(); i++) {
					getNodeFromID(Group.get(i)).parent = false;
					getNodeFromID(Group.get(i)).setTextColor();
				}
				
				parent = true;
				shouldResetStringValue = true;
				if (isMode(id)) {
					setModesCoordinate(id);
					Gui.currentMode = id;
				}
			}
 			setTextColor();
 		}
	}
	
	public static boolean isMode(String id) {
		for (int i = 0; i < SetNodes.Modes.length; i++) {
			if (SetNodes.Modes[i] == getNodeFromID(id)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isAnyModeActivated() {
		for (int i = 0; i < SetNodes.Modes.length; i++) {
			if (SetNodes.Modes[i].parent) {
				return true;
			}
		}
		return false;
	}
	
	public void setTextColor() {
 		if (this.isClickable() && !this.isTypeable()) {
 			if (parent) {
 				this.textColor = ChatFormatting.GREEN;
 			} else {
 				this.textColor = ChatFormatting.RED;
 			}
 		}
	}
	
	public boolean isBelowClickable() {
		for (int i = 0; i < Node.Nodes.size(); i++) {
			Node n = Node.Nodes.get(i);
			if (n.isInRightMode()) {
				if (this.getX() == n.getX() && this.getY2() == n.getY() && n.isClickable) {
					if (!n.isAnExtend || n.isAnExtend && n.getExtendParent().isExtended) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean isAboveClickable() {
		for (int i = 0; i < Node.Nodes.size(); i++) {
			Node n = Node.Nodes.get(i);
			if (n.isInRightMode()) {
				if (this.getX() == n.getX() && this.getY() == n.getY2() && n.isClickable) {
					if (!n.isAnExtend || n.isAnExtend && n.getExtendParent().isExtended) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static void setModesCoordinate(String mode) {
		for (int i = 0; i < Nodes.size(); i++) {
			Node n = Nodes.get(i);
			n.isExtended = false;
			n.setCoordMultiplier(0, 0);
			
			if (n.mode.equals("null")) {				
				if (isAnyModeActivated()) {
					if (mode.equals("Games"))
						n.setCoordMultiplier(130, 120);
					 else if (mode.equals("Highway"))
						n.setCoordMultiplier(120, 110);
					 else if (mode.equals("Overworld"))
						n.setCoordMultiplier(120, 40);
				}
			}
		}
		
		if (mode == "Overworld") {
			getNodeFromID("ElytraFly:").setCoordMultiplier(0, -80);
			getNodeFromID("Speed").setCoordMultiplier(0, -80);
			getNodeFromID("GlideSpeed").setCoordMultiplier(0, -80);
		}
	}
	
	public Node getExtendParent() {
		for (int i = 0; i < Nodes.size(); i++) {
			Node n = Nodes.get(i);
			if (n.Extends.contains(this)) {
				return n;
			}
		}
		return null;
	}
	
	public static Node getNodeFromID(String id) {
		for (int i = 0; i < Nodes.size(); i++) {
			if (Nodes.get(i).getID().toLowerCase().equals(id.toLowerCase())) { 
				return Nodes.get(i);
			}
		}
		return null;
	}
}
