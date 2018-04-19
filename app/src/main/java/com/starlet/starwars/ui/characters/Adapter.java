package com.starlet.starwars.ui.characters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.starlet.starwars.R;
import com.starlet.starwars.model.Person;
import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

		private final Context mContext;
		private final ArrayList<Person> mCharacters;
		private final OnItemClickListener mOnItemClickListener;

		public Adapter(Context context, ArrayList<Person> characters,
				OnItemClickListener onItemClickListener) {
				mContext = context;
				mCharacters = characters;
				mOnItemClickListener = onItemClickListener;
		}

		@NonNull @Override
		public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
				LayoutInflater inflater = LayoutInflater.from(mContext);
				View itemView = inflater.inflate(R.layout.item_viewholder,  parent, false);
				return new Holder(itemView);
		}

		@Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
				((Holder)holder).bind(mCharacters.get(position), mOnItemClickListener);
		}

		@Override public int getItemCount() {
				return mCharacters.size();
		}
}
