package com.example.sossangue.view.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.sossangue.R;
import com.example.sossangue.controller.Constantes;
import com.example.sossangue.controller.DoacaoController;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends Fragment {
	@SuppressLint("StaticFieldLeak")
	public static LinearLayout bubbleProgress;
	public static DrawerLayout drawerLayout;
	public static Toolbar toolbar;

	public FeedFragment() {
		// Required empty public constructor
	}

	private DoacaoController doacaoController;
	private TextInputEditText edtPesquisa;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_feed, container, false);

		doacaoController = new DoacaoController(getContext());

		final RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
		edtPesquisa = v.findViewById(R.id.edtPesquisa);

		doacaoController.carregarFeedDoacoes(bubbleProgress, drawerLayout, toolbar, Constantes.getUsuarioLogado().getCodigoUsuario(), recyclerView, "");

		edtPesquisa.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				String pesquisa = edtPesquisa.getText().toString().trim();
				doacaoController.carregarFeedDoacoes(bubbleProgress, drawerLayout, toolbar, Constantes.getUsuarioLogado().getCodigoUsuario(), recyclerView, pesquisa);
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
		});

		return v;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}
}
