package com.starlet.starwars.list;

import com.starlet.starwars.model.ContractModel;
import com.starlet.starwars.model.Model;
import com.starlet.starwars.model.pojo.Characters;
import com.starlet.starwars.model.pojo.Person;
import java.util.ArrayList;
import java.util.List;

public class ListPresenter implements ListContract.Presenter {

  private ListContract.View mListView;
  private ContractModel mModel;
  private ArrayList<Person> mCharacters;

  ListPresenter(ArrayList<Person> characters) {
    this.mModel = new Model();
    mCharacters = characters;
  }

  @Override public void attachView(ListContract.View view) {
    this.mListView = view;
  }

  @Override public void detachView() {
    this.mListView = null;
  }

  @Override public void viewIsReady() {
    mModel.loadAllCharacters(new ContractModel.LoadAllCharactersCallback() {
      @Override public void onAllCharactersLoaded(Characters characters) {
        List<Person> charactersList = characters.getResults();
        charactersList.removeAll(mCharacters);
        mCharacters.addAll(charactersList);
        mListView.refreshCharactersList();
      }

      @Override public void onError(Throwable throwable) {
        mListView.displayError(throwable.getMessage());
      }
    });
  }
}