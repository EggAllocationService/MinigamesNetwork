package io.egg.minigames.generators;

import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.ChunkGenerator;
import net.minestom.server.instance.ChunkPopulator;
import net.minestom.server.instance.batch.ChunkBatch;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.BlockAlternative;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.utils.NamespaceID;
import net.minestom.server.world.biomes.Biome;
import net.minestom.server.world.biomes.BiomeEffects;
import net.minestom.server.world.biomes.BiomeParticles;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class SuperflatWorldGenerator implements ChunkGenerator {
    private static final BiomeEffects DEFAULT_EFFECTS = BiomeEffects.builder()
            .fogColor(0xff0000)
            .skyColor(0xff0000)
            .grassColor(0xff0000)
            .waterColor(0xff0000)
            .waterFogColor(0x0000ff)
            .foliageColor(0x0000ff)
            .biomeParticles(new BiomeParticles(0.22f, new BiomeParticles.ItemParticle(new ItemStack(Material.REDSTONE))))
            .build();

    public static final Biome CRIMSON = Biome.builder()
            .category(Biome.Category.NONE)
            .name(NamespaceID.from("kyle:crimson"))
            .temperature(0.8F)
            .downfall(0.4F)
            .depth(0.125F)
            .scale(0.05F)
            .effects(DEFAULT_EFFECTS)
            .build();
    Block bl;
    public SuperflatWorldGenerator(Block b) {
        bl = b;
    }

    @Override
    public void generateChunkData(@NotNull ChunkBatch batch, int chunkX, int chunkZ) {

        for (byte x = 0; x < Chunk.CHUNK_SIZE_X; x++)
            for (byte z = 0; z < Chunk.CHUNK_SIZE_Z; z++) {
                for (byte y = 0; y < 64; y++) {
                    batch.setBlock(x, y, z, bl);
                }
            }
    }

    @Override
    public void fillBiomes(@NotNull Biome[] biomes, int chunkX, int chunkZ) {
        Arrays.fill(biomes, CRIMSON);

    }

    @Override
    public @Nullable List<ChunkPopulator> getPopulators() {
        return null;
    }
}
