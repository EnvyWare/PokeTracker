package com.envyful.legend.tracker.forge.gui;

import com.envyful.api.config.type.ConfigItem;
import com.envyful.api.forge.chat.UtilChatColour;
import com.envyful.api.forge.config.UtilConfigItem;
import com.envyful.api.gui.factory.GuiFactory;
import com.envyful.api.gui.pane.Pane;
import com.envyful.api.player.EnvyPlayer;
import com.envyful.legend.tracker.forge.LegendTrackerForge;
import com.envyful.legend.tracker.forge.config.LegendTrackerConfig;
import com.envyful.legend.tracker.forge.config.LegendTrackerGui;
import net.minecraft.entity.player.EntityPlayerMP;

public class LegendTrackerUI {

    public static void open(EnvyPlayer<EntityPlayerMP> player) {
        LegendTrackerGui config = LegendTrackerForge.getInstance().getGui();

        Pane pane = GuiFactory.paneBuilder()
                .topLeftX(0)
                .topLeftY(0)
                .width(9)
                .height(config.getGuiSettings().getHeight())
                .build();

        for (ConfigItem fillerItem : config.getGuiSettings().getFillerItems()) {
            pane.add(GuiFactory.displayable(UtilConfigItem.fromConfigItem(fillerItem)));
        }

        for (LegendTrackerConfig.TrackerSection value :
                LegendTrackerForge.getInstance().getConfig().getTrackers().values()) {

        }

        GuiFactory.guiBuilder()
                .setPlayerManager(LegendTrackerForge.getInstance().getPlayerManager())
                .addPane(pane)
                .height(config.getGuiSettings().getHeight())
                .title(UtilChatColour.translateColourCodes('&', config.getGuiSettings().getTitle()))
                .build().open(player);
    }

}
