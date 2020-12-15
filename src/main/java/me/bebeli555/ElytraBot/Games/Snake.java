package me.bebeli555.ElytraBot.Games;

import java.util.ArrayList;
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
import net.minecraftforge.client.event.GuiScreenEvent;
import scala.util.Random;

public class Snake extends GuiScreen{
	//Snake game made by: bebeli555

	static Minecraft mc = Minecraft.getMinecraft();
	static ArrayList<Integer> BodyX = new ArrayList<Integer>();
	static ArrayList<Integer> BodyY = new ArrayList<Integer>();
	static int SnakeSize = 0;
	static int SnakeX = 0;
	static int SnakeY = 0;
	static int LastSnakeX = 0;
	static int LastSnakeY = 0;
	static int LastBodyX = 0;
	static int LastBodyY = 0;
	static int delay = 0;
	static String status = "Right";
	static boolean GameOver = true;
	static int Score = 0;
	static int AppleX, AppleY;
	static long lastSec = 0;

	public static void DrawSnake() {
		//Draw the thingy rectangle and text
		drawRect(150, 150, 350, 350, 0xFF000000);
		GlStateManager.pushMatrix();
		GlStateManager.scale(3.0F, 3.0F, 3.0F);
		mc.fontRenderer.drawStringWithShadow(ChatFormatting.GREEN + "Snake", 70, 40, 0xffff);
		GlStateManager.popMatrix();

		//Draw personal best
		GlStateManager.pushMatrix();
		GlStateManager.scale(2.0F, 2.0F, 2.0F);
		mc.fontRenderer.drawStringWithShadow(ChatFormatting.GOLD + "Personal Best: " + ChatFormatting.GREEN + (int)Settings.getDouble("SnakeBest"), 85, 180, 0xffff);
		GlStateManager.popMatrix();

		//Game over.
		if (SnakeX < 140 || SnakeX > 340 || SnakeY < 140 || SnakeY > 340) {
			GameOver();
		}

		if (!GameOver) {
			//Die if head is colliding in body
			for (int i = 0; i < BodyX.size(); i++) {
				if (BodyX.get(i) == SnakeX) {
					if (BodyY.get(i) == SnakeY) {
						GameOver();
						return;
					}
				}
			}

			//Draw score
			mc.fontRenderer.drawStringWithShadow(ChatFormatting.RED + "Score = " + ChatFormatting.GREEN + SnakeSize, 158, 155, 0xffff);

			// Generate apple
			if (AppleX == 0 || AppleY == 0) {
				GenerateApple();
			}

			// Draw apple
			drawRect(AppleX, AppleY, AppleX + 20, AppleY + 20, 0xFFff0000);

			long sec = System.currentTimeMillis() / 150;
			if (sec != lastSec) {
				delay = 0;
				// Control snake movement
				switch (status) {
					case "Up":
						SnakeY -= 20;
						break;
					case "Down":
						SnakeY += 20;
						break;
					case "Right":
						SnakeX += 20;
						break;
					case "Left":
						SnakeX -= 20;
						break;
				}

				if (!BodyX.isEmpty()) {
					BodyX.remove(BodyX.get(0));
					BodyY.remove(BodyY.get(0));
					BodyX.add(LastSnakeX);
					BodyY.add(LastSnakeY);
				}
				lastSec = sec;
			}

			//Eat apple
			if (SnakeX == AppleX) {
				if (SnakeY == AppleY) {
					SnakeSize++;
					AppleX = 0;
					AppleY = 0;

					//More body for snake bcs he fat and eating all those sugary apples
					if (BodyX.isEmpty()) {
						BodyX.add(LastSnakeX);
						BodyY.add(LastSnakeY);
					} else {
						BodyX.add(LastBodyX);
						BodyY.add(LastBodyY);
					}
					BlockPos Player = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
					mc.world.playSound(Player, SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.AMBIENT, 100.0f, 2.0F, true);
				}
			}

			LastSnakeX = SnakeX;
			LastSnakeY = SnakeY;
			if (!BodyX.isEmpty()) {
				LastBodyX = BodyX.get(BodyX.size() - 1);
				LastBodyY = BodyY.get(BodyY.size() - 1);
			}

			// Draw snake
			drawRect(SnakeX, SnakeY, SnakeX + 20, SnakeY + 20, 0xFF55ff00);
			drawRect(SnakeX + 3, SnakeY + 3, SnakeX + 8, SnakeY + 8, 0xFF000000);
			for (int i = 0; i < BodyX.size(); i++) {
				if (!BodyX.isEmpty())
					drawRect(BodyX.get(i), BodyY.get(i), BodyX.get(i) + 20, BodyY.get(i) + 20, 0xFF55ff00);
			}
		}

		//Draw white lines
		for (int i = 0; i < 10; i++) {
			GlStateManager.pushMatrix();
			GlStateManager.scale(0.25f, 0.25f, 0.25f);
			drawRect(600, 150 * 4,602, 350 * 4, 0xFFFFFFFF);
			drawRect(680 + ((i * 20) * 4), 150 *4, 682 + ((i * 20) * 4), 350 *4, 0xFFFFFFFF);
			GlStateManager.popMatrix();
		}

		//Other lines This is big math time.
		for (int i = 0; i < 11; i++) {
			GlStateManager.pushMatrix();
			GlStateManager.scale(0.25f, 0.25f, 0.25f);
			drawRect(150 * 4, 600 + ((i * 20) * 4), 350 * 4, 602 + ((i * 20) * 4), 0xFFFFFFFF);
			GlStateManager.popMatrix();
		}

		//Game over screen
		if (GameOver) {
			GlStateManager.pushMatrix();
			GlStateManager.scale(3.0F, 3.0F, 3.0F);
			mc.fontRenderer.drawStringWithShadow(ChatFormatting.RED + "Game Over!", 56, 63, 0xffff);
			mc.fontRenderer.drawStringWithShadow(ChatFormatting.RED + "Score = " + ChatFormatting.GREEN + SnakeSize, 59, 80, 0xffff);
			GlStateManager.popMatrix();
			drawRect(170, 290, 330, 330, 0xFFFFFFFF);
			GlStateManager.pushMatrix();
			GlStateManager.scale(2.0F, 2.0F, 2.0F);
			mc.fontRenderer.drawStringWithShadow(ChatFormatting.GREEN + "Click to Play!", 92, 150, 0xffff);
			GlStateManager.popMatrix();
			GlStateManager.pushMatrix();
			GlStateManager.scale(1.5F, 1.5F, 1.5F);
			mc.fontRenderer.drawStringWithShadow(ChatFormatting.AQUA + "Use " + ChatFormatting.GREEN + "ARROW KEYS " + ChatFormatting.AQUA + "To play!", 107, 110, 0xffff);
			GlStateManager.popMatrix();
		}
	}

	public static void OnClick(int i, int j, int k) {
		//Start game 150, 150, 350, 350
		if (150 < i && 350 > i && 150 < j && 350 > j)
			if (GameOver) StartGame();
	}

	public static void OnClick(GuiScreenEvent.KeyboardInputEvent.Post e) {

		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) status = "Down";

		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) status = "Up";

		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) status = "Right";

		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) status = "Left";
	}

	public static void GameOver() {
		if (!GameOver)
			if (SettingsInfo.getSetting("SnakeBest").value == null || Integer.parseInt(String.valueOf(SettingsInfo.getSetting("SnakeBest").value)) < SnakeSize) {
				SettingsInfo.getSetting("SnakeBest").value = SnakeSize;
				Settings.WriteSettings();
			}
			GameOver = true;
			BodyX.clear();
			BodyY.clear();
			LastSnakeX = 0;
			LastSnakeY = 0;
			AppleX = 0;
			AppleY = 0;
		}

	public static void StartGame() {
		GameOver = false;
		SnakeX = 230;
		SnakeY = 330;
		status = "Up";
		SnakeSize = 1;
	}

	public static void GenerateApple() {
		for (int i = 0; i < 100; i++) {
			Random rand = new Random();
			int random = rand.nextInt(10);
			int random2 = rand.nextInt(10);
			AppleX = 150 + random * 20;
			AppleY = 150 + random2 * 20;

			for (int i2 = 0; i2 < BodyX.size(); i2++) {
				if (!BodyX.isEmpty()) {
					if (BodyX.get(i2) == AppleX) {
						if (BodyY.get(i2) == AppleY) {
							AppleX = 0;
							AppleY = 0;
							break;
						}
					}
				}
			}

			if (SnakeX == AppleX && SnakeY == AppleY) {
				AppleX = 0;
				AppleY = 0;
				continue;
			}

			if (AppleX < 140 || AppleX > 340 || AppleY < 140 || AppleY > 340) {
				AppleX = 0;
				AppleY = 0;
				continue;
			}

			if (AppleX != 0 && AppleY != 0) {
				break;
			}
		}
	}
}
