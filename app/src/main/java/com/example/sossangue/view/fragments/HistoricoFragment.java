package com.example.sossangue.view.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.sossangue.R;
import com.example.sossangue.controller.Constantes;
import com.example.sossangue.controller.DoacaoController;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoricoFragment extends Fragment {
	@SuppressLint("StaticFieldLeak")
	public static LinearLayout bubbleProgress;
	public static DrawerLayout drawerLayout;
	public static Toolbar toolbar;

	private DoacaoController doacaoController;

	public HistoricoFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_historico, container, false);

		doacaoController = new DoacaoController(getContext());

		RecyclerView recyclerView = v.findViewById(R.id.recyclerView);

		doacaoController.historicoDoacoes(bubbleProgress, drawerLayout, toolbar, Constantes.getUsuarioLogado().getCodigoUsuario(), recyclerView);

		return v;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}
}
