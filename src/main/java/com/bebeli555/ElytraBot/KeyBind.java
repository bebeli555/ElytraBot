package com.bebeli555.ElytraBot;
import org.lwjgl.input.Keyboard;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class KeyBind {
	static String BindGUI = "NONE";
	static String BindSTART = "NONE";
	static String BindSTOP = "NONE";
	
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
