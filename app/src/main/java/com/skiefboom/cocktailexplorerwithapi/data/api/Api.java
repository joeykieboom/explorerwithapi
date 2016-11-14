package com.skiefboom.cocktailexplorerwithapi.data.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skiefboom.cocktailexplorerwithapi.data.api.deserializers.CocktailDeserializer;
import com.skiefboom.cocktailexplorerwithapi.data.api.resources.Drinks;
import com.skiefboom.cocktailexplorerwithapi.data.database.models.Cocktail;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    public static final String API_URL = "https://addb.absolutdrinks.com";

    public static Drinks drinks;

    public static void initialize() {

        Gson gson = createGson();
        OkHttpClient client = createClient();

        Retrofit retrofit = createRetrofit(gson, client);

        drinks = retrofit.create(Drinks.class);
    }

    private static Retrofit createRetrofit(Gson gson, OkHttpClient client) {

        return new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
    }

    private static OkHttpClient createClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        builder.addInterceptor(new Interceptor() {

            @Override
            public Response intercept(Chain chain) throws IOException {

                Request original = chain.request();

                HttpUrl url = original.url().newBuilder().addQueryParameter("apiKey", "93cbe9d151c8458ca7bc718da8358a30").build();

                Request.Builder requestBuilder = original.newBuilder()
                        .url(url)
                        .method(original.method(), original.body());

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        }).addInterceptor(logging);

        return builder.build();    }

    private static Gson createGson() {

        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(Cocktail.class, new CocktailDeserializer())
                .create();
    }
}
