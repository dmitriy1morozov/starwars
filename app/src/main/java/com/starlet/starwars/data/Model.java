package com.starlet.starwars.data;

import android.support.annotation.NonNull;
import com.starlet.starwars.data.network.Swapi;
import com.starlet.starwars.data.pojo.Characters;
import com.starlet.starwars.data.pojo.Person;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Model implements ContractModel {

  private Swapi mRetrofit;

  public Model() {
    mRetrofit = Swapi.mRetrofit.create(Swapi.class);
  }

  @Override public void loadAllCharacters(final LoadAllCharactersCallback presenterCallback) {
    Call<Characters> call = mRetrofit.getAllCharacters();
    call.enqueue(new Callback<Characters>() {
      @Override
      public void onResponse(@NonNull Call<Characters> call, @NonNull Response<Characters> response) {
        Characters characters = response.body();
        presenterCallback.onAllCharactersLoaded(characters);
      }

      @Override public void onFailure(@NonNull Call<Characters> call, @NonNull Throwable t) {
        presenterCallback.onError(t);
      }
    });
  }

  @Override
  public void loadSingleCharacter(String characterId,
      final LoadSingleCharacterCallback presenterCallback) {
    Call<Person> call = mRetrofit.getCharacter(characterId);
    call.enqueue(new Callback<Person>() {
      @Override
      public void onResponse(@NonNull Call<Person> call, @NonNull Response<Person> response) {
        Person person = response.body();
        presenterCallback.onSingleCharactersLoaded(person);
      }

      @Override public void onFailure(@NonNull Call<Person> call, @NonNull Throwable t) {
        presenterCallback.onError(t);
      }
    });
  }
}