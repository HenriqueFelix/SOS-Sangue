package com.example.sossangue.controller.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sossangue.R;
import com.example.sossangue.controller.Constantes;
import com.example.sossangue.controller.DoacaoController;
import com.example.sossangue.model.Doacao;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

class ModalDoarSangue {
	private TextInputEditText edtTipoSangue, edtHemocentro, edtData;
	private TextView lblModal;

	private Context mCtx;
	private Doacao mDoacao;
	private DoacaoController mDoacaoController;
	private LinearLayout bubbleProgress;
	private DrawerLayout drawerLayout;
	private Toolbar toolbar;

	ModalDoarSangue(Context ctx, LinearLayout bubbleProgress, DrawerLayout drawerLayout, Toolbar toolbar, Doacao doacao) {
		this.mCtx 			= ctx;
		this.mDoacao 		= doacao;
		this.bubbleProgress = bubbleProgress;
		this.drawerLayout 	= drawerLayout;
		this.toolbar 		= toolbar;

		instanciarDoacaoController();
	}

	private void instanciarDoacaoController() {
		mDoacaoController = new DoacaoController(this.mCtx);
	}

	void openModal() {
		((Activity)mCtx).runOnUiThread(new Runnable() {
			@SuppressLint("SetTextI18n")
			@Override
			public void run() {
				try {
					AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mCtx);
					dialogBuilder.setCancelable(true);
					LayoutInflater inflater = ((Activity)mCtx).getLayoutInflater();
					@SuppressLint("InflateParams")
					View dialogView = inflater.inflate(R.layout.modal_doar_sangue, null);
					dialogBuilder.setView(dialogView);

					final AlertDialog alertDialog = dialogBuilder.create();

					lblModal = dialogView.findViewById(R.id.lblModal);
					lblModal.setText("Agendar doação de sangue para "+mDoacao.getUsuarioSolicitante().getNome());

					edtTipoSangue = dialogView.findViewById(R.id.edtTipoSangue);
					edtTipoSangue.setText(mDoacao.getUsuarioSolicitante().getTipoSangue());

					edtHemocentro = dialogView.findViewById(R.id.edtHemocentro);
					edtHemocentro.setText(mDoacao.getHemocentro().getNomeFantasia().toUpperCase());

					edtData = dialogView.findViewById(R.id.edtData);
					edtData.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							openDataPicker();
						}
					});

					final Button btnAgendar = dialogView.findViewById(R.id.btnAgendar);
					btnAgendar.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							String data = edtData.getText().toString().trim();
							mDoacaoController.doarParaSolicitante(Constantes.getUsuarioLogado().getCodigoUsuario(), data, mDoacao, bubbleProgress, drawerLayout, toolbar, alertDialog, btnAgendar);
							//alertDialog.dismiss();
						}
					});

					alertDialog.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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

	private void openDataPicker() {
		((Activity)mCtx).runOnUiThread(new Runnable() {
			@Override
			public void run() {
				try {
					new DatePickerDialog(mCtx, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
