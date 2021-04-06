package io.egg.minigames.commands;

import io.egg.minigames.entities.MonsterBoatCreature;
import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpawnBoatCommand extends Command {
    public SpawnBoatCommand() {
        super("theboatcometh");
        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("Mistakes were made");
            if (sender.isPlayer()) {
                Player p = (Player) sender;
                EntityCreature horror = new MonsterBoatCreature();
                horror.setInstance(p.getInstance(), p.getPosition());
                horror.setTarget(p);

            }
        });
    }
}
