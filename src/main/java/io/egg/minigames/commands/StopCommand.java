package io.egg.minigames.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;

public class StopCommand extends Command {
    public StopCommand() {
        super("stop");
        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("Stopping server");
            MinecraftServer.stopCleanly();
        });
    }
}
