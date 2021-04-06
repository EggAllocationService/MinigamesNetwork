package io.egg.minigames.blocks.spawn;

import io.egg.minigames.loading.World;
import net.minestom.server.instance.Instance;
import net.minestom.server.utils.BlockPosition;

import java.util.ArrayList;
import java.util.HashMap;

public class SpawnLocations {
    public SpawnLocations(String w) {
        world = w;
    }
    public String world;
    public BlockPosition primary;
    public HashMap<String, ArrayList<BlockPosition>> locations = new HashMap<>();


}
