package io.egg.minigames.loading;

import org.bson.codecs.pojo.annotations.BsonId;

import java.util.zip.Inflater;

public class WorldChunk {
    @BsonId
    public String id;
    public String world;
    public int x;
    public int z;
    public byte[] data;
    public int rawSize;
}
