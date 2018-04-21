package com.starlet.starwars.list;


public interface ListContract {
   interface Presenter {
    void attachView(View view);
    void detachView();
    void viewIsReady();
  }

  interface View {
    void displayError(String error);
    void refreshCharactersList();
  }
}
