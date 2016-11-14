package com.skiefboom.cocktailexplorerwithapi.data.api.responses;

import com.google.gson.annotations.Expose;
import com.skiefboom.cocktailexplorerwithapi.data.database.models.Cocktail;

import java.util.List;

public class DrinkListResponse extends ListResponse<Cocktail> {

    @Expose
    public List<Cocktail> result;

    public int totalResult;

    public void saveAll() {

        saveAll(result);
    }
}
