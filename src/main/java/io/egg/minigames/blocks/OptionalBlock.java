package io.egg.minigames.blocks;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.minestom.server.data.Data;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.CustomBlock;
import net.minestom.server.network.packet.server.play.ParticlePacket;
import net.minestom.server.particle.Particle;
import net.minestom.server.particle.ParticleCreator;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.utils.Position;
import net.minestom.server.utils.binary.BinaryWriter;
import net.minestom.server.utils.time.TimeUnit;
import net.minestom.server.utils.time.UpdateOption;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public class OptionalBlock extends CustomBlock {

    static float r = 0.1f;
    static float g = 0.3f;
    static float b = 0.5f;

    public OptionalBlock() {
        super(Block.STRUCTURE_BLOCK, "optional_block");
    }

    @Override
    public void update(@NotNull Instance instance, @NotNull BlockPosition blockPosition, @Nullable Data data) {


        for (double x = 0; x <= 1; x+= 0.5) {
            for (double y = 0; y <= 1; y+=0.5) {
                for (double z = 0; z <= 1; z+=0.5) {
                    createParticle(instance, blockPosition.toPosition().add(x, y, z));
                }
            }
        }

    }

    @Override
    public @Nullable UpdateOption getUpdateOption() {
        return new UpdateOption(3, TimeUnit.TICK);
    }

    @Override
    public void onPlace(@NotNull Instance instance, @NotNull BlockPosition blockPosition, @Nullable Data data) {

    }

    @Override
    public void onDestroy(@NotNull Instance instance, @NotNull BlockPosition blockPosition, @Nullable Data data) {

    }

    @Override
    public boolean onInteract(@NotNull Player player, Player.@NotNull Hand hand, @NotNull BlockPosition blockPosition, @Nullable Data data) {
        assert data != null;
        if (data.get("block") == null) {
            return true;
        }
        if (!player.getItemInMainHand().getMaterial().isBlock()) return true;
        short material = Block.valueOf(player.getItemInMainHand().getMaterial().toString()).getBlockId();

        data.set("block", material);
        player.sendMessage(Component.text(material));
        player.getInstance().refreshBlockStateId(blockPosition, material);
        player.getInstance().playSound(Sound.sound(Key.key("minecraft", "block.note_block.iron_xylophone"), Sound.Source.MASTER, 1, 2));
        return true;
    }

    @Override
    public short getCustomBlockId() {
        return 4;
    }



    public static void createParticle(Instance i, Position p) {
        ParticlePacket pp = ParticleCreator.createParticlePacket(Particle.DUST, false, p.getX(), p.getY(), p.getZ(), 0, 0, 0, 1, 0, binaryWriter -> {
            binaryWriter.writeFloat(r);
            binaryWriter.writeFloat(g);
            binaryWriter.writeFloat(b);
            binaryWriter.writeFloat(0.5f);


        }) ;

        i.getChunkAt(p).sendPacketToViewers(pp);
    }
    public static void tick() {

        r = ThreadLocalRandom.current().nextFloat();
        g = ThreadLocalRandom.current().nextFloat();
        b =ThreadLocalRandom.current().nextFloat();
    }
}
