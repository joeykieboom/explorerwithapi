package com.skiefboom.cocktailexplorerwithapi.data.api.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.skiefboom.cocktailexplorerwithapi.data.database.models.Cocktail;

import java.lang.reflect.Type;

public class CocktailDeserializer extends JsonDeserializer<Cocktail> {

    @Override
    public Cocktail deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject responseObject = json.getAsJsonObject();

        Cocktail cocktail = new Cocktail();
        cocktail.name = getString(responseObject, "name");
        cocktail.descriptionPlain = getString(responseObject, "descriptionPlain");
        cocktail.save();

        return cocktail;
    }
}
