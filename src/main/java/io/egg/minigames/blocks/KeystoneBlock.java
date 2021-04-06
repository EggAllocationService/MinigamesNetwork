package io.egg.minigames.blocks;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.minestom.server.data.Data;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.CustomBlock;
import net.minestom.server.particle.Particle;
import net.minestom.server.particle.ParticleCreator;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.utils.time.UpdateOption;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class KeystoneBlock extends CustomBlock {
    public KeystoneBlock() {
        super(Block.COMMAND_BLOCK, "keystone");
    }

    @Override
    public void onPlace(@NotNull Instance instance, @NotNull BlockPosition blockPosition, @Nullable Data data) {
        instance.playSound(Sound.sound(Key.key("minecraft", "entity.ender_dragon.ambient"), Sound.Source.MASTER, 1, 1),
                blockPosition.getX(),
                blockPosition.getY(),
                blockPosition.getZ()
                );

    }

    @Override
    public void onDestroy(@NotNull Instance instance, @NotNull BlockPosition blockPosition, @Nullable Data data) {
        instance.playSound(Sound.sound(Key.key("minecraft", "entity.ender_dragon.ambient"), Sound.Source.MASTER, 1, 1),
                blockPosition.getX(),
                blockPosition.getY(),
                blockPosition.getZ()
        );

    }


    @Override
    public boolean onInteract(@NotNull Player player, Player.@NotNull Hand hand, @NotNull BlockPosition blockPosition, @Nullable Data data) {
        player.sendMessage(Component.text("lmao this is a keystone nerd"));
        int i = data.getOrDefault("interactions", 1);
        player.sendMessage("People have interacted with this " + i + " times");
        data.set("interactions", i + 1);
        player.getInstance().refreshBlockStateId(blockPosition, (short) (i + 100));
        return false;
    }

    @Override
    public float getDrag(@NotNull Instance instance, @NotNull BlockPosition blockPosition) {
        return 0;
    }

    @Override
    public short getCustomBlockId() {
        return 3;
    }
}
