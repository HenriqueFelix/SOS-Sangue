package com.example.sossangue.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.example.sossangue.R;

public class PermissionsActivity extends AppCompatActivity
{
	private String[] permissoes = new String[]{
			Manifest.permission.INTERNET,
			Manifest.permission.ACCESS_NETWORK_STATE,
			Manifest.permission.READ_PHONE_STATE,
			Manifest.permission.READ_EXTERNAL_STORAGE,
			Manifest.permission.WRITE_EXTERNAL_STORAGE,
			Manifest.permission.ACCESS_FINE_LOCATION,
			Manifest.permission.ACCESS_COARSE_LOCATION
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_permissions);

		int MyVersion = Build.VERSION.SDK_INT; //VERSAO DO ANDROID

		//VERIFICA SE A VERSÃO DO ANDROID É COMPATIVEL COM O APP
		if (MyVersion >= Build.VERSION_CODES.LOLLIPOP) {

			//VERIFICA SE TEM AS PERMISSÕES NECESSARIAS PARA O APP
			if (!checkIfAlreadyhavePermission()) {
				requestForSpecificPermission();
			} else {
				//SE TIVER TODAS AS PERMISSOES ABRE A TELA DE SPLASH
				Intent intent = new Intent();
				intent.setClass(PermissionsActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		}
		else {
			//FuncoesGlobal.showToast("Ops! O aplicativo não esta disponivel para essa versão do android.", Toast.LENGTH_SHORT);
		}
	}

	private boolean checkIfAlreadyhavePermission() {
		int qtdNaoPermissoes = 0;
		for(int x = 0; x < (permissoes.length); x++) {
			int result = ContextCompat.checkSelfPermission(this, permissoes[x]);
			if (result != PackageManager.PERMISSION_GRANTED) {
				qtdNaoPermissoes++;
			}
		}

		if(qtdNaoPermissoes > 0) {
			return false;
		} else {
			return true;
		}
	}

	private void requestForSpecificPermission() {
		ActivityCompat.requestPermissions(this, permissoes, 101);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		switch (requestCode) {
			case 101:
				int qtdPermitidos = 0;
				for(int x = 0; x < grantResults.length; x++) {
					if(grantResults[x] == PackageManager.PERMISSION_GRANTED)
					{
						qtdPermitidos++;
					}
				}

				if (grantResults.length == qtdPermitidos) {
					Intent intent = new Intent();
					intent.setClass(PermissionsActivity.this, MainActivity.class);
					startActivity(intent);
					finish();
				} else {
					if (!checkIfAlreadyhavePermission()) {
						requestForSpecificPermission();
					}
				}
				break;
			default:
				super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		}
	}
}
