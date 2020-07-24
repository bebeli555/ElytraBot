package me.bebeli555.ElytraBot;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;

public class SoundGUI extends GuiScreen{
	//Epic music. You can find this while having Highway and Overworld modes both disabled in the left top corner on the Gui!
	
	static Minecraft mc = Minecraft.getMinecraft();
	static boolean IsExtended = false;
	static boolean Chirp = false;
	static boolean Far = false;
	static boolean Stal = false;
	static boolean Strad = false;
	public static boolean StopPlaySpeed = false;
	public static float PlaySpeed = 1.0F;
	public static String playSpeed = "";
	
	public static void drawSoundGUI() {
		if (StopPlaySpeed) {
			mc.fontRenderer.drawStringWithShadow(ChatFormatting.GOLD + "Type numbers in your keyboard to set this!", 125, 145, 0xffff);	
		}
		
		drawRect(10, 40, 120, 60, 0x50ffffff);
		mc.fontRenderer.drawStringWithShadow(ChatFormatting.BOLD + "Music!", 45, 45, 0xffaa00);
		
		if (IsExtended == false) {
			mc.fontRenderer.drawStringWithShadow(ChatFormatting.GOLD + "<-- ", 130, 45, 0xffff);
			mc.fontRenderer.drawStringWithShadow("Right click to extend!", 153, 45, 0xffff);
		} else {
			//Chirp
			drawRect(10, 60, 120, 80, 0x36393fff);
			if (Chirp == true) {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.GREEN + "Chirp", 50, 65, 0xffff);	
			} else {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.RED + "Chirp", 50, 65, 0xffff);	
			}
			
			//Far
			drawRect(10, 80, 120, 100, 0x36393fff);
			if (Far == true) {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.GREEN + "Far", 55, 85, 0xffff);	
			} else {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.RED + "Far", 55, 85, 0xffff);	
			}
			
			//Stal
			drawRect(10, 100, 120, 120, 0x36393fff);
			if (Stal == true) {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.GREEN + "Stal", 53, 105, 0xffff);	
			} else {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.RED + "Stal", 53, 105, 0xffff);	
			}
			
			//Strad
			drawRect(10, 120, 120, 140, 0x36393fff);
			if (Strad == true) {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.GREEN + "Strad", 50, 125, 0xffff);	
			} else {
				mc.fontRenderer.drawStringWithShadow(ChatFormatting.RED + "Strad", 50, 125, 0xffff);	
			}
			
			//Speed
			drawRect(10, 140, 120, 160, 0x36393fff);
			mc.fontRenderer.drawStringWithShadow(ChatFormatting.DARK_AQUA + "Speed: " + ChatFormatting.GREEN + PlaySpeed, 40, 145, 0xffff);	
		}
	}
	
	public static void ClickEvent(int i, int j, int k) {
		StopPlaySpeed = false;
		BlockPos Player = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
		
		//Extend 10, 40, 120, 60
		if (10 < i && 120 > i && 40 < j && 60 > j) {
			if (IsExtended == true) {
				IsExtended = false;
			} else {
				IsExtended = true;
			}
		}
		
		//Chirp 10, 60, 120, 80
		if (10 < i && 120 > i && 60 < j && 80 > j) {
			if (Chirp == true) {
				Reset();
				Chirp = false;
				mc.getSoundHandler().stopSounds();
			} else {
				Chirp = true;
				mc.world.playSound(Player, SoundEvents.RECORD_CHIRP, SoundCategory.AMBIENT, 102.5f, PlaySpeed, true);
			}
		}
		
		//Far 10, 80, 120, 100
		if (10 < i && 120 > i && 80 < j && 100 > j) {
			if (Far == true) {
				Reset();
				Far = false;
				mc.getSoundHandler().stopSounds();
			} else {
				Far = true;
				mc.world.playSound(Player, SoundEvents.RECORD_FAR, SoundCategory.AMBIENT, 102.5f, PlaySpeed, true);
			}
		}
		
		//Stal 10, 100, 120, 120
		if (10 < i && 120 > i && 100 < j && 120 > j) {
			if (Stal == true) {
				Reset();
				Stal = false;
				mc.getSoundHandler().stopSounds();
			} else {
				Stal = true;
				mc.world.playSound(Player, SoundEvents.RECORD_STAL, SoundCategory.AMBIENT, 102.5f, PlaySpeed, true);
			}
		}
		
		//Strad 10, 120, 120, 140
		if (10 < i && 120 > i && 120 < j && 140 > j) {
			if (Strad == true) {
				Reset();
				Strad= false;
				mc.getSoundHandler().stopSounds();
			} else {
				Strad = true;
				mc.world.playSound(Player, SoundEvents.RECORD_STRAD, SoundCategory.AMBIENT, 102.5f, PlaySpeed, true);
			}
		}
		
		//Speed 10, 140, 120, 160
		if (10 < i && 120 > i && 140 < j && 160 > j) {
			StopPlaySpeed = true;
		}
	}

	
	public static void Reset() {
		mc.getSoundHandler().stopSounds();
		Chirp = false;
		Far = false;
		Stal = false;
		Strad = false;
	}
}
