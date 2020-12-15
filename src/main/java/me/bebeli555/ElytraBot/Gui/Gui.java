package me.bebeli555.ElytraBot.Gui;

import org.lwjgl.input.Keyboard;
import me.bebeli555.ElytraBot.Renderer;
import me.bebeli555.ElytraBot.Games.Snake;
import me.bebeli555.ElytraBot.Games.SoundGUI;
import me.bebeli555.ElytraBot.Games.Tetris;
import me.bebeli555.ElytraBot.Highway.GetPath;
import me.bebeli555.ElytraBot.Highway.Main;
import me.bebeli555.ElytraBot.Highway.TakeOff;
import me.bebeli555.ElytraBot.Settings.AutoEat;
import me.bebeli555.ElytraBot.Settings.AutoRepair;
import me.bebeli555.ElytraBot.Settings.Settings;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class Gui extends GuiScreen {
	static Minecraft mc = Minecraft.getMinecraft();
	static int delay;
	static boolean ChangeToOld, warn;
	static int OldScale;
	static boolean on;
	public static String currentMode = "";
	public static double originalSpeed;

	@Override
	public void initGui() {
		super.initGui();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		if (!mc.isFullScreen()) {
			if (!warn) {
				mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.RED + "The GUI might not work on Window mode!"));
				warn = true;
			}
		} else {
			warn = false;
		}

		//Draw all the Gui Rectangles and their texts
		out: for (int i = 0; i < Node.Nodes.size(); i++) {
			Node n = Node.Nodes.get(i);
			if (n.isInRightMode()) {

				//Don't draw modes that are extends and are not extended
				if (n.isAnExtend) {
					if (!n.getExtendParent().isExtended) {
						continue out;

					}
				}

				if (!n.isClickable)
					n.setColor(0x3632a8a2);

				drawRect(n.getX(), n.getY(), n.getX2(), n.getY2(), n.getColor());

				GlStateManager.pushMatrix();
				GlStateManager.scale(n.getScale(), n.getScale(), n.getScale());
				if (n.stringValue.equals("") || n.getStringValue().equals("-1") || n.isKeybind && n.getStringValue().equals("NONE") || n.getStringValue().equals("-1.0")) {
					if (n.textColor == null) {
						mc.fontRenderer.drawStringWithShadow(n.getText(), n.getTextX(), n.getTextY(), 0xffff);
					} else {
						mc.fontRenderer.drawStringWithShadow(n.textColor + n.getText(), n.getTextX(), n.getTextY(), 0xffff);
					}

					if (n.isTypeable()) {
						mc.fontRenderer.drawStringWithShadow(n.textColor + n.getText() + ChatFormatting.RED + "NONE", n.getTextX(), n.getTextY(), 0xffff);
					}
				} else {
					mc.fontRenderer.drawStringWithShadow(n.textColor + n.getText() + ChatFormatting.GREEN + n.getStringValue(), n.getTextX(), n.getTextY(), 0xffff);
				}
				GlStateManager.popMatrix();
			}
		}

		//Draw games thing
		if (currentMode.equals("Games")) {
			Snake.DrawSnake();
			Tetris.DrawTetris(400, 150, false);
			SoundGUI.drawSoundGUI();
		}

		//Draw lines around the GUI Objects
		for (int i = 0; i < Node.Nodes.size(); i++) {
			Node n = Node.Nodes.get(i);
			if (n.isInRightMode()) {
				if (!n.isAnExtend || n.isAnExtend && n.getExtendParent().isExtended) {
					int color = 0xFF32a86d;
					if (n.isAboveClickable() && n.isBelowClickable())
						drawContainer(true, true, false, false, color, n);
					else if (n.isAboveClickable())
						drawContainer(true, true, false, true, color, n);
					else drawContainer(true, true, true, !n.isBelowClickable(), color, n);
				}
			}
		}

		//Draw tips
		for (int i = 0; i < Node.Nodes.size(); i++) {
			Node n = Node.Nodes.get(i);
			if (n.isExtendable) {
				if (!n.Tips.contains(ChatFormatting.GREEN + "(This setting is Extendable!)")) {
					n.Tips.add(ChatFormatting.GREEN + "(This setting is Extendable!)");
				}
			}
			if (n.Tips.isEmpty()) {
				n.Tips.add("");
			}
			for (int i2 = 0; i2 < n.Tips.size(); i2++) {
				if (n.isInRightMode()) {
					if (n.isClickInArea(mouseX, mouseY) || n.isTypeable() && n.parent) {
						//Don't draw modes that are extends and are not extended
						if (n.isAnExtend) {
							if (!n.getExtendParent().isExtended) {
								continue;
							}
						}

						int y = (int) ((n.getTextY() + (i2 * 10)) * n.getScale());
						if (n.isTypeable() && n.parent) {
							String text = ChatFormatting.GOLD + "Use you're keyboard to set this value!";
							drawRect(n.getX2() + 8, y - 2, n.getX2() + mc.fontRenderer.getStringWidth(text) + 12, y + 10, 0xFF000000);
							mc.fontRenderer.drawStringWithShadow(text, n.getX2() + 10, (n.getTextY() + (i2 * 10)) * n.getScale(), 0xffff);
							break;
						}

						if (!n.Tips.get(i2).equals("")) {
							drawRect(n.getX2() + 8, y - 2, n.getX2() + mc.fontRenderer.getStringWidth(n.Tips.get(i2)) + 12, y + 10, 0xFF000000);
							mc.fontRenderer.drawStringWithShadow(n.Tips.get(i2), n.getX2() + 10, (n.getTextY() + (i2 * 10)) * n.getScale(), 0xffff);
						}
					}
				}
			}
		}

		//Start and stop
		Node n = Node.getNodeFromID("StartAndStop");
		if (AutoEat.IsElytrabotEnabled() || me.bebeli555.ElytraBot.Overworld.Main.toggle) {
			n.setText("STOP", ChatFormatting.RED, 1F);
			on = true;
		} else {
			n.setText("START", ChatFormatting.GREEN, 1F);
			on = false;
		}
		n.id = "StartAndStop";

		GlStateManager.popMatrix();
	}

	// Mouse click event it activates the modules and stuff when u click the things on the screen!
	@Override
	protected void mouseClicked(int x, int y, int button) {
		if (currentMode.equals("Games")) {
			Snake.OnClick(x, y, button);
			SoundGUI.ClickEvent(x, y, button);
			//Start tetris 165, 140, 215, 155
			if (165 * 2.5 < x && 215 * 2.5 > x && 140 * 2.5 < y && 155 * 2.5 > y) {
				if (Tetris.GameOver) {
					Tetris.StartGame();
				}
			}
		}

		for (int i = 0; i < Node.Nodes.size(); i++) {
			Node n = Node.Nodes.get(i);

			if (n.isTypeable()) {
				n.centerText(n.getRealText(), n.getScale());
				n.parent = false;
			}

			if (!n.isInRightMode()) {
				continue;
			}

			if (n.isAnExtend) {
				if (!n.getExtendParent().isExtended) {
					continue;
				}
			}

			if (n.isClickInArea(x, y)) {
				n.setClicked();

				if (n.id.equals("StartAndStop")) {
					if (on) {
						TurnOff();
					} else {
						TurnOn();
					}
				} else if (AutoEat.IsElytrabotEnabled() || me.bebeli555.ElytraBot.Overworld.Main.toggle) {
					mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.RED + "Restart ElytraBot to apply new settings."));
				}
				return;
			}
		}
	}

	// Keyboard type event
	@SubscribeEvent
	public void onKeyPress(GuiScreenEvent.KeyboardInputEvent.Post e) {
		if (currentMode.equals("Games"))
			Snake.OnClick(e);

		for (int i = 0; i < Node.Nodes.size(); i++) {
			Node n = Node.Nodes.get(i);

			if (n.isTypeable()) {
				if (n.parent) {
					if (n.shouldResetStringValue) {
						n.value = 0;
						n.stringValue = "";
						n.shouldResetStringValue = false;
					}

					if (Keyboard.isKeyDown(Keyboard.KEY_BACK) || Keyboard.isKeyDown(Keyboard.KEY_DELETE)) {
						n.resetValue();
						return;
					}

					if (n.isKeybind) {
						if (Keyboard.isKeyDown(Keyboard.getEventKey())) {
							n.setStringValue(Keyboard.getKeyName(Keyboard.getEventKey()));
							setValue(n);
							return;
						}
					}

					n.setStringValue(n.getStringValue() + Keyboard.getEventCharacter());

					if (n.getStringValue().length() == 0) {
						n.resetValue();
						return;
					}

					if (n.getStringValue().equals("-") || n.getStringValue().substring(n.getStringValue().length() - 1).equals("."))
						if (!n.getStringValue().substring(n.getStringValue().length() - 1).equals(".") && n.allowDoubleValue)
							return;
					setValue(n);
				}
			}
		}
	}

	public static void setValue(Node n) {
		try {
			if (n.getStringValue().contains(".") && n.allowDoubleValue)
				n.value = Double.parseDouble(n.getStringValue());
			else
				n.value = Integer.parseInt(n.getStringValue());
		} catch (Exception e2)
		{
		}
	}

	// Activate the GUI.
	@SubscribeEvent
	public void onUpdate(ClientTickEvent e) {
		if (me.bebeli555.ElytraBot.Commands.GuiON) {
			delay++;
			if (delay > 1) {
				ChangeToOld = true;
				//Change scale to normal for moment
				OldScale = mc.gameSettings.guiScale;
				if (mc.gameSettings.guiScale != 2) {
					mc.gameSettings.guiScale = 2;
				}

				me.bebeli555.ElytraBot.Commands.GuiON = false;
				delay = 0;

				Minecraft.getMinecraft().displayGuiScreen(new Gui());
			}
		}

		// Stop listening to keys when gui is closed
		if (mc.currentScreen == null) {
			if (ChangeToOld) {
				ChangeToOld = false;
				mc.gameSettings.guiScale = OldScale;
			}
		}
	}

	public static void drawContainer(boolean rightSide, boolean leftSide, boolean upSide, boolean downSide, int color, Node n) {
		if (upSide) {
			drawRect(n.getX(), n.getY(), n.getX2(), n.getY() + 1, color);
		}
		if (downSide) {
			drawRect(n.getX(), n.getY2(), n.getX2(), n.getY2() + 1, color);
		}
		if (leftSide) {
			drawRect(n.getX(), n.getY(), n.getX() + 1, n.getY2(), color);
		}
		if (rightSide) {
			drawRect(n.getX2(), n.getY(), n.getX2() + 1, n.getY2() + 1, color);
		}
	}

	public static void SetStuff() {
		Settings.SetSettingsFromGUI();
		Settings.WriteSettings();
		Main.FlySpeed = Settings.getDouble("Speed");
	}

	//Turns ElytraBot OFF
	public static void TurnOff() {
		try {
			GetPath.Path.clear();
			me.bebeli555.ElytraBot.Overworld.GetPath.Path.clear();
			Renderer.PositionsYellow.clear();
			Renderer.PositionsRed.clear();
			Renderer.PositionsGreen.clear();
		} catch (Exception e) {

		}
		if (Settings.getDouble("Speed") < originalSpeed) {
			Settings.setValue("Speed", originalSpeed);
		}
		me.bebeli555.ElytraBot.Overworld.GetGoal.NoPath = false;
		me.bebeli555.ElytraBot.Overworld.Main.toggle = false;
		me.bebeli555.ElytraBot.Overworld.Main.TurnOff();
		TakeOff.ActivatePacketFly = false;
		Main.delay5 = 0;
		me.bebeli555.ElytraBot.ElytraFly.YCenter = false;
		Main.delay18 = 0;
		me.bebeli555.ElytraBot.ElytraFly.FlyMinus = 0;
		Renderer.IsRendering = false;
		TakeOff.ActivatePacketFly = false;
		me.bebeli555.ElytraBot.Settings.AutoRepair.AutoRepair = false;
		AutoRepair.ArmorTakeoff = false;
		if (Main.baritonetoggle) {
			mc.player.sendChatMessage("#cancel");
		} else if (me.bebeli555.ElytraBot.Settings.Diagonal.baritonetoggle) {
			mc.player.sendChatMessage("#cancel");
		}
		me.bebeli555.ElytraBot.Highway.Main.toggle = false;
		me.bebeli555.ElytraBot.Highway.Main.UnCheck();
		me.bebeli555.ElytraBot.Settings.Diagonal.toggle = false;
		me.bebeli555.ElytraBot.Settings.Diagonal.UnCheck();
		me.bebeli555.ElytraBot.Settings.StopAt.toggle = false;
	}

	//Turns ElytraBot ON
	public static void TurnOn() {
		originalSpeed = Settings.getDouble("Speed");
		SetStuff();
		if (Node.getNodeFromID("Highway").parent) {
			if (Settings.getBoolean("Diagonal")) {
				me.bebeli555.ElytraBot.Settings.Diagonal.toggle = true;
				me.bebeli555.ElytraBot.Settings.Diagonal.Check();
			} else {
				me.bebeli555.ElytraBot.Highway.Main.toggle = true;
				me.bebeli555.ElytraBot.Highway.Main.Check();
			}
		} else if (Node.getNodeFromID("Overworld").parent) {
			if (Main.FlySpeed > 2) {
				mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.RED + "The speed you are using is too high the Maximum speed for this mode to work is 2"));
			}
			me.bebeli555.ElytraBot.Overworld.Main.Check();
		} else {
			mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.RED + "No mode selected. Setting to Highway as default."));
			if (Settings.getBoolean("Diagonal")) {
				me.bebeli555.ElytraBot.Settings.Diagonal.toggle = true;
				me.bebeli555.ElytraBot.Settings.Diagonal.Check();
			} else {
				me.bebeli555.ElytraBot.Highway.Main.toggle = true;
				me.bebeli555.ElytraBot.Highway.Main.Check();
			}
		}
	}
}