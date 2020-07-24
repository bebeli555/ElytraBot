package me.bebeli555.ElytraBot;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import me.bebeli555.ElytraBot.Overworld.Main;
import me.bebeli555.ElytraBot.PathFinding.AStar;
import me.bebeli555.ElytraBot.PathFinding.GetPath;
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
	
	public static ArrayList<BlockPos> PositionsGreen = new ArrayList<BlockPos>();
	public static ArrayList<BlockPos> PositionsRed = new ArrayList<BlockPos>();

	//This renders the Status above your hotbar
	@SubscribeEvent
	public void render(RenderGameOverlayEvent.Text e) {
		if (me.bebeli555.ElytraBot.Main.toggle == true) {
			mc.fontRenderer.drawString(ChatFormatting.RED + "ElytraBot Status: " + ChatFormatting.BLUE + me.bebeli555.ElytraBot.Main.status, 390, 475, 0xffff);
		} else if (me.bebeli555.ElytraBot.Main.baritonetoggle == true) {
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
	
	@SubscribeEvent
	public void RenderPath(RenderWorldLastEvent e) {
		if (IsRendering == true) {
				try {
					for (int i = 0; i < PositionsGreen.size(); i++) {
						BlockPos BlockPos4 = new BlockPos(PositionsGreen.get(i));
						final AxisAlignedBB Axis = new AxisAlignedBB(
								BlockPos4.getX() - mc.getRenderManager().viewerPosX,
								BlockPos4.getY() - mc.getRenderManager().viewerPosY,
								BlockPos4.getZ() - mc.getRenderManager().viewerPosZ,
								BlockPos4.getX() + 1 - mc.getRenderManager().viewerPosX,
								BlockPos4.getY() - mc.getRenderManager().viewerPosY,
								BlockPos4.getZ() + 1 - mc.getRenderManager().viewerPosZ);
						DrawPathBox(Axis, 0f, 0f, 1f, 1f, true, false);
					}
				}catch (Exception e22) {
					
				}
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
