package com.bebeli555.ElytraBot;

import org.apache.logging.log4j.LogManager;


import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import com.bebeli555.ElytraBot.proxy.CommonProxy;

import com.bebeli555.ElytraBot.util.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.ITextComponent;

import com.bebeli555.ElytraBot.*;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class Init {
	public static final Logger LOGGER = LogManager.getLogger(Reference.MOD_ID);

	@Instance
	public static Init instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;

	@EventHandler
	public void PreInit(FMLPreInitializationEvent event) {

	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		init();
	}

	@EventHandler
	public void Postinit(FMLPostInitializationEvent event) {

	}

	public void init() {
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(new Init());
		MinecraftForge.EVENT_BUS.register(new Main());
		MinecraftForge.EVENT_BUS.register(new Commands());
		MinecraftForge.EVENT_BUS.register(new Renderer());
	
	}
	
}

