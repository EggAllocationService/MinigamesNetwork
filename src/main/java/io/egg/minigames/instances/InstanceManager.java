package io.egg.minigames.instances;

import com.google.common.collect.HashBiMap;
import io.egg.minigames.blocks.OptionalBlock;
import io.egg.minigames.generators.VoidWorldGenerator;
import io.egg.minigames.loading.DatabaseWorldLoader;
import io.egg.minigames.profiles.DefaultProfileDelegate;
import io.egg.minigames.profiles.PlayerJoinProfileEvent;
import io.egg.minigames.profiles.ProfileData;
import io.egg.minigames.profiles.delegates.LobbyProfileDelegate;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.Position;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;


/*
Ideally here is what we want:
InstanceManager.spawn(String name, Class<DefaultProfileDelegate> delegate);

// from there, DefaultProfileDelegate should handle setting the world, getting profile data, etc
 */

public class InstanceManager {
    private static InstanceManager inst;

    private net.minestom.server.instance.InstanceManager handle;
    private HashBiMap<String, ProfiledInstance> instances = HashBiMap.create();
    private HashBiMap<String, InstanceContainer> instancesByName = HashBiMap.create();
    public String instanceName(InstanceContainer i) {
        return instancesByName.inverse().get(i);
    }


    public DefaultProfileDelegate getDelegate(String s) {
        ProfiledInstance i = instances.get(s);
        if (i == null) return null;
        return i.getDelegate();
    }

    public void tick() {
        OptionalBlock.tick();
        for (ProfiledInstance i : instances.values()) {
            i.getDelegate().tick();
        }
    }

    public ProfiledInstance getProfile(String name) {
        return instances.get(name);
    }
    public ProfiledInstance getProfile(InstanceContainer c) {
        return instances.get(instanceName(c));
    }
    public Set<String> getNames() {
        return instances.keySet();
    }
    public InstanceContainer spawn(String name, DefaultProfileDelegate delegate) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        InstanceContainer ic = handle.createInstanceContainer();
        delegate.setInstance(ic);
        delegate.setupInstance(ic);
        ProfileData pd = delegate.getData();
        ProfiledInstance pi = new ProfiledInstance(ic, delegate, pd, name);
        instances.put(name, pi);
        instancesByName.put(name, ic);
        ic.setChunkLoader(new DatabaseWorldLoader(ic, pd.mapName));
        return ic;
    }

    public void transfer(Player p, InstanceContainer target) {
        ProfiledInstance pi = getProfile(target);
        if (pi == null) {
            //p.setInstance(target, new Position(0.5, 65, 0.5));
            return;
        }
        PlayerJoinProfileEvent e = new PlayerJoinProfileEvent(p);
        target.callEvent(PlayerJoinProfileEvent.class, e);
        if (e.isCancelled()) {
            p.sendMessage(Component.text("Pre-receive hook was declined by other instance: ", TextColor.color(255, 50, 50))
                .append(Component.text(e.getCancelReason(), TextColor.color(0xfff133)))
            );
            return;
        }

        p.sendMessage(Component.text("Sending you to ", TextColor.color(0x036bfc))
            .append(Component.text(instanceName(target), TextColor.color(0x036bfc)))
        );

        p.setInstance(target, e.getJoinPos());


    }

    public InstanceContainer getInstance(String name) {
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
