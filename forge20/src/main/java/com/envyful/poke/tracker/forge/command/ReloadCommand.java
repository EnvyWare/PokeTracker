package com.envyful.poke.tracker.forge.command;

import com.envyful.api.command.annotate.Command;
import com.envyful.api.command.annotate.executor.CommandProcessor;
import com.envyful.api.command.annotate.executor.Sender;
import com.envyful.api.command.annotate.permission.Permissible;
import com.envyful.api.forge.chat.UtilChatColour;
import com.envyful.poke.tracker.forge.PokeTrackerForge;
import net.minecraft.server.level.ServerPlayer;

@Command(
        value = "reload"
)
@Permissible("legend.tracker.forge.command.reload")
public class ReloadCommand {

        @CommandProcessor
        public void onCommand(@Sender ServerPlayer player, String[] args) {
                PokeTrackerForge.getInstance().reloadConfig();
                player.sendSystemMessage(UtilChatColour.colour(
                        PokeTrackerForge.getInstance().getLocale().getReloadedConfigs()
                ));
        }
}
