package com.envyful.legend.tracker.forge.command;

import com.envyful.api.command.annotate.Child;
import com.envyful.api.command.annotate.Command;
import com.envyful.api.command.annotate.Permissible;
import com.envyful.api.command.annotate.executor.CommandProcessor;
import com.envyful.api.command.annotate.executor.Sender;
import com.envyful.api.forge.chat.UtilChatColour;
import com.envyful.legend.tracker.forge.LegendTrackerForge;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;

@Command(
        value = "reload",
        description = "Reloads the configs"
)
@Permissible("legend.tracker.forge.command.reload")
@Child
public class ReloadCommand {

        @CommandProcessor
        public void onCommand(@Sender EntityPlayerMP player, String[] args) {
                LegendTrackerForge.getInstance().reloadConfig();
                player.sendMessage(new TextComponentString(UtilChatColour.translateColourCodes(
                        '&',
                        LegendTrackerForge.getInstance().getLocale().getReloadedConfigs()
                )));
        }
}
