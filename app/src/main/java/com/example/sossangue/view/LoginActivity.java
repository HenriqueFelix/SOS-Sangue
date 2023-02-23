package com.example.sossangue.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sossangue.R;
import com.example.sossangue.controller.GPSTracker;
import com.example.sossangue.controller.UsuarioController;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		final TextInputEditText edtLogin = findViewById(R.id.edtLogin);
		final TextInputEditText edtSenha = findViewById(R.id.edtSenha);

		final LinearLayout bubbleProgress = findViewById(R.id.bubbleProgress);

		final UsuarioController usuarioController = new UsuarioController(LoginActivity.this);

		final Button btnLogin = findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String login = edtLogin.getText().toString().trim();
				String senha = edtSenha.getText().toString();

				if (login.equals("")) {
					edtLogin.setError("Preencha o e-mail corretamente!");
					return;
				} else if (senha.trim().equals("")) {
					edtSenha.setError("Preencha a senha!");
					return;
				}

				try {
					GPSTracker gps = new GPSTracker(LoginActivity.this);
					if(gps.canGetLocation()) {
						gps.getDadosGeograficos();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				btnLogin.setEnabled(false);

				usuarioController.loginApp(login, senha, bubbleProgress, btnLogin);
			}
		});

		TextView lblCadastro = findViewById(R.id.lblCadastro);
		lblCadastro.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent loginIntent = new Intent(LoginActivity.this, CadastroActivity.class); //ABRE A TELA DE CADASTRO
				startActivity(loginIntent);
			}
		});
	}
}
