package io.egg.minigames.profiles;

import net.minestom.server.entity.Player;
import net.minestom.server.event.Event;
import net.minestom.server.utils.Position;

public class PlayerLeaveProfileEvent extends Event {
    private Player p;
    public PlayerLeaveProfileEvent(Player e) {
        p = e;
    }

    public Player getPlayer() {
        return p;
    }


}
