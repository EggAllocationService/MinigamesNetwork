package io.egg.minigames.profiles.delegates;

import io.egg.minigames.blocks.KeystoneBlock;
import io.egg.minigames.generators.VoidWorldGenerator;
import io.egg.minigames.profiles.DefaultProfileDelegate;
import io.egg.minigames.profiles.EventHandler;
import io.egg.minigames.profiles.ProfileData;
import net.minestom.server.data.Data;
import net.minestom.server.data.DataImpl;
import net.minestom.server.data.SerializableData;
import net.minestom.server.data.SerializableDataImpl;
import net.minestom.server.entity.GameMode;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.event.player.PlayerMoveEvent;
import net.minestom.server.event.player.PlayerUseItemOnBlockEvent;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.block.Block;
import net.minestom.server.item.Material;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.utils.Position;

public class LobbyProfileDelegate extends DefaultProfileDelegate {
    public LobbyProfileDelegate() {

    }

    @Override
    public ProfileData getData() {
        return new ProfileData("lobby", true);
    }

    @Override
    public void setupInstance(Instance i) {
        i.setChunkGenerator(new VoidWorldGenerator(VoidWorldGenerator.LOBBY));
        i.getWorldBorder().setCenter(0, 0);
        i.getWorldBorder().setDiameter(60);
    }

    @Override
    @EventHandler
    public void placeBlock(PlayerBlockPlaceEvent e) {
        if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
        }
    }

    @Override
    @EventHandler
    public void removeBlock(PlayerBlockBreakEvent e) {
        if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
        }
    }

    @Override
    public String getName() {
        return "io.egg.LobbyProfileDelegate";
    }

    @EventHandler
    public void voidTeleport(PlayerMoveEvent e) {
       if (e.getNewPosition().getY() <= 60 && e.getPlayer().getGameMode() != GameMode.CREATIVE) {
           e.getPlayer().teleport(new Position(0.5, 65, 0.5, e.getPlayer().getPosition().getYaw(), e.getPlayer().getPosition().getPitch()));
       }
    }
    @EventHandler
    public void bucket(PlayerUseItemOnBlockEvent e) {
        if (e.getItemStack().getMaterial() == Material.MILK_BUCKET && e.getPlayer().getGameMode() == GameMode.CREATIVE) {
            Instance i = e.getPlayer().getInstance();
            SerializableData d = new SerializableDataImpl();
            d.set("interactions", 0);
            BlockPosition target = e.getPosition().add(0, 1, 0);
            i.setCustomBlock(target.getX(), target.getY(), target.getZ(), "keystone", d);

        } else if (e.getItemStack().getMaterial() == Material.WATER_BUCKET && e.getPlayer().getGameMode() == GameMode.CREATIVE) {
            Instance i = e.getPlayer().getInstance();
            i.setBlock(e.getPosition().add(0, 1, 0), Block.WATER);

        }
    }

}
