package com.starlet.starwars.ui.characters;

import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.starlet.starwars.R;
import com.starlet.starwars.model.People;
import com.starlet.starwars.model.Person;
import com.starlet.starwars.network.OnPeopleRequest;
import com.starlet.starwars.network.Swapi;
import com.starlet.starwars.ui.DetailsFragment;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnItemClickListener,
		OnPeopleRequest {

		private ArrayList<Person> mPeople;
		private RecyclerView mRecyclerView;
		private Adapter mAdapter;

		Swapi mRetrofit;

		@Override protected void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_main);
				Toolbar myToolbar = findViewById(R.id.toolbar_main);
				TextView toolbarTitle = myToolbar.findViewById(R.id.text_main_title);
				setSupportActionBar(myToolbar);
				Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/starwars.ttf");
				toolbarTitle.setTypeface(typeface);
				mPeople = new ArrayList<>();
				if(mRetrofit == null){
						mRetrofit = Swapi.mRetrofit.create(Swapi.class);
				}
		}

		@Override protected void onStart() {
				super.onStart();

				mAdapter = new Adapter(this, mPeople, this);
				mRecyclerView = findViewById(R.id.recycler_main_list);
				LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
				mRecyclerView.setLayoutManager(linearLayoutManager);
				mRecyclerView.setAdapter(mAdapter);

				DisplayMetrics metrics = getResources().getDisplayMetrics();
				final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, metrics);
				mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
						@Override public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
								RecyclerView.State state) {
								outRect.set(margin ,margin ,margin, margin);
						}
				});

				getAllPeople(this);
		}

		//==============================================================================================
		@Override public void onItemClick(Holder item) {
				int id = item.getId();
				Bundle bundle = new Bundle();
				bundle.putInt("id", id);
				DetailsFragment details = new DetailsFragment();
				details.setArguments(bundle);
				details.show(getSupportFragmentManager(), String.valueOf(id));
		}

		@Override public void onPeopleReceived(List<Person> people) {
				people.removeAll(mPeople);
				mPeople.addAll(people);
				mAdapter.notifyDataSetChanged();
		}
		//==============================================================================================
		private void getAllPeople(OnPeopleRequest OnPeopleRequest){
				final WeakReference<OnPeopleRequest> callback = new WeakReference<>(OnPeopleRequest);

				Call<People> call = mRetrofit.getAllPeople();
				call.enqueue(new Callback<People>() {
						@Override public void onResponse(@NonNull Call<People> call, @NonNull Response<People> response) {
								assert response.body() != null;
								ArrayList<Person> people = (ArrayList<Person>) response.body().getResults();
								if(callback.get() != null){
										callback.get().onPeopleReceived(people);
								}
						}

						@Override public void onFailure(@NonNull Call<People> call, @NonNull Throwable t) {
								String errorDetails = t.getMessage();
								Toast.makeText(MainActivity.this, "Oops. Something went wrong with API call. " + errorDetails, Toast.LENGTH_SHORT).show();
						}
				});
		}
}
