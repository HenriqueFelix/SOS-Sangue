package com.example.sossangue.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sossangue.R;
import com.example.sossangue.controller.Constantes;
import com.example.sossangue.view.fragments.AngendarDoacaoFragment;
import com.example.sossangue.view.fragments.ContatosFragment;
import com.example.sossangue.view.fragments.HemocentroFragment;
import com.example.sossangue.view.fragments.HistoricoFragment;
import com.example.sossangue.view.fragments.PerfilFragment;
import com.example.sossangue.view.fragments.SolicitarDoacaoFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainNavigationActivity extends AppCompatActivity {

	private AppBarConfiguration mAppBarConfiguration;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_navigation);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		NavigationView navigationView = findViewById(R.id.nav_view);

		// Passing each menu ID as a set of Ids because each
		// menu should be considered as top level destinations.
		mAppBarConfiguration = new AppBarConfiguration.Builder(
				R.id.nav_home, R.id.nav_agendar_doacao, R.id.nav_solicitar_doacao, R.id.nav_feed, R.id.nav_historico, R.id.nav_contatos, R.id.nav_perfil)
				.setDrawerLayout(drawer)
				.build();
		NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
		NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
		NavigationUI.setupWithNavController(navigationView, navController);


		navigationView.getMenu().findItem(R.id.nav_logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				openModalLogout();
				return false;
			}
		});

		View headerView = navigationView.getHeaderView(0);

		TextView txtNomeUsuario = headerView.findViewById(R.id.txtNomeUsuario);
		txtNomeUsuario.setText(Constantes.getUsuarioLogado().getPrimeiroNome());

		TextView txtTipoSangueUsuario = headerView.findViewById(R.id.txtTipoSangueUsuario);
		txtTipoSangueUsuario.setText(Constantes.getUsuarioLogado().getTipoSangue());

		LinearLayout bubbleProgress = findViewById(R.id.bubbleProgress);

		HemocentroFragment.bubbleProgress 		= bubbleProgress;
		HemocentroFragment.drawerLayout 		= drawer;
		HemocentroFragment.toolbar 				= toolbar;

		AngendarDoacaoFragment.bubbleProgress 	= bubbleProgress;
		AngendarDoacaoFragment.drawerLayout 	= drawer;
		AngendarDoacaoFragment.toolbar 			= toolbar;

		ContatosFragment.bubbleProgress 		= bubbleProgress;
		ContatosFragment.drawerLayout 			= drawer;
		ContatosFragment.toolbar 				= toolbar;

		HistoricoFragment.bubbleProgress 		= bubbleProgress;
		HistoricoFragment.drawerLayout 			= drawer;
		HistoricoFragment.toolbar 				= toolbar;

		PerfilFragment.bubbleProgress 			= bubbleProgress;
		PerfilFragment.drawerLayout 		= drawer;
		PerfilFragment.toolbar 				= toolbar;

		SolicitarDoacaoFragment.bubbleProgress 	= bubbleProgress;
		SolicitarDoacaoFragment.drawerLayout 	= drawer;
		SolicitarDoacaoFragment.toolbar 		= toolbar;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_navigation, menu);
		return true;
	}

	@Override
	public boolean onSupportNavigateUp() {
		NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
		return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
	}

	private void openModalLogout() {
		AlertDialog.Builder builder = new AlertDialog.Builder(MainNavigationActivity.this);
		builder.setCancelable(true);
		builder.setTitle("");
		builder.setMessage("Deseja sair do aplicativo?");
		builder.setPositiveButton("Sim",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent loginIntent = new Intent(MainNavigationActivity.this, LoginActivity.class); //ABRE A TELA DE LOGIN
						startActivity(loginIntent);
						MainNavigationActivity.this.finish();
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
}
