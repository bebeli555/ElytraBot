package me.bebeli555.ElytraBot.Gui;

import com.mojang.realmsclient.gui.ChatFormatting;

import me.bebeli555.ElytraBot.Settings.Settings;

//Sets the info about the nodes.
public class SetNodes {
	public static Node[] Modes = new Node[3];
	
	public static void SetAllNodes() {
		Node.Nodes.clear();
		Node n;
		Node extend;
		
		//Start and Stop 430, 70, 540, 90
		n = new Node(430, 70, false, "Highway, Overworld");
		n.setText("START", ChatFormatting.GREEN, 1F);
		n.id = "StartAndStop";
		
		//Modes
		n = new Node(430, 150, false, "null");
		n.setText("Mode:", ChatFormatting.GOLD, 1.25F);
		
		n = new Node(430, 170, true, "null");
		n.setText("Highway", null, 1F);
		n.addTip("A Mode designed for the highways just");
		n.addTip("look at the direction u wanna go and start it.");
		n.addTip("Also has alot of settings.");
		Modes[0] = n;
		
		n = new Node(430, 190, true, "null");
		n.setText("Overworld", null, 1F);
		n.addTip("A Mode designed for Overworld travel and other");
		n.addTip("It will go to the set destination.");
		Modes[1] = n;
		
		n = new Node(430, 210, true, "null");
		n.setText("Games", null, 1F);
		n.addTip("A Mode where you can play games");
		n.addTip("Like Snake and Tetris for fun.");
		n.addTip("You can change the mode to this even if");
		n.addTip("u have other mode on. It wont affect it");
		Modes[2] = n;
		
		//Highway
		n = new Node(430, 100, false, "Highway, Overworld");
		n.setText("Settings:", ChatFormatting.GOLD, 1.25F);
		
		extend = new Node(435, 140, true, "Highway");
		extend.setText("Blocks: ", ChatFormatting.DARK_AQUA, 1F);
		extend.id = "usebaritoneBlocks";
		extend.setTypeAble(true);
		extend.isAnExtend = true;
		extend.addTip("The amount of blocks baritone will walk");
		extend.addTip("forward when activated.");
		n = new Node(430, 120, true, "Highway");
		n.setText("UseBaritone", null, 1F);
		n.addTip("Whenever elytrabot gets stuck with this on");
		n.addTip("It will activate baritone and walk forward");
		n.addTip("To overcome the obstacle and then keep flying.");
		n.isExtendable = true;
		n.Extends.add(extend);
		
		extend = new Node(435, 160, true, "Highway");
		extend.setText("DropItems", null, 1F);
		extend.isAnExtend = true;
		extend.addTip("When Elytrabot is repairing armor with this on");
		extend.addTip("It will drop useless items from ur inventory if its full");
		extend.addTip("So it can take off the armor for maximum mending!");
		Node extend2;
		extend2 = new Node(435, 180, true, "Highway");
		extend2.setText("Durability: ", ChatFormatting.DARK_AQUA, 1F);
		extend2.id = "Durability";
		extend2.isAnExtend = true;
		extend2.setTypeAble(true);
		extend2.addTip("When elytra durability goes below");
		extend2.addTip("This value then it will start repairing it.");
		n = new Node(430, 140, true, "Highway");
		n.setText("AutoRepair", null, 1F);
		n.addTip("When elytra durability is low elytrabot will");
		n.addTip("Automatically mend the elytra with XP bottles");
		n.addTip("Also it takes ur armor off if enough space in inv");
		n.addTip("XP Bottles are required to have in ur inv.");
		n.isExtendable = true;
		n.Extends.add(extend);
		n.Extends.add(extend2);
		
		n = new Node(430, 160, true, "Highway");
		n.setText("Diagonal", null, 1F);
		n.addTip("Changes the highwaymode to diagonal mode");
		n.addTip("Works the same but goes on diagonal highways");
		n.addTip("No pathfinding in this mode yet");
		
		n = new Node(430, 180, true, "Highway");
		n.setText("AutoEat", null, 1F);
		n.addTip("When health or hunger gets low elytrabot");
		n.addTip("Will eat food. Prefers gaps and doesnt eat chorus");
		n.addTip("The food is required to be in ur HOTBAR for this to work");

		n = new Node(430, 200, true, "Highway");
		n.setText("PrefY: ", ChatFormatting.DARK_AQUA, 1F);
		n.addTip("If set elytrabot will get to the set Y-Coordinate on takeoff.");
		n.addTip("So basicly if u set it to 122.5 it will get to that Y coordinate and then start going.");
		n.addTip("Tip: With this set elytrabot will not center the Y so u should set");
		n.addTip("it to like y.5 so it would be centered!");
		n.addTip(ChatFormatting.RED + "Don't set this setting if you don't need it!");
		n.setTypeAble(true);
		n.id = "PrefY";
		n.allowDoubleValue = true;
		
		n = new Node(430, 230, false, "Highway");
		n.setText("Stopat Setting:", ChatFormatting.GOLD, 1.25F);
		n.addTip("When 1 of the set coords is reached");
		n.addTip("Elytrabot will stop.");
		
		n = new Node(430, 250, true, "Highway");
		n.setText("X: ", ChatFormatting.DARK_AQUA, 1F);
		n.setTypeAble(true);
		n.id = "StopAtX";
		
		n = new Node(430, 270, true, "Highway");
		n.setText("Z: ", ChatFormatting.DARK_AQUA, 1F);
		n.setTypeAble(true);
		n.id = "StopAtZ";
		
		n = new Node(430, 290, true, "Highway");
		n.setText("Logout", null, 1F);
		n.addTip("When elytrabot reaches the set coordinates");
		n.addTip("And stops then it will also logout with this on.");
		n.id = "StopAtLog";
		
		//ElytraFly
		n = new Node(430, 320, false, "Highway, Overworld");
		n.setText("ElytraFly:", ChatFormatting.GOLD, 1.25F);
		
		n = new Node(430, 340, true, "Highway, Overworld");
		n.setText("Speed: ", ChatFormatting.DARK_AQUA, 1F);
		n.id = "Speed";
		n.setTypeAble(true);
		n.allowDoubleValue = true;
		
		n = new Node(430, 360, true, "Highway, Overworld");
		n.setText("GlideSpeed: ", ChatFormatting.DARK_AQUA, 1F);
		n.id = "GlideSpeed";
		n.setTypeAble(true);
		n.allowDoubleValue = true;
		
		//Keybinds
		n = new Node(550, 100, false, "Highway, Overworld");
		n.setText("Keybinds:", ChatFormatting.GOLD, 1.25F);
		
		n = new Node(550, 120, true, "Highway, Overworld");
		n.setText("GUI: ", ChatFormatting.DARK_AQUA, 1F);
		n.id = "KeybindGUI";
		n.setTypeAble(true);
		n.isKeybind = true;
		
		n = new Node(550, 140, true, "Highway, Overworld");
		n.setText("Start: ", ChatFormatting.DARK_AQUA, 1F);
		n.id = "KeybindStart";
		n.setTypeAble(true);
		n.isKeybind = true;
		
		n = new Node(550, 160, true, "Highway, Overworld");
		n.setText("Stop: ", ChatFormatting.DARK_AQUA, 1F);
		n.id = "KeybindStop";
		n.setTypeAble(true);
		n.isKeybind = true;
		
		//Takeoff
		n = new Node(550, 190, false, "Highway");
		n.setText("TakeOff:", ChatFormatting.GOLD, 1.25F);
		n.addTip("Set the Takeoff setting.");
		n.addTip("These will help to success the takeoff!");
		n.addTip("If you set none then it will just jump");
		n.addTip("And try to takeoff from that");
		
		n = new Node(550, 210, true, "Highway");
		n.setText("PacketFly", null, 1F);
		n.addTip("Uses packetfly with takeoff");
		n.addTip("Best for servers that dont kick u for it!");
		
		extend = new Node(555, 250, true, "Highway");
		extend.setText("Speed: ", ChatFormatting.DARK_AQUA, 1F);
		extend.isAnExtend = true;
		extend.addTip("This is the speed it will glide downwards");
		extend.addTip("The lower the speed the lower it will fall.");
		extend.allowDoubleValue = true;
		extend.setTypeAble(true);
		extend.id = "SlowGlideSpeed";
		n = new Node(550, 230, true, "Highway");
		n.setText("SlowGlide", null, 1F);
		n.addTip("In takeoff after it starts going down");
		n.addTip("It goes down slower than normal.");
		n.isExtendable = true;
		n.Extends.add(extend);
		
		//Overworld
		n = new Node(430, 120, true, "Overworld");
		n.setText("AutoSwitch", null, 1F);
		n.addTip("Automatically switches LOW Durability");
		n.addTip("Elytras with higher durability ones");
		
		n = new Node(430, 150, false, "Overworld");
		n.setText("Destination:", ChatFormatting.GOLD, 1.25F);
		n.addTip("The Coordinates that elytrabot");
		n.addTip("Will go to.");
		
		n = new Node(430, 170, true, "Overworld");
		n.setText("X: ", ChatFormatting.DARK_AQUA, 1F);
		n.id = "OverworldX";
		n.setTypeAble(true);
		
		n = new Node(430, 190, true, "Overworld");
		n.setText("Z: ", ChatFormatting.DARK_AQUA, 1F);
		n.id = "OverworldZ";
		n.setTypeAble(true);
		
		n = new Node(430, 210, true, "Overworld");
		n.setText("Logout", null, 1F);
		n.id = "OverworldLog";
		n.addTip("When the Destination is reached with this on");
		n.addTip("Elytrabot will also logout from the server.");
		
		//Add nodes to their groups this means that only 1 of them can be activated at a time
		Node.getNodeFromID("Highway").addToGroup("Overworld");
		Node.getNodeFromID("Highway").addToGroup("Games");
		Node.getNodeFromID("Overworld").addToGroup("Games");
		Node.getNodeFromID("PacketFly").addToGroup("SlowGlide");
		
		//Add settings values to the nodes.
		Settings.setSettings();
		for (Node node : Node.Nodes) {
			node.setTextColor();
			node.centerText(node.getRealText(), node.getScale());
		}
	}
}
