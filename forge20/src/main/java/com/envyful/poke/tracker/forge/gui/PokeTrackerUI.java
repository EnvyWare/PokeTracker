package com.envyful.poke.tracker.forge.gui;

import com.envyful.api.forge.chat.UtilChatColour;
import com.envyful.api.forge.config.UtilConfigInterface;
import com.envyful.api.forge.config.UtilConfigItem;
import com.envyful.api.forge.player.ForgeEnvyPlayer;
import com.envyful.api.gui.factory.GuiFactory;
import com.envyful.api.gui.pane.Pane;
import com.envyful.poke.tracker.forge.PokeTrackerForge;
import com.envyful.poke.tracker.forge.config.PokeTrackerConfig;
import com.envyful.poke.tracker.forge.config.PokeTrackerGui;
import com.envyful.poke.tracker.forge.tracker.PokeTrackerFactory;
import com.envyful.poke.tracker.forge.tracker.data.EntityData;

import java.util.List;

public class PokeTrackerUI {

    public static void open(ForgeEnvyPlayer player) {
        PokeTrackerGui config = PokeTrackerForge.getInstance().getGui();

        Pane pane = GuiFactory.paneBuilder()
                .topLeftX(0)
                .topLeftY(0)
                .width(9)
                .height(config.getGuiSettings().getHeight())
                .build();

        UtilConfigInterface.fillBackground(pane, config.getGuiSettings());

        for (PokeTrackerConfig.TrackerSection value :
                PokeTrackerForge.getInstance().getConfig().getTrackers().values()) {
            List<EntityData> trackedEntities = PokeTrackerFactory.getTrackedEntities(value.getName());

            if (trackedEntities.isEmpty()) {
                UtilConfigItem.builder()
                                .extendedConfigItem(player, pane, value.getDisplayItem(),
                                        PlaceholderAPITransformer.of(player.getParent()),
                                        SpriteTransformer.of(null));
                continue;
            }

            UtilConfigItem.builder()
                    .extendedConfigItem(player, pane, value.getDisplayItem(),
                            PlaceholderAPITransformer.of(player.getParent()),
                            SpriteTransformer.of(trackedEntities.get(0)));
        }

        GuiFactory.guiBuilder()
                .setPlayerManager(PokeTrackerForge.getInstance().getPlayerManager())
                .addPane(pane)
                .height(config.getGuiSettings().getHeight())
                .title(UtilChatColour.colour(config.getGuiSettings().getTitle()))
                .build().open(player);
    }

}
