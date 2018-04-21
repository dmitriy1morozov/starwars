package com.starlet.starwars.list;

import android.graphics.Rect;
import android.graphics.Typeface;
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
import com.starlet.starwars.data.pojo.Person;
import com.starlet.starwars.details.DetailsFragment;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements Holder.OnItemClickListener, ListContract.View {

  private Adapter mAdapter;
  private ListContract.Presenter mPresenter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar myToolbar = findViewById(R.id.toolbar_main);
    TextView toolbarTitle = myToolbar.findViewById(R.id.text_main_title);
    setSupportActionBar(myToolbar);
    Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/starwars.ttf");
    toolbarTitle.setTypeface(typeface);

    ArrayList<Person> characters = new ArrayList<>();
    mAdapter = new Adapter(this, characters, this);
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

    mPresenter = new ListPresenter(characters);
  }

  @Override protected void onStart() {
    super.onStart();
    mPresenter.attachView(this);
    mPresenter.viewIsReady();
  }

  @Override protected void onStop() {
    super.onStop();
    mPresenter.detachView();
  }

  //================================================================================================
  @Override public void onItemClick(Holder item) {
    String id = String.valueOf(item.getId());

    Bundle bundle = new Bundle();
    bundle.putString("id", id);
    DetailsFragment details = new DetailsFragment();
    details.setArguments(bundle);
    details.show(getSupportFragmentManager(), String.valueOf(id));
  }

  //================================================================================================
  @Override public void displayError(String error) {
    Toast.makeText(this, "Error! " + error, Toast.LENGTH_SHORT).show();
  }

  @Override public void refreshCharactersList() {
    mAdapter.notifyDataSetChanged();
  }
}