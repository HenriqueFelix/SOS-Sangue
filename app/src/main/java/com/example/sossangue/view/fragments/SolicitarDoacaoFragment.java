package com.example.sossangue.view.fragments;

import android.annotation.SuppressLint;
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
import android.widget.TextView;

import com.example.sossangue.R;
import com.example.sossangue.controller.Constantes;
import com.example.sossangue.controller.DoacaoController;
import com.example.sossangue.controller.FuncoesGlobal;
import com.example.sossangue.controller.MaskEditUtil;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SolicitarDoacaoFragment extends Fragment {
	@SuppressLint("StaticFieldLeak")
	public static LinearLayout bubbleProgress;
	public static DrawerLayout drawerLayout;
	public static Toolbar toolbar;

	public SolicitarDoacaoFragment() {
		// Required empty public constructor
	}

	private DoacaoController doacaoController;

	private TextInputEditText edtTelefone, edtMotivo;

	private AutoCompleteTextView aucHemocentro, aucTipoSangue;
	private TextView lblMsg;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_solicitar_doacao, container, false);

		doacaoController = new DoacaoController(getContext());

		TextInputEditText edtNome = v.findViewById(R.id.edtNome);
		edtNome.setEnabled(false);
		edtNome.setText(Constantes.getUsuarioLogado().getNome());

		TextInputEditText edtCPF = v.findViewById(R.id.edtCPF);
		edtCPF.setEnabled(false);
		edtCPF.setText(Constantes.getUsuarioLogado().getCpf());

		edtTelefone = v.findViewById(R.id.edtTelefone);
		edtTelefone.addTextChangedListener(MaskEditUtil.mask(edtTelefone, MaskEditUtil.FORMAT_FONE));
		edtTelefone.setText(Constantes.getUsuarioLogado().getTelefone());

		aucHemocentro = v.findViewById(R.id.aucHemocentro);
		if (Constantes.getListHemocentros() != null) {
			FuncoesGlobal.setupAutoCompleteHemocentro(aucHemocentro, Constantes.getListHemocentros(), getContext());
		}

		edtMotivo = v.findViewById(R.id.edtMotivo);

		ArrayList<String> listTipoSangue = Constantes.getListTiposSangues();
		listTipoSangue.add("Todos os tipos");
		aucTipoSangue = v.findViewById(R.id.aucTipoSangue);
		ArrayAdapter<String> adapterSangue = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, listTipoSangue);
		aucTipoSangue.setAdapter(adapterSangue);

		if (Constantes.getUsuarioLogado().getTipoSangue() != null && !Constantes.getUsuarioLogado().getTipoSangue().trim().equals("")) {
			aucTipoSangue.setText(Constantes.getUsuarioLogado().getTipoSangue(), false);
		}

		final Button btnSolicitar = v.findViewById(R.id.btnSolicitar);
		btnSolicitar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				solicitarDoacao(btnSolicitar, lblMsg);
			}
		});

		lblMsg = v.findViewById(R.id.lblMsg);

		return v;
	}

	private void solicitarDoacao(Button btnSolicitar, TextView lblMsg) {
		String telefone = edtTelefone.getText().toString().trim();
		telefone = telefone.replace("'", " ");
		telefone = telefone.replace("\'", " ");
		telefone = telefone.replace("\"", " ");
		telefone = telefone.trim();

		String motivo_solicitacao = edtMotivo.getText().toString().trim();
		motivo_solicitacao = motivo_solicitacao.replace("'", " ");
		motivo_solicitacao = motivo_solicitacao.replace("\'", " ");
		motivo_solicitacao = motivo_solicitacao.replace("\"", " ");
		motivo_solicitacao = motivo_solicitacao.trim();

		int codigo_hemocentro = 0;

		if (FuncoesGlobal.hemocentroSelecionado != null) {
			if (FuncoesGlobal.hemocentroSelecionado.getNomeFantasia().trim().equals(aucHemocentro.getText().toString().trim())) {
				codigo_hemocentro = FuncoesGlobal.hemocentroSelecionado.getCodigoHemocentro();
			} else {
				FuncoesGlobal.hemocentroSelecionado = null;
			}
		}

		String tipo_sangue = "Todos os tipos";
		String strAucTipoSangue = aucTipoSangue.getText().toString().trim();
		if(!strAucTipoSangue.equals("")) {
			tipo_sangue = strAucTipoSangue;
		}

		doacaoController.solicitarDoacao(bubbleProgress, drawerLayout, toolbar, Constantes.getUsuarioLogado().getCodigoUsuario(), tipo_sangue, telefone, motivo_solicitacao, codigo_hemocentro, btnSolicitar, lblMsg);
	}
}
