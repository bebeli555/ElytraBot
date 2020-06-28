package com.bebeli555.ElytraBot;

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

	@SubscribeEvent
	public void onUpdate(ClientTickEvent e) {	
		if (AutoRepair == true) {		
			com.bebeli555.ElytraBot.Main.toggle = false;
			com.bebeli555.ElytraBot.Main.baritonetoggle = false;
			com.bebeli555.ElytraBot.Diagonal.toggle = false;
			com.bebeli555.ElytraBot.Diagonal.baritonetoggle = false;
			CheckStuff();
			checkstuff = false;
			CheckDur();

			if (mc.player.onGround == true) {
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
						mc.player.rotationYaw = 2;
						mc.playerController.processRightClick(mc.player, mc.world, EnumHand.MAIN_HAND);
						delay2 = 0;
					}
				}
				delay5++;
				if (delay5 > 900) {
					if (com.bebeli555.ElytraBot.Settings.Diagonal == false) {
						com.bebeli555.ElytraBot.Main.toggle = true;
					} else {
						com.bebeli555.ElytraBot.Diagonal.toggle = true;
					}
					AutoRepair = false;
					StartRepair = false;
					delay5 = 0;
				}
			}
		}
	}

	public static void ManageBottle() {
		ItemStack Check = mc.player.inventory.getStackInSlot(5);
		for (int i = 0; i < 36; ++i) {
			final ItemStack stack2 = mc.player.inventory.getStackInSlot(i);
			if (stack2.getItem() == Items.EXPERIENCE_BOTTLE) {
				mc.playerController.windowClick(mc.player.inventoryContainer.windowId, i, 0, ClickType.QUICK_MOVE,
						mc.player);
				mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 5, 0, ClickType.QUICK_MOVE,
						mc.player);
				break;
			}

		}
	}

	public static int GetBottle() {
		for (int i = 0; i < 9; ++i) {
			final ItemStack stack = mc.player.inventory.getStackInSlot(i);
			if (stack != ItemStack.EMPTY && stack.getItem() == Items.EXPERIENCE_BOTTLE) {
				return i;

			} else {
				mc.player.dropItem(true);
			}
		}
		return 0;
	}

	public void CheckDur() {
		ItemStack elytra = mc.player.inventory.armorItemInSlot(2);
		int Durability = (elytra.getMaxDamage() - elytra.getItemDamage());

		if (Durability > 400) {
			if (com.bebeli555.ElytraBot.Settings.Diagonal == false) {
				com.bebeli555.ElytraBot.Main.toggle = true;
			} else {
				com.bebeli555.ElytraBot.Diagonal.toggle = true;
			}
			AutoRepair = false;
			StartRepair = false;
		}
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
