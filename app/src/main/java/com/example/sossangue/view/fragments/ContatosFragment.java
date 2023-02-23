package com.example.sossangue.view.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.sossangue.R;
import com.example.sossangue.controller.Constantes;
import com.example.sossangue.controller.MaskEditUtil;
import com.example.sossangue.controller.UsuarioController;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContatosFragment extends Fragment {
	@SuppressLint("StaticFieldLeak")
	public static LinearLayout bubbleProgress;
	public static DrawerLayout drawerLayout;
	public static Toolbar toolbar;

	public ContatosFragment() {
		// Required empty public constructor
	}

	private TextInputEditText edtMensagem, edtEmail, edtTelefone;
	private UsuarioController usuarioController;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_contatos, container, false);

		usuarioController = new UsuarioController(getContext());

		edtMensagem = v.findViewById(R.id.edtMensagem);

		edtTelefone = v.findViewById(R.id.edtTelefone);
		edtTelefone.addTextChangedListener(MaskEditUtil.mask(edtTelefone, MaskEditUtil.FORMAT_FONE));
		edtTelefone.setText(Constantes.getUsuarioLogado().getTelefone());

		edtEmail = v.findViewById(R.id.edtEmail);
		edtEmail.setText(Constantes.getUsuarioLogado().getEmail());

		final Button btnEnviar = v.findViewById(R.id.btnEnviar);
		btnEnviar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String email = edtEmail.getText().toString().trim();

				String telefone = edtTelefone.getText().toString().trim();

				String mensagem = edtMensagem.getText().toString().trim();

				mensagem = mensagem.replace("'", " ");
				mensagem = mensagem.replace("\'", " ");
				mensagem = mensagem.replace("\"", " ");
				mensagem = mensagem.trim();

				usuarioController.enviarMensagem(Constantes.getUsuarioLogado().getCodigoUsuario(), mensagem, telefone, email, bubbleProgress, toolbar, btnEnviar);
			}
		});

		return v;
	}
}
