package me.bebeli555.ElytraBot.Settings;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import me.bebeli555.ElytraBot.Init;

//Settings Object.
public class SettingsInfo {
	public static ArrayList<SettingsInfo> Settings = new ArrayList<SettingsInfo>();
	public String name;
	public Object value;
	public Object defaultValue;
	
	public SettingsInfo(String name, Object defaultValue) {
		this.name = name;
		this.value = getSettingFromFile(name);
		this.defaultValue = defaultValue;
		Settings.add(this);
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public Object getValue() {
		if (value == null) {
			return defaultValue;
		}
		return value;
	}
	
	public static SettingsInfo getSetting(String name) {
		for (SettingsInfo setting : Settings) {
			if (setting.getName().equals(name)) {
				return setting;
			}
		}
		return null;
	}
	
	public static Object getSettingFromFile(String name) {
		File file = new File(Init.Path);
		Scanner scanner;
		try {
			scanner = new Scanner(new File(file.getAbsolutePath()));
		} catch (Exception e1) {
			return null;
		}
		
		//Read lines
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String lines[] = line.split(" ");
			for (String s : lines) {
				if (s.equalsIgnoreCase(name)) {
					String arr[] = line.split(" ", 2);
					String firstWord = arr[0];
					return firstWord;
				}
			}
		}
		
		return null;
	}
}
