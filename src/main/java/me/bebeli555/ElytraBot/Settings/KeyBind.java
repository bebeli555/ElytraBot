package me.bebeli555.ElytraBot.Settings;
import org.lwjgl.input.Keyboard;

import me.bebeli555.ElytraBot.Commands;
import me.bebeli555.ElytraBot.Gui.Gui;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class KeyBind {
	
	@SubscribeEvent
	public void onKeyPress(InputEvent.KeyInputEvent event) {
		int Key;
		
		Key = Keyboard.getKeyIndex(Settings.getString("KeybindGUI"));
		if (Keyboard.isKeyDown(Key)) {
			Commands.GuiON = true;
		}

		Key = Keyboard.getKeyIndex(Settings.getString("KeybindStart"));
		if (Keyboard.isKeyDown(Key)) {
			Gui.TurnOn();
		}

		Key = Keyboard.getKeyIndex(Settings.getString("KeybindStop"));
		if (Keyboard.isKeyDown(Key)) {
			Gui.TurnOff();
		}
	}
}
