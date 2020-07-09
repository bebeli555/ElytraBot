package com.bebeli555.ElytraBot;

import java.util.Random;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class AutoRepair {
	public static boolean AutoRepair = false;
	static boolean StartRepair = false;
	static Minecraft mc = Minecraft.getMinecraft();
	static int delay = 0;
	static int delay2 = 0;
	static int delay5 = 0;
	static boolean checkstuff = false;
	static int check = 0;
	static int delaylmao = 0;
	static boolean ArmorTakeoff = false;
	static int delay10 = 0;
	static int delay11 = 0;
	static boolean TakeOff2 = false;
	static boolean PlaceBack = false;
	static int delay12 = 0;
	
	static int Helmet = 0;
	static int Leggings = 0;
	static int Boots = 0;
	
	static int HelmetSlot = 5;
	static int LeggingsSlot = 7;
	static int BootsSlot = 8;

	@SubscribeEvent
	public void onUpdate(ClientTickEvent e) {	
		if (AutoRepair == true) {	
			com.bebeli555.ElytraBot.Main.toggle = false;
			com.bebeli555.ElytraBot.Main.baritonetoggle = false;
			com.bebeli555.ElytraBot.Diagonal.toggle = false;
			com.bebeli555.ElytraBot.Diagonal.baritonetoggle = false;
			checkstuff = false;
			if (PlaceBack == false && TakeOff2 == false) {
				CheckStuff();
				CheckDur();
			}

			if (mc.player.onGround == true) {
				//Take off armor if enough space in inventory to do so
				if (ArmorTakeoff == false) {
					ArmorTakeoff = true;
					if (HasArmor()) {
						if (HasSpace()) {
							TakeOff2 = true;
						} else {
							mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_AQUA + "ElytraBot: " + ChatFormatting.RED + "No space in inventory to takeoff armor for maximum repair!"));
						}
					}
					
				}
				
				//Take it off with a little delay.
				if (TakeOff2 == true) {
					TakeOffArmor();
				}
				
				//Put it back when done repairing
				if (PlaceBack == true) {
					PlaceBackArmor();
				}
				
				
				//AutoRepair
				if (ArmorTakeoff == true && TakeOff2 == false) {
					if (mc.player.getHeldItemMainhand().getItem() != Items.EXPERIENCE_BOTTLE) {

						delay++;
						if (delay == 1) {
							ManageBottle();
						} else if (delay > 20) {
							mc.player.inventory.currentItem = GetBottle();
							delay = 0;
						}
					} else {
						delay2++;
						if (delay2 > 5) {
							mc.player.rotationPitch = 90;
							Random rand = new Random();
							//This prevents it throwing them into the side bcs 2b is wierd.
							int random = rand.nextInt(10);
							mc.player.rotationYaw = random;
							mc.playerController.processRightClick(mc.player, mc.world, EnumHand.MAIN_HAND);
							delay2 = 0;
						}
					}
					delay5++;
					if (delay5 > 500) {
						delay5 = 0;
						if (HasArmor() == false) {
							PlaceBack = true;
						} else {
							if (com.bebeli555.ElytraBot.Settings.Diagonal == false) {
								com.bebeli555.ElytraBot.Main.toggle = true;
							} else {
								com.bebeli555.ElytraBot.Diagonal.toggle = true;
							}
							AutoRepair = false;
							StartRepair = false;
							ArmorTakeoff = false;
						}
					}
				}
			}
		}
	}

	public static void ManageBottle() {
		for (int i = 0; i < 36; ++i) {
			final ItemStack stack2 = mc.player.inventory.getStackInSlot(i);
			if (stack2.getItem() == Items.EXPERIENCE_BOTTLE) {
				mc.playerController.windowClick(mc.player.inventoryContainer.windowId, i, 0, ClickType.PICKUP, mc.player);
				mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 38, 0, ClickType.PICKUP, mc.player);
				mc.playerController.windowClick(mc.player.inventoryContainer.windowId, i, 0, ClickType.PICKUP, mc.player);
				break;
			}

		}
	}

	public static int GetBottle() {
		for (int i = 0; i < 9; ++i) {
			final ItemStack stack = mc.player.inventory.getStackInSlot(i);
			if (stack != ItemStack.EMPTY && stack.getItem() == Items.EXPERIENCE_BOTTLE) {
				return i;
			}
		}
		return 0;
	}

	public void CheckDur() {
		ItemStack elytra = mc.player.inventory.armorItemInSlot(2);
		int Durability = (elytra.getMaxDamage() - elytra.getItemDamage());

		if (Durability > 400) {
			if (HasArmor() == false) {
				PlaceBack = true;
			} else {
				if (com.bebeli555.ElytraBot.Settings.Diagonal == false) {
					com.bebeli555.ElytraBot.Main.toggle = true;
				} else {
					com.bebeli555.ElytraBot.Diagonal.toggle = true;
				}
				AutoRepair = false;
				StartRepair = false;
				ArmorTakeoff = false;
			}
		}
	}
	
	public static boolean HasSpace() {
		for (int i = 0; i < 36; ++i) {
			final ItemStack stack2 = mc.player.inventory.getStackInSlot(i);
			if (stack2.getItem() == Items.AIR) {
				check++;
			}
		}
		
		if (check > 2) {
			check = 0;
			return true;
		} else {
			check = 0;
			return false;
		}
	}
	
	public static boolean HasArmor() {
		ItemStack Helmet = mc.player.inventory.armorItemInSlot(1);
		ItemStack Leggings = mc.player.inventory.armorItemInSlot(3);
		ItemStack Boots = mc.player.inventory.armorItemInSlot(0);
		if (Helmet.getItem() == Items.AIR) {
			return false;
		}
		
		if (Leggings.getItem() == Items.AIR) {
			return false;
		}
		
		if (Boots.getItem() == Items.AIR) {
			return false;
		}
		
		return true;
	}
	
	public static void TakeOffArmor() {
		delay11++;
		if (delay11 == 5) {
			//Helmet
			mc.playerController.windowClick(mc.player.inventoryContainer.windowId, HelmetSlot, 0, ClickType.PICKUP, mc.player);
			Helmet = GetEmpty();
			mc.playerController.windowClick(mc.player.inventoryContainer.windowId, GetEmpty(), 0, ClickType.PICKUP, mc.player);
		} else if (delay11 == 10) {
			//Leggings
			mc.playerController.windowClick(mc.player.inventoryContainer.windowId, LeggingsSlot, 0, ClickType.PICKUP, mc.player);
			Leggings = GetEmpty();
			mc.playerController.windowClick(mc.player.inventoryContainer.windowId, GetEmpty(), 0, ClickType.PICKUP, mc.player);
		} else if (delay11 > 15) {
			//Boots
			mc.playerController.windowClick(mc.player.inventoryContainer.windowId, BootsSlot, 0, ClickType.PICKUP, mc.player);
			Boots = GetEmpty();
			mc.playerController.windowClick(mc.player.inventoryContainer.windowId, GetEmpty(), 0, ClickType.PICKUP, mc.player);
			
			TakeOff2 = false;
			delay11 = 0;
		}
	}
	
	public static void PlaceBackArmor() {
		delay12++;
		if (delay12 == 5) {
			//Helmet
			mc.playerController.windowClick(mc.player.inventoryContainer.windowId, Helmet, 0, ClickType.PICKUP, mc.player);
			mc.playerController.windowClick(mc.player.inventoryContainer.windowId, HelmetSlot, 0, ClickType.PICKUP, mc.player);
		} else if (delay12 == 10) {
			//Leggings
			mc.playerController.windowClick(mc.player.inventoryContainer.windowId, Leggings, 0, ClickType.PICKUP, mc.player);
			mc.playerController.windowClick(mc.player.inventoryContainer.windowId, LeggingsSlot, 0, ClickType.PICKUP, mc.player);
		} else if (delay12 > 15) {
			//Boots
			mc.playerController.windowClick(mc.player.inventoryContainer.windowId, Boots, 0, ClickType.PICKUP, mc.player);
			mc.playerController.windowClick(mc.player.inventoryContainer.windowId, BootsSlot, 0, ClickType.PICKUP, mc.player);
			
			PlaceBack = false;
			delay12 = 0;
			
			if (com.bebeli555.ElytraBot.Settings.Diagonal == false) {
				com.bebeli555.ElytraBot.Main.toggle = true;
			} else {
				com.bebeli555.ElytraBot.Diagonal.toggle = true;
			}
			AutoRepair = false;
			StartRepair = false;
			ArmorTakeoff = false;
			delay5 = 0;
		}
	}
	
	public static int GetEmpty() {
		for (int i = 9; i < 44; ++i) { 
			final ItemStack stack2 = mc.player.inventory.getStackInSlot(i);
			if (stack2.getItem() == Items.AIR) {
				System.out.println(i);
				return i;
			}
		}
		return -1;
	}
	
	public void CheckStuff() {
		for (int i = 0; i < 36; ++i) {
			final ItemStack stack2 = mc.player.inventory.getStackInSlot(i);
			if (stack2.getItem() == Items.EXPERIENCE_BOTTLE) {
				checkstuff = true;
			} else {
				if (checkstuff == false) {
					if (i > 34) {
						mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_AQUA + "ElytraBot: "+ ChatFormatting.RED + "Cannot Repair Elytra Becuase No XP-Bottles In Inventory!"));
						if (HasArmor() == false) {
							PlaceBack = true;
						} else {
							if (com.bebeli555.ElytraBot.Settings.Diagonal == false) {
								com.bebeli555.ElytraBot.Main.toggle = true;
							} else {
								com.bebeli555.ElytraBot.Diagonal.toggle = true;
							}
							AutoRepair = false;
							StartRepair = false;
						}
					}

				}

			}

		}
	}

}
