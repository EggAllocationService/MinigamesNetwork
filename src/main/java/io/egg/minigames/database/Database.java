package io.egg.minigames.database;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.egg.minigames.loading.World;
import io.egg.minigames.loading.WorldChunk;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class Database {
    static Database instance;

    public static Database getInstance() {
        return instance;
    }
    public static void init() {
        instance = new Database();
    }

    MongoClient client;
    MongoDatabase db;
    public MongoCollection<World> worlds;
    public MongoCollection<WorldChunk> worldChunks;

    public Database() {
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        client = MongoClients.create("mongodb://192.168.3.10:25563/?retryWrites=true&w=majority");
        db = client.getDatabase("minigames").withCodecRegistry(pojoCodecRegistry);
        worlds = db.getCollection("worlds", World.class);
        worldChunks = db.getCollection("chunks", WorldChunk.class);
    }

}
