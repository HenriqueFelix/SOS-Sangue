package com.example.sossangue.view.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sossangue.R;
import com.example.sossangue.controller.Constantes;
import com.example.sossangue.controller.DoacaoController;
import com.example.sossangue.controller.FuncoesGlobal;
import com.example.sossangue.controller.MaskEditUtil;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class AngendarDoacaoFragment extends Fragment {
	@SuppressLint("StaticFieldLeak")
	public static LinearLayout bubbleProgress;
	public static DrawerLayout drawerLayout;
	public static Toolbar toolbar;

	public AngendarDoacaoFragment() {
		// Required empty public constructor
	}

	private DoacaoController doacaoController;

	private TextInputEditText edtTelefone;
	private TextInputEditText edtData;
	private AutoCompleteTextView aucHemocentro;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_angendar_doacao, container, false);

		doacaoController = new DoacaoController(getContext());

		TextInputEditText edtNome = v.findViewById(R.id.edtNome);
		edtNome.setEnabled(false);
		edtNome.setText(Constantes.getUsuarioLogado().getNome());

		edtTelefone = v.findViewById(R.id.edtTelefone);
		edtTelefone.addTextChangedListener(MaskEditUtil.mask(edtTelefone, MaskEditUtil.FORMAT_FONE));
		edtTelefone.setText(Constantes.getUsuarioLogado().getTelefone());

		edtData = v.findViewById(R.id.edtData);
		edtData.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new DatePickerDialog(getContext(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
			}
		});

		aucHemocentro = v.findViewById(R.id.aucHemocentro);
		if (Constantes.getListHemocentros() != null) {
			FuncoesGlobal.setupAutoCompleteHemocentro(aucHemocentro, Constantes.getListHemocentros(), getContext());
		}

		final TextView lblMsg = v.findViewById(R.id.lblMsg);

		final Button btnAgendar = v.findViewById(R.id.btnAgendar);
		btnAgendar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String data_doacao = edtData.getText().toString().trim();

				try {
					if (!FuncoesGlobal.validarData(data_doacao, 0)) {
						edtData.setError("Preencha a data de doação.");
						return;
					} else {
						//edtData.setError("");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
				builder.setCancelable(true);
				builder.setTitle("");
				builder.setMessage("Deseja agendar a sua doação para o dia "+data_doacao+"?");
				builder.setPositiveButton("Sim",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								agendarDoacao(btnAgendar, lblMsg);
							}
						});

				builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
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

	private void agendarDoacao(Button btnAgendar, TextView lblMsg) {
		String data_doacao = edtData.getText().toString().trim();

		if (!FuncoesGlobal.validarData(data_doacao, 0)) {
			edtData.setError("Preencha a data de doação.");
			edtData.requestFocus();
			return;
		}

		String telefone = edtTelefone.getText().toString().trim();

		int codigo_hemocentro = 0;

		if (FuncoesGlobal.hemocentroSelecionado != null) {
			if (FuncoesGlobal.hemocentroSelecionado.getNomeFantasia().trim().equals(aucHemocentro.getText().toString().trim())) {
				codigo_hemocentro = FuncoesGlobal.hemocentroSelecionado.getCodigoHemocentro();
			} else {
				FuncoesGlobal.hemocentroSelecionado = null;
			}
		}

		doacaoController.agendarDoacao(bubbleProgress, drawerLayout, toolbar, Constantes.getUsuarioLogado().getCodigoUsuario(), Constantes.getUsuarioLogado().getTipoSangue(), telefone, data_doacao, codigo_hemocentro, btnAgendar, lblMsg);
	}

	private Calendar myCalendar = Calendar.getInstance();
	private DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			myCalendar.set(Calendar.YEAR, year);
			myCalendar.set(Calendar.MONTH, monthOfYear);
			myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

			updateEditTextData();
		}
	};

	private void updateEditTextData() {
		String myFormat = "dd/MM/yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));
		edtData.setText(sdf.format(myCalendar.getTime()));
	}

}
