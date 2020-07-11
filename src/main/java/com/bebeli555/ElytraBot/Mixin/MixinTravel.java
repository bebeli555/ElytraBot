package com.bebeli555.ElytraBot.Mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.bebeli555.ElytraBot.ElytraFly;
import org.spongepowered.asm.mixin.injection.At;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

@Mixin(value = EntityPlayer.class, priority = Integer.MAX_VALUE)
public abstract class MixinTravel extends EntityLivingBase{
	private Minecraft mc = Minecraft.getMinecraft();
	// Did you know that i spent 15 minutes thinking what you wouldnt know but i couldnt come up with anything
	// so i just wrote about how i didnt know what you wouldnt know here?

    public MixinTravel (World worldIn) {
        super(worldIn);
    }

    @Inject(method = "travel", at = @At("HEAD"), cancellable = true)
    public void travel(float strafe, float vertical, float forward, CallbackInfo info) {
    	if (ElytraFly.IsElytrabotOn()) {
			if (mc.player.isElytraFlying()) {
				ElytraFly.ElytraFlight();

				move(MoverType.SELF, motionX, motionY, motionZ);
				info.cancel();
			}
    	}
    }
}
