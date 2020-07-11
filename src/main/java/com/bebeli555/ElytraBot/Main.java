package com.bebeli555.ElytraBot;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.File;
import java.util.Objects;
import java.util.Random;

import com.mojang.realmsclient.gui.ChatFormatting;

import baritone.api.BaritoneAPI;
import net.minecraft.block.material.Material;

public class Main {
	//God damn! thats alot of variables
	static Minecraft mc = Minecraft.getMinecraft();
	static boolean toggle = false;
	static EnumFacing direction;
	int delay29 = 0;
	static int StartupPosX;
	static int StartupPosZ;
	static int StartupPosY;
	static double StartupPosZ2;
	static double StartupPosX2;
	static int ManuverPosX;
	static int ManuverPosZ;
	static boolean z = false;
	static boolean x = false;
	static boolean manuver = true;
	static int manuverdelay = 0;
	static boolean isgoing = false;
	static int delay2 = 0;
	static int somedelay = 0;
	static int delay3 = 0;
	static boolean takeoff = false;
	static int delay69;
	static boolean right2 = false;
	boolean mine = false;
	static int okposx;
	static int okposz;
	static boolean manuver1 = false;
	static boolean manuver2 = false;
	static boolean manuver3 = false;
	static boolean manuverM1 = false;
	static boolean manuverM2 = false;
	static boolean manuverM3 = false;
	static boolean bebeli555 = false;
	static boolean baritonetoggle = false;
	static boolean MoveRight = false;
	static boolean MoveStraight = false;
	static boolean MoveOn = false;
	static double MoveY = 0;
	int delay21 = 0;
	int bdelay2 = 0;
	int barposx = 0;
	int barposz = 0;
	int delay18 = 0;
	int delay72 = 0;
	static boolean lmao5 = false;
	static String status;
	static BlockPos playerPos;
	static int stutter = 0;
	static int delay15 = 0;
	static boolean ThisX = false;
	static boolean ThisZ = false;
	static boolean manuverboolean = false;
	static boolean idkshit = false;
	static int delay50 = 0;
	static int delay11 = 0;
	static int delay82 = 0;
	static boolean baritonecheck = false;
	
	// Elytra Highway Bot V1.7
	// Made by: bebeli555
	
	//Starts going Straight again when at the startuppos when returning to it.
	//Faster Method than TickEvent so it shouldnt miss it
	@SubscribeEvent
	public void bebeli555OnTop(LivingUpdateEvent e) {
		if (toggle == true) {
			try {
			if (e.getEntity().getName() == mc.player.getName()) {				
				//Start going straight again.
				if (status == "Going back to Startup Position") {
					if (StartupPosZ2 == Math.round(mc.player.posZ * 10) / 10.0) {
						StartAnyway();
					} else if (StartupPosX2 == Math.round(mc.player.posX * 10) / 10.0) {
						StartAnyway();
					}
				}
			}
			}catch (NullPointerException e3) {
				
			}
		}
	}
	
	@SubscribeEvent
	public void ServerLeave (PlayerLoggedOutEvent e) {
		toggle = false;
		com.bebeli555.ElytraBot.Main.UnCheck();
		com.bebeli555.ElytraBot.Diagonal.UnCheck();
		com.bebeli555.ElytraBot.StopAt.toggle = false;
	}

	@SubscribeEvent
	public void onUpdate(TickEvent.ClientTickEvent e) {
		// Check if baritone is installed
		if (baritonecheck == false) {
			baritonecheck = true;
			try {
				if (!BaritoneAPI.getProvider().getAllBaritones().isEmpty()) {
					Settings.UseBaritone = true;
				}
			} catch (NoClassDefFoundError e5) {
				
			}
		}
		
		try {
		//uses baritone if the player is stuck...
		if (baritonetoggle == true) {
			TakeOff.ActivatePacketFly = false;
			if (mc.player.onGround == true) {
				//Walks 35 blocks straight with baritone if player is stuck and then continues flying
				bdelay2++;
				if (lmao5 == false) {
					this.useBaritone();
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
			MoveOn = false;

			if (takeoff == false) {
				status = "Attempting to TakeOff";
				delay29 = 823;
				
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
			// End Of TakeOff
			
			
			
			
			
			
			
			
		} else {
			//Prevent pitch going too up or too low or 2bs anticheat will start rubberbanding you
			if (mc.player.rotationPitch < -5 || mc.player.rotationPitch > 45) {
				mc.player.rotationPitch = 0;
			}
			
			if (status == "Attempting to TakeOff") {
				delay18 = delay18;
			} else {
				delay18 = 0;
			}
			
			// Activate baritone support if player is stuck
			if (status == "Going Straight" || status == "Going back to Startup Position" || status == "Avoiding Collision") {
				if (com.bebeli555.ElytraBot.Settings.UseBaritone == true) {
					delay21++;
					if (delay21 == 2) {
						barposx = (int) mc.player.posX;
						barposz = (int) mc.player.posZ;
					}
					if (x == true) {
						if (delay21 > 100) {
							if (barposz == (int) mc.player.posZ) {
								baritonetoggle = true;
								lmao5 = false;
								toggle = false;
								delay21 = 0;
							} else {
								delay21 = 0;
							}
						}
					} else if (z == true) {
						if (delay21 > 100) {
							if (barposx == (int) mc.player.posX) {
								baritonetoggle = true;
								lmao5 = false;
								toggle = false;
								delay21 = 0;
							} else {
								delay21 = 0;
							}
						}
					}

				}
			} else {
				delay21 = 0;
			}

			if (status == "Going back to Startup Position") {
				delay69++;
				if (delay69 > 100) {
					manuver = false;
					bebeli555 = false;
					manuver1 = false;
					manuver2 = false;
					manuver3 = false;
					manuverM1 = false;
					manuverM2 = false;
					manuverM3 = false;
					
					delay69 = 0;
				}
			}
			 

			if (mc.player.isElytraFlying()) {
				int ok2 = 0;
			} else {
				takeoff = false;
			}

			// PathFind
			boolean check = false;
			boolean check1 = false;
			boolean check2 = false;
			boolean check3 = false;
			boolean checkM1 = false;
			boolean checkM2 = false;
			boolean checkM3 = false;

			// Set check variables. The delay is for if theres blocks in like 1 ahead 1 left 1 right but those in the right and left
			// Are 2 blocks left it would choose the path and go straight to the 1 in the
			// left if there was no delay
			if (isgoing == false) {
				if (direction.equals(EnumFacing.NORTH)) {
					check = this.GetPath(check, 0, 69, true);
					check1 = this.GetPath(check1, 1, 69, true);
					check2 = this.GetPath(check2, 2, 69, true);
					check3 = this.GetPath(check3, 3, 69, true);
					checkM1 = this.GetPath(checkM1, -1, 69, true);
					checkM2 = this.GetPath(checkM2, -2, 69, true);
					checkM3 = this.GetPath(checkM3, -3, 69, true);
				} else if (direction.equals(EnumFacing.WEST)) {
					check = this.GetPath(check, 0, 0, true);
					check1 = this.GetPath(check1, 0, 1, true);
					check2 = this.GetPath(check2, 0, 2, true);
					check3 = this.GetPath(check3, 0, 3, true);
					checkM1 = this.GetPath(checkM1, 0, -1, true);
					checkM2 = this.GetPath(checkM2, 0, -2, true);
					checkM3 = this.GetPath(checkM3, 0, -3, true);
				} else if (direction.equals(EnumFacing.EAST)) {
					check = this.GetPath(check, 0, 0, false);
					check1 = this.GetPath(check1, 0, 1, false);
					check2 = this.GetPath(check2, 0, 2, false);
					check3 = this.GetPath(check3, 0, 3, false);
					checkM1 = this.GetPath(checkM1, 0, -1, false);
					checkM2 = this.GetPath(checkM2, 0, -2, false);
					checkM3 = this.GetPath(checkM3, 0, -3, false);
				} else if (direction.equals(EnumFacing.SOUTH)) {
					check = this.GetPath(check, 0, 69, false);
					check1 = this.GetPath(check1, 1, 69, false);
					check2 = this.GetPath(check2, 2, 69, false);
					check3 = this.GetPath(check3, 3, 69, false);
					checkM1 = this.GetPath(checkM1, -1, 69, false);
					checkM2 = this.GetPath(checkM2, -2, 69, false);
					checkM3 = this.GetPath(checkM3, -3, 69, false);
				}
			}

			//go to startup position if path is clear
			if (status == "Going Straight") {
				delay50++;
				if (delay50 > 100) {
					delay50 = 0;
					if (check == false) {
						if (z == true) {
							if ((int) mc.player.posX != (int) mc.player.lastTickPosX) {
								if ((int)mc.player.posZ != StartupPosZ) {
									bebeli555 = true;
								}
							}
						} else {
							if ((int) mc.player.posZ != (int) mc.player.lastTickPosZ) {
								if ((int)mc.player.posX != StartupPosX) {
									bebeli555 = true;
								}
							}
						}
					}
				}
			} else {
				delay50 = 0;
			}
			
			//Check if path would hit highway wall and choose another path
			if (mc.player.isElytraFlying()) {
				if (WillHitWall(false) == true)  {
					check1 = true;
					check2 = true;
					check3 = true;
				} else if (WillHitWall(true) == true) {
					checkM1 = true;
					checkM2 = true;
					checkM3 = true;
				}
			}
			
			//Find the best path to go and set the other variables
			//So they can be used with the delays
			if (mc.player.isElytraFlying()) {
				if (check == true) {
					if (check1 == false) {
						manuver1 = true;
					} else if (checkM1 == false) {
						manuverM1 = true;
					} else if (check2 == false) {
						manuver2 = true;
					} else if (checkM2 == false) {
						manuverM2 = true;
					} else if (check3 == false) {
						manuver3 = true;
					} else if (checkM3 == false) {
						manuverM3 = true;
					} else {
						if (Settings.UseBaritone == true) {
							baritonetoggle = true;
							lmao5 = false;
							toggle = false;
						} else {
							NoUsebaritoneMessage();
						}
					}
				}
			}

			// Go straight
			if (mc.player.isElytraFlying()) {
				if (manuver == false) {
					status = "Going Straight";
					MoveOn = true;
					this.setMove(1.8, false, true, 0);
				}
			}
			
			//Set manuver
			if (mc.player.isElytraFlying()) {
				if (manuver1 == true) {
					this.Manuver(1, true, 1.8);
				} else if (manuverM1 == true) {
					this.Manuver(1, false, 1.8);
				} else if (manuver2 == true) {
					this.Manuver(2, true, 1.8);
				} else if (manuverM2 == true) {
					this.Manuver(2, false, 1.8);
				} else if (manuver3 == true) {
					this.Manuver(3, true, 1.8);
				} else if (manuverM3 == true) {
					this.Manuver(3, false, 1.8);
				}
			}

			//Start returning to startup position
			if (bebeli555 == true) {
				if (stutter < 3) {
					if (x == true) {
						if (StartupPosX2 < Math.round(mc.player.posX * 10) / 10.0) {
							this.bebeli555(false);
							ThisX = true;
						} else {
							this.bebeli555(true);
							if (ThisX == true) {
								ThisX = false;
								stutter++;
							}
						}
					} else if (z == true) {
						if (StartupPosZ2 < Math.round(mc.player.posZ * 10) / 10.0) {
							this.bebeli555(false);
							ThisZ = true;
						} else {
							this.bebeli555(true);
							if (ThisZ == true) {
								ThisZ = false;
								stutter++;
							}
						}
					}
				} else {
					StartAnyway();
				}
			}
			// Checks if Elytra is needing repair and if so repairs it
			if (com.bebeli555.ElytraBot.Settings.AutoRepair == true) {
				delay29++;
				if (delay29 > 500) {
					this.CheckDur();
					delay29 = 0;
				}
			}

			delay15++;
			if (delay15 > 100) {
				stutter = 0;
				delay15 = 0;
			}
			
			//start going straight if cannot return to startup pos
			if (status == "Going back to Startup Position") {
				delay82++;
				if (delay82 > 60) {
					StartAnyway();
					delay82 = 0;
				}
			} else {
				delay82 = 0;
			}
			//End of pathfind
		}
	}
} catch (NullPointerException e16) {

}
}
		
		
		
		
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
		
	
	//Methods

	//Checks needed stuff on startup
	public static void Check() {
		direction = mc.player.getHorizontalFacing();
		double StartPosX = mc.player.posX;
		double StartPosZ = mc.player.posZ;
		StartupPosX = (int) StartPosX;
		StartupPosZ = (int) StartPosZ;
		StartupPosY = (int) mc.player.posY;
		
		//Startup Positions. Also they get centered
		if (mc.player.posX < -5) {
			StartupPosX2 = (int)mc.player.posX - 0.5;
		} else {
			StartupPosX2 = (int)mc.player.posX + 0.5;
		}
		
		if (mc.player.posZ < -5) {
			StartupPosZ2 = (int)mc.player.posZ - 0.5;
		} else {
			StartupPosZ2 = (int)mc.player.posZ + 0.5;
		}
		
		if (direction.equals(EnumFacing.NORTH)) {
			x = true;
			mc.player.rotationYaw = 180;
		} else if (direction.equals(EnumFacing.WEST)) {
			z = true;
			mc.player.rotationYaw = 90;
		} else if (direction.equals(EnumFacing.EAST)) {
			z = true;
			mc.player.rotationYaw = -90;
		} else if (direction.equals(EnumFacing.SOUTH)) {
			x = true;
			mc.player.rotationYaw = 0;
		}
		
		if (StartupPosX == 0) {
			int allgood;
		} else if (StartupPosZ == 0) {
			int allgood2;
		}
		ManuverPosX = (int) mc.player.posX;
		ManuverPosZ = (int) mc.player.posZ;

		
	}

	public static void NoUsebaritoneMessage() {
		toggle = false;
		UnCheck();
		mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.RED + "Were stuck... UseBaritone setting would help!"));
	}
	
	//Sets them to default when u do ++stop
	public static void UnCheck() {
		x = false;
		z = false;
		manuver = false;
		manuverdelay = 0;
		delay2 = 0;
		delay3 = 0;
		takeoff = false;
		manuver1 = false;
		manuver2 = false;
		manuver3 = false;
		manuverM1 = false;
		manuverM2 = false;
		manuverM3 = false;
		baritonetoggle = false;
		com.bebeli555.ElytraBot.AutoRepair.AutoRepair = false;
		idkshit = false;
	}

	//Check if upcoming blocks are air or lava if not then set the variables to true and later on
	//Do a manuver to avoid going 9/11 to them
	public boolean GetPath(boolean kek, int x, int z, boolean minus) {
		Vec3d vec = Vec.getInterpolatedPos(mc.player, mc.getRenderPartialTicks());
		BlockPos playerPos = new BlockPos(vec);
		
		if (minus == true) {
			for (int i = -1; i <= 5; i++) {
				int ok = i - i - i;
				if (kek == false) {
					BlockPos x2;
					if (z == 69) {
						BlockPos Check = playerPos.add(x, 1, ok);
						if (this.z == true) {
							if (Math.round(mc.player.posX * 10) / 10.0 == Math.round(mc.player.lastTickPosX * 10) / 10.0 && mc.world.getBlockState(Check).getBlock() != Blocks.AIR) {
								x2 = playerPos.add(x, 1, ok);
							} else {
								x2 = playerPos.add(x, 0, ok);
							}
						} else {
							if (Math.round(mc.player.posZ * 10) / 10.0 == Math.round(mc.player.lastTickPosZ * 10) / 10.0 && mc.world.getBlockState(Check).getBlock() != Blocks.AIR) {
								x2 = playerPos.add(x, 1, ok);
							} else {
								x2 = playerPos.add(x, 0, ok);
							}
						}
					} else {
						BlockPos Check = playerPos.add(ok, 1, z);
						if (this.z == true) {
							if (Math.round(mc.player.posX * 10) / 10.0 == Math.round(mc.player.lastTickPosX * 10) / 10.0 && mc.world.getBlockState(Check).getBlock() != Blocks.AIR) {
								x2 = playerPos.add(ok, 1, z);
							} else {
								x2 = playerPos.add(ok, 0, z);
							}
						} else {
							if (Math.round(mc.player.posZ * 10) / 10.0 == Math.round(mc.player.lastTickPosZ * 10) / 10.0 && mc.world.getBlockState(Check).getBlock() != Blocks.AIR) {
								x2 = playerPos.add(ok, 1, z);
							} else {
								x2 = playerPos.add(ok, 0, z);
							}
						}
					}
					if (mc.world.getBlockState(x2).getMaterial().isSolid()) {
						if (mc.world.getBlockState(x2).getBlock() != Blocks.STANDING_BANNER) {
							if (mc.world.getBlockState(x2).getBlock() != Blocks.STANDING_SIGN) {
								kek = true;
								return true;
							}
						}
					} else {
						kek = false;
					}
				}
			}
		} else {
			for (int i = -1; i <= 5; i++) {
				if (kek == false) {
					BlockPos x2;
					if (z == 69) {
						BlockPos Check = playerPos.add(x, 1, i);
						if (Math.round(mc.player.posZ * 10) / 10.0 == Math.round(mc.player.lastTickPosZ * 10) / 10.0 && mc.world.getBlockState(Check).getBlock() != Blocks.AIR) {
							x2 = playerPos.add(x, 1, i);
						} else {
							x2 = playerPos.add(x, 0, i);
						}
					} else {
						BlockPos Check = playerPos.add(i, 1, z);
						if (Math.round(mc.player.posX * 10) / 10.0 == Math.round(mc.player.lastTickPosX * 10) / 10.0 && mc.world.getBlockState(Check).getBlock() != Blocks.AIR) {
							x2 = playerPos.add(i, 1, z);
						} else {
							x2 = playerPos.add(i, 0, z);
						}
					}
					if (mc.world.getBlockState(x2).getMaterial().isSolid()) {
						if (mc.world.getBlockState(x2).getBlock() != Blocks.STANDING_BANNER) {
							if (mc.world.getBlockState(x2).getBlock() != Blocks.STANDING_SIGN) {
								kek = true;
								return true;
							}
						}
					} else {
						kek = false;
					}
				}
			}
		}
		return false;
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
					SetMotion(0.18, y, 0);
				} else if (direction.equals(EnumFacing.WEST)) {
					SetMotion(0, y, 0.18);
				} else if (direction.equals(EnumFacing.EAST)) {
					SetMotion(0, y, 0.18);
				} else if (direction.equals(EnumFacing.SOUTH)) {
					SetMotion(0.18, y, 0);
				}
			} else {
				if (direction.equals(EnumFacing.NORTH)) {
					SetMotion(-0.18, y, 0);
				} else if (direction.equals(EnumFacing.WEST)) {
					SetMotion(0, y, -0.18);
				} else if (direction.equals(EnumFacing.EAST)) {
					SetMotion(0, y, -0.18);
				} else if (direction.equals(EnumFacing.SOUTH)) {
					SetMotion(-0.18, y, 0);
				}
			}
		}
	}
	
	public static void SetMotion(double X, double Y, double Z) {
		mc.player.motionX = X;
		mc.player.motionZ = Z;
		mc.player.motionY = Y;
	}
	

	// Goes around the blocked path and returns to original path after the manuver
	public void Manuver(int blocks, boolean right, double speed) {
		status = "Avoiding Collision";
		manuver = true;
		isgoing = true;

		MoveOn = true;
		this.setMove(speed, right, false, -(Settings.GlideSpeed / 10000f));
		
		int PosX = (int) mc.player.posX;
		int PosZ = (int) mc.player.posZ;
		
		if (PosX < 0) {
			PosX = PosX - 1;
		}
		
		if (PosZ < 0) {
			PosZ = PosZ - 1;
		}
		if ((int)Math.round(mc.player.posX) == (ManuverPosX - blocks)) {
			MoveOn = true;
			setMove(1.8, false, true, 0);
			isgoing = false;
		} else if ((int)Math.round(mc.player.posZ) == (ManuverPosZ - blocks)) {
			MoveOn = true;
			setMove(1.8, false, true, 0);
			isgoing = false;
		} else if (PosX == (ManuverPosX + blocks)) {
			delay2++;
			if (delay2 > 4) {
				MoveOn = true;
				setMove(1.8, false, true, 0);
				isgoing = false;
			}
		} else if (PosZ == (ManuverPosZ + blocks)) {
			delay2++;
			if (delay2 > 4) {
				MoveOn = true;
				setMove(1.8, false, true, 0);
				isgoing = false;
			}
		} else if (PosZ == (ManuverPosZ - blocks)) {
			delay2++;
			if (delay2 > 4) {
				MoveOn = true;
				setMove(1.8, false, true, 0);
				isgoing = false;
			}
		} else if (PosX == (ManuverPosX - blocks)) {
			delay2++;
			if (delay2 > 4) {
				MoveOn = true;
				setMove(1.8, false, true, 0);
				isgoing = false;
			}
		}

		right2 = right;
		somedelay++;
		if (somedelay > 5) {
			if (z == true) {
				if ((int) mc.player.posX != (int) mc.player.lastTickPosX) {
					manuverboolean = true;
					somedelay = 0;
				}
			} else {
				if ((int) mc.player.posZ != (int) mc.player.lastTickPosZ) {
					manuverboolean = true;
					somedelay = 0;
				}
			}
		}
		
		if (manuverboolean == true) {
			manuverdelay++;
			if (manuverdelay > 10) {
				bebeli555 = true;
				manuverdelay = 0;
				manuverboolean = false;
			}
		}

	}
	
	public static void setMove(double speed, boolean right, boolean straight, double y) {
		MoveRight = right;
		MoveStraight = straight;
		MoveY = y;
	}

	public void useBaritone() {
		//no risk getting coords leaked. if someone doesnt have baritone installed
		// Lock yaw and pitch
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

	public void bebeli555(boolean right) {
		MoveOn = true;
		this.setMove(1.6, right, false, -(Settings.GlideSpeed / 10000f));
		status = "Going back to Startup Position";
		manuver1 = false;
		manuver2 = false;
		manuver3 = false;
		manuverM1 = false;
		manuverM2 = false;
		manuverM3 = false;
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
	
	//Activate ElytraRepair if durability is low
	public void CheckDur() {
		ItemStack elytra = mc.player.inventory.armorItemInSlot(2);
		int Durability = (elytra.getMaxDamage()-elytra.getItemDamage());
		if (Durability < 50) {
			com.bebeli555.ElytraBot.AutoRepair.AutoRepair = true;
		}
	}
	
	public static void StartAnyway() {
		delay69 = 0;
		status = "Going Straight";
		Flight(1.8, false, true, 0);
		bebeli555 = false;
		manuver = false;
		manuverdelay = 0;
		isgoing = false;
		delay2 = 0;
		delay3 = 0;
		manuver1 = false;
		manuver2 = false;
		manuver3 = false;
		manuverM1 = false;
		manuverM2 = false;
		manuverM3 = false;
		somedelay = 0;
	}
	
	//Check that manuver wont hit the wall so choose another one
	public static boolean WillHitWall(boolean Minus) {
		Vec3d vec = Vec.getInterpolatedPos(mc.player, mc.getRenderPartialTicks());
		BlockPos playerPos = new BlockPos(vec);
		
		if (status == "Going Straight") {
			if ((int) mc.player.posX == StartupPosX || (int) mc.player.posZ == StartupPosZ) {
				if (direction.equals(EnumFacing.NORTH) || direction.equals(EnumFacing.SOUTH)) {
					if (Minus == true) {
						BlockPos pos = playerPos.add(-1, 0, 0);
						if (IsSolid(pos)) {
							return true;
						}
					} else {
						BlockPos pos = playerPos.add(1, 0, 0);
						if (IsSolid(pos)) {
							return true;
						}
					}
				} else if (direction.equals(EnumFacing.WEST) || direction.equals(EnumFacing.EAST)) {
					if (Minus == true) {
						BlockPos pos = playerPos.add(0, 0, -1);
						if (IsSolid(pos)) {
							return true;
						}
					} else {
						BlockPos pos = playerPos.add(0, 0, 1);
						if (IsSolid(pos)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public static boolean IsSolid(BlockPos pos) {
		if (mc.world.getBlockState(pos).getBlock() != Blocks.STANDING_BANNER) {
			if (mc.world.getBlockState(pos).getBlock() != Blocks.STANDING_SIGN) {
				if (mc.world.getBlockState(pos).getMaterial().isSolid()) {
					return true;
				}
			}
		}
		return false;
	}
}