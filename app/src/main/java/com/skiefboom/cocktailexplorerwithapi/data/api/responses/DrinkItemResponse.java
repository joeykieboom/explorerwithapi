package com.skiefboom.cocktailexplorerwithapi.data.api.responses;

import com.google.gson.annotations.Expose;
import com.skiefboom.cocktailexplorerwithapi.data.database.models.Cocktail;

public class DrinkItemResponse {

    @Expose
    public Cocktail cocktail;
}
