package io.egg.minigames.games;

import io.egg.minigames.blocks.spawn.SpawnLocationManager;
import io.egg.minigames.loading.WorldManager;
import io.egg.minigames.profiles.DefaultProfileDelegate;
import io.egg.minigames.profiles.EventHandler;
import io.egg.minigames.profiles.PlayerJoinProfileEvent;
import io.egg.minigames.profiles.ProfileData;
import net.minestom.server.instance.Instance;
import net.minestom.server.utils.BlockPosition;

public class BasicMinigameDelegate extends DefaultProfileDelegate {
    String map;

    @Override
    public ProfileData getData() {
        return new ProfileData(map, false);
    }

    @Override
    public void setupInstance(Instance i) {

        int id = 0;
        for (int cx = -5; cx < 6; cx++) {
            for (int cz = -5; cz < 6; cz++) {
                i.loadChunk(cx, cz);
                id++;
            }
        }
        System.out.println(id);

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
    }

    @Override
    public String getName() {
        return "GenericMinigame";
    }
}
