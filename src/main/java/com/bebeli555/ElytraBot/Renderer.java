package com.bebeli555.ElytraBot;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.mojang.realmsclient.gui.ChatFormatting;

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

public class Renderer {
	static Minecraft mc = Minecraft.getMinecraft();
	public static boolean IsRendering = false;
	public static BlockPos GreenPos = new BlockPos(2, 5000 ,2);
	public static BlockPos RedPos = new BlockPos(2, 5000 ,2);
	public static BlockPos YellowPos = new BlockPos(2, 5000 ,2);

	//This renders the Status above your hotbar
	@SubscribeEvent
	public void render(RenderGameOverlayEvent.Text e) {
		if (com.bebeli555.ElytraBot.Main.toggle == true) {
			mc.fontRenderer.drawString(ChatFormatting.RED + "ElytraBot Status: " + ChatFormatting.BLUE + com.bebeli555.ElytraBot.Main.status, 390, 475, 0xffff);
		} else if (com.bebeli555.ElytraBot.Main.baritonetoggle == true) {
			mc.fontRenderer.drawString(ChatFormatting.RED + "ElytraBot Status: " + ChatFormatting.BLUE + "Using baritone to overcome obstacle", 390, 475, 0xffff);
		} else if (com.bebeli555.ElytraBot.AutoRepair.AutoRepair == true) {
			mc.fontRenderer.drawString(ChatFormatting.RED + "ElytraBot Status: " + ChatFormatting.BLUE + "Repairing Elytra", 390, 475, 0xffff);
		} else if (com.bebeli555.ElytraBot.Diagonal.toggle == true) {
			mc.fontRenderer.drawString(ChatFormatting.RED + "ElytraBot Status: " + ChatFormatting.BLUE + com.bebeli555.ElytraBot.Diagonal.status, 390, 475, 0xffff);
		} else if (com.bebeli555.ElytraBot.Diagonal.baritonetoggle == true) {
			mc.fontRenderer.drawString(ChatFormatting.RED + "ElytraBot Status: " + ChatFormatting.BLUE + "Using baritone to overcome obstacle", 390, 475, 0xffff);
		}
	}
	
	@SubscribeEvent
	public void RenderPath(RenderWorldLastEvent e) {
		if (IsRendering == true) {
			  //Render paths
				BlockPos BlockPos = new BlockPos(GreenPos);
				BlockPos BlockPos2 = new BlockPos(RedPos);
				BlockPos BlockPos3 = new BlockPos(YellowPos);
				final AxisAlignedBB bb = new AxisAlignedBB(BlockPos.getX() - mc.getRenderManager().viewerPosX,
						BlockPos.getY() - mc.getRenderManager().viewerPosY,
						BlockPos.getZ() - mc.getRenderManager().viewerPosZ,
						BlockPos.getX() + 1 - mc.getRenderManager().viewerPosX,
						BlockPos.getY() - mc.getRenderManager().viewerPosY,
						BlockPos.getZ() + 1 - mc.getRenderManager().viewerPosZ);
				final AxisAlignedBB bb2 = new AxisAlignedBB(BlockPos2.getX() - mc.getRenderManager().viewerPosX,
						BlockPos2.getY() - mc.getRenderManager().viewerPosY,
						BlockPos2.getZ() - mc.getRenderManager().viewerPosZ,
						BlockPos2.getX() + 1 - mc.getRenderManager().viewerPosX,
						BlockPos2.getY() - mc.getRenderManager().viewerPosY,
						BlockPos2.getZ() + 1 - mc.getRenderManager().viewerPosZ);
				final AxisAlignedBB bb3 = new AxisAlignedBB(BlockPos3.getX() - mc.getRenderManager().viewerPosX,
						BlockPos3.getY() - mc.getRenderManager().viewerPosY,
						BlockPos3.getZ() - mc.getRenderManager().viewerPosZ,
						BlockPos3.getX() + 1 - mc.getRenderManager().viewerPosX,
						BlockPos3.getY() - mc.getRenderManager().viewerPosY,
						BlockPos3.getZ() + 1 - mc.getRenderManager().viewerPosZ);

				DrawPathBox(bb, 0f, 0f, 1f, 1f, true, false);
				DrawPathBox(bb2, 0f, 0f, 1f, 1f, false, false);
				DrawPathBox(bb3, 0f, 0f, 1f, 1f, false, true);
		}
	}
	
    public static void DrawPathBox(AxisAlignedBB axisalignedbb, float red, float green, float blue, float alpha, boolean Green, boolean Yellow)
    {
        Tessellator ts = Tessellator.getInstance();
        BufferBuilder vb = ts.getBuffer();
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        if (Green == true) {
        	//Green
        	GL11.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
        } else if (Yellow == true){
        	//Yellow
        	GL11.glColor4f(1.0f, 1.0f, 0.0f, 1.0f);
        } else {
        	//Red
        	GL11.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
        }
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    
    public static void DrawGreenBox(BlockPos BlockPos) {
        IsRendering = true;
        GreenPos = BlockPos;
    }
    
    public static void DrawRedBox(BlockPos BlockPos) {
        IsRendering = true;
        RedPos = BlockPos;
    }
    
    public static void DrawYellowBox(BlockPos BlockPos) {
        IsRendering = true;
        YellowPos = BlockPos;
    }
}
