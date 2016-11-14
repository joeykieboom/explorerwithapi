package com.skiefboom.cocktailexplorerwithapi.data.api.deserializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;


public abstract class JsonDeserializer<T> implements com.google.gson.JsonDeserializer<T> {

    protected String getString(JsonObject object, String key) {

        JsonElement value = object.get(key);
        return value != null && !value.isJsonNull() ? value.getAsString() : null;
    }

    protected Integer getInt(JsonObject object, String key) {

        JsonElement value = object.get(key);
        return value != null && !value.isJsonNull() ? value.getAsInt() : null;
    }

    protected Long getLong(JsonObject object, String key) {

        JsonElement value = object.get(key);
        return value != null && !value.isJsonNull() ? value.getAsLong() : null;
    }

    protected Boolean getBool(JsonObject object, String key) {

        JsonElement value = object.get(key);
        return value != null && !value.isJsonNull() ? value.getAsBoolean() : null;
    }

    protected Float getFloat(JsonObject object, String key) {

        JsonElement value = object.get(key);

        try {
            return value != null && !value.isJsonNull() ? value.getAsFloat() : null;
        } catch(NumberFormatException ex) {
            return 0f;
        }
    }

    protected Double getDouble(JsonObject object, String key) {

        JsonElement value = object.get(key);
        return value != null && !value.isJsonNull() ? value.getAsDouble() : null;
    }

    protected <K> Type getListType(Class<K> type) {

        return new TypeToken<List<K>>() {}.getType();
    }
}
