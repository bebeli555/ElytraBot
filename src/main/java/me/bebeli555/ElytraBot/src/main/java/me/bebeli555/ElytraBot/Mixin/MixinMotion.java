package me.bebeli555.ElytraBot.Mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.bebeli555.ElytraBot.Highway.TakeOff;

import org.spongepowered.asm.mixin.injection.At;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.CPacketPlayer;

@Mixin(EntityPlayerSP.class)
public class MixinMotion {
	private Minecraft mc = Minecraft.getMinecraft();
	
    @Inject(method = "onUpdateWalkingPlayer", at = @At("HEAD"), cancellable = true)
    public void MotionUpdate(CallbackInfo info) {
    	if (TakeOff.ShouldActivatePacketFly()) {
    		mc.player.setVelocity(0, 0, 0);
    		info.cancel();
    		
            mc.player.motionY = -0.04f;
            mc.player.connection.sendPacket(new CPacketPlayer.PositionRotation(mc.player.posX + mc.player.motionX, mc.player.posY + mc.player.motionY, mc.player.posZ + mc.player.motionZ, mc.player.rotationYaw, mc.player.rotationPitch, mc.player.onGround));
            
            double x = mc.player.posX + mc.player.motionX;
            double y = mc.player.posY + mc.player.motionY;
            double z = mc.player.posZ + mc.player.motionZ;
            y -= -1337.0;
            
            mc.player.connection.sendPacket(new CPacketPlayer.Position(x, y, z, mc.player.onGround));
    	}
    }
}
