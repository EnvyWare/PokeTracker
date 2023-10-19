package com.envyful.poke.tracker.forge.command;

import com.envyful.api.command.annotate.Command;
import com.envyful.api.command.annotate.SubCommands;
import com.envyful.api.command.annotate.executor.CommandProcessor;
import com.envyful.api.command.annotate.executor.Sender;
import com.envyful.poke.tracker.forge.PokeTrackerForge;
import com.envyful.poke.tracker.forge.gui.PokeTrackerUI;
import net.minecraft.server.level.ServerPlayer;

@Command(
        value = {
                "poketracker",
                "lastlegend",
                "ll",
                "lastultrabeast",
                "lastub",
                "lastboss",
                "lastshiny",
                "last"
        }
)
@SubCommands(
        ReloadCommand.class
)
public class PokeTrackerCommand {

    @CommandProcessor
    public void onCommand(@Sender ServerPlayer player, String[] args) {
        PokeTrackerUI.open(PokeTrackerForge.getInstance().getPlayerManager().getPlayer(player));
    }
}
