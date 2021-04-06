package io.egg.minigames.blocks.spawn;

import io.egg.minigames.loading.World;
import net.minestom.server.utils.BlockPosition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class SpawnLocationManager {
    // indexed by world name
    public static HashMap<String, SpawnLocations> worlds = new HashMap<>();
    public static HashSet<Integer> takenPos = new HashSet<>();
    public static void posLoad(World w, BlockPosition b, String tag, boolean primary) {
        posLoad(w.id, b, tag,primary);
    }
    public static void posLoad(String world, BlockPosition position, String tag, boolean primary) {
        assert tag != null;
        takenPos.add(world.hashCode() * position.hashCode());
        if (!worlds.containsKey(world)) {
            worlds.put(world, new SpawnLocations(world));
        }
        SpawnLocations sp = worlds.get(world);
        if (primary && sp.primary == null) {
            sp.primary = position;
        }
        if (!sp.locations.containsKey(tag)) sp.locations.put(tag, new ArrayList<>());
        boolean found = false;
        for (BlockPosition p : sp.locations.get(tag)) {
            if (p.hashCode() == position.hashCode()) {
                found = true;
                break;
            }
        }
        if (found) return;
        sp.locations.get(tag).add(position);

    }

    public static BlockPosition getPrimary(String world) {
        if (!worlds.containsKey(world)) return null;
        return worlds.get(world).primary;
    }
}
