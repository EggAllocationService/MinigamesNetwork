package io.egg.minigames.games;

import io.egg.minigames.blocks.spawn.SpawnLocationManager;
import io.egg.minigames.entities.MonsterBoatCreature;
import io.egg.minigames.generators.VoidWorldGenerator;
import io.egg.minigames.loading.WorldManager;
import io.egg.minigames.profiles.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.instance.Instance;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.utils.Position;
import net.minestom.server.world.biomes.Biome;

public class BasicMinigameDelegate extends DefaultProfileDelegate {
    String map;

    @Override
    public ProfileData getData() {
        return new ProfileData(map, false);
    }

    @Override
    public void setupInstance(Instance i) {
        i.setChunkGenerator(new VoidWorldGenerator(Biome.PLAINS));
        defaultBar.name(Component.text("Waiting For Players", TextColor.color(0x341bad)));
        defaultBar.progress(0.0f);
        EntityCreature chunkloader = new MonsterBoatCreature();
        chunkloader.setInstance(i, new Position(0, 100, 0));
        for (int x = -5; x <= 5; x++) {
            for (int y = -5; y <= 5; y++) {
                i.loadChunk(x, y);
            }
        }
        

    }

    public int getMaxPlayers() {
        return 8;
    }

    @EventHandler
    public void join(PlayerJoinProfileEvent e) {
        if (map == null) {
            e.setCancelled(true);
            e.setCancelReason("Map is not set, cannot start instance!");
            return;
        }
        BlockPosition spawnLocation = SpawnLocationManager.getPrimary(map);
        if (spawnLocation == null) {
            e.setCancelled(true);
            e.setCancelReason("No primary spawn location found");
            return;
        }
        e.setJoinPos(spawnLocation.toPosition().add(0.5, 1, 0.5));
        e.getP().showBossBar(defaultBar);
        defaultBar.progress((getInstance().getPlayers().size() + 1) / getMaxPlayers());

    }
    @EventHandler
    public void leave(PlayerLeaveProfileEvent e) {

    }

    @Override
    public String getName() {
        return "GenericMinigame";
    }
}
