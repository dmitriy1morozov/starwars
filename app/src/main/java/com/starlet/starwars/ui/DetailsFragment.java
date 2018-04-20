package com.starlet.starwars.ui;

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
import com.starlet.starwars.model.Person;
import com.starlet.starwars.network.OnCharacterRequest;
import com.starlet.starwars.network.Swapi;
import java.lang.ref.WeakReference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsFragment extends BottomSheetDialogFragment implements OnCharacterRequest {
   
   @BindView(R.id.text_details_name) TextView mNameText;
   @BindView(R.id.text_details_height) TextView mHeightText;
   @BindView(R.id.text_details_mass) TextView mMassText;
   @BindView(R.id.text_details_gender) TextView mGenderText;
   @BindView(R.id.button_details_close) AppCompatImageButton mCloseButton;
   @BindView(R.id.text_details_starships) TextView mStarshipsText;
   @BindView(R.id.text_details_films) TextView mFilmsText;
   @BindView(R.id.text_details_vehicles) TextView mVehiclesText;
   @BindView(R.id.button_details_open_browser) Button mOpenBrowserButton;
   Unbinder unbinder;
   
   private int mId;
   private Person mPerson;
   private Swapi mRetrofit;
   
   public DetailsFragment() {
   }
   
   @Override public void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      if (mRetrofit == null) {
         mRetrofit = Swapi.mRetrofit.create(Swapi.class);
      }
   }
   
   @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
       Bundle savedInstanceState) {
      Bundle bundle = getArguments();
      if (bundle == null) {
         bundle = savedInstanceState;
      }
      if (bundle == null) {
         Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
      } else {
         mId = bundle.getInt("id");
      }
      View rootView = inflater.inflate(R.layout.fragment_details, container, false);
      unbinder = ButterKnife.bind(this, rootView);
      return rootView;
   }
   
   @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      getCharacter(mId, this);
   }
   
   @Override public void onSaveInstanceState(Bundle outState) {
      outState.putInt("id", mId);
      super.onSaveInstanceState(outState);
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
      if (mPerson == null) {
         Toast.makeText(getActivity(), "Character data not yet downloaded. Please wait",
             Toast.LENGTH_SHORT).show();
         return;
      }
      Intent openBrowserIntent = new Intent(Intent.ACTION_VIEW);
      Uri url = Uri.parse(mPerson.getUrl());
      openBrowserIntent.setData(url);
      startActivity(openBrowserIntent);
   }
   
   //==============================================================================================
   @Override public void onCharacterReceived(Person person) {
      if (person != null) {
         mPerson = person;
         displayPersonDetails();
      } else {
         Log.d("MyLogs", "onCharacterReceived: Received null");
      }
   }
   
   //==============================================================================================
   public void displayPersonDetails() {
      mNameText.setText(mPerson.getName());
      mHeightText.setText(mPerson.getHeight());
      mMassText.setText(mPerson.getMass());
      mGenderText.setText(mPerson.getGender());
      mStarshipsText.setText(String.valueOf(mPerson.getStarships().size()));
      mFilmsText.setText(String.valueOf(mPerson.getFilms().size()));
      mVehiclesText.setText(String.valueOf(mPerson.getVehicles().size()));
   }
   
   //Left without Loader to show another approach with weak reference
   private void getCharacter(int id, OnCharacterRequest onCharacterRequest) {
      final WeakReference<OnCharacterRequest> callback = new WeakReference<>(onCharacterRequest);
      
      Call<Person> call = mRetrofit.getCharacter(String.valueOf(id));
      call.enqueue(new Callback<Person>() {
         @Override
         public void onResponse(@NonNull Call<Person> call, @NonNull Response<Person> response) {
            assert response.body() != null;
            Person person = response.body();
            if (callback.get() != null) {
               callback.get().onCharacterReceived(person);
            }
         }
         
         @Override public void onFailure(@NonNull Call<Person> call, @NonNull Throwable t) {
            String errorDetails = t.getMessage();
            Toast.makeText(getActivity(),
                "Oops. Something went wrong with API call. " + errorDetails, Toast.LENGTH_SHORT)
                .show();
         }
      });
   }
}