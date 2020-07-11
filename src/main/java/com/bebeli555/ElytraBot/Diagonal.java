package com.bebeli555.ElytraBot;

import java.util.Objects;
import java.util.Random;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Diagonal {
	//This is Like The Main.class But for Diagonal Highways...
	//This is a mess too
	static boolean toggle = false;
	static EnumFacing direction;
	static Minecraft mc = Minecraft.getMinecraft();
	static boolean baritonetoggle = false;
	static float Yaw;
	static String facing;
	static double CheckX;
	static double CheckZ;
	static int delay = 0;
	static boolean MoveOn = false;
	static boolean cancelled = false;
	static String status;
	static boolean takeoff = false;
	static boolean mess = false;
	static boolean ffs = false;
	static int delay18 = 0;
	static boolean MoveRight = false;
	static boolean MoveStraight = false;
	static boolean manuver = false;
	static int StartupPosX;
	static int StartupPosZ;
	static int StartupPosY;
	static int delay21 = 0;
	static int barposx = 0;
	static int barposz = 0;
	static int bdelay2 = 0;
	static boolean lmao5 = false;
	static int okposx;
	static int okposz;
	int delay29 = 0;
	static boolean idkshit = false;

	
	// Main Method!
	@SubscribeEvent
	public void onUpdate(TickEvent.ClientTickEvent e) {
			try {
			//uses baritone if the player is stuck...
			if (baritonetoggle == true) {
				TakeOff.ActivatePacketFly = false;
				if (mc.player.onGround == true) {
					//Walks 35 blocks straight with baritone if player is stuck and then continues flying
					bdelay2++;
					if (lmao5 == false) {
						UseBaritone();
						lmao5 = true;
					}
					
					if (bdelay2 == 5) {
						okposx = (int)mc.player.posX;
						okposz = (int)mc.player.posZ;
					} else if (bdelay2 > 75) {
						if ((int) mc.player.posX == okposx) {
							if ((int) mc.player.posZ == okposz) {
								baritonetoggle = false;
								toggle = true;
								bdelay2 = 0;
								mc.player.sendChatMessage("#cancel");
								takeoff = false;
								lmao5 = false;
							} else {
								bdelay2 = 0;
							}
						} else {
							bdelay2 = 0;
						}
					}
				}
			}
			//End of baritone setting
			if (toggle == true) {
				// Checks if Elytra is needing repair and if so repairs it
				if (com.bebeli555.ElytraBot.Settings.AutoRepair == true) {
					delay29++;
					if (delay29 > 500) {
						this.CheckDur();
						delay29 = 0;
					}
				}
				
				// Activate baritone support if player is stuck
				if (status == "Going Straight") {
					if (com.bebeli555.ElytraBot.Settings.UseBaritone == true) {
						delay21++;
						if (delay21 == 2) {
							barposx = (int) mc.player.posX;
							barposz = (int) mc.player.posZ;
						} else if (delay21 > 100) {
							delay21 = 0;
							if (barposx == (int)mc.player.posX | barposz == (int)mc.player.posZ) {
								delay21 = 0;
								baritonetoggle = true;
								lmao5 = false;
								toggle = false;
							}
						}

					}
				}
				
				//Takeoff
				MoveOn = false;
				if (cancelled == true) {
					MoveOn = false;
					mc.player.setVelocity(0, -0.025, 0);

				}

				if (takeoff == false) {
					status = "Attempting to TakeOff";
					delay18++;
					if (delay18 > 400) {
						if (com.bebeli555.ElytraBot.Settings.UseBaritone == true) {
							baritonetoggle = true;
							lmao5 = false;
							toggle = false;
							delay18 = 0;
						}
					}
					
					if (mc.player.onGround == true) {						
						//Activate baritone if above block is bedrock
						BlockPos playerPos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
						BlockPos Check = playerPos.add(0, 2, 0);
						
						if (mc.world.getBlockState(Check).getBlock() == Blocks.BEDROCK) {
							if (com.bebeli555.ElytraBot.Settings.UseBaritone == true) {
								baritonetoggle = true;
								lmao5 = false;
								toggle = false;
							} else {
								toggle = false;
								this.UnCheck();
								mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.RED + "Were stuck... UseBaritone setting would help!"));
							}
						}
					}
					
					TakeOff.TakeOffMethod(true, Settings.PacketFly, Settings.SlowGlide);
					// End Of TakeOff
				} else {
					//Prevent pitch going too up or too low or 2bs anticheat will start rubberbanding you
					if (mc.player.rotationPitch < -5 || mc.player.rotationPitch > 45) {
						mc.player.rotationPitch = 0;
					}
					
					//Other Stuff
					if (mc.player.isElytraFlying()) {
						if (manuver == false) {
							MoveOn = true;
							this.setMove(false, true);
							status = "Going Straight";
						}
					}
					
					if (mc.player.onGround == true) {
						takeoff = false;
					}
				}
			}
		}catch (NullPointerException e4) {
			
		}
	}
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//Methods
	public static void Check() {
		Yaw = mc.player.rotationYaw;
		direction = mc.player.getHorizontalFacing();
		SetYaws();
		StartupPosX = (int)mc.player.posX;
		StartupPosZ = (int)mc.player.posZ;
		StartupPosY = (int)mc.player.posY;
	}
	
	public static void UnCheck() {
		toggle = false;
		baritonetoggle = false;
		Yaw = 0;
		takeoff = false;
		delay = 0;
		MoveOn = false;
		cancelled = false;
		idkshit = false;
	}
	
	//Flight method 0.18
	public static void Flight(boolean right, boolean straight, double y) {
		if (mc.player.isElytraFlying()) {
			if (straight == true) {
				double Speed = Settings.FlySpeed / 1.408;
				
				if (facing.equals("--")) {
					Main.SetMotion(-Speed, y, -Speed);
				} else if (facing.equals("+-")) {
					Main.SetMotion(+Speed, y, -Speed);
				} else if (facing.equals("++")) {
					Main.SetMotion(+Speed, y, +Speed);
				} else if (facing.equals("-+")) {
					Main.SetMotion(-Speed, y, +Speed);
				}
			}
		}
	}
	
	public static void SetYaws() {
		mc.player.rotationPitch = 0;
		// First check if Yaw is out of valid range (positive or negative 0-180)
		// This prevents snapping to the wrong axis on diagonals
		// Credit to tycrek for this fix.
		if (Yaw < -180.0) Yaw += 360;
		else if (Yaw > 180) Yaw -= 360;
		
		//These shits took fucking an hour to figure out...
		if (direction.equals(EnumFacing.NORTH)) {
			double YawCheck = Closer(135, -135, Yaw);
			if (YawCheck == -135) {
				mc.player.rotationYaw = -135;
				facing = "+-";
			} else {
				mc.player.rotationYaw = 135;
				facing = "--";
			}
		} else if (direction.equals(EnumFacing.WEST)) {
			double YawCheck = Closer(135, 45, Yaw);
			if (YawCheck == 135) {
				mc.player.rotationYaw = 135;
				facing = "--";
			} else {
				mc.player.rotationYaw = 45;
				facing = "-+";
			}
		} else if (direction.equals(EnumFacing.EAST)) {
			double YawCheck = Closer(-45, -135, Yaw);
			if (YawCheck == -135) {
				mc.player.rotationYaw = -135;
				facing = "+-";
			} else {
				mc.player.rotationYaw = -45;
				facing = "++";
			}
		} else if (direction.equals(EnumFacing.SOUTH)) {
			double YawCheck = Closer(45, -45, Yaw);
			if (YawCheck == 45) {
				mc.player.rotationYaw = 45;
				facing = "-+";
			} else {
				mc.player.rotationYaw = -45;
				facing = "++";
			}
		}
	}
	
	public static void setMove(boolean right, boolean straight) {
		MoveRight = right;
		MoveStraight = straight;
	}
	
	public static double Closer(double a , double b , double c ) {
	     if (Math.abs(c - a) < Math.abs(c - b)) 
	         return a;
	     else
	        return b;
	   }
	
	public void UseBaritone() {
		SetYaws();
		int GoY = StartupPosY - (int)mc.player.posY;
		if (facing.equals("--")) {
			mc.player.sendChatMessage("#goto ~-30 ~" + GoY + " ~-30");
		} else if (facing.equals("++")) {
			mc.player.sendChatMessage("#goto ~+30 ~" + GoY + " ~+30");
		} else if (facing.equals("+-")) {
			mc.player.sendChatMessage("#goto ~+30 ~" + GoY + " ~-30");
		} else if (facing.equals("-+")) {
			mc.player.sendChatMessage("#goto ~-30 ~" + GoY + " ~+30");
		}
	}
	
	//Activate ElytraRepair if durability is low
	public void CheckDur() {
		ItemStack elytra = mc.player.inventory.armorItemInSlot(2);
		int Durability = (elytra.getMaxDamage()-elytra.getItemDamage());
		
		if (Durability < 50) {
			com.bebeli555.ElytraBot.AutoRepair.AutoRepair = true;
		}
	}
}
