package com.example.sossangue.view.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {
	@SuppressLint("StaticFieldLeak")
	public static LinearLayout bubbleProgress;
	public static DrawerLayout drawerLayout;
	public static Toolbar toolbar;

	private UsuarioController usuarioController;

	private TextInputEditText edtNome, edtCPF, edtEmail, edtDataNasc, edtTelefone, edtSenha, edtContraSenha;
	private AutoCompleteTextView aucTipoSangue, aucSexo;

	public PerfilFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_perfil, container, false);

		System.gc();

		usuarioController = new UsuarioController(getContext());

		edtNome = v.findViewById(R.id.edtNome);
		edtNome.setText(Constantes.getUsuarioLogado().getNome());

		edtCPF = v.findViewById(R.id.edtCPF);
		edtCPF.addTextChangedListener(MaskEditUtil.mask(edtCPF, MaskEditUtil.FORMAT_CPF));
		edtCPF.setEnabled(false);
		edtCPF.setText(Constantes.getUsuarioLogado().getCpf());

		edtDataNasc = v.findViewById(R.id.edtDataNasc);
		edtDataNasc.addTextChangedListener(MaskEditUtil.mask(edtDataNasc, MaskEditUtil.FORMAT_DATE));
		edtDataNasc.setText(Constantes.getUsuarioLogado().getDataNascimento());

		edtTelefone = v.findViewById(R.id.edtTelefone);
		edtTelefone.addTextChangedListener(MaskEditUtil.mask(edtTelefone, MaskEditUtil.FORMAT_FONE));
		edtTelefone.setText(Constantes.getUsuarioLogado().getTelefone());

		edtEmail = v.findViewById(R.id.edtEmail);
		edtEmail.setEnabled(false);
		edtEmail.setText(Constantes.getUsuarioLogado().getEmail());

		edtSenha = v.findViewById(R.id.edtSenha);
		edtSenha.setText(Constantes.getUsuarioLogado().getSenha());

		edtContraSenha = v.findViewById(R.id.edtContraSenha);
		edtContraSenha.setText(Constantes.getUsuarioLogado().getSenha());

		aucTipoSangue = v.findViewById(R.id.aucTipoSangue);
		ArrayAdapter<String> adapterSangue = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, Constantes.getListTiposSangues());
		aucTipoSangue.setAdapter(adapterSangue);
		aucTipoSangue.setText(Constantes.getUsuarioLogado().getTipoSangue(), false);

		aucSexo = v.findViewById(R.id.aucSexo);
		ArrayAdapter<String> adapterSexo = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, Constantes.getListSexos());
		aucSexo.setAdapter(adapterSexo);
		aucSexo.setText(Constantes.getListSexos().get(Constantes.getUsuarioLogado().getSexo()), false);

		Button btnSalvar = v.findViewById(R.id.btnSalvar);
		btnSalvar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
				builder.setCancelable(true);
				builder.setTitle("");
				builder.setMessage("Deseja alterar seus dados?");
				builder.setPositiveButton("Sim",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								editarUsuario();
							}
						});

				builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});

				AlertDialog dialog = builder.create();
				dialog.show();
			}
		});
		return v;
	}

	private void editarUsuario() {
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

		usuarioController.editarUsuario(Constantes.getUsuarioLogado().getCodigoUsuario(), nome, cpf, email, telefone, senha, contra_senha, tipo_sangue, sexo, data_nasc, bubbleProgress, toolbar);
	}
}
