package me.bebeli555.ElytraBot.Settings;
import org.lwjgl.input.Keyboard;

import me.bebeli555.ElytraBot.Commands;
import me.bebeli555.ElytraBot.Gui;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class KeyBind {
	public static String BindGUI = "NONE";
	public static String BindSTART = "NONE";
	public static String BindSTOP = "NONE";
	
	@SubscribeEvent
	public void onKeyPress(InputEvent.KeyInputEvent event) {
		if (IsBindSet(BindGUI)) {
			int Key = Keyboard.getKeyIndex(BindGUI);
			if (Keyboard.isKeyDown(Key)) {
				Commands.GuiON = true;
			}
		}
		
		if (IsBindSet(BindSTART)) {
			int Key = Keyboard.getKeyIndex(BindSTART);
			if (Keyboard.isKeyDown(Key)) {
				Gui.TurnOn();
			}
		}
		
		if (IsBindSet(BindSTOP)) {
			int Key = Keyboard.getKeyIndex(BindSTOP);
			if (Keyboard.isKeyDown(Key)) {
				Gui.TurnOff();
			}
		}
	}
	
	public static boolean IsBindSet(String name) {
		if (name.contains("NONE")) {
			return false;
		}
		
		return true;
	}
}
