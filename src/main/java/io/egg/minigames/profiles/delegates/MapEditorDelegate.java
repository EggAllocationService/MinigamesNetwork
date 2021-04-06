package io.egg.minigames.profiles.delegates;

import io.egg.minigames.generators.VoidWorldGenerator;
import io.egg.minigames.loading.World;
import io.egg.minigames.loading.WorldManager;
import io.egg.minigames.profiles.DefaultProfileDelegate;
import io.egg.minigames.profiles.EventHandler;
import io.egg.minigames.profiles.ProfileData;
import net.minestom.server.data.SerializableData;
import net.minestom.server.data.SerializableDataImpl;
import net.minestom.server.entity.GameMode;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.event.player.PlayerUseItemOnBlockEvent;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.item.Material;
import net.minestom.server.network.packet.server.play.BlockChangePacket;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.world.biomes.Biome;

public class MapEditorDelegate extends DefaultProfileDelegate {
    World w;
    public MapEditorDelegate(String world) {
        w = WorldManager.getWorld(world);

    }
    @Override
    public ProfileData getData() {
        return new ProfileData(w.id, true);
    }

    @Override
    public void setupInstance(Instance i) {
        i.setChunkGenerator(new VoidWorldGenerator(Biome.PLAINS));

    }

    @Override
    public void placeBlock(PlayerBlockPlaceEvent e) {

    }

    @Override
    @EventHandler
    public void removeBlock(PlayerBlockBreakEvent e) {
        if (e.getPlayer().getItemInMainHand().getMaterial() == Material.BLAZE_ROD && e.getPlayer().getGameMode() == GameMode.CREATIVE) {
            e.setCancelled(true);
            Instance i = e.getPlayer().getInstance();
            SerializableData d = new SerializableDataImpl();
            d.set("block", 1);
            BlockPosition target = e.getBlockPosition().add(0, 0,0);

            i.setCustomBlock(target.getX(), target.getY(), target.getZ(), "optional_block", d);
        }
    }

    @EventHandler
    public void bucket(PlayerUseItemOnBlockEvent e) {
        if (e.getItemStack().getMaterial() == Material.LAVA_BUCKET && e.getPlayer().getGameMode() == GameMode.CREATIVE) {
            Instance i = e.getPlayer().getInstance();
            i.setBlock(e.getPosition().add(0, 1, 0), Block.LAVA);
        } else if (e.getItemStack().getMaterial() == Material.WATER_BUCKET && e.getPlayer().getGameMode() == GameMode.CREATIVE) {
            Instance i = e.getPlayer().getInstance();
            i.setBlock(e.getPosition().add(0, 1, 0), Block.WATER);

        } else if (e.getItemStack().getMaterial() == Material.BLAZE_ROD && e.getPlayer().getGameMode() == GameMode.CREATIVE) {
            Instance i = e.getPlayer().getInstance();
            SerializableData d = new SerializableDataImpl();
            d.set("primary", false);
            d.set("tag", "");
            BlockPosition target = e.getPosition().add(0, 1,0);

            i.setCustomBlock(target.getX(), target.getY(), target.getZ(), "spawn_location", d);
        }
    }

    @Override
    public String getName() {
        return "io.egg.MapEditor";
    }
}
