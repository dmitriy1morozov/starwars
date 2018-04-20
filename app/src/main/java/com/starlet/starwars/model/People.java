package com.starlet.starwars.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator") public class People {

		@SerializedName("next") private String next;

		@SerializedName("previous") private Object previous;

		@SerializedName("count") private int count;

		@SerializedName("results") private List<Person> results;

		public void setNext(String next) {
				this.next = next;
		}

		public String getNext() {
				return next;
		}

		public void setPrevious(Object previous) {
				this.previous = previous;
		}

		public Object getPrevious() {
				return previous;
		}

		public void setCount(int count) {
				this.count = count;
		}

		public int getCount() {
				return count;
		}

		public void setResults(List<Person> results) {
				this.results = results;
		}

		public List<Person> getResults() {
				return results;
		}

		@Override public String toString() {
				return "People{"
						+ "next = '"
						+ next
						+ '\''
						+ ",previous = '"
						+ previous
						+ '\''
						+ ",count = '"
						+ count
						+ '\''
						+ ",results = '"
						+ results
						+ '\''
						+ "}";
		}
}