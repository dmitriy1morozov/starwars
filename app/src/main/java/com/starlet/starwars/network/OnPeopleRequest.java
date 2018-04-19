package com.starlet.starwars.network;

import com.starlet.starwars.model.Person;
import java.util.List;

public interface OnPeopleRequest {
		void onPeopleReceived(List<Person> people);
}
