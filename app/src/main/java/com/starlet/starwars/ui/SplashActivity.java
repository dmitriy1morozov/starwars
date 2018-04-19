package com.starlet.starwars.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.starlet.starwars.R;
import com.starlet.starwars.ui.characters.MainActivity;

public class SplashActivity extends AppCompatActivity {

		private static final String LOGO_URL = "https://cdn.iconscout.com/public/images/icon/free/png-512/star-wars-logo-tv-show-series-31ebf3d1fc35cf6a-512x512.png";

		@Override protected void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_splash);

				ImageView logoImageView = findViewById(R.id.logo_splash);
				Glide.with(this).load(LOGO_URL).into(logoImageView);

				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {
						@Override public void run() {
								Intent mainScreen = new Intent(SplashActivity.this, MainActivity.class);
								mainScreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
								startActivity(mainScreen);
						}
				}, 2000);
		}

}
