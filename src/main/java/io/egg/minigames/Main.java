package io.egg.minigames;

import io.egg.minigames.commands.*;
import io.egg.minigames.database.Database;
import io.egg.minigames.generators.VoidWorldGenerator;
import io.egg.minigames.instances.InstanceManager;
import io.egg.minigames.tasks.InstanceNameTask;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.LivingEntity;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.damage.DamageType;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.entity.EntityAttackEvent;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.network.player.PlayerConnection;
import net.minestom.server.ping.ResponseData;
import net.minestom.server.ping.ResponseDataConsumer;
import net.minestom.server.utils.Position;
import net.minestom.server.utils.time.TimeUnit;

import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        MinecraftServer m = MinecraftServer.init();
        //InstanceContainer mainInstance = MinecraftServer.getInstanceManager().createInstanceContainer();
       // mainInstance.setChunkGenerator(new SuperflatWorldGenerator(Block.STONE));
        MinecraftServer.setBrandName("Cabot");
        InstanceManager.init();
        Database.init();
        InstanceManager.get().createLobby();
        MinecraftServer.getCommandManager().register(new SaveCommand());
        MinecraftServer.getSchedulerManager().buildTask(new InstanceNameTask()).repeat(100, TimeUnit.MILLISECOND).schedule();
        MinecraftServer.getBiomeManager().addBiome(VoidWorldGenerator.LOBBY);
        MinecraftServer.getCommandManager().register(new StopCommand());
        MinecraftServer.getCommandManager().register(new SpawnBoatCommand());
        MinecraftServer.getCommandManager().register(new GamemodeCommand());
        MinecraftServer.getCommandManager().register(new DisguiseCommand());


        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addEventCallback(PlayerLoginEvent.class, event -> {
            final Player player = event.getPlayer();
            event.setSpawningInstance(InstanceManager.get().getInstance("lobby"));
            player.setGameMode(GameMode.CREATIVE);
            player.setAllowFlying(true);
            player.setRespawnPoint(new Position(0, 65, 0));
        });
        globalEventHandler.addEventCallback(EntityAttackEvent.class, event -> {
            if (event.getTarget() instanceof LivingEntity) {
                LivingEntity l = (LivingEntity) event.getTarget();
                l.damage(DamageType.fromEntity(event.getEntity()), 2);
            }
        });



        m.start("0.0.0.0", 25565, new ResponseDataConsumer() {
            @Override
            public void accept(PlayerConnection playerConnection, ResponseData responseData) {
                responseData.setOnline(69);
                responseData.setMaxPlayer(420);
                responseData.addPlayer("Notch", UUID.randomUUID());
                responseData.addPlayer("Sam's Ego", UUID.randomUUID());
                responseData.setDescription(Component.text("Testing Server Instance", TextColor.color(0xc13f6f)));
            }
        });
    }
}
