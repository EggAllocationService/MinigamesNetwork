package io.egg.minigames.commands;

import io.egg.minigames.instances.InstanceManager;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.CommandExecutor;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.utils.Position;
import org.apache.logging.log4j.util.Strings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SwitchInstanceCommand extends Command {
    public SwitchInstanceCommand() {
        super("instance");
        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("Available instances:");
            sender.sendMessage(Strings.join(InstanceManager.get().getNames(), ','));
        });
        var instanceArgument = ArgumentType.String("instance");
        addSyntax(this::switchInstance, instanceArgument);
    }
    public void switchInstance(CommandSender sender, CommandContext context) {
        InstanceContainer i = InstanceManager.get().getInstance(context.get("instance"));
        if ( i == null) {
            sender.sendMessage("Available instances:");
            sender.sendMessage(Strings.join(InstanceManager.get().getNames(), ' '));
            return;
        }
        Player p = (Player) sender;
        InstanceManager.get().transfer(p, i);
    }
}

