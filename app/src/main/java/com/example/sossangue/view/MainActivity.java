package com.example.sossangue.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.sossangue.R;
import com.example.sossangue.controller.FuncoesGlobal;
import com.example.sossangue.controller.GPSTracker;

public class MainActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		GPSTracker gps = new GPSTracker(MainActivity.this);
		if(!gps.canGetLocation()) {
			FuncoesGlobal.toastApp("Ops! GPS desligado, para uma experiência melhor ative o GPS.", MainActivity.this);
		} else {
			gps.getDadosGeograficos();
		}

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class); //ABRE A TELA DE LOGIN APOS 2 SEGUNDOS
				startActivity(loginIntent);
				finish();
			}
		}, 3000);
	}
}
