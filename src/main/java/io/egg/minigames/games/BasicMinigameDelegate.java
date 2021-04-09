package io.egg.minigames.games;

import io.egg.minigames.blocks.spawn.SpawnLocationManager;
import io.egg.minigames.loading.WorldManager;
import io.egg.minigames.profiles.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
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
        defaultBar.name(Component.text("Waiting For Players", TextColor.color(0x341bad)));
        defaultBar.progress(0.0f);

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
