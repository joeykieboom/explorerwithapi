package com.skiefboom.cocktailexplorerwithapi.data.api.responses;

import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

public class ListResponse<T extends BaseModel> {

    public void saveAll(List<T> items) {

        for(int i = 0; i < items.size(); i++) {
            items.get(i).save();
        }
    }
}
