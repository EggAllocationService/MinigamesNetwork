package io.egg.minigames.instances;

import com.google.common.collect.HashBiMap;
import io.egg.minigames.generators.SuperflatWorldGenerator;
import io.egg.minigames.profiles.DefaultProfileDelegate;
import io.egg.minigames.profiles.ProfileData;
import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.block.Block;


/*
Ideally here is what we want:
InstanceManager.spawn(
 */

public class InstanceManager {
    private static InstanceManager inst;

    private net.minestom.server.instance.InstanceManager handle;
    private HashBiMap<String, ProfiledInstance> instances = HashBiMap.create();
    private HashBiMap<String, InstanceContainer> instancesByName = HashBiMap.create();
    public String instanceName(InstanceContainer i) {
        return instancesByName.inverse().get(i);
    }

    public void createLobby() {
        InstanceContainer ic = handle.createInstanceContainer();

        ic.setChunkGenerator(new SuperflatWorldGenerator(Block.GRASS_BLOCK));
        ProfileData pd = new ProfileData("lobby", false);
        ProfiledInstance pi = new ProfiledInstance(ic, new DefaultProfileDelegate(ic, "lobby"), null);

        instances.put("lobby", pi);
        instancesByName.put("lobby", ic);
    }

    public static InstanceManager getInstance() {
        return inst;
    }
    public InstanceManager() {
        handle = MinecraftServer.getInstanceManager();
    }

    public static void init() {
        inst = new InstanceManager();
    }
    public static InstanceManager get() {
        return inst;
    }
}
