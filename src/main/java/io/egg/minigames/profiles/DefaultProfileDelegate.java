package io.egg.minigames.profiles;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.minestom.server.color.Color;
import net.minestom.server.event.Event;
import net.minestom.server.event.entity.EntityAttackEvent;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.InstanceContainer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public abstract class DefaultProfileDelegate {
    InstanceContainer instance;


    public void initEvents() {
        Method[] ms = this.getClass().getMethods();
        for (Method m : ms) {
            if (m.isAnnotationPresent(EventHandler.class)) {
                System.out.println("Registering method " + m.getName() + " on " + this.getClass().getName());
                // has thingy
                Parameter[] t = m.getParameters();
                System.out.println(t.length + " parameters");
                if (t.length == 0) continue;
                Class eventClass = t[0].getType();
               // if (eventClass.getSuperclass() != Event.class) return;
                System.out.println("Method " + m.getName() + " is listening for event " + eventClass.getName());
                instance.addEventCallback((Class<Event>) eventClass, event -> {
                    //System.out.println("invoking method " + m.getName() + " on " + this.getClass().getName());
                    try {
                        m.invoke(this, event);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
            }
        }

    }
    public void setInstance(InstanceContainer i ) {
        instance = i;
        initEvents();
    }
    public abstract ProfileData getData();
    public abstract void setupInstance(Instance i);

    public void tick() {

    }

    @EventHandler
    public void damage(EntityAttackEvent e) {
        instance.getPlayers().forEach(player -> {
            player.sendMessage("entity attack!");
        });
    }
    @EventHandler
    public void placeBlock(PlayerBlockPlaceEvent e) {
        e.setCancelled(true);
        e.getPlayer().sendMessage(Component.text("This instance does not have any delegate for placing blocks", TextColor.color(255, 25, 25)));
    }
    @EventHandler
    public void removeBlock(PlayerBlockBreakEvent e) {
        e.setCancelled(true);
        e.getPlayer().sendMessage(Component.text("This instance does not have any delegate for breaking blocks", TextColor.color(255, 25, 25)));
    }

    public abstract String getName();

}
