package com.starlet.starwars.ui.characters;

import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import com.starlet.starwars.R;
import com.starlet.starwars.model.People;
import com.starlet.starwars.model.Person;
import com.starlet.starwars.network.PeopleLoader;
import com.starlet.starwars.ui.DetailsFragment;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
    implements OnItemClickListener, LoaderManager.LoaderCallbacks<People> {
   
   private ArrayList<Person> mPeople;
   private Adapter mAdapter;
   
   @Override protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      Toolbar myToolbar = findViewById(R.id.toolbar_main);
      TextView toolbarTitle = myToolbar.findViewById(R.id.text_main_title);
      setSupportActionBar(myToolbar);
      Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/starwars.ttf");
      toolbarTitle.setTypeface(typeface);
      mPeople = new ArrayList<>();
   }
   
   @Override protected void onStart() {
      super.onStart();
      
      mAdapter = new Adapter(this, mPeople, this);
      RecyclerView recyclerView = findViewById(R.id.recycler_main_list);
      LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
      recyclerView.setLayoutManager(linearLayoutManager);
      recyclerView.setAdapter(mAdapter);
      
      DisplayMetrics metrics = getResources().getDisplayMetrics();
      final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, metrics);
      recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
         @Override public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
             RecyclerView.State state) {
            outRect.set(margin, margin, margin, margin);
         }
      });
      loadAllPeople(false);
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
   
   @NonNull @Override public Loader<People> onCreateLoader(int id, @Nullable Bundle args) {
      return new PeopleLoader(this);
   }
   
   @Override public void onLoadFinished(@NonNull Loader<People> loader, People people) {
      List<Person> persons = people.getResults();
      persons.removeAll(mPeople);
      mPeople.addAll(persons);
      mAdapter.notifyDataSetChanged();
   }
   
   @Override public void onLoaderReset(@NonNull Loader<People> loader) {
   }
   
   //==============================================================================================
   private void loadAllPeople(boolean restart) {
      if (restart) {
         getSupportLoaderManager().restartLoader(1, Bundle.EMPTY, this);
      } else {
         getSupportLoaderManager().initLoader(1, Bundle.EMPTY, this);
      }
   }
}