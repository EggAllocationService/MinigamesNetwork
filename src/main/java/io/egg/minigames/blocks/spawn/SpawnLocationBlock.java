package io.egg.minigames.blocks.spawn;

import io.egg.minigames.instances.InstanceManager;
import net.minestom.server.data.Data;
import net.minestom.server.data.SerializableDataImpl;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.CustomBlock;
import net.minestom.server.network.packet.server.play.ParticlePacket;
import net.minestom.server.particle.Particle;
import net.minestom.server.particle.ParticleCreator;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.utils.Position;
import net.minestom.server.utils.time.TimeUnit;
import net.minestom.server.utils.time.UpdateOption;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SpawnLocationBlock extends CustomBlock {
    public SpawnLocationBlock() {
        super(Block.STRUCTURE_VOID, "spawn_location");
    }

    @Override
    public void onPlace(@NotNull Instance instance, @NotNull BlockPosition blockPosition, @Nullable Data data) {

    }

    @Override
    public void update(@NotNull Instance instance, @NotNull BlockPosition blockPosition, @Nullable Data data) {
        if (data == null) return;
        createParticle(instance, blockPosition.toPosition().add(0.5, 0.5, 0.5), data);

        if (!SpawnLocationManager.takenPos.contains(InstanceManager.get().getProfile((InstanceContainer) instance).getProfileData().mapName.hashCode() * blockPosition.hashCode())) {
            SpawnLocationManager.posLoad(InstanceManager.get().getProfile((InstanceContainer) instance).getProfileData().mapName, blockPosition, data.get("tag"), data.get("primary"));
            System.out.println("update location block");
        }
    }

    @Override
    public @Nullable UpdateOption getUpdateOption() {
        return new UpdateOption(2, TimeUnit.TICK);
    }

    @Override
    public void onDestroy(@NotNull Instance instance, @NotNull BlockPosition blockPosition, @Nullable Data data) {

    }

    @Override
    public boolean onInteract(@NotNull Player player, Player.@NotNull Hand hand, @NotNull BlockPosition blockPosition, @Nullable Data data) {
        if (data == null) return true;
        if (player.isSneaking()) {
            if (!data.hasKey("primary")) data.set("primary", false);
            boolean b = !((boolean) data.get("primary"));
            data.set("primary", b);
            if (b) {
                SpawnLocationManager.posLoad(InstanceManager.get().getProfile((InstanceContainer) player.getInstance()).getProfileData().mapName, blockPosition, data.get("tag"), b);

            }

        }
        return true;
    }

    @Override
    public @Nullable Data createData(@NotNull Instance instance, @NotNull BlockPosition blockPosition, @Nullable Data data) {
        System.out.println("createData location block");
        Data d = data;
        if (d == null) {
            d = new SerializableDataImpl();
            d.set("primary", false);
            d.set("tag", "");
        }
        SpawnLocationManager.posLoad(InstanceManager.get().getProfile((InstanceContainer) instance).getProfileData().mapName, blockPosition, data.get("tag"), d.get("primary"));
        return d;
    }

    @Override
    public short getCustomBlockId() {
        return 7;
    }
    public static void createParticle(Instance i, Position p, Data d) {
        boolean b = d.hasKey("primary") ? d.get("primary") : false;
        ParticlePacket pp = ParticleCreator.createParticlePacket(Particle.DUST, false, p.getX(), p.getY(), p.getZ(), 0, 0, 0, 1, 0, binaryWriter -> {
            binaryWriter.writeFloat(0.2f);
            binaryWriter.writeFloat(1f);
            binaryWriter.writeFloat(b ? 1f : 0.2f);
            binaryWriter.writeFloat(0.5f);


        }) ;

        i.getChunkAt(p).sendPacketToViewers(pp);
    }
}
