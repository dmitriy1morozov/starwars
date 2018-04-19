package com.starlet.starwars.network;

import com.starlet.starwars.model.Person;

public interface OnCharacterRequest {
		void onCharacterReceived(Person person);
}
