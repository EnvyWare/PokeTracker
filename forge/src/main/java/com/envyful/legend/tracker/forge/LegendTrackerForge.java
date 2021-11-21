package com.envyful.legend.tracker.forge;

import com.envyful.api.forge.command.ForgeCommandFactory;
import com.envyful.api.forge.gui.factory.ForgeGuiFactory;
import com.envyful.api.forge.player.ForgePlayerManager;
import com.envyful.api.gui.factory.GuiFactory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(
        modid = LegendTrackerForge.MOD_ID,
        name = "LegendTracker Forge",
        version = LegendTrackerForge.VERSION,
        acceptableRemoteVersions = "*"
)
public class LegendTrackerForge {

    protected static final String MOD_ID = "legendtracker";
    protected static final String VERSION = "1.6.0";

    @Mod.Instance(MOD_ID)
    private static LegendTrackerForge instance;

    private ForgeCommandFactory commandFactory = new ForgeCommandFactory();
    private ForgePlayerManager playerManager = new ForgePlayerManager();

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        GuiFactory.setPlatformFactory(new ForgeGuiFactory());
    }

    @Mod.EventHandler
    public void onServerStart(FMLServerStartingEvent event) {

    }

    public static LegendTrackerForge getInstance() {
        return instance;
    }
}