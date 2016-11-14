package com.skiefboom.cocktailexplorerwithapi.data.api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.skiefboom.cocktailexplorerwithapi.data.api.exceptions.UnexpectedStatusException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ApiCallback<T> implements Callback<T> {

    public abstract void onOK(Call<T> call, Response<T> response);
    public abstract void onError(Call<T> call, Throwable t);

    public final void onResponse(Call<T> call, Response<T> response) {

        if(response.code() == 200) {
            onOK(call, response);
        }
        else {

            String responseBody = null;
            JsonObject responseObject = null;

            try {

                responseBody = response.errorBody().string();
                responseObject = new JsonParser().parse(responseBody).getAsJsonObject();
            }
            catch (Exception e) {
            }

            Integer errorCode = null;

            if(responseObject != null && responseObject.has("error") && responseObject.getAsJsonObject("error").has("code")) {
                errorCode = responseObject.getAsJsonObject("error").get("code").getAsInt();
            }

            onError(call, new UnexpectedStatusException("Unexpected status: " + response.code(), response.code(), responseBody, errorCode));
        }
    }

    public final void onFailure(Call<T> call, Throwable t) {

        onError(call, t);
    }
}
