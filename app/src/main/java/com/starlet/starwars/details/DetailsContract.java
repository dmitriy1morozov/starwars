package com.starlet.starwars.details;

import com.starlet.starwars.data.pojo.Person;

public interface DetailsContract {
  interface Presenter {
    void attachView(View view);
    void detachView();
    void viewIsReady();
    void openBrowserClicked();
  }

  interface View {
    void displayError(String error);
    void displaySingleCharacter(Person character);
    void openBrowser(String url);
  }
}
