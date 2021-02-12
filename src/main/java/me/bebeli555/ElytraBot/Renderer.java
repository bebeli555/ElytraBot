package me.bebeli555.ElytraBot;

import java.util.ArrayList;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.bebeli555.ElytraBot.Overworld.Main;
import me.bebeli555.ElytraBot.Settings.Settings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import static org.lwjgl.opengl.GL11.*;

public class Renderer extends GuiScreen {
	static Minecraft mc = Minecraft.getMinecraft();
	public static boolean IsRendering = false;
	public static ArrayList<BlockPos> PositionsGreen = new ArrayList<BlockPos>();
	static int delay = 0;
	static int EstimatedHours, EstimatedMinutes, EstimatedSeconds, BlocksASec;
	static BlockPos Latest;

	//This renders the Status above your hotbar
	@SubscribeEvent
	public void render(RenderGameOverlayEvent.Text e) {
		if (me.bebeli555.ElytraBot.Highway.Main.toggle == true) {
			mc.fontRenderer.drawString(ChatFormatting.RED + "ElytraBot Status: " + ChatFormatting.BLUE + me.bebeli555.ElytraBot.Highway.Main.status, 390, 475, 0xffff);
		} else if (me.bebeli555.ElytraBot.Highway.Main.baritonetoggle == true) {
			mc.fontRenderer.drawString(ChatFormatting.RED + "ElytraBot Status: " + ChatFormatting.BLUE + "Using baritone to overcome obstacle", 390, 475, 0xffff);
		} else if (me.bebeli555.ElytraBot.Settings.AutoRepair.AutoRepair == true) {
			mc.fontRenderer.drawString(ChatFormatting.RED + "ElytraBot Status: " + ChatFormatting.BLUE + "Repairing Elytra", 390, 475, 0xffff);
		} else if (me.bebeli555.ElytraBot.Settings.Diagonal.toggle == true) {
			mc.fontRenderer.drawString(ChatFormatting.RED + "ElytraBot Status: " + ChatFormatting.BLUE + me.bebeli555.ElytraBot.Settings.Diagonal.status, 390, 475, 0xffff);
		} else if (me.bebeli555.ElytraBot.Settings.Diagonal.baritonetoggle == true) {
			mc.fontRenderer.drawString(ChatFormatting.RED + "ElytraBot Status: " + ChatFormatting.BLUE + "Using baritone to overcome obstacle", 390, 475, 0xffff);
		} else if (me.bebeli555.ElytraBot.Overworld.Main.toggle == true) {
			String Seconds = ChatFormatting.GOLD + " Seconds: ";
			String Minutes = ChatFormatting.GOLD + " Minutes: ";
			String Hours = ChatFormatting.GOLD + "Hours: ";
			String ok1 = "" + Main.EstimatedSeconds; String EstimatedSec = ChatFormatting.GREEN + ok1;
			String ok2 = "" + Main.EstimatedMinutes; String EstimatedMin = ChatFormatting.GREEN + ok2;
			String ok3 = "" + Main.EstimatedHours; String EstimatedHour = ChatFormatting.GREEN + ok3;
			mc.fontRenderer.drawString(ChatFormatting.RED + "Estimated Time " + Hours + EstimatedHour + Minutes + EstimatedMin + Seconds + EstimatedSec, 370, 475, 0xffff);
		}
	}
	
	public static void EstimatedTime() {
		//Get Blocks a second player is travelling
		BlockPos Player = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
		delay++;
		if (delay > 19) {
			delay = 0;
			if (Latest == null) {
				Latest = Player;
				return;
			}
			int X = Player.getX() - Latest.getX();
			int Z = Player.getZ() - Latest.getZ();
			BlocksASec = Math.abs(X) + Math.abs(Z);
			Latest = Player;
		}
		
		//Set time
		if (BlocksASec != 0) {
			int X = 0;
			int Z = 0;
			if (me.bebeli555.ElytraBot.Overworld.Main.toggle) {
				X = (int) ((int) mc.player.posX - Settings.getDouble("OverworldX"));
				Z = (int) ((int) mc.player.posZ - Settings.getDouble("OverworldZ"));
			} else {
				if (Settings.getDouble("StopAtX") < Settings.getDouble("StopAtZ")) {
					
				} else {
					
				}
			}
			int Blocks = Math.abs(X) + Math.abs(Z);
			Blocks = Math.abs(Blocks);

			int seconds = 0;
			seconds = (Blocks / BlocksASec) / 2;
			int p1 = seconds % 60;
			int p2 = seconds / 60;
			int p3 = p2 % 60;
			p2 = p2 / 60;

			EstimatedHours = p2;
			EstimatedMinutes = p3;
			EstimatedSeconds = p1;
		}
	}
	
	@SubscribeEvent
	public void RenderPath(RenderWorldLastEvent e) {
		IsRendering = true;
		if (IsRendering == true) {
			try {
				for (int i = 0; i < PositionsGreen.size(); i++) {
					drawPathBox(PositionsGreen.get(i), 0f, 1f, 0f, 1f);
				}
			} catch (Exception e22) {

			}
		}
	}
	
    public static void drawPathBox(BlockPos pos, float red, float green, float blue, float alpha) {
    	BlockPos next = PositionsGreen.get(PositionsGreen.indexOf(pos) + 1);
    	if (next == null) {
    		return;
    	}
    	
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        glEnable(GL_LINE_SMOOTH);
        glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
        glLineWidth(1.5F);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
        AxisAlignedBB bb = GetAxis(pos);
    	if (next.getX() > pos.getX()) {
    		bufferbuilder.pos(bb.minX + 0.5, bb.maxY, bb.minZ + 0.5).color(red, green, blue, alpha).endVertex();
        	bufferbuilder.pos(bb.maxX + 0.5, bb.maxY, bb.minZ + 0.5).color(red, green, blue, alpha).endVertex();
    	} else if (next.getX() < pos.getX()) {
    		bufferbuilder.pos(bb.minX - 0.5, bb.maxY, bb.minZ + 0.5).color(red, green, blue, alpha).endVertex();
        	bufferbuilder.pos(bb.maxX - 0.5, bb.maxY, bb.minZ + 0.5).color(red, green, blue, alpha).endVertex();
    	} else if (next.getZ() > pos.getZ()) {
    		bufferbuilder.pos(bb.minX + 0.5, bb.maxY, bb.maxZ + 0.5).color(red, green, blue, alpha).endVertex();
            bufferbuilder.pos(bb.minX + 0.5, bb.maxY, bb.minZ + 0.5).color(red, green, blue, alpha).endVertex();
    	} else {
            bufferbuilder.pos(bb.minX + 0.5, bb.maxY, bb.maxZ - 0.5).color(red, green, blue, alpha).endVertex();
            bufferbuilder.pos(bb.minX + 0.5, bb.maxY, bb.minZ - 0.5).color(red, green, blue, alpha).endVertex();
    	}
        tessellator.draw();
        glDisable(GL_LINE_SMOOTH);
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    
    public static AxisAlignedBB GetAxis(BlockPos BlockPos4) {
		final AxisAlignedBB Axis = new AxisAlignedBB(
				BlockPos4.getX() - mc.getRenderManager().viewerPosX,
				BlockPos4.getY() - mc.getRenderManager().viewerPosY,
				BlockPos4.getZ() - mc.getRenderManager().viewerPosZ,
				BlockPos4.getX() + 1 - mc.getRenderManager().viewerPosX,
				BlockPos4.getY() - mc.getRenderManager().viewerPosY,
				BlockPos4.getZ() + 1 - mc.getRenderManager().viewerPosZ);
		return Axis;
    }
}
