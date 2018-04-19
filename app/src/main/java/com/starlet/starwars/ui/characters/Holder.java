package com.starlet.starwars.ui.characters;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.starlet.starwars.R;
import com.starlet.starwars.model.Person;
import java.util.ArrayList;
import java.util.Arrays;

public class Holder extends RecyclerView.ViewHolder {

		private int mId;
		private TextView mNameText;
		private TextView mBirthText;
		private ImageView mIconImage;

		private static ArrayList<String> sGenders;

		static
		{
				String[] genders = {"n/a", "male", "female"};
				sGenders = new ArrayList<>(Arrays.asList(genders));
		}

		public Holder(View itemView) {
				super(itemView);
				itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
				mNameText = itemView.findViewById(R.id.text_main_name);
				mBirthText = itemView.findViewById(R.id.text_main_birth_year);
				mIconImage = itemView.findViewById(R.id.image_main_icon);
		}

		public int getId() {
				return mId;
		}

		public void setId(int id) {
				mId = id;
		}

		public String getName() {
				return mNameText.getText().toString();
		}

		public void setName(String name) {
				mNameText.setText(name);
		}

		public String getBirth() {
				return mBirthText.getText().toString();
		}

		public void setBirth(String birth) {
				mBirthText.setText(birth);
		}

		public String getGender() {
				int level = mIconImage.getDrawable().getLevel();
				return sGenders.get(level);
		}

		public void setGender(String gender) {
				int level = sGenders.indexOf(gender);
				if(level == -1){
						level = 0;
				}
				mIconImage.setImageLevel(level);
		}

		public void bind(Person character, final OnItemClickListener onItemClickListener){
				String stringId = Uri.parse(character.getUrl()).getLastPathSegment();
				setId(Integer.parseInt(stringId));
				setName(character.getName());
				setBirth(character.getBirthYear());
				setGender(character.getGender());

				itemView.setOnClickListener(new View.OnClickListener() {
						@Override public void onClick(View v) {
								onItemClickListener.onItemClick(Holder.this);
						}
				});
		}
}
