package com.envyful.legend.tracker.forge.command;

import com.envyful.api.command.annotate.Command;
import com.envyful.api.command.annotate.executor.CommandProcessor;
import com.envyful.api.command.annotate.executor.Sender;
import net.minecraft.entity.player.EntityPlayerMP;

@Command(
        value = "legendtracker",
        description = "Opens the tracker UI",
        aliases = {
                "lastlegend",
                "ll",
                "lastultrabeast",
                "lastub",
                "lastboss",
                "lastshiny",
                "last"
        }
)
public class LegendTrackerCommand {

        @CommandProcessor
        public void onCommand(@Sender EntityPlayerMP player, String[] args) {

        }
}
