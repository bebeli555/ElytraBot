package com.bebeli555.ElytraBot;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import com.bebeli555.ElytraBot.PathFinding.Center;
import com.bebeli555.ElytraBot.PathFinding.GetPath;
import com.bebeli555.ElytraBot.PathFinding.Manuver;
import com.bebeli555.ElytraBot.PathFinding.ReturnToStart;
import com.mojang.realmsclient.gui.ChatFormatting;
import baritone.api.BaritoneAPI;

public class Main {
	static Minecraft mc = Minecraft.getMinecraft();
	public static boolean toggle = false;
	public static EnumFacing direction;
	public static int StartupPosX;
	public static int StartupPosZ;
	public static int StartupPosY;
	public static boolean z = false;
	public static boolean x = false;
	static boolean takeoff = false;
	boolean mine = false;
	static int okposx;
	static int okposz;
	static boolean baritonetoggle = false;
	static boolean MoveRight = false;
	static boolean MoveStraight = false;
	public static boolean MoveOn = false;
	static int delay21 = 0;
	public static int delay5 = 0;
	static int bdelay2 = 0;
	static int barposx = 0;
	static int barposz = 0;
	static int delay18 = 0;
	int delay72 = 0;
	static boolean lmao5 = false;
	public static String status;
	static BlockPos playerPos;
	static boolean baritonecheck = false;
	
	public static boolean manuverRight = false;
	public static double ManuverSpeed;
	
	// Elytra Highway Bot V1.9
	// Made by: bebeli555
	
	@SubscribeEvent
	public void ServerLeave (PlayerLoggedOutEvent e) {
		Gui.TurnOff();
	}

	@SubscribeEvent
	public void onUpdate(TickEvent.ClientTickEvent e) {
		// Check if baritone is installed
		CheckIfBaritoneIsInstalled();
		
		try {
		//uses baritone if the player is stuck...
		UseBaritoneSetting();
		
		if (toggle == true) {
			MoveOn = false;

			if (takeoff == false) {
				status = "Attempting to TakeOff";
				
				//Baritone backup for takeoff
				if (status == "Attempting to TakeOff") {
					if (com.bebeli555.ElytraBot.Settings.UseBaritone == true) {
						delay18++;
						if (delay18 > 400) {
							baritonetoggle = true;
							lmao5 = false;
							toggle = false;
							delay18 = 0;
						}
					}
				} else {
					delay18 = 0;
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
							UnCheck();
							mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.RED + "Were stuck... UseBaritone setting would help!"));
						}
					}
				}
				
				//Mine block above head if its preventing takeoff
				playerPos= new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
				BlockPos Check = playerPos.add(0, 2, 0);
				BlockPos Check2 = playerPos.add(0, 2, 1);
				BlockPos Check3 = playerPos.add(0, 2, -1);
				BlockPos CheckM = playerPos.add(0, 2, 0);
				BlockPos CheckM2 = playerPos.add(1, 2, 0);
				BlockPos CheckM3 = playerPos.add(-1, 2, 0);
				if (mc.player.onGround == true) {
					if (mine == false) {
						if (GetPick() != -1) {
							if (x == true) {
								if (mc.world.getBlockState(Check).getBlock() == Blocks.NETHERRACK) {
									mine = true;
								} else if (mc.world.getBlockState(Check2).getBlock() == Blocks.NETHERRACK) {
									mine = true;
								} else if (mc.world.getBlockState(Check3).getBlock() == Blocks.NETHERRACK) {
									mine = true;
								}
							} else if (z == true) {
								if (mc.world.getBlockState(CheckM).getBlock() == Blocks.NETHERRACK) {
									mine = true;
								} else if (mc.world.getBlockState(CheckM2).getBlock() == Blocks.NETHERRACK) {
									mine = true;
								} else if (mc.world.getBlockState(CheckM3).getBlock() == Blocks.NETHERRACK) {
									mine = true;
								}
							}
						}
					}
				}
				
		if (mine == false) {
			TakeOff.TakeOffMethod(false, Settings.PacketFly, Settings.SlowGlide);
		} else {
				// Mines block above head
				delay72++;
				if (x == true) {
					if (delay72 == 3) {
						mc.player.inventory.currentItem = GetPick();
						mc.player.rotationYaw = 0;
						mc.player.rotationPitch = -90;
					} else if (delay72 == 10) {
						mc.player.inventory.currentItem = GetPick();
						mc.player.swingArm(EnumHand.MAIN_HAND);
						mc.playerController.clickBlock(Check, EnumFacing.DOWN);
					} else if (delay72 == 14) {
						mc.player.rotationYaw = 0;
						mc.player.rotationPitch = -50;
					}else if (delay72 == 20) {
						mc.player.inventory.currentItem = GetPick();
						mc.player.swingArm(EnumHand.MAIN_HAND);
						mc.playerController.clickBlock(Check2, EnumFacing.DOWN);
					} else if (delay72 == 28) {
						mc.player.rotationYaw = 180;
						mc.player.rotationPitch = -50;
					} else if (delay72 > 35) {
						mc.player.inventory.currentItem = GetPick();
						mc.player.swingArm(EnumHand.MAIN_HAND);
						mc.playerController.clickBlock(Check3, EnumFacing.DOWN);
						mine = false;
						delay72 = 0;
					}
				} else if (z == true) {
					if (delay72 == 3) {
						mc.player.inventory.currentItem = GetPick();
						mc.player.rotationYaw = 0;
						mc.player.rotationPitch = -90;
					} else if (delay72 == 10) {
						mc.player.inventory.currentItem = GetPick();
						mc.player.swingArm(EnumHand.MAIN_HAND);
						mc.playerController.clickBlock(CheckM, EnumFacing.DOWN);
					} else if (delay72 == 14) {
						mc.player.rotationYaw = -90;
						mc.player.rotationPitch = -48;
					}else if (delay72 == 20) {
						mc.player.inventory.currentItem = GetPick();
						mc.player.swingArm(EnumHand.MAIN_HAND);
						mc.playerController.clickBlock(CheckM2, EnumFacing.DOWN);
					} else if (delay72 == 28) {
						mc.player.rotationYaw = 90;
						mc.player.rotationPitch = -50;
					} else if (delay72 > 35) {
						mc.player.inventory.currentItem = GetPick();
						mc.player.swingArm(EnumHand.MAIN_HAND);
						mc.playerController.clickBlock(CheckM3, EnumFacing.DOWN);
						mine = false;
						delay72 = 0;
					}
				}

			}
		} else {
			delay18 = 0;
			
			//Y Center go up
			if (Center.IsYCentered() == false && mc.player.isElytraFlying() && TakeOff.IsClearToCenter()) {
				delay5++;
				if (delay5 > 25) {
					TakeOff.YCenter();
					return;	
				}
			} else {
				delay5 = 0;
			}
			ElytraFly.YCenter = false;
			
			//Prevent pitch going too up or too low or 2bs anticheat will start rubberbanding you
			if (mc.player.rotationPitch < -5 || mc.player.rotationPitch > 45) {
				mc.player.rotationPitch = 0;
			}
			
			// Activate baritone support if player is stuck
			CheckBaritone();

			//Check if need of takeoff
			if (!mc.player.isElytraFlying()) {
				takeoff = false;
				return;
			}
			
			String message = GetPath.GetBestPath();
			
			//Go straight
			if (Manuver.IsManuvering == false) {
				if (message.contains("null")) {
					if (TakeOff.IsTakeOffCentered()) {
						GoStraight();
					} else {
						//Center
						if (ReturnToStart.HasPassedManuver() && ReturnToStart.IsNotAtStartup() == false) {
							TakeOff.TakeOffCenter();
						} else {
							GoStraight();
						}
					}
				}
			}
			
			//Slow down if gonna hit block soon
			if (ElytraFly.FlyMinus != Settings.FlySpeed - 0.1 && ElytraFly.FlyMinus != Settings.FlySpeed + 0.1) {
				if (Manuver.WillHitSoon()) {
					ElytraFly.FlyMinus = Settings.FlySpeed - 0.9;
				} else {
					ElytraFly.FlyMinus = 0;
				}
			}
			
			// Pathfinding. the methods gets called on com.bebeli555.ElytraBot.PathFinding. classes
			if (!message.contains("null")) {
				String Coordinate = message.substring(6);
				double Coord = Double.parseDouble(Coordinate);

				if (message.contains("right")) {
					manuverRight = true;
					Manuver.IsManuvering = true;
				} else if (message.contains("left")) {
					manuverRight = false;
					Manuver.IsManuvering = true;
				}
			}

			// Activate UseBaritone if no clear path
			if (message.equals("null")) {
				if (GetPath.ShouldManuver(false)) {
					if (Settings.UseBaritone == true) {
						ActivateUseBaritone();
					} else {
						NoUsebaritoneMessage();
					}
				}
			}
			
			if (message.contains("No need for manuver")) {
				if (GetPath.ShouldManuver(false) == false) {
					if (Manuver.IsCentered(Main.z)) {
						Manuver.IsManuvering = false;
					}
				}
			}
			
			if (Manuver.IsManuvering == true) {
				Center.ManuverCenter(manuverRight);
			}
			
			//Return to startup position
			if (ReturnToStart.CanReturn()) {
				if (ReturnToStart.IsStartClear()) {
					if (ReturnToStart.IsNotAtStartup()) {
						if (ReturnToStart.HasPassedManuver()) {
							if (Center.IsManuverCentered(Main.z)) {
								ReturnToStart.StartReturn();
							} else {
								if (Center.ShouldCenterBackwards()) {
									Center.CenterBackwards();
								} else {
									Center.CenterForward();
								}
							}
						}
					}
				}
			}
		}
	}
} catch (Exception e16) {
	System.out.println("Exception in main class Elytrabot");
	System.out.println(e16.getCause());
}
}
		
		
		
		
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
		
	
	//Methods

	//Checks needed stuff on startup
	public static void Check() {
		direction = mc.player.getHorizontalFacing();
		StartupPosX = (int) mc.player.posX;
		StartupPosZ = (int) mc.player.posZ;
		StartupPosY = (int) mc.player.posY;
		
		if (direction.equals(EnumFacing.NORTH)) {
			x = true;
			z = false;
			mc.player.rotationYaw = 180;
		} else if (direction.equals(EnumFacing.WEST)) {
			z = true;
			x = false;
			mc.player.rotationYaw = 90;
		} else if (direction.equals(EnumFacing.EAST)) {
			z = true;
			x = false;
			mc.player.rotationYaw = -90;
		} else if (direction.equals(EnumFacing.SOUTH)) {
			x = true;
			z = false;
			mc.player.rotationYaw = 0;
		}
	}

	public static void NoUsebaritoneMessage() {
		toggle = false;
		UnCheck();
		Gui.TurnOff();
		mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.RED + "Were stuck... UseBaritone setting would help!"));
	}
	
	//Sets them to default when u do ++stop
	public static void UnCheck() {
		x = false;
		z = false;
		takeoff = false;
		baritonetoggle = false;
		com.bebeli555.ElytraBot.AutoRepair.AutoRepair = false;
	}

	//Flight method
	public static void Flight(double speed, boolean right, boolean straight, double y) {
		if (mc.player.isElytraFlying()) {
			if (straight == true) {
				if (direction.equals(EnumFacing.NORTH)) {
					SetMotion(0, y, -speed);
				} else if (direction.equals(EnumFacing.WEST)) {
					SetMotion(-speed, y, 0);
				} else if (direction.equals(EnumFacing.EAST)) {
					SetMotion(speed, y, 0);
				} else if (direction.equals(EnumFacing.SOUTH)) {
					SetMotion(0, y, speed);
				}
			} else if (right == true) {
				if (direction.equals(EnumFacing.NORTH)) {
					SetMotion(ManuverSpeed, y, 0);
				} else if (direction.equals(EnumFacing.WEST)) {
					SetMotion(0, y, ManuverSpeed);
				} else if (direction.equals(EnumFacing.EAST)) {
					SetMotion(0, y, ManuverSpeed);
				} else if (direction.equals(EnumFacing.SOUTH)) {
					SetMotion(ManuverSpeed, y, 0);
				}
			} else {
				if (direction.equals(EnumFacing.NORTH)) {
					SetMotion(-ManuverSpeed, y, 0);
				} else if (direction.equals(EnumFacing.WEST)) {
					SetMotion(0, y, -ManuverSpeed);
				} else if (direction.equals(EnumFacing.EAST)) {
					SetMotion(0, y, -ManuverSpeed);
				} else if (direction.equals(EnumFacing.SOUTH)) {
					SetMotion(-ManuverSpeed, y, 0);
				}
			}
		}
	}
	
	public static void SetMotion(double X, double Y, double Z) {
		mc.player.motionX = X;
		mc.player.motionZ = Z;
		mc.player.motionY = Y;
	}
	
	public static void setMove(double speed, boolean right, boolean straight, double y) {
		MoveRight = right;
		MoveStraight = straight;
	}

	public static void useBaritone() {
		//no risk getting coords leaked. if someone doesnt have baritone installed
		// Lock yaw and pitch
		LockPitchAndYaw();
		int GoX = StartupPosX - (int)mc.player.posX;
		int GoZ = StartupPosZ - (int)mc.player.posZ;
		int GoY = StartupPosY - (int)mc.player.posY;
		// Use baritone
		if (direction.equals(EnumFacing.NORTH)) {
			mc.player.sendChatMessage("#goto ~" + GoX + " ~" +  GoY + " ~-35");
		} else if (direction.equals(EnumFacing.WEST)) {
			mc.player.sendChatMessage("#goto ~-35 ~" + GoY + " ~" + GoZ);
		} else if (direction.equals(EnumFacing.EAST)) {
			mc.player.sendChatMessage("#goto ~35 ~" + GoY + " ~" + GoZ);
		} else if (direction.equals(EnumFacing.SOUTH)) {
			mc.player.sendChatMessage("#goto ~" + GoX + " ~" + GoY + " ~35");
		}

	}
	
	
	public static int GetPick() {
		for (int i = 0; i < 9; ++i) {
			final ItemStack stack = mc.player.inventory.getStackInSlot(i);
			if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemPickaxe) {
				return i;

			}
		}
		return -1;
	}
	
	public static void StartAnyway() {
		status = "Going Straight";
		Flight(1.8, false, true, 0);
	}
	
	public static void LockPitchAndYaw() {
		mc.player.rotationPitch = (float) -4.5;
		if (direction.equals(EnumFacing.NORTH)) {
			mc.player.rotationYaw = 180;
		} else if (direction.equals(EnumFacing.WEST)) {
			mc.player.rotationYaw = 90;
		} else if (direction.equals(EnumFacing.EAST)) {
			mc.player.rotationYaw = -90;
		} else if (direction.equals(EnumFacing.SOUTH)) {
			mc.player.rotationYaw = 0;
		}
	}
	
	public static void CheckBaritone() {
		if (com.bebeli555.ElytraBot.Settings.UseBaritone == true) {
			delay21++;
			if (delay21 == 2) {
				barposx = (int) mc.player.posX;
				barposz = (int) mc.player.posZ;
			}
			if (x == true) {
				if (delay21 > 100) {
					if (barposz == (int) mc.player.posZ) {
						ActivateUseBaritone();
						delay21 = 0;
					} else {
						delay21 = 0;
					}
				}
			} else if (z == true) {
				if (delay21 > 100) {
					if (barposx == (int) mc.player.posX) {
						ActivateUseBaritone();
						delay21 = 0;
					} else {
						delay21 = 0;
					}
				}
			}

		}
	}
	
	public static void CheckTakeoffBaritone() {
		if (com.bebeli555.ElytraBot.Settings.UseBaritone == true) {
			delay18++;
			if (delay18 > 400) {
				ActivateUseBaritone();
				delay18 = 0;
			}
		}
	}
	
	public static void ActivateUseBaritone() {
		baritonetoggle = true;
		lmao5 = false;
		toggle = false;
		Manuver.HasCentered = false;
	}
	
	public static void UseBaritoneSetting() {
		if (baritonetoggle == true) {
			TakeOff.ActivatePacketFly = false;
			if (mc.player.onGround == true) {
				//Walks 35 blocks straight with baritone if player is stuck and then continues flying
				bdelay2++;
				if (lmao5 == false) {
					useBaritone();
					lmao5 = true;
				}
				
				if (bdelay2 == 5) {
					okposx = (int)mc.player.posX;
					okposz = (int)mc.player.posZ;
				} else if (bdelay2 > 50) {
					if ((int) mc.player.posX == okposx) {
						if ((int) mc.player.posZ == okposz) {
							baritonetoggle = false;
							toggle = true;
							bdelay2 = 0;
							mc.player.sendChatMessage("#cancel");
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
	}
	
	public static void CheckIfBaritoneIsInstalled() {
		if (baritonecheck == false) {
			baritonecheck = true;
			try {
				if (!BaritoneAPI.getProvider().getAllBaritones().isEmpty()) {
					Settings.UseBaritone = true;
				}
			} catch (NoClassDefFoundError e5) {
				
			}
		}
	}
	
	public static void GoStraight() {
		MoveOn = true;
		setMove(0, false, true, 0);
		status = "Going Straight";
		ElytraFly.FlyMinus = 0;
	}
 }