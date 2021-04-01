package io.egg.minigames.tasks;

import com.google.common.collect.Collections2;
import io.egg.minigames.instances.InstanceManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.InstanceContainer;

public class InstanceNameTask implements Runnable{
    @Override
    public void run() {
        for (Player p : MinecraftServer.getConnectionManager().getOnlinePlayers()) {
            String s = InstanceManager.getInstance().instanceName((InstanceContainer) p.getInstance());
            Component t = Component.text("You are playing on instance")
                    .color(TextColor.color(0x33abd3))
                    .append(
                            Component.text(s)
                            .color(TextColor.color(0x8a329b))

                    );
            p.sendActionBar(t);
        }
    }

}
