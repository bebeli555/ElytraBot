package me.bebeli555.ElytraBot;

import java.util.ArrayList;
import org.lwjgl.opengl.GL11;

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

public class Renderer extends GuiScreen {
	static Minecraft mc = Minecraft.getMinecraft();
	public static String elytrabotStatus;
	public static boolean IsRendering = false;
	
	public static ArrayList<BlockPos> PositionsGreen = new ArrayList<BlockPos>();
	public static ArrayList<BlockPos> PositionsRed = new ArrayList<BlockPos>();
	public static ArrayList<BlockPos> PositionsYellow = new ArrayList<BlockPos>();
	
	static int delay = 0;
	static int EstimatedHours, EstimatedMinutes, EstimatedSeconds, BlocksASec;
	static BlockPos Latest;

	//This renders the Status above your hotbar
	@SubscribeEvent
	public void render(RenderGameOverlayEvent.Text e) {
		if (me.bebeli555.ElytraBot.Highway.Main.toggle) {
			mc.fontRenderer.drawString(ChatFormatting.RED + "ElytraBot Status: " + ChatFormatting.BLUE + me.bebeli555.ElytraBot.Highway.Main.status, 390, 475, 0xffff);
		} else if (me.bebeli555.ElytraBot.Highway.Main.baritonetoggle) {
			mc.fontRenderer.drawString(ChatFormatting.RED + "ElytraBot Status: " + ChatFormatting.BLUE + "Using baritone to overcome obstacle", 390, 475, 0xffff);
		} else if (me.bebeli555.ElytraBot.Settings.AutoRepair.AutoRepair) {
			mc.fontRenderer.drawString(ChatFormatting.RED + "ElytraBot Status: " + ChatFormatting.BLUE + "Repairing Elytra", 390, 475, 0xffff);
		} else if (me.bebeli555.ElytraBot.Settings.Diagonal.toggle) {
			mc.fontRenderer.drawString(ChatFormatting.RED + "ElytraBot Status: " + ChatFormatting.BLUE + me.bebeli555.ElytraBot.Settings.Diagonal.status, 390, 475, 0xffff);
		} else if (me.bebeli555.ElytraBot.Settings.Diagonal.baritonetoggle) {
			mc.fontRenderer.drawString(ChatFormatting.RED + "ElytraBot Status: " + ChatFormatting.BLUE + "Using baritone to overcome obstacle", 390, 475, 0xffff);
		} else if (me.bebeli555.ElytraBot.Overworld.Main.toggle) {
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
			try {
				for (BlockPos blockPos : PositionsYellow) {
					BlockPos BlockPos4 = new BlockPos(blockPos);
					final AxisAlignedBB Axis = GetAxis(BlockPos4);
					DrawPathBox(Axis, 0f, 0f, 1f, 1f, false, true);
				}

				for (BlockPos blockPos : PositionsGreen) {
					BlockPos BlockPos4 = new BlockPos(blockPos);
					final AxisAlignedBB Axis = GetAxis(BlockPos4);
					DrawPathBox(Axis, 0f, 0f, 1f, 1f, true, false);
				}
			} catch (Exception e22) {}
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
        if (Green) {
        	//Green
        	GL11.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
        } else if (Yellow){
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
