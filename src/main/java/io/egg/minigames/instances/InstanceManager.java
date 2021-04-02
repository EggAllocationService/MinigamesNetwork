package io.egg.minigames.instances;

import com.google.common.collect.HashBiMap;
import io.egg.minigames.generators.VoidWorldGenerator;
import io.egg.minigames.loading.DatabaseWorldLoader;
import io.egg.minigames.profiles.DefaultProfileDelegate;
import io.egg.minigames.profiles.ProfileData;
import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.Instance;
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


    public ProfiledInstance getProfile(String name) {
        return instances.get(name);
    }
    public ProfiledInstance getProfile(InstanceContainer c) {
        return instances.get(instanceName(c));
    }
    public void createLobby() {
        InstanceContainer ic = handle.createInstanceContainer();

        ic.setChunkGenerator(new VoidWorldGenerator(Block.GRASS_BLOCK));
        ProfileData pd = new ProfileData("lobby", true);
        ProfiledInstance pi = new ProfiledInstance(ic, new DefaultProfileDelegate(ic, "lobby"), pd);
        instances.put("lobby", pi);
        instancesByName.put("lobby", ic);
        ic.setChunkLoader(new DatabaseWorldLoader(ic));
        ic.getWorldBorder().setCenter(0, 0);
        ic.getWorldBorder().setDiameter(51);
    }

    public Instance getInstance(String name) {
        return instancesByName.get(name);
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
