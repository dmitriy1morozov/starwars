package com.starlet.starwars.network;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.widget.Toast;
import com.starlet.starwars.model.People;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeopleLoader extends Loader<People> {

		private final Call<People> mCall;
		@Nullable
		private People mPeople;

		public PeopleLoader(@NonNull Context context) {
				super(context);
				Swapi retrofit = Swapi.mRetrofit.create(Swapi.class);
				mCall = retrofit.getAllPeople();
		}

		@Override protected void onStartLoading() {
				super.onStartLoading();
				if(mPeople != null){
						deliverResult(mPeople);
				}else{
						forceLoad();
				}
		}

		@Override protected void onForceLoad() {
				super.onForceLoad();
				mCall.enqueue(new Callback<People>() {
						@Override public void onResponse(@NonNull Call<People> call, @NonNull
								Response<People> response) {
								assert response.body() != null;
								mPeople = response.body();
								deliverResult(mPeople);
						}

						@Override public void onFailure(@NonNull Call<People> call, @NonNull Throwable t) {
								String errorDetails = t.getMessage();
								Toast.makeText(getContext(), "Oops. Something went wrong with API call. " + errorDetails, Toast.LENGTH_SHORT).show();
								deliverResult(null);
						}
				});
		}

		@Override protected void onStopLoading() {
				super.onStopLoading();
				mCall.cancel();
		}
}
