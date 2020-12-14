package me.bebeli555.ElytraBot.Highway;

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
import me.bebeli555.ElytraBot.ElytraFly;
import me.bebeli555.ElytraBot.Gui.Gui;
import me.bebeli555.ElytraBot.Settings.Settings;
import com.mojang.realmsclient.gui.ChatFormatting;
import baritone.api.BaritoneAPI;

public class Main {
	//Variables are cool!
	static Minecraft mc = Minecraft.getMinecraft();
	public static boolean toggle = false;
	public static EnumFacing direction;
	public static int StartupPosX;
	public static int StartupPosZ;
	public static int StartupPosY;
	public static boolean z = false;
	public static boolean x = false;
	static boolean takeoff = false;
	static boolean mine = false;
	static int okposx;
	static int okposz;
	public static boolean baritonetoggle = false;
	public static boolean MoveRight = false;
	public static boolean MoveStraight = false;
	public static boolean MoveOn = false;
	static int delay21 = 0;
	public static int delay5 = 0;
	static int bdelay2 = 0;
	static int barposx = 0;
	static int barposz = 0;
	public static int delay18 = 0;
	static int delay72 = 0;
	static boolean lmao5 = false;
	public static String status;
	static BlockPos playerPos;
	static boolean baritonecheck = false;
	public static boolean CantContinue = false;
	static int delay112 = 0;
	static int delay666 = 0, delay51 = 0;
	public static double FlySpeed = 0;
	static boolean check = false, StayStill = false;
	public static int LastX, LastZ;
	public static boolean manuverRight = false;
	public static double ManuverSpeed;

	@SubscribeEvent
	public void ServerLeave (PlayerLoggedOutEvent e) {
		Gui.TurnOff();
	}
	
	@SubscribeEvent
	public void onUpdate(TickEvent.ClientTickEvent e) {
		try {
			// Check if baritone is installed
			CheckIfBaritoneIsInstalled();

			// uses baritone if the player is stuck...
			UseBaritoneSetting();

			if (toggle) {
				MoveOn = false;
				
				if (!takeoff) {
					status = "Attempting to TakeOff";

					// Baritone backup for takeoff
					if (status.equals("Attempting to TakeOff")) {
						if (Settings.getBoolean("UseBaritone")) {
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

					if (mc.player.onGround) {
						// Activate baritone if above block is bedrock
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

					// Mine block above head if its preventing takeoff
					playerPos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
					BlockPos Check = playerPos.add(0, 2, 0);
					BlockPos Check2 = playerPos.add(0, 2, 1);
					BlockPos Check3 = playerPos.add(0, 2, -1);
					BlockPos CheckM = playerPos.add(0, 2, 0);
					BlockPos CheckM2 = playerPos.add(1, 2, 0);
					BlockPos CheckM3 = playerPos.add(-1, 2, 0);
					if (mc.player.onGround) {
						if (!mine) {
							if (GetPick() != -1) {
								if (x) {
									if (mc.world.getBlockState(Check).getBlock() == Blocks.NETHERRACK) {
										mine = true;
									} else if (mc.world.getBlockState(Check2).getBlock() == Blocks.NETHERRACK) {
										mine = true;
									} else if (mc.world.getBlockState(Check3).getBlock() == Blocks.NETHERRACK) {
										mine = true;
									}
								} else if (z) {
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

					if (!mine) {
						TakeOff.TakeOffMethod(false, Settings.getBoolean("PacketFly"), Settings.getBoolean("SlowGlide"));
					} else {
						// Mines block above head
						delay72++;
						if (x) {
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
							} else if (delay72 == 20) {
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
						} else if (z) {
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
							} else if (delay72 == 20) {
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

					BlockPos Player = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
					if (!GetPath.IsBlockInRenderDistance(Player)) {
						SetMotion(0, -(Settings.getDouble("GlideSpeed") / 10000f), 0);
						status = "Waiting Chunks to load";
						return;
					}
					
					// Y Center go up
					if (Settings.getDouble("PrefY") == -1) {
						if (!Center.IsYCentered() && mc.player.isElytraFlying() && TakeOff.IsClearToCenter()) {
							delay5++;
							if (delay5 > 25) {
								TakeOff.YCenter();
								return;
							}
						} else {
							delay5 = 0;
						}
						ElytraFly.YCenter = false;
					}

					// Prevent pitch going too up or too low or 2bs anticheat will start
					// rubberbanding you
					if (mc.player.rotationPitch < -5 || mc.player.rotationPitch > 45) {
						mc.player.rotationPitch = 0;
					}

					// Activate baritone support if player is stuck
					CheckBaritone();

					// Check if need of takeoff
					if (!mc.player.isElytraFlying()) {
						takeoff = false;
						return;
					}

					// Pathfinding and ElytraFly control
					String message = GetPath.WhereToGo();

					// Center
					if (message.equals("Right") || message.equals("Left")) {
						if (!Center.IsManuverCentered(Main.z)) {
							CantContinue = true;
							if (Center.ShouldCenterBackwards()) {
								Center.CenterBackwards();
							} else {
								Center.CenterForward();
							}
						}
					}

					if (Center.IsManuverCentered(Main.z)) {
						CantContinue = false;
					}

					// Center
					if (!Center.IsCentered(Main.z)) {
						delay666++;
						if (delay666 > 10) {
							TakeOff.TakeOffCenter();
							return;
						}
					} else {
						delay666 = 0;
					}
					
					//Stay still for moment if stuck.
					if (StayStill) {
						SetMotion(0, -(Settings.getDouble("GlideSpeed") / 10000f), 0);
						delay51++;
						if (delay51 > 20) {
							StayStill = false;
						}
						return;
					}

					// Control ElytraFly
					if (!CantContinue) {
						Main.MoveOn = true;
						ElytraFly.FlyMinus = 0;
						if (message.contains("Forward")) {
							Main.ManuverSpeed = 0.1;
							if (Center.IsCentered(Main.z)) {
								Main.setMove(0, false, true, 0);
							}
						} else if (message.contains("Backwards")) {
							Main.ManuverSpeed = 0.1;
							if (Center.IsCentered(Main.z)) {
								ElytraFly.FlyMinus = Settings.getDouble("Speed") * 2;
								Main.setMove(0, false, true, 0);
							}
						} else if (message.contains("Right")) {
							Main.ManuverSpeed = 1;
							Main.setMove(0, true, false, 0);
						} else if (message.contains("Left")) {
							Main.ManuverSpeed = 1;
							Main.setMove(0, false, false, 0);
						}
					}
					
					// Slow down
					if (GetPath.ShouldSlowDown(GetPath.Path)) {
						Settings.setValue("Speed", 1);
					} else {
						Settings.setValue("Speed", FlySpeed);
					}

					// Set normal speed back backsafe
					if (message.equals("Forward")) {
						delay112++;
						if (delay112 > 20) {
							Settings.setValue("Speed", FlySpeed);
						}
					} else {
						delay112 = 0;
					}

					// Fix status
					if (direction.equals(EnumFacing.SOUTH)) {
						if (message.contains("Right")) {
							status = "Left";
						} else if (message.contains("Left")) {
							status = "Right";
						} else {
							status = message;
						}
					} else {
						status = message;
					}
				}
			}
		} catch (Exception e22) {
			System.out.println("Exception in main class ElytraBot");
			e22.printStackTrace();
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
		me.bebeli555.ElytraBot.Settings.AutoRepair.AutoRepair = false;
	}

	//Flight method
	public static void Flight(double speed, boolean right, boolean straight, double y) {
		if (mc.player.isElytraFlying()) {
			if (straight) {
				if (direction.equals(EnumFacing.NORTH)) {
					SetMotion(0, y, -speed);
				} else if (direction.equals(EnumFacing.WEST)) {
					SetMotion(-speed, y, 0);
				} else if (direction.equals(EnumFacing.EAST)) {
					SetMotion(speed, y, 0);
				} else if (direction.equals(EnumFacing.SOUTH)) {
					SetMotion(0, y, speed);
				}
			} else if (right) {
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
		//no risk getting coords leaked. if someone doesn't have baritone installed
		// Lock yaw and pitch
		LockPitchAndYaw();
		int GoX = StartupPosX - (int)mc.player.posX;
		int GoZ = StartupPosZ - (int)mc.player.posZ;
		int GoY = StartupPosY - (int)mc.player.posY;
		int blocks = (int)Settings.getDouble("usebaritoneBlocks");
		// Use baritone
		if (direction.equals(EnumFacing.NORTH)) {
			mc.player.sendChatMessage("#goto ~" + GoX + " ~" +  GoY + " ~-" + blocks);
		} else if (direction.equals(EnumFacing.WEST)) {
			mc.player.sendChatMessage("#goto ~-" + blocks + " ~" + GoY + " ~" + GoZ);
		} else if (direction.equals(EnumFacing.EAST)) {
			mc.player.sendChatMessage("#goto ~" + blocks + " ~" + GoY + " ~" + GoZ);
		} else if (direction.equals(EnumFacing.SOUTH)) {
			mc.player.sendChatMessage("#goto ~" + GoX + " ~" + GoY + " ~" + blocks);
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
	
	public static void LockYaw() {
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
		if (Settings.getBoolean("UseBaritone")) {
			delay21++;
			if (delay21 == 2) {
				barposx = (int) mc.player.posX;
				barposz = (int) mc.player.posZ;
			}
			if (x) {
				if (delay21 > 100) {
					if (barposz == (int) mc.player.posZ)
						ActivateUseBaritone();
					delay21 = 0;
				}
			} else if (z) {
				if (delay21 > 100) {
					if (barposx == (int) mc.player.posX) {
						ActivateUseBaritone();
						delay21 = 0;
					}
				}
			}
		}
	}
	
	public static void CheckTakeoffBaritone() {
		if (Settings.getBoolean("UseBaritone")) {
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
	}
	
	public static void UseBaritoneSetting() {
		if (baritonetoggle) {
			TakeOff.ActivatePacketFly = false;
			if (mc.player.onGround) {
				//Walks 35 blocks straight with baritone if player is stuck and then continues flying
				bdelay2++;
				if (!lmao5) {
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
		if (!baritonecheck) {
			baritonecheck = true;
			try {
				if (!BaritoneAPI.getProvider().getAllBaritones().isEmpty())
					Settings.setValue("UseBaritone", true);

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