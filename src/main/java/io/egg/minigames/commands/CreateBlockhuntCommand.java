package io.egg.minigames.commands;

import io.egg.minigames.games.BlockHuntGamemodeDelegate;
import io.egg.minigames.instances.InstanceManager;
import net.minestom.server.command.builder.Command;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;

public class CreateBlockhuntCommand extends Command {
    public CreateBlockhuntCommand() {
        super("blockhunt");
        setDefaultExecutor((sender, context) -> {
            BlockHuntGamemodeDelegate d = new BlockHuntGamemodeDelegate();
            try {
                InstanceManager.get().spawn("blockhunt", d);
                sender.sendMessage("Created blockhunt");
            } catch (NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }
}
