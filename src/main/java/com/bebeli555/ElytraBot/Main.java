package com.bebeli555.ElytraBot;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.Objects;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.block.material.Material;

public class Main {
	static Minecraft mc = Minecraft.getMinecraft();
	static boolean toggle = false;
	static EnumFacing direction;
	int delay = 0;
	boolean mess = false;
	static int StartupPosX;
	static int StartupPosZ;
	static double StartupPosZ2;
	static double StartupPosX2;
	static int ManuverPosX;
	static int ManuverPosZ;
	static boolean z = false;
	static boolean x = false;
	static boolean startreturn = false;
	static boolean manuver = true;
	static int manuverdelay = 0;
	static boolean isgoing = false;
	static int delay2 = 0;
	static int delay3 = 0;
	static boolean takeoff = false;
	int delay69;
	static boolean right2 = false;
	
	static boolean manuver1 = false;
	static boolean manuver2 = false;
	static boolean manuver3 = false;
	static boolean manuverM1 = false;
	static boolean manuverM2 = false;
	static boolean manuverM3 = false;
	static boolean cancelled = false;
	static boolean bebeli555 = false;
	
	
	static String status;

	// Elytra Highway Bot V1.0
	// Made by: bebeli555

	@SubscribeEvent
	public void onUpdate(TickEvent.ClientTickEvent e) {
		if (toggle == true) {
			if (cancelled == true) {
				mc.player.setVelocity(0, 0, 0);

			}

			if (takeoff == false) {
				status = "Attempting to TakeOff";
				delay++;
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
			//TakeOff
			if (mc.player.onGround == true) {
				mc.player.jump();
				delay = 0;
				mess = true;
			}
			if (mess == true) {
				if (delay == 12) {
					Objects.requireNonNull(mc.getConnection()).sendPacket(
							new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
				} else if (delay == 14) {
					cancelled = true;
				} else if (delay == 25) {
					cancelled = false;
					startreturn = false;
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
					bebeli555 = false;
					this.Flight(1.8, false, true);
				} else if (delay == 30) {
					mess = false;
					if (mc.player.isElytraFlying()) {
						takeoff = true;
					}
				}
			}

			// End Of TakeOff
		} else {
			
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
			 
			
			//lock yaw and pitch
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

			// Set check variables
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

			// Start going straight again if player is at their original location
			// After a manuver
			if (startreturn == true) {
				if (mc.player.isElytraFlying()) {
					if (x == true) {
						if (StartupPosX2 == Math.round(mc.player.posX * 10) / 10.0) {
							delay69 = 0;
							status = "Going Straight";
							this.Flight(1.8, false, true);
							bebeli555 = false;
							startreturn = false;
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
						}

					}
				} else if (z == true) {
					if (StartupPosZ2 == Math.round(mc.player.posZ * 10) / 10.0) {
						delay69 = 0;
						status = "Going Straight";
						this.Flight(1.8, false, true);
						bebeli555 = false;
						startreturn = false;
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

					}
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
					}
				}
			}

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

			// Go straight
			if (mc.player.isElytraFlying()) {
				if (manuver == false) {
					status = "Going Straight";
					this.Flight(1.8, false, true);
				}
			}

			if (bebeli555 == true) {
				if (right2 == true) {
					startreturn = true;
					this.Flight(1.6, false, false);
					status = "Going back to Startup Position";
					manuver1 = false;
					manuver2 = false;
					manuver3 = false;
					manuverM1 = false;
					manuverM2 = false;
					manuverM3 = false;

				} else {
					startreturn = true;
					this.Flight(1.6, true, false);
					status = "Going back to Startup Position";
					manuver1 = false;
					manuver2 = false;
					manuver3 = false;
					manuverM1 = false;
					manuverM2 = false;
					manuverM3 = false;

				}

			}
			// End of Pathfind
		}
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
		if (StartupPosX == 0) {
			StartupPosX2 = 0.5;
		} else {
			StartupPosX2 = Math.round(mc.player.posX * 10) / 10.0;
		}

		if (StartupPosZ == 0) {
			StartupPosZ2 = 0.5;
		} else {
			StartupPosZ2 = Math.round(mc.player.posZ * 10) / 10.0;
		}
		if (direction.equals(EnumFacing.NORTH)) {
			x = true;
		} else if (direction.equals(EnumFacing.WEST)) {
			z = true;
		} else if (direction.equals(EnumFacing.EAST)) {
			z = true;
		} else if (direction.equals(EnumFacing.SOUTH)) {
			x = true;
		}
		
		if (StartupPosX == 0) {
			int allgood;
		} else if (StartupPosZ == 0) {
			int allgood2;
		} else {
			mc.player.sendMessage(new TextComponentString(ChatFormatting.RED + "WARNING: " + ChatFormatting.BLUE
					+ "Its Only Recommended to use this in the highways X: 0 or Z: 0"));
		}
		ManuverPosX = (int) mc.player.posX;
		ManuverPosZ = (int) mc.player.posZ;

		
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
		cancelled = false;
		manuver1 = false;
		manuver2 = false;
		manuver3 = false;
		manuverM1 = false;
		manuverM2 = false;
		manuverM3 = false;
	}

	//Check if upcoming blocks are air or lava if not then set the variables to true and later on
	//Do a manuver to avoid going 9/11 to them
	public boolean GetPath(boolean kek, int x, int z, boolean minus) {
		Vec3d vec = Vec.getInterpolatedPos(mc.player, mc.getRenderPartialTicks());
		BlockPos playerPos = new BlockPos(vec);
		if (minus == true) {
			for (int i = 0; i <= 35; i++) {
				int ok = i - i - i;
				if (kek == false) {
					BlockPos x2;
					if (z == 69) {
						x2 = playerPos.add(x, 0, ok);
					} else {
						x2 = playerPos.add(ok, 0, z);
					}
					if (mc.world.getBlockState(x2).getMaterial() == Material.AIR) {
						kek = false;
					} else if (mc.world.getBlockState(x2).getMaterial() == Material.LAVA) {
						kek = false;
					} else if (mc.world.getBlockState(x2).getMaterial() == Material.PORTAL) {
						kek = false;
					} else {
						kek = true;
						return true;
					}
				}
			}
		} else {
			for (int i = 0; i <= 35; i++) {
				if (kek == false) {
					BlockPos x2;
					if (z == 69) {
						x2 = playerPos.add(x, 0, i);
					} else {
						x2 = playerPos.add(i, 0, z);
					}
					if (mc.world.getBlockState(x2).getMaterial() == Material.AIR) {
						kek = false;
					} else if (mc.world.getBlockState(x2).getMaterial() == Material.LAVA) {
						kek = false;
					} else if (mc.world.getBlockState(x2).getMaterial() == Material.PORTAL) {
						kek = false;
					} else {
						kek = true;
						return true;
					}
				}
			}
		}
		return false;
	}

	//Flight method
	public void Flight(double speed, boolean right, boolean straight) {
		if (mc.player.isElytraFlying()) {
			if (straight == true) {
				if (direction.equals(EnumFacing.NORTH)) {
					mc.player.setVelocity(0, 0, -speed);
				} else if (direction.equals(EnumFacing.WEST)) {
					mc.player.setVelocity(-speed, 0, 0);
				} else if (direction.equals(EnumFacing.EAST)) {
					mc.player.setVelocity(speed, 0, 0);
				} else if (direction.equals(EnumFacing.SOUTH)) {
					mc.player.setVelocity(0, 0, speed);
				}
			} else if (right == true) {
				if (direction.equals(EnumFacing.NORTH)) {
					mc.player.setVelocity(0.18, 0, -1.6);
				} else if (direction.equals(EnumFacing.WEST)) {
					mc.player.setVelocity(-1.6, 0, 0.18);
				} else if (direction.equals(EnumFacing.EAST)) {
					mc.player.setVelocity(1.6, 0, 0.18);
				} else if (direction.equals(EnumFacing.SOUTH)) {
					mc.player.setVelocity(0.18, 0, 1.6);
				}
			} else {
				if (direction.equals(EnumFacing.NORTH)) {
					mc.player.setVelocity(-0.18, 0, -1.6);
				} else if (direction.equals(EnumFacing.WEST)) {
					mc.player.setVelocity(-1.6, 0, -0.18);
				} else if (direction.equals(EnumFacing.EAST)) {
					mc.player.setVelocity(1.6, 0, -0.18);
				} else if (direction.equals(EnumFacing.SOUTH)) {
					mc.player.setVelocity(-0.18, 0, 1.6);
				}
			}
		}
	}
	
	public void CenterFlight(boolean right) {
		if (mc.player.isElytraFlying()) {
			if (right == true) {
				if (direction.equals(EnumFacing.NORTH)) {
					mc.player.setVelocity(0.1, 0, 0);
				} else if (direction.equals(EnumFacing.WEST)) {
					mc.player.setVelocity(0, 0, 0.1);
				} else if (direction.equals(EnumFacing.EAST)) {
					mc.player.setVelocity(0, 0, 0.1);
				} else if (direction.equals(EnumFacing.SOUTH)) {
					mc.player.setVelocity(0.1, 0, 0);
				}
			} else {
				if (direction.equals(EnumFacing.NORTH)) {
					mc.player.setVelocity(-0.1, 0, -0);
				} else if (direction.equals(EnumFacing.WEST)) {
					mc.player.setVelocity(-0, 0, -0.1);
				} else if (direction.equals(EnumFacing.EAST)) {
					mc.player.setVelocity(0, 0, -0.1);
				} else if (direction.equals(EnumFacing.SOUTH)) {
					mc.player.setVelocity(-0.1, 0, 0);
				}
			}
		}
	}

	// Goes around the blocked path and returns to original path after the manuver
	public void Manuver(int blocks, boolean right, double speed) {
		status = "Avoiding Collision";
		manuver = true;
		isgoing = true;
		manuverdelay++;

		this.Flight(speed, right, false);
		if ((int) mc.player.posX == ManuverPosX + blocks) {
			delay2++;
			if (delay2 > 4) {
				this.Flight(1.8, false, true);
				isgoing = false;
			}
		} else if ((int) mc.player.posZ == ManuverPosZ + blocks) {
			delay2++;
			if (delay2 > 4) {
				this.Flight(1.8, false, true);
				isgoing = false;
			}
		} else if ((int) mc.player.posZ == ManuverPosZ - blocks) {
			delay2++;
			if (delay2 > 4) {
				this.Flight(1.8, false, true);
				isgoing = false;
			}
		} else if ((int) mc.player.posX == ManuverPosX - blocks) {
			delay2++;
			if (delay2 > 4) {
				this.Flight(1.8, false, true);
				isgoing = false;
			}
		}

		right2 = right;
		if (manuverdelay > 55) {
			bebeli555 = true;
		}

	}
}
