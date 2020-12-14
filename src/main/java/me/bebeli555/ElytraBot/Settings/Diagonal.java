package me.bebeli555.ElytraBot.Settings;

import me.bebeli555.ElytraBot.Highway.Main;
import me.bebeli555.ElytraBot.Highway.TakeOff;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Diagonal {
	public static boolean toggle = false;
	public static EnumFacing direction;
	public static Minecraft mc = Minecraft.getMinecraft();
	public static boolean baritonetoggle = false;
	public static float Yaw;
	public static String facing;
	public static boolean MoveOn = false;
	public static String status;
	public static boolean takeoff = false;
	public static int delay18 = 0;
	public static boolean MoveRight = false;
	public static boolean MoveStraight = false;
	public static boolean manuver = false;
	public static int StartupPosX;
	public static int StartupPosZ;
	public static int StartupPosY;
	public static int delay21 = 0;
	public static int barposx = 0;
	public static int barposz = 0;
	public static int bdelay2 = 0;
	public static boolean lmao5 = false;
	public static int okposx;
	public static int okposz;

	
	@SubscribeEvent
	public void onUpdate(TickEvent.ClientTickEvent e) {
			try {
			//uses baritone if the player is stuck...
			if (baritonetoggle) {
				TakeOff.ActivatePacketFly = false;
				if (mc.player.onGround) {
					//Walks 35 blocks straight with baritone if player is stuck and then continues flying
					bdelay2++;
					if (!lmao5) {
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
			if (toggle) {
				
				// Activate baritone support if player is stuck
				if (status.equals("Going Straight")) {
					if (Settings.getBoolean("UseBaritone")) {
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

				if (!takeoff) {
					status = "Attempting to TakeOff";
					delay18++;
					if (delay18 > 400) {
						if (Settings.getBoolean("UseBaritone")) {
							baritonetoggle = true;
							lmao5 = false;
							toggle = false;
							delay18 = 0;
						}
					}
					
					if (mc.player.onGround) {
						//Activate baritone if above block is bedrock
						BlockPos playerPos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
						BlockPos Check = playerPos.add(0, 2, 0);
						
						if (mc.world.getBlockState(Check).getBlock() == Blocks.BEDROCK) {
							if (Settings.getBoolean("UseBaritone")) {
								baritonetoggle = true;
								lmao5 = false;
								toggle = false;
							} else {
								toggle = false;
								UnCheck();
								mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.RED + "Were stuck... UseBaritone setting would help!"));
							}
						}
					}
					
					TakeOff.TakeOffMethod(true, Settings.getBoolean("PacketFly"), Settings.getBoolean("SlowGlide"));
					// End Of TakeOff
				} else {
					//Prevent pitch going too up or too low or 2bs anticheat will start rubberbanding you
					if (mc.player.rotationPitch < -5 || mc.player.rotationPitch > 45) {
						mc.player.rotationPitch = 0;
					}
					
					//Other Stuff
					if (mc.player.isElytraFlying()) {
						if (!manuver) {
							MoveOn = true;
							setMove(false, true);
							status = "Going Straight";
						}
					}
					
					if (mc.player.onGround) {
						takeoff = false;
					}
				}
			}
		} catch (NullPointerException e4) {
			
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
		MoveOn = false;
	}
	
	//Flight method 0.18
	public static void Flight(boolean right, boolean straight, double y) {
		if (mc.player.isElytraFlying()) {
			if (straight) {
				double Speed = Settings.getDouble("Speed") / 1.408;
				
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
		int blocks = (int)Settings.getDouble("usebaritoneBlocks");
		if (facing.equals("--")) {
			mc.player.sendChatMessage("#goto ~-" + blocks + " ~" + GoY + " ~-" + blocks);
		} else if (facing.equals("++")) {
			mc.player.sendChatMessage("#goto ~+" + blocks + " ~" + GoY + " ~+" + blocks);
		} else if (facing.equals("+-")) {
			mc.player.sendChatMessage("#goto ~+" + blocks + " ~" + GoY + " ~-" + blocks);
		} else if (facing.equals("-+")) {
			mc.player.sendChatMessage("#goto ~-" + blocks + " ~" + GoY + " ~+" + blocks);
		}
	}
}
