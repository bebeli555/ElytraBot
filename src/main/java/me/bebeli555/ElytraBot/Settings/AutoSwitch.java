package me.bebeli555.ElytraBot.Settings;

import me.bebeli555.ElytraBot.Highway.TakeOff;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class AutoSwitch {
	static Minecraft mc = Minecraft.getMinecraft();
	
	@SubscribeEvent
	public void onUpdate(ClientTickEvent e) {
		if (Settings.getBoolean("AutoSwitch")) {
			if (me.bebeli555.ElytraBot.Overworld.Main.toggle || AutoEat.IsElytrabotEnabled()) {
				if (!TakeOff.HasElytraEquipped()) {
					int switchSlot = getBestElytra();
					mc.playerController.windowClick(mc.player.inventoryContainer.windowId, switchSlot, 0, ClickType.PICKUP, mc.player);
					mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 6, 0, ClickType.PICKUP, mc.player);
				} else if (isElytraBroken()) {
					int switchSlot = getBestElytra();
					mc.playerController.windowClick(mc.player.inventoryContainer.windowId, switchSlot, 0, ClickType.PICKUP, mc.player);
					mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 6, 0, ClickType.PICKUP, mc.player);
					mc.playerController.windowClick(mc.player.inventoryContainer.windowId, switchSlot, 0, ClickType.PICKUP, mc.player);
				}
			}
		}
	}
	
	public static boolean isElytraBroken() {
		ItemStack elytra = mc.player.inventory.armorItemInSlot(2);
		int durability = (elytra.getMaxDamage()-elytra.getItemDamage());
		
		if (durability < 2) {
			return true;
		}
		return false;
	}
	
	public static int getBestElytra() {
		int bestDur = Integer.MIN_VALUE;
		int bestSlot = -1;
		
		for (int i = 9; i < 44; ++i) { 
			ItemStack elytra = mc.player.inventory.getStackInSlot(i);
			int durability = (elytra.getMaxDamage()-elytra.getItemDamage());
			if (durability > bestDur && durability > 1) {
				bestSlot = i;
				bestDur = durability;
			}
			
		}
		
		return bestSlot;
	}
}
