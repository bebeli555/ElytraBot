package me.bebeli555.ElytraBot.Games;

import org.lwjgl.input.Keyboard;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.bebeli555.ElytraBot.Settings.Settings;
import me.bebeli555.ElytraBot.Settings.SettingsInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import scala.util.Random;

import java.util.Objects;

public class Tetris extends GuiScreen{
	//Tetris. A cool game, can be active while Snake is active lol, its fun.
	
	static Minecraft mc = Minecraft.getMinecraft();
	static int fromX, toX;
	static int fromY, toY;
	static TetrisNode CurrentNode;
	static int BeenDown = 0;
	public static boolean GameOver = true;
	public static int Score = 0;
	static long lastSec = 0;
	static long lastSecMove = 0;
	
	public static void DrawTetris(int drawX, int drawY) {
		//Set values
		fromX = drawX;
		toX = fromX + 150;
		fromY = drawY;
		toY = fromY + 250;
		
		//Draw main thingy.
		drawRect(fromX, fromY, toX, toY, 0xFF000000);

		GlStateManager.pushMatrix();
		GlStateManager.scale(3.0F, 3.0F, 3.0F);
		mc.fontRenderer.drawStringWithShadow(ChatFormatting.GREEN + "Tetris", 143, 40, 0xffff);
		GlStateManager.popMatrix();

		// Draw personal best
		GlStateManager.pushMatrix();
		GlStateManager.scale(1.7F, 1.7F, 1.7F);
		mc.fontRenderer.drawStringWithShadow(ChatFormatting.GOLD + "Personal Best: " + ChatFormatting.GREEN + (int) Settings.getDouble("TetrisBest"), 238, 240, 0xffff);
		GlStateManager.popMatrix();
		
		int Divided = 150 - (Score / 10);
		if (Divided < 10) {
			Divided = 10;
		}
		long sec = System.currentTimeMillis() / Divided;
		if (sec != lastSec && !GameOver) {
			//Create new node from the top.
			if (TetrisNode.Nodes.isEmpty() || !CurrentNode.CanGoDown()) {
				Score++;
				BlockPos Player = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
				mc.world.playSound(Player, SoundEvents.BLOCK_GLASS_PLACE, SoundCategory.AMBIENT, 10222.5f, 1.5f, true);
				RemoveLayer();
				SetShapes();
				
				if (!CurrentNode.CanGoDown()) {
					GameOver();
				}
			}
			
			if (CurrentNode.CanGoDown()) {
				CurrentNode.MoveDown();
			}
			lastSec = sec;
		}
		
		long sec2 = System.currentTimeMillis() / 40;
		if (sec2 != lastSecMove) {
			// Move automatically if holding key
			if (!GameOver && CurrentNode != null) {
				if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) || Keyboard.isKeyDown(Keyboard.KEY_LEFT) || Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
					BeenDown++;
					if (BeenDown > 3) {
						if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
							if (CurrentNode.CanMoveRight()) {
								CurrentNode.MoveRight();
							}
						} else if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
							if (CurrentNode.CanMoveLeft()) {
								CurrentNode.MoveLeft();
							}
						} else {
							if (CurrentNode.CanGoDown()) {
								CurrentNode.MoveDown();
							}
						}
					}
				} else {
					BeenDown = 0;
				}
			}
			lastSecMove = sec2;
		}
		
		//Draw the tetris blocks.
		for (int i = 0; i < TetrisNode.Nodes.size(); i++) {
			TetrisNode Node = TetrisNode.Nodes.get(i);
			drawRect(Node.GetX(), Node.GetY(), Node.GetX() + TetrisNode.multiplier, Node.GetY() - TetrisNode.multiplier, Node.GetColor());
		}
		
		//Draw white lines.
		drawWhiteLine(fromX, toX, fromY, toY);
		
		
		//Draw score
		GlStateManager.pushMatrix();
		GlStateManager.scale(1.25F, 1.25F, 1.25F);
		mc.fontRenderer.drawStringWithShadow(ChatFormatting.DARK_AQUA + "Score: " + ChatFormatting.GREEN + Score, 325, 125, 0xffff);
		GlStateManager.popMatrix();
		
		
		if (GameOver) {
			// GameOver Screen
			GlStateManager.pushMatrix();
			GlStateManager.scale(2.5F, 2.5F, 2.5F);
			mc.fontRenderer.drawStringWithShadow(ChatFormatting.RED + "Game Over!", 163, 70, 0xffff);
			mc.fontRenderer.drawStringWithShadow(ChatFormatting.RED + "Score = " + ChatFormatting.GREEN + Score, 165, 80, 0xffff);
			mc.fontRenderer.drawStringWithShadow(ChatFormatting.AQUA + "Controls:", 167, 95, 0xffff);
			drawRect(165, 140, 215, 155, 0xFFFFFFFF);
			GlStateManager.popMatrix();

			GlStateManager.pushMatrix();
			GlStateManager.scale(1.25F, 1.25F, 1.25F);
			mc.fontRenderer.drawStringWithShadow(ChatFormatting.GREEN + "Arrow UP: " + ChatFormatting.DARK_AQUA + "Rotate", 335, 215, 0xffff);
			mc.fontRenderer.drawStringWithShadow(ChatFormatting.GREEN + "Arrow Right: " + ChatFormatting.DARK_AQUA + "Move Right", 322, 225, 0xffff);
			mc.fontRenderer.drawStringWithShadow(ChatFormatting.GREEN + "Arrow Left: " + ChatFormatting.DARK_AQUA + "Move Left", 325, 235, 0xffff);
			mc.fontRenderer.drawStringWithShadow(ChatFormatting.GREEN + "Arrow Down: " + ChatFormatting.DARK_AQUA + "Drop Soft", 325, 245, 0xffff);
			mc.fontRenderer.drawStringWithShadow(ChatFormatting.GREEN + "Space: " + ChatFormatting.DARK_AQUA + "Drop Hard", 335, 255, 0xffff);
			GlStateManager.popMatrix();

			GlStateManager.pushMatrix();
			GlStateManager.scale(1.8F, 1.8F, 1.8F);
			mc.fontRenderer.drawStringWithShadow(ChatFormatting.GREEN + "Click to play", 233, 200, 0xffff);
			GlStateManager.popMatrix();
			return;
		}
		
		//Draw white marks at bottom.
		for (int i = 0; i < CurrentNode.GetFamily().size(); i++) {
			if (CurrentNode.CanGoDown()) {
				CurrentNode.SetDownPosition();
				TetrisNode Node = CurrentNode.GetFamily().get(i);
				int X = Node.GetX();
				int Y = Node.GetDownPosition();
				drawRect(X, Y, X + 1, Y - TetrisNode.multiplier, 0xFFFFFFFF);
				drawRect(X, Y, X + TetrisNode.multiplier, Y + 1, 0xFFFFFFFF);
				drawRect(X + TetrisNode.multiplier, Y, X + TetrisNode.multiplier + 1, Y - TetrisNode.multiplier, 0xFFFFFFFF);
				drawRect(X, Y - TetrisNode.multiplier, X + TetrisNode.multiplier, Y - TetrisNode.multiplier + 1, 0xFFFFFFFF);
			}
		}
	}
	
	//Set a random shape.
	public static void SetShapes() {
		TetrisNode n = new TetrisNode(fromX + 50, fromY);
		n.AddToFamily(n);
		int x = n.GetX();
		int y = n.GetY();
		
		Random rand = new Random();
		int random = rand.nextInt(7);
		if (random == 0) {
			n.AddToFamily(new TetrisNode(x + 10, y));
			n.AddToFamily(new TetrisNode(x, y + 10));
			n.AddToFamily(new TetrisNode(x + 10, y + 10));
			n.SetShape("O");
			n.SetColor(0xFFFFFF00);
		} else if (random == 1) {
			n.AddToFamily(new TetrisNode(x, y + 10));
			n.AddToFamily(new TetrisNode(x, y + 20));
			n.AddToFamily(new TetrisNode(x, y + 30));
			n.SetShape("I");
			n.SetColor(0xFF36EAFF);
		} else if (random == 2) {
			n.AddToFamily(new TetrisNode(x + 10, y));
			n.AddToFamily(new TetrisNode(x, y + 10));
			n.AddToFamily(new TetrisNode(x - 10, y + 10));
			n.SetShape("S");
			n.SetColor(0xFFFF0009);
		} else if (random == 3) {
			n.AddToFamily(new TetrisNode(x - 10, y));
			n.AddToFamily(new TetrisNode(x, y + 10));
			n.AddToFamily(new TetrisNode(x + 10, y + 10));
			n.SetShape("Z");
			n.SetColor(0xFF00FF2B);
		} else if (random == 4) {
			n.AddToFamily(new TetrisNode(x, y + 10));
			n.AddToFamily(new TetrisNode(x, y + 20));
			n.AddToFamily(new TetrisNode(x + 10, y + 20));
			n.SetShape("L");
			n.SetColor(0xFFEC830C);
		} else if (random == 5) {
			n.AddToFamily(new TetrisNode(x, y + 10));
			n.AddToFamily(new TetrisNode(x, y + 20));
			n.AddToFamily(new TetrisNode(x - 10, y + 20));
			n.SetShape("J");
			n.SetColor(0xFFFF19EF);
		} else {
			n.AddToFamily(new TetrisNode(x + 10, y));
			n.AddToFamily(new TetrisNode(x - 10, y));
			n.AddToFamily(new TetrisNode(x, y + 10));
			n.SetShape("T");
			n.SetColor(0xFF9100FF);
		}
		
		CurrentNode = n;
	}
	
	public static void RemoveLayer() {
		if (CurrentNode == null) {
			return;
		}
		
		for (int i = 0; i < CurrentNode.GetFamily().size(); i++) {
			int y = CurrentNode.GetFamily().get(i).GetY();
			int x = fromX - 10;
			for (int i2 = 0; i2 < 100; i2++) {
				x = x + 10;
				if (x > toX - 10) {
					x = fromX - 10;
					for (int i3 = 0; i3 < 100; i3++) {
						x = x + 10;
						TetrisNode.Nodes.remove(TetrisNode.GetNode(x, y));
					}
					for (int i4 = 0; i4 < TetrisNode.Nodes.size(); i4++) {
						if (TetrisNode.Nodes.get(i4).GetY() <= y) {
							TetrisNode.Nodes.get(i4).SetY(TetrisNode.Nodes.get(i4).GetY() + 10);
						}
					}
					BlockPos Player = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
					mc.world.playSound(Player, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.AMBIENT, 10222.5f, 1.5f, true);
					Score = Score + 5;
					break;
				}
				
				if (TetrisNode.GetNode(x, y) == null) {
					break;
				}
			}
		}
	}
	
	public static void GameOver() {
		if (Objects.requireNonNull(SettingsInfo.getSetting("TetrisBest")).value == null || Integer.parseInt(String.valueOf(Objects.requireNonNull(SettingsInfo.getSetting("TetrisBest")).value)) < Score) {
			Objects.requireNonNull(SettingsInfo.getSetting("TetrisBest")).value = Score;
			Settings.WriteSettings();
		}
		GameOver = true;
		TetrisNode.Nodes.clear();
	}

	public static void StartGame() {
		GameOver = false;
		Score = 0;
	}
	
	public static void drawWhiteLine(int x, int x2, int y, int y2) {
		GlStateManager.pushMatrix();
		GlStateManager.scale(0.25f, 0.25f, 0.25f);
		int Thisx = x * 4; int Thisy = y * 4; int Thisx2 = x2 * 4; int Thisy2 = y2 * 4;
		drawRect(Thisx, Thisy, Thisx + 2, Thisy2, 0xFFFFFFFF);
		drawRect(Thisx2, Thisy, Thisx2 - 2, Thisy2, 0xFFFFFFFF);
		drawRect(Thisx, Thisy, Thisx2, Thisy + 2, 0xFFFFFFFF);
		drawRect(Thisx, Thisy2, Thisx2, Thisy2 + 2, 0xFFFFFFFF);
		GlStateManager.popMatrix();
	}
}
