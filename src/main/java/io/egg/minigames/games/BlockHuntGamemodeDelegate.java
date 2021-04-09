package io.egg.minigames.games;

import io.egg.minigames.profiles.EventHandler;
import io.egg.minigames.profiles.PlayerJoinProfileEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class BlockHuntGamemodeDelegate extends BasicMinigameDelegate {
    public BlockHuntGamemodeDelegate() {
        map = "bh-caverns";
        defaultBar.name(Component.text("Playing Block Hunt on " + map, TextColor.color(0x00ffff)));
    }

    @Override
    @EventHandler
    public void join(PlayerJoinProfileEvent e) {
        super.join(e);
        if (e.isCancelled()) return;


    }

    @Override
    public String getName() {
        return "Block Hunt";
    }
}
