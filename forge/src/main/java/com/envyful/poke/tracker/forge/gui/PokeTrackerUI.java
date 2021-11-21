package com.envyful.poke.tracker.forge.gui;

import com.envyful.api.config.type.ConfigItem;
import com.envyful.api.forge.chat.UtilChatColour;
import com.envyful.api.forge.config.UtilConfigItem;
import com.envyful.api.gui.factory.GuiFactory;
import com.envyful.api.gui.pane.Pane;
import com.envyful.api.player.EnvyPlayer;
import com.envyful.poke.tracker.forge.PokeTrackerForge;
import com.envyful.poke.tracker.forge.config.PokeTrackerConfig;
import com.envyful.poke.tracker.forge.config.PokeTrackerGui;
import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayerMP;

public class PokeTrackerUI {

    public static void open(EnvyPlayer<EntityPlayerMP> player) {
        PokeTrackerGui config = PokeTrackerForge.getInstance().getGui();

        Pane pane = GuiFactory.paneBuilder()
                .topLeftX(0)
                .topLeftY(0)
                .width(9)
                .height(config.getGuiSettings().getHeight())
                .build();

        for (ConfigItem fillerItem : config.getGuiSettings().getFillerItems()) {
            pane.add(GuiFactory.displayable(UtilConfigItem.fromConfigItem(fillerItem)));
        }

        for (PokeTrackerConfig.TrackerSection value :
                PokeTrackerForge.getInstance().getConfig().getTrackers().values()) {
            UtilConfigItem.addConfigItem(pane, Lists.newArrayList(PlaceholderAPITransformer.of(player.getParent())),
                                         value.getDisplayItem()
            );
        }

        GuiFactory.guiBuilder()
                .setPlayerManager(PokeTrackerForge.getInstance().getPlayerManager())
                .addPane(pane)
                .height(config.getGuiSettings().getHeight())
                .setCloseConsumer(envyPlayer -> {})
                .title(UtilChatColour.translateColourCodes('&', config.getGuiSettings().getTitle()))
                .build().open(player);
    }

}
