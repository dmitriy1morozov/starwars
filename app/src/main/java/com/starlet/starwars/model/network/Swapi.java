package com.starlet.starwars.model.network;

import com.starlet.starwars.model.pojo.Characters;
import com.starlet.starwars.model.pojo.Person;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Swapi {
   
   String BASE_URL = "https://swapi.co/api/";
   
   @GET("people/{people_id}/") Call<Person> getCharacter(@Path("people_id") String peopleId);
   
   @GET("people/") Call<Characters> getAllCharacters();
   
   Retrofit mRetrofit = new Retrofit.Builder().baseUrl(BASE_URL)
       .addConverterFactory(GsonConverterFactory.create())
       .build();
}
