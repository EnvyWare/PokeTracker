package com.envyful.poke.tracker.forge.command;

import com.envyful.api.command.annotate.Child;
import com.envyful.api.command.annotate.Command;
import com.envyful.api.command.annotate.Permissible;
import com.envyful.api.command.annotate.executor.CommandProcessor;
import com.envyful.api.command.annotate.executor.Sender;
import com.envyful.api.forge.chat.UtilChatColour;
import com.envyful.poke.tracker.forge.PokeTrackerForge;
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
                PokeTrackerForge.getInstance().reloadConfig();
                player.sendMessage(new TextComponentString(UtilChatColour.translateColourCodes(
                        '&',
                        PokeTrackerForge.getInstance().getLocale().getReloadedConfigs()
                )));
        }
}
