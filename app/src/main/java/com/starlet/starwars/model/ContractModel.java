package com.starlet.starwars.model;

import com.starlet.starwars.model.pojo.Characters;
import com.starlet.starwars.model.pojo.Person;

public interface ContractModel {
  interface LoadAllCharactersCallback{
    void onAllCharactersLoaded(Characters characters);
    void onError(Throwable throwable);
  }
  interface LoadSingleCharacterCallback{
    void onSingleCharactersLoaded(Person person);
    void onError(Throwable throwable);
  }

  void loadAllCharacters(LoadAllCharactersCallback callback);
  void loadSingleCharacter(String characterId, LoadSingleCharacterCallback callback);
}