package com.starlet.starwars.details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.starlet.starwars.R;
import com.starlet.starwars.model.pojo.Person;

public class DetailsFragment extends BottomSheetDialogFragment implements DetailsContract.View {

  @BindView(R.id.text_details_name) TextView mNameText;
  @BindView(R.id.text_details_height) TextView mHeightText;
  @BindView(R.id.text_details_mass) TextView mMassText;
  @BindView(R.id.text_details_gender) TextView mGenderText;
  @BindView(R.id.button_details_close) AppCompatImageButton mCloseButton;
  @BindView(R.id.text_details_starships) TextView mStarshipsText;
  @BindView(R.id.text_details_films) TextView mFilmsText;
  @BindView(R.id.text_details_vehicles) TextView mVehiclesText;
  @BindView(R.id.button_details_open_browser) Button mOpenBrowserButton;
  private Unbinder unbinder;

  private DetailsContract.Presenter mPresenter;

  public DetailsFragment() {
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle bundle = getArguments();
    if(getArguments() == null) {
      Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
      return;
    }
    String characterId = bundle.getString("id");

    mPresenter = new DetailsPresenter(characterId);
    mPresenter.attachView(this);
  }

  @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_details, container, false);
    unbinder = ButterKnife.bind(this, rootView);
    return rootView;
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mPresenter.attachView(this);
    mPresenter.viewIsReady();
  }

  @Override public void onStop() {
    super.onStop();
    mPresenter.detachView();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  //==============================================================================================
  @OnClick(R.id.button_details_close) public void onClose() {
    this.dismiss();
  }

  @OnClick(R.id.button_details_open_browser) public void onOpenBrowser() {
    mPresenter.openBrowserClicked();
  }

  //==============================================================================================
  @Override public void displayError(String error) {
    Toast.makeText(getActivity(), "Error! " + error, Toast.LENGTH_SHORT).show();
  }

  @Override public void displaySingleCharacter(Person character) {
    if(character != null) {
      displayPersonDetails(character);
    } else {
      Log.d("MyLogs", "displaySingleCharacter: Received null");
    }
  }

  @Override public void openBrowser(String stringUrl) {
    Intent openBrowserIntent = new Intent(Intent.ACTION_VIEW);
    Uri uri = Uri.parse(stringUrl);
    openBrowserIntent.setData(uri);
    startActivity(openBrowserIntent);
  }

  //==============================================================================================
  public void displayPersonDetails(Person person) {
    mNameText.setText(person.getName());
    mHeightText.setText(person.getHeight());
    mMassText.setText(person.getMass());
    mGenderText.setText(person.getGender());
    mStarshipsText.setText(String.valueOf(person.getStarships().size()));
    mFilmsText.setText(String.valueOf(person.getFilms().size()));
    mVehiclesText.setText(String.valueOf(person.getVehicles().size()));
  }
}