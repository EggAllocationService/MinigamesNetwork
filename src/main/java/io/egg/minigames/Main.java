package io.egg.minigames;

import io.egg.minigames.generators.SuperflatWorldGenerator;
import io.egg.minigames.tasks.InstanceNameTask;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.NamespaceID;
import net.minestom.server.utils.Position;
import net.minestom.server.utils.time.TimeUnit;
import net.minestom.server.world.biomes.Biome;
import net.minestom.server.world.biomes.BiomeEffects;

public class Main {

    public static void main(String[] args) {
        MinecraftServer m = MinecraftServer.init();
        InstanceContainer mainInstance = MinecraftServer.getInstanceManager().createInstanceContainer();
        mainInstance.setChunkGenerator(new SuperflatWorldGenerator(Block.STONE));
        MinecraftServer.getSchedulerManager().buildTask(new InstanceNameTask()).repeat(100, TimeUnit.MILLISECOND);
        MinecraftServer.getBiomeManager().addBiome(SuperflatWorldGenerator.CRIMSON);
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addEventCallback(PlayerLoginEvent.class, event -> {
            final Player player = event.getPlayer();
            event.setSpawningInstance(mainInstance);
            player.setGameMode(GameMode.CREATIVE);
            player.setRespawnPoint(new Position(0, 65, 0));
        });



        m.start("0.0.0.0", 25565);
    }
}
