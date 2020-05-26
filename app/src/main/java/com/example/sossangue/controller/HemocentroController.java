package com.example.sossangue.controller;

import android.app.Activity;
import android.content.Context;
import android.widget.LinearLayout;

import com.example.sossangue.R;
import com.example.sossangue.model.Hemocentro;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

public class HemocentroController {
	public static Map<Marker, Hemocentro> mapMarkHemocentros;

	private Context mCtx;
	private Hemocentro mHemocentro;

	public HemocentroController(Context context) {
		this.mCtx = context;
	}

	public HemocentroController(Context context, Hemocentro hmc) {
		this.mCtx 			= context;
		this.mHemocentro 	= hmc;
	}

	public void carregarHemocentros(final LinearLayout bubbleProgress, final DrawerLayout drawerLayout, final Toolbar toolbar, final GoogleMap googleMap, final String filtro) {

		mapMarkHemocentros = new HashMap<>();
		final ArrayList<Hemocentro> listHemocentros = new ArrayList<>();

		if (FuncoesGlobal.verificaConexao(mCtx)) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						FuncoesGlobal.showCustomProgress(mCtx, drawerLayout, toolbar, bubbleProgress, 1, 0, null);

						Map<String, String> Params = new HashMap<>();
						Params.put("metodo", 	"CarregarHemocentros");
						Params.put("latitude", 	Constantes.getUltimaLatitude());
						Params.put("longitude", Constantes.getUltimaLongitude());
						Params.put("filtro", 	filtro);

						String JSON = FuncoesGlobal.executeHttptPostDataFile(Constantes.urlWebservice, Params, "", "", "");

						if (JSON == null || JSON.equals("")) {
							FuncoesGlobal.showCustomProgress(mCtx, drawerLayout, toolbar, bubbleProgress, 0, 1, "Falha ao realizar operação! Serviço temporariamente indisponível.");
							return;
						}

						JSONObject objectJSON = new JSONObject(JSON);

						int valido = objectJSON.getInt("valido");
						String msg = objectJSON.getString("mensagem");

						if (valido == 1) {
							JSONArray ArrJSON = objectJSON.getJSONArray("hemocentros");
							for (int i = 0; i < ArrJSON.length(); i++) {
								JSONObject object = new JSONObject(ArrJSON.getString(i));

								int codigo_hemocentro 	= object.getInt("codigo_hemocentro");
								String razao_social 	= object.getString("razao_social");
								String nome_fantasia 	= object.getString("nome_fantasia");
								String latitude 		= object.getString("latitude");
								String longitude 		= object.getString("longitude");
								String logradouro 		= object.getString("logradouro");
								String bairro 			= object.getString("bairro");
								String cidade 			= object.getString("cidade");
								String estado 			= object.getString("estado");
								String num_endereco 	= object.getString("num_endereco");
								String cep 				= object.getString("cep");
								String telefone 		= object.getString("telefone");
								String email 			= object.getString("email");
								String site 			= object.getString("site");

								Hemocentro hemocentro = new Hemocentro();
								hemocentro.setCodigoHemocentro(codigo_hemocentro);
								hemocentro.setRazaoSocial(razao_social);
								hemocentro.setNomeFantasia(nome_fantasia);
								hemocentro.setLatitude(latitude);
								hemocentro.setLongitude(longitude);
								hemocentro.setLogradouro(logradouro);
								hemocentro.setBairro(bairro);
								hemocentro.setCidade(cidade);
								hemocentro.setEstado(estado);
								hemocentro.setNumEndereco(num_endereco);
								hemocentro.setCep(cep);
								hemocentro.setTelefone(telefone);
								hemocentro.setEmail(email);
								hemocentro.setSite(site);

								listHemocentros.add(hemocentro);
							}

							exibirHemocentrosMaps(listHemocentros, googleMap, bubbleProgress, drawerLayout, toolbar);
							//FuncoesGlobal.showCustomProgress(mCtx, drawerLayout, toolbar, bubbleProgress, 0, 0, null); //FINALIZA O PROGRESS
						} else {
							FuncoesGlobal.showCustomProgress(mCtx, drawerLayout, toolbar, bubbleProgress, 0, 1, msg);
						}
					} catch (Exception e) {
						e.printStackTrace();

						FuncoesGlobal.showCustomProgress(mCtx, drawerLayout, toolbar, bubbleProgress, 0, 1, "Ops! Falha ao realizar operação."); //FINALIZA O PROGRESS
					}

					System.gc(); //LIBERA ESPAÇO NA MEMORIA
				}
			}).start();
		} else {
			FuncoesGlobal.toastApp("Ops! Verifique sua conexão.", mCtx);
		}
	}

	private void exibirHemocentrosMaps(final ArrayList<Hemocentro> listHemocentros, final GoogleMap googleMap, final LinearLayout bubbleProgress, final DrawerLayout drawerLayout, final Toolbar toolbar) {
		((Activity) mCtx).runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mapMarkHemocentros = new HashMap<>();

				for (Hemocentro hemocentro: listHemocentros) {
					if (hemocentro != null) {
						if (!hemocentro.getLatitude().equals("") && !hemocentro.getLongitude().equals("")) {
							double lat = Double.parseDouble(hemocentro.getLatitude());
							double lon = Double.parseDouble(hemocentro.getLongitude());

							LatLng latLng = new LatLng(lat, lon);
							Marker marker = googleMap.addMarker(new MarkerOptions()
									.position(latLng)
									.title(hemocentro.getNomeFantasia())
									.snippet(hemocentro.getLogradouro()+", "+hemocentro.getNumEndereco())
									.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_hospital))
							);

							mapMarkHemocentros.put(marker, hemocentro);
						}
					}
				}

				if (Constantes.getUltimaLatitude() != null && !Constantes.getUltimaLatitude().trim().equals("") && Constantes.getUltimaLongitude() != null && !Constantes.getUltimaLongitude().trim().equals("")) {
					double latUsuario = Double.parseDouble(Constantes.getUltimaLatitude());
					double lonUsuario = Double.parseDouble(Constantes.getUltimaLongitude());

					LatLng latLng = new LatLng(latUsuario, lonUsuario);
					Marker markerUsuario = googleMap.addMarker(new MarkerOptions()
							.position(latLng)
							.title(Constantes.getUsuarioLogado().getPrimeiroNome())
							.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_street_view))
					);

					googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13.5F));
				}

				FuncoesGlobal.showCustomProgress(mCtx, drawerLayout, toolbar, bubbleProgress, 0, 0, null); //FINALIZA O PROGRESS
			}
		});
	}

	public void modalDetalhesHemocentro(final Hemocentro hemocentro) {
		if (hemocentro != null) {
			((Activity) mCtx).runOnUiThread(new Runnable() {
				@Override
				public void run() {
					try {
						FuncoesGlobal.toastApp(hemocentro.getRazaoSocial(), mCtx);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

}
