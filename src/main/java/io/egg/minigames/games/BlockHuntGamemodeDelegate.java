package io.egg.minigames.games;

import io.egg.minigames.profiles.EventHandler;
import io.egg.minigames.profiles.PlayerJoinProfileEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.metadata.other.FallingBlockMeta;
import net.minestom.server.event.player.PlayerHandAnimationEvent;
import net.minestom.server.event.player.PlayerStartDiggingEvent;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;

public class BlockHuntGamemodeDelegate extends BasicMinigameDelegate {
    public BlockHuntGamemodeDelegate() {
        map = "bh-caverns";

    }

    @Override
    public void setupInstance(Instance i) {
        super.setupInstance(i);
        defaultBar.name(Component.text("Playing Block Hunt on " + map, TextColor.color(0x00ffff)));
    }

    @Override
    @EventHandler
    public void join(PlayerJoinProfileEvent e) {
        super.join(e);
        if (e.isCancelled()) return;


    }
    @EventHandler
    public void changeblock(PlayerStartDiggingEvent e) {
        Player p = e.getPlayer();
        if (p.getEntityType() != EntityType.FALLING_BLOCK) {
            p.switchEntityType(EntityType.FALLING_BLOCK);
        }
        FallingBlockMeta bb = (FallingBlockMeta) p.getEntityMeta();
        Block b = Block.fromStateId((short) e.getBlockStateId());
        bb.setBlock(b);
        e.getPlayer().sendMessage(Component.text("You are now disguised as " + b.getName(), TextColor.color(0x82abba)));


    }

    @Override
    public String getName() {
        return "Block Hunt";
    }
}
