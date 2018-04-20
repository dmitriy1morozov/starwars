package com.starlet.starwars.network;

import com.starlet.starwars.model.People;
import com.starlet.starwars.model.Person;
import java.util.List;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Swapi {

		String BASE_URL = "https://swapi.co/api/";

		@GET("people/{people_id}/") Call<Person> getCharacter(@Path("people_id") String peopleId);

		@GET("people/") Call<People> getAllPeople();

		Retrofit mRetrofit = new Retrofit.Builder().baseUrl(BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();
}
