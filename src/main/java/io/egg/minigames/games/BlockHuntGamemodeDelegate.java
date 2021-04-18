package io.egg.minigames.games;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import io.egg.minigames.profiles.EventHandler;
import io.egg.minigames.profiles.PlayerJoinProfileEvent;
import io.egg.minigames.profiles.PlayerLeaveProfileEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.minestom.server.attribute.Attribute;
import net.minestom.server.attribute.AttributeOperation;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.metadata.other.FallingBlockMeta;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import net.minestom.server.event.player.PlayerStartDiggingEvent;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.item.StackingRule;
import net.minestom.server.item.attribute.AttributeSlot;
import net.minestom.server.item.attribute.ItemAttribute;
import net.minestom.server.utils.BlockPosition;

public class BlockHuntGamemodeDelegate extends BasicMinigameDelegate {
    HashMap<BlockPosition, Player> hiddenPlayers = new HashMap();

    public BlockHuntGamemodeDelegate() {
        map = "bh-caverns";

    }

    @Override
    public void tick() {
        super.tick();
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
        e.getP().setGameMode(GameMode.ADVENTURE);
        Player p = e.getP();
        p.setAllowFlying(false);
        ItemStack helmetOfDeath = ItemStack.of(Material.DIAMOND_HELMET);
        helmetOfDeath.getMeta().getAttributes().add(new ItemAttribute(UUID.randomUUID(), "health", Attribute.MAX_HEALTH, AttributeOperation.ADDITION, -18, AttributeSlot.HEAD));
        p.setHelmet(helmetOfDeath);
    


    }

    @EventHandler
    @Override
    public void leave(PlayerLeaveProfileEvent e) {
        e.getPlayer().switchEntityType(EntityType.PLAYER);
    }
    
    public void disconnect(PlayerDisconnectEvent e) {
        System.out.println("Player disconnect");
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
        Set<Player> viewers = new HashSet<>(p.getViewers());
        viewers.forEach(p::removeViewer);
        viewers.forEach(p::addViewer);
        
    
        
        e.getPlayer().sendMessage(Component.text("You are now disguised as " + ((FallingBlockMeta)p.getEntityMeta()).getBlock().getName(), TextColor.color(0x82abba)));


    }

    @Override
    public String getName() {
        return "Block Hunt";
    }
}
