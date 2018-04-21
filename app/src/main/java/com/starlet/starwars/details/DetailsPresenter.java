package com.starlet.starwars.details;

import com.starlet.starwars.data.ContractModel;
import com.starlet.starwars.data.Model;
import com.starlet.starwars.data.pojo.Person;

public class DetailsPresenter implements DetailsContract.Presenter {

  private DetailsContract.View mDetailsView;
  private ContractModel mModel;
  private String mCharacterId;
  private Person mPerson;

  DetailsPresenter(String characterId) {
    this.mModel = new Model();
    this.mCharacterId = characterId;
  }

  @Override public void attachView(DetailsContract.View view) {
    this.mDetailsView = view;
  }

  @Override public void detachView() {
    this.mDetailsView = null;
  }

  @Override public void viewIsReady() {
    mModel.loadSingleCharacter(mCharacterId, new ContractModel.LoadSingleCharacterCallback() {
      @Override public void onSingleCharactersLoaded(Person person) {
        mPerson = person;
        mDetailsView.displaySingleCharacter(person);
      }

      @Override public void onError(Throwable throwable) {
        mDetailsView.displayError(throwable.getMessage());
      }
    });
  }

  @Override public void openBrowserClicked() {
    if(mPerson == null) {
      mDetailsView.displayError("Character data not yet downloaded. Please wait");
    } else if(mPerson.getUrl() == null) {
      mDetailsView.displayError("No URL for that character!");
    } else {
      mDetailsView.openBrowser(mPerson.getUrl());
    }
  }
}