package io.egg.minigames.loading;

import io.egg.minigames.instances.InstanceManager;
import io.egg.minigames.instances.ProfiledInstance;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.IChunkLoader;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.utils.binary.BinaryReader;
import net.minestom.server.utils.chunk.ChunkCallback;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class DatabaseWorldLoader implements IChunkLoader {
    InstanceContainer i;
    public DatabaseWorldLoader(InstanceContainer b) {
        i = b;
    }

    @Override
    public boolean loadChunk(@NotNull Instance instance, int chunkX, int chunkZ, @Nullable ChunkCallback callback) {
        ProfiledInstance iData;
        try {
             iData = InstanceManager.get().getProfile(i);
        } catch(Exception e) {

            return false;
        }

        String world = iData.getProfileData().mapName;
        if (world == null) {
            // This is not managed by the InstanceManager, so we can't load chunks
            return false;
        }
        World w = WorldManager.getWorld(world);
        WorldChunk c = w.getChunkAt(chunkX, chunkZ);
        if (c == null) {
            // chunk has never been loaded
            return false;
        } else {
            //chunk has been loaded, lets have fun!
            BinaryReader b = new BinaryReader(c.data);
            Chunk chunk = (i).getChunkSupplier().createChunk(null, chunkX, chunkZ);
            chunk.readChunk(b, callback);
            return true; // chunk has been loaded from db and is being read
        }

    }

    @Override
    public void saveChunk(@NotNull Chunk chunk, @Nullable Runnable callback) {
        ProfiledInstance iData = InstanceManager.get().getProfile(i);

        String world = iData.getProfileData().mapName;
        if (!iData.getProfileData().saveMap) return;
        if (world == null) {
            // This is not managed by the InstanceManager, so we can't load chunks

            return;
        }
        World w = WorldManager.getWorld(world);
        if (w == null) {
            return; // shits really fucked like REALLY FUCKED
        }
        byte[] data = chunk.getSerializedData();
        w.saveChunk(chunk.getChunkX(), chunk.getChunkZ(), data);
        if (callback != null) callback.run();
    }


    @Override
    public boolean supportsParallelSaving() {
        return true;
    }

    @Override
    public boolean supportsParallelLoading() {
        return true;
    }
    private static String getChunkKey(int chunkX, int chunkZ) {
        return chunkX + "~" + chunkZ;
    }
}
