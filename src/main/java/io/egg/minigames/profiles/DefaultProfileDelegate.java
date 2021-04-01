package io.egg.minigames.profiles;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.minestom.server.color.Color;
import net.minestom.server.event.Event;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.instance.InstanceContainer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class DefaultProfileDelegate {
    InstanceContainer instance;
    String world;
    public DefaultProfileDelegate(InstanceContainer i, String w) {

        instance = i;
        world = w;
        initEvents();
    }

    public void initEvents() {
        Method[] ms = this.getClass().getMethods();
        for (Method m : ms) {
            if (m.isAnnotationPresent(EventHandler.class)) {
                // has thingy
                Parameter[] t = m.getParameters();
                if (t.length == 0) continue;
                Class eventClass = t[0].getClass();
                if (eventClass.getSuperclass() != Event.class) return;
                instance.addEventCallback((Class<Event>) eventClass, event -> {
                    try {
                        m.invoke(this, event);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
            }
        }

    }
    @EventHandler
    public void placeBlock(PlayerBlockPlaceEvent e) {
        e.setCancelled(true);
        e.getPlayer().sendMessage(Component.text("This world does not have any delegate for placing blocks", TextColor.color(255, 25, 25)));
    }
    @EventHandler
    public void removeBlock(PlayerBlockBreakEvent e) {
        e.setCancelled(true);
        e.getPlayer().sendMessage(Component.text("This world does not have any delegate for breaking blocks", TextColor.color(255, 25, 25)));
    }

}
