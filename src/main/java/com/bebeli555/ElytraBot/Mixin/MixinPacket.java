package com.bebeli555.ElytraBot.Mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.bebeli555.ElytraBot.TakeOff;

import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.Minecraft;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;

@Mixin(NetworkManager.class)
public class MixinPacket {
	private Minecraft mc = Minecraft.getMinecraft();
	
    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void SendPacket(Packet<?> packet2, CallbackInfo info) {
    	if (TakeOff.ShouldActivatePacketFly()) {
    		if (packet2 instanceof SPacketPlayerPosLook && mc.currentScreen == null) {
                SPacketPlayerPosLook packet = (SPacketPlayerPosLook) packet2;
                mc.player.connection.sendPacket(new CPacketConfirmTeleport(packet.getTeleportId()));
                mc.player.connection.sendPacket(new CPacketPlayer.PositionRotation(packet.getX(), packet.getY(), packet.getZ(), packet.getYaw(), packet.getPitch(), false));
                mc.player.setPosition(packet.getX(), packet.getY(), packet.getZ());

                info.cancel();
    		}
    	}
    }
}
