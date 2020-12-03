package me.bebeli555.ElytraBot.Highway;

import java.util.Objects;
import me.bebeli555.ElytraBot.ElytraFly;
import me.bebeli555.ElytraBot.Gui.Gui;
import me.bebeli555.ElytraBot.Settings.Diagonal;
import me.bebeli555.ElytraBot.Settings.Settings;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

public class TakeOff {
	static Minecraft mc = Minecraft.getMinecraft();
	
	static int delay = 0;
	static boolean Jumped = false;
	static boolean diagonal;
	public static boolean ActivatePacketFly = false;
	static double StartPos = -69;

	public static void TakeOffMethod (boolean Diagonal, boolean PacketFly, boolean SlowGlide) {
		try {
			diagonal = Diagonal;
			
			//Check if player has elytra if not equip it
			if (!HasElytraEquipped()) {
				if (HasElytra() != -1) {
					EquipElytra();
				} else {
					Gui.TurnOff();
					mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.RED + "Bro you need an elytra"));
				}
			}
			
			//Jump
			if (Jumped == false) {
				if (mc.player.onGround) {
					Jumped = true;
					mc.player.jump();
					StartPos = mc.player.posY;
					GetPath.Path.clear();
				}
			}
			
			//Reset takeoff
			if (Jumped == true) {
				if (mc.player.onGround) {
					ResetTakeOff();
				}
			}
			
			//Activate packetfly or slowglide and send elytra activation packet
			if (mc.player.posY < mc.player.lastTickPosY) {
				//Activate elytra
				if (!mc.player.isElytraFlying()) {
					Objects.requireNonNull(mc.getConnection()).sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
				}
				
				//This prevents packetfly from phasing through ground (Hopefully)
				double PreventPhase = (StartPos + 0.6);
				//Activate PacketFly
				if (PacketFly == true) {
					if (mc.player.posY > PreventPhase) {
						if (!mc.player.isElytraFlying()) {
							ActivatePacketFly = true;
						}
					} else {
						ResetTakeOff();
					}
				} else if (SlowGlide == true) {
					if (!mc.player.isElytraFlying()) {
						mc.player.setVelocity(0, Settings.getDouble("SlowGlideSpeed"), 0);
					}
				}
			}
			
			//Wait a bit then start moving when takeoff is successfull
			if (mc.player.isElytraFlying()) {
				
				//Center downwards
				if (IsTakeOffCentered() == false) {
					TakeOffCenter();
				} else if (Settings.getDouble("PrefY") != -1 && mc.player.posY > Settings.getDouble("PrefY")) {
					//Center to PrefY level
					Main.SetMotion(0, -0.15, 0);
					me.bebeli555.ElytraBot.Highway.Main.status = "Centering to Y: " + Settings.getDouble("PrefY");
					me.bebeli555.ElytraBot.Settings.Diagonal.status = "Centering to Y: " + Settings.getDouble("PrefY");
				} else {
					Main.SetMotion(0, -(Settings.getDouble("GlideSpeed") / 10000f), 0);
					delay++;
				}
				
				if (delay > 14) {
					delay = 0;
					ActivateElytraBot();
				}
			}
			
		} catch (Exception e) {
			System.out.println("Exception while taking off Elytrabot");
			e.printStackTrace();
		}
	}
	
	public static void ResetTakeOff() {
		delay = 0;
		Jumped = false;
		ActivatePacketFly = false;
	}
	
	public static void ActivateElytraBot() {
		ActivatePacketFly = false;
		if (IsTakeOffCentered()) {
			if (diagonal == true) {
				Diagonal.manuver = false;
				Diagonal.MoveOn = true;
				Diagonal.setMove(false, true);
				Diagonal.takeoff = true;
			} else {
				Main.MoveOn = true;
				Main.setMove(1.8, false, true, 0);
				Main.takeoff = true;
			}
		}
	}
	
	public static boolean ShouldActivatePacketFly() {
		return ActivatePacketFly;
	}
	
	public static void TakeOffCenter() {		
		//Center X or Z
		double Coord = Math.round(mc.player.posZ * 10) / 10.0;
		double Coord2 = Math.round(mc.player.posX * 10) / 10.0;
		String Z = String.valueOf(Coord);
		String X = String.valueOf(Coord2);
		

		if (IsTakeOffCentered() == false && diagonal == false) {
			Main.MoveOn = true;
			Main.ManuverSpeed = 0.065;
			if (Main.direction.equals(EnumFacing.NORTH)) {
				if (Coord2 < 0) {
					if (X.contains(".4") || X.contains(".3") || X.contains(".2") || X.contains(".1") || X.contains(".0")) {
						Main.setMove(0, false, false, 0);
					} else {
						Main.setMove(0, true, false, 0);
					}
				} else {
					if (X.contains(".4") || X.contains(".3") || X.contains(".2") || X.contains(".1") || X.contains(".0")) {
						Main.setMove(0, true, false, 0);
					} else {
						Main.setMove(0, false, false, 0);
					}
				}
			} else if (Main.direction.equals(EnumFacing.WEST)) {
				if (Coord < 0) {
					if (Z.contains(".4") || Z.contains(".3") || Z.contains(".2") || Z.contains(".1") || Z.contains(".0")) {
						Main.setMove(0, false, false, 0);
					} else {
						Main.setMove(0, true, false, 0);
					}	
				} else {
					if (Z.contains(".4") || Z.contains(".3") || Z.contains(".2") || Z.contains(".1") || Z.contains(".0")) {
						Main.setMove(0, true, false, 0);
					} else {
						Main.setMove(0, false, false, 0);
					}	
				}
			} else if (Main.direction.equals(EnumFacing.EAST)) {
				if (Coord < 0) {
					if (Z.contains(".4") || Z.contains(".3") || Z.contains(".2") || Z.contains(".1") || Z.contains(".0")) {
						Main.setMove(0, false, false, 0);
					} else {
						Main.setMove(0, true, false, 0);
					}
				} else {
					if (Z.contains(".4") || Z.contains(".3") || Z.contains(".2") || Z.contains(".1") || Z.contains(".0")) {
						Main.setMove(0, true, false, 0);
					} else {
						Main.setMove(0, false, false, 0);
					}	
				}
			} else {
				if (Coord2 < 0) {
					if (X.contains(".4") || X.contains(".3") || X.contains(".2") || X.contains(".1") || X.contains(".0")) {
						Main.setMove(0, false, false, 0);
					} else {
						Main.setMove(0, true, false, 0);
					}
				} else {
					if (X.contains(".4") || X.contains(".3") || X.contains(".2") || X.contains(".1") || X.contains(".0")) {
						Main.setMove(0, true, false, 0);
					} else {
						Main.setMove(0, false, false, 0);
					}	
				}
			}
		} else {
			Main.MoveOn = false;
			Main.ManuverSpeed = 0;
		}
	}
	
	public static boolean IsTakeOffCentered() {
		if (diagonal == true) {
			return true;
		}
		
		double Coord = Math.round(mc.player.posZ * 10) / 10.0;
		double Coord2 = Math.round(mc.player.posX * 10) / 10.0;
		String Z = String.valueOf(Coord);
		String X = String.valueOf(Coord2);
		if (Main.z == true) {
			if (Z.contains(".5")) {
				return true;
			}
		} else {
			if (X.contains(".5")) {
				return true;
			}
		}
		
		return false;
	}
	
	public static int HasElytra() {
		for (int i = 9; i < 44; ++i) {
			final ItemStack stack2 = mc.player.inventory.getStackInSlot(i);
			if (stack2.getItem() == Items.ELYTRA) {
				return i;
			}

		}
		return -1;
	}
	
	public static boolean HasElytraEquipped() {
		ItemStack Elytra = mc.player.inventory.armorItemInSlot(2);
		if (Elytra.getItem() == Items.ELYTRA) {
			return true;
		}
		return false;
	}
	
	public static void EquipElytra() {
		mc.playerController.windowClick(mc.player.inventoryContainer.windowId, HasElytra(), 0, ClickType.PICKUP, mc.player);
		mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 6, 0, ClickType.PICKUP, mc.player);
		mc.playerController.windowClick(mc.player.inventoryContainer.windowId, HasElytra(), 0, ClickType.PICKUP, mc.player);
	}
	
	//Flies up a bit to YCenter in the middle of the block also keeps the player on the air all time
	public static void YCenter() {
		Main.LockYaw();
		ElytraFly.YCenter = true;
		mc.player.rotationPitch = -8;
		Main.MoveOn = false;
		
		BlockPos Player = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
		if (GetPath.Path.contains(Player)) {
			GetPath.Path.remove(Player);
		}
	}
	

	public static boolean IsClearToCenter() {
		for (int i = 1; i < 35; i++) {
			if (Main.status == "Forward") {
				BlockPos Player = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
				BlockPos Check;
				
				if (Main.direction.equals(EnumFacing.NORTH)) {
					Check = Player.add(0, 0, -i);
				} else if (Main.direction.equals(EnumFacing.WEST)) {
					Check = Player.add(-i, 0, 0);
				} else if (Main.direction.equals(EnumFacing.EAST)) {
					Check = Player.add(i, 0, 0);
				} else {
					Check = Player.add(0, 0, i);
				}
				
				if (GetPath.IsSolid(Check)) {
					return false;
				}
			} else {
				return false;
			}
		}
		
		return true;
	}
}
