package com.example.sossangue.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.sossangue.R;
import com.example.sossangue.controller.Constantes;
import com.example.sossangue.controller.FuncoesGlobal;
import com.example.sossangue.controller.MaskEditUtil;
import com.example.sossangue.controller.UsuarioController;
import com.google.android.material.textfield.TextInputEditText;


public class CadastroActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastro);

		final Toolbar myToolbar = findViewById(R.id.myToolbar);
		myToolbar.setTitle("Cadastre-se");
		myToolbar.setTitleTextColor(Color.WHITE);
		setSupportActionBar(myToolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		final UsuarioController usuarioController = new UsuarioController(CadastroActivity.this);

		final LinearLayout bubbleProgress = findViewById(R.id.bubbleProgress);

		final TextInputEditText edtNome = findViewById(R.id.edtNome);

		final TextInputEditText edtCPF = findViewById(R.id.edtCPF);
		edtCPF.addTextChangedListener(MaskEditUtil.mask(edtCPF, MaskEditUtil.FORMAT_CPF));

		final TextInputEditText edtDataNasc = findViewById(R.id.edtDataNasc);
		edtDataNasc.addTextChangedListener(MaskEditUtil.mask(edtDataNasc, MaskEditUtil.FORMAT_DATE));

		final TextInputEditText edtTelefone = findViewById(R.id.edtTelefone);
		edtTelefone.addTextChangedListener(MaskEditUtil.mask(edtTelefone, MaskEditUtil.FORMAT_FONE));

		final TextInputEditText edtEmail = findViewById(R.id.edtEmail);

		final TextInputEditText edtSenha = findViewById(R.id.edtSenha);

		final TextInputEditText edtContraSenha = findViewById(R.id.edtContraSenha);

		final AutoCompleteTextView aucTipoSangue = findViewById(R.id.aucTipoSangue);
		ArrayAdapter<String> adapterSangue = new ArrayAdapter<>(CadastroActivity.this, android.R.layout.simple_spinner_dropdown_item, Constantes.getListTiposSangues());
		aucTipoSangue.setAdapter(adapterSangue);

		final AutoCompleteTextView aucSexo = findViewById(R.id.aucSexo);
		ArrayAdapter<String> adapterSexo = new ArrayAdapter<>(CadastroActivity.this, android.R.layout.simple_spinner_dropdown_item, Constantes.getListSexos());
		aucSexo.setAdapter(adapterSexo);

		Button btnCadastrar = findViewById(R.id.btnCadastrar);
		btnCadastrar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String nome = edtNome.getText().toString().trim();

				String cpf = edtCPF.getText().toString().trim();

				String email = edtEmail.getText().toString().trim();

				String data_nasc = edtDataNasc.getText().toString().trim();

				String telefone = edtTelefone.getText().toString().trim();

				String senha = edtSenha.getText().toString();

				String contra_senha = edtContraSenha.getText().toString();

				String tipo_sangue = aucTipoSangue.getText().toString().trim();

				String descricao_sexo = aucSexo.getText().toString();
				int sexo = Constantes.getListSexos().indexOf(descricao_sexo);
				if (sexo < 0) {
					sexo = 2;
				}

				if (nome.equals("")) {
					edtNome.setError("Preencha o seu nome.");
					edtNome.requestFocus();
					return;
				} else if (cpf.length() != 14) {
					edtCPF.setError("CPF inválido.");
					edtCPF.requestFocus();
					return;
				} else if (!FuncoesGlobal.validarData(data_nasc, 1)) {
					edtDataNasc.setError("Data de nascimento inválida.");
					edtDataNasc.requestFocus();
					return;
				} else if (email.equals("")) {
					edtEmail.setError("Preencha o e-mail corretamente.");
					edtEmail.requestFocus();
					return;
				} else if (senha.trim().equals("")) {
					edtSenha.setError("Preencha a senha!");
					edtSenha.requestFocus();
					return;
				} else if (contra_senha.trim().equals("")) {
					edtContraSenha.setError("Confirme sua senha.");
					edtContraSenha.requestFocus();
					return;
				} else if (!contra_senha.equals(senha)) {
					edtContraSenha.setText("");
					edtContraSenha.setError("Confirme sua senha corretamente.");
					edtContraSenha.requestFocus();
					return;
				}

				usuarioController.cadastrarUsuario(nome, cpf, email, telefone, senha, contra_senha, tipo_sangue, sexo, data_nasc, bubbleProgress, myToolbar);
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
		}
		return true;
	}
}
