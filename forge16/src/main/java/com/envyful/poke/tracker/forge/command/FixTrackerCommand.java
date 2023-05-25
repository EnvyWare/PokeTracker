package com.envyful.poke.tracker.forge.command;

import com.envyful.api.command.annotate.Child;
import com.envyful.api.command.annotate.Command;
import com.envyful.api.command.annotate.Permissible;
import com.envyful.api.command.annotate.executor.CommandProcessor;
import com.envyful.api.command.annotate.executor.Sender;
import com.envyful.api.concurrency.UtilConcurrency;
import com.envyful.api.forge.chat.UtilChatColour;
import com.envyful.poke.tracker.forge.PokeTrackerForge;
import com.envyful.poke.tracker.forge.tracker.PokeTrackerFactory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Util;

@Command(
        value = "fix",
        description = "Fix poketracker duplicates"
)
@Permissible("legend.tracker.forge.command.reload")
@Child
public class FixTrackerCommand {

    @CommandProcessor
    public void onCommand(@Sender ServerPlayerEntity player, String[] args) {
        UtilConcurrency.runAsync(() -> {
            PokeTrackerFactory.fixTrackerDuplicates();
            PokeTrackerFactory.save();
        });
        player.sendMessage(UtilChatColour.colour(
                PokeTrackerForge.getInstance().getLocale().getReloadedConfigs()
        ), Util.NIL_UUID);
    }
}
