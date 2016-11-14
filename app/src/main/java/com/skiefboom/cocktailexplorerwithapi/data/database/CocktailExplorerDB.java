package com.skiefboom.cocktailexplorerwithapi.data.database;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = CocktailExplorerDB.NAME, version = CocktailExplorerDB.VERSION)
public class CocktailExplorerDB {

    public static final String NAME = "CocktailExplorerDB";

    public static final int VERSION = 1;
}
