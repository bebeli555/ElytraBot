package me.bebeli555.ElytraBot.Settings;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import me.bebeli555.ElytraBot.Init;
import me.bebeli555.ElytraBot.Gui.Node;

public class Settings {
	// This stores the setting values in Settings.txt Which is located at
	// .minecraft/ElytraBot/Settings.txt
	public static void WriteSettings(){
		try {
			SetSettingsFromGUI();
			//Clears the file so it doesnt mess up
			File file = new File(Init.Path);
			if (file.exists() && file.isFile()) {
				file.delete();
			}
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

			//Sets settings to the file.
			for (int i = 0; i < SettingsInfo.Settings.size(); i++) {
				SettingsInfo s = SettingsInfo.Settings.get(i);
				bw.write(s.getValue() + " = " + s.getName());
				bw.newLine();
			}
			bw.newLine();
			bw.write("(Dont touch this file manually)"); bw.newLine();
			bw.write("If its causing issues then delete it.");
			bw.close();
		}catch (Exception e) {
			System.out.println("Exception in writing setting file. ELYTRABOT");
			e.printStackTrace();
		}
	}
	
	//Adds all the settings.
	public static void setSettings() {
		SettingsInfo.Settings.clear();
		
		//Highway
		new SettingsInfo("UseBaritone", false);
		new SettingsInfo("usebaritoneBlocks", 15);
		new SettingsInfo("AutoRepair", false);
		new SettingsInfo("DropItems", true);
		new SettingsInfo("Durability", 50);
		new SettingsInfo("Diagonal", false);
		new SettingsInfo("AutoEat", false);
		new SettingsInfo("StopAtX", -1);
		new SettingsInfo("StopAtZ", -1);
		new SettingsInfo("StopAtLog", false);
		new SettingsInfo("PacketFly", false);
		new SettingsInfo("SlowGlide", true);
		new SettingsInfo("SlowGlideSpeed", -0.025);
		new SettingsInfo("PrefY", -1);
		
		//OverWorld
		new SettingsInfo("AutoSwitch", false);
		new SettingsInfo("OverworldX", -1);
		new SettingsInfo("OverworldZ", -1);
		new SettingsInfo("OverworldLog", false);
		
		//Games
		new SettingsInfo("SnakeBest", 0);
		new SettingsInfo("TetrisBest", 0);
		
		//Neutral
		new SettingsInfo("KeybindGUI", "NONE");
		new SettingsInfo("KeybindStart", "NONE");
		new SettingsInfo("KeybindStop", "NONE");
		new SettingsInfo("Speed", 1.8);
		new SettingsInfo("GlideSpeed", 0.1);
		
		setNodesFromSettings();
	}
	
	public static void setNodesFromSettings() {
		//Set gui nodes values from the settings.
		for (int i = 0; i < SettingsInfo.Settings.size(); i++) {
			SettingsInfo si = SettingsInfo.Settings.get(i);
			Node n = Node.getNodeFromID(si.getName());
			if (n == null) {
				continue;
			}
			
			if (n.isKeybind) {
				n.stringValue = String.valueOf(si.getValue());
			} else if (n.isTypeable()) {
				n.stringValue = String.valueOf(si.getValue());
				if (!n.stringValue.isEmpty()) {
					n.value = Double.parseDouble(String.valueOf(si.getValue()));
				}
			} else {
				n.parent = Boolean.valueOf(String.valueOf(si.getValue()));
			}
		}	
	}
	
	public static void SetSettingsFromGUI() {
		for (int i = 0; i < SettingsInfo.Settings.size(); i++) {
			SettingsInfo s = SettingsInfo.Settings.get(i);
			Node n = Node.getNodeFromID(s.name);
			if (n == null) {
				continue;
			}
			
			if (n.isKeybind == true) {
				if (n.stringValue == "0.0") {
					s.setValue("");
				} else {
					s.setValue(n.stringValue);
				}
			} else if (n.isTypeable()) {
				s.setValue(n.value);
			} else {
				s.setValue(n.parent);
			}
		}
	}
	
	public static void setValue(String name, Object value) {
		for (int i = 0; i < SettingsInfo.Settings.size(); i++) {
			SettingsInfo s = SettingsInfo.Settings.get(i);
			if (s.name.toLowerCase().equals(name.toLowerCase())) {
				Node n = Node.getNodeFromID(s.getName());
				if (n.isTypeable()) {
					n.setStringValue(String.valueOf(value));
					if (!n.isKeybind) {
						n.value = Double.parseDouble(String.valueOf(value));
					}
				} else {
					n.parent = Boolean.parseBoolean(String.valueOf(value));
				}
				for (int i2 = 0; i2 < Node.Nodes.size(); i2++) {
					Node.Nodes.get(i2).setTextColor();
				}
				SetSettingsFromGUI();
				WriteSettings();
				break;
			}
		}
	}
	
	public static boolean getBoolean(String name) {
		for (int i = 0; i < SettingsInfo.Settings.size(); i++) {
			if (SettingsInfo.Settings.get(i).name.toLowerCase().equals(name.toLowerCase())) {
				return Boolean.parseBoolean(String.valueOf(SettingsInfo.Settings.get(i).getValue()));
			}
		}
		return false;
	}
	
	public static double getDouble(String name) {
		for (int i = 0; i < SettingsInfo.Settings.size(); i++) {
			SettingsInfo s = SettingsInfo.Settings.get(i);
			if (s.name.toLowerCase().equals(name.toLowerCase())) {
				return Double.parseDouble(String.valueOf(s.getValue()));
			}
		}
		return 0;
	}
	
	public static String getString(String name) {
		for (int i = 0; i < SettingsInfo.Settings.size(); i++) {
			SettingsInfo s = SettingsInfo.Settings.get(i);
			if (s.name.toLowerCase().equals(name.toLowerCase())) {
				return String.valueOf(s.getValue());
			}
		}
		return "";
	}
}
