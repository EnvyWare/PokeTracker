package com.envyful.poke.tracker.forge.tracker.data;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.UUID;

public class EntityDataTypeAdapter implements JsonSerializer<EntityData>, JsonDeserializer<EntityData> {

    @Override
    public EntityData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        String catcher = null;

        if (object.has("catcher")) {
            catcher = object.get("catcher").getAsString();
        }

        return EntityData.of(
                UUID.fromString(object.get("entityUUID").getAsString()),
                object.get("pokemonName").getAsString(),
                object.get("spawnTime").getAsLong(),
                object.get("caught").getAsBoolean(),
                catcher
        );
    }

    @Override
    public JsonElement serialize(EntityData src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        object.addProperty("entityUUID", src.getEntityUUID().toString());
        object.addProperty("pokemonName", src.getPokemonName());
        object.addProperty("spawnTime", src.getSpawnTime());
        object.addProperty("caught", src.isCaught());
        object.addProperty("catcher", src.getCatcher(true));

        return object;
    }
}
