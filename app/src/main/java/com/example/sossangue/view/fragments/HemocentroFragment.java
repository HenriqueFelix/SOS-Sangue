package com.example.sossangue.view.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.sossangue.R;
import com.example.sossangue.controller.FuncoesGlobal;
import com.example.sossangue.controller.GPSTracker;
import com.example.sossangue.controller.HemocentroController;
import com.example.sossangue.model.Hemocentro;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class HemocentroFragment extends Fragment implements GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback {
	@SuppressLint("StaticFieldLeak")
	public static LinearLayout bubbleProgress;
	public static DrawerLayout drawerLayout;
	public static Toolbar toolbar;

	private HemocentroController hemocentroController;

	public HemocentroFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_hemocentro, container, false);

		hemocentroController = new HemocentroController(getContext());

		return v;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		GPSTracker gps = new GPSTracker(getContext());
		if(!gps.canGetLocation()) {
			FuncoesGlobal.toastApp("Ops! GPS desligado, para uma experiência melhor ative o GPS.", getContext());
		} else {
			gps.getDadosGeograficos();

			SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map1);
			if (mapFragment != null) {
				mapFragment.getMapAsync(this);
			}
		}
	}

	private GoogleMap mGoogleMap;

	@Override
	public void onMapReady(GoogleMap googleMap) {
		mGoogleMap = googleMap;

		hemocentroController.carregarHemocentros(bubbleProgress, drawerLayout, toolbar, googleMap, "");

		googleMap.setOnInfoWindowClickListener(this);
	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		Map<Marker, Hemocentro> markersMap = HemocentroController.mapMarkHemocentros;

		if (markersMap != null) {
			if (markersMap.containsKey(marker)) {
				if (markersMap.get(marker) != null) {
					hemocentroController.modalDetalhesHemocentro(markersMap.get(marker));
				}
			}
		}
	}

}
