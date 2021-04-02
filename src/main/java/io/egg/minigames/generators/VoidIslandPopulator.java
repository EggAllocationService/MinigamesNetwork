package io.egg.minigames.generators;

import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.ChunkPopulator;
import net.minestom.server.instance.batch.ChunkBatch;
import net.minestom.server.instance.block.Block;

public class VoidIslandPopulator implements ChunkPopulator {
    @Override
    public void populateChunk(ChunkBatch batch, Chunk chunk) {
        if (chunk.getChunkX() == 0 && chunk.getChunkZ() ==0) {
            batch.setBlock(0, 64, 0, Block.BEDROCK);
        }
    }
}
