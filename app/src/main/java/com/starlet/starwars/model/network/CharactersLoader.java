package com.starlet.starwars.model.network;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.widget.Toast;
import com.starlet.starwars.model.pojo.Characters;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//TODO not used yet
public class CharactersLoader extends Loader<Characters> {

  private final Call<Characters> mCall;
  @Nullable private Characters mCharacters;

  public CharactersLoader(@NonNull Context context) {
    super(context);
    Swapi retrofit = Swapi.mRetrofit.create(Swapi.class);
    mCall = retrofit.getAllCharacters();
  }

  @Override protected void onStartLoading() {
    super.onStartLoading();
    if(mCharacters != null) {
      deliverResult(mCharacters);
    } else {
      forceLoad();
    }
  }

  @Override protected void onForceLoad() {
    super.onForceLoad();
    mCall.enqueue(new Callback<Characters>() {
      @Override
      public void onResponse(@NonNull Call<Characters> call, @NonNull Response<Characters> response) {
        assert response.body() != null;
        mCharacters = response.body();
        deliverResult(mCharacters);
      }

      @Override public void onFailure(@NonNull Call<Characters> call, @NonNull Throwable t) {
        String errorDetails = t.getMessage();
        Toast.makeText(getContext(),
            "Oops. Something went wrong with API call. " + errorDetails, Toast.LENGTH_SHORT)
            .show();
        deliverResult(null);
      }
    });
  }

  @Override protected void onStopLoading() {
    super.onStopLoading();
    mCall.cancel();
  }
}