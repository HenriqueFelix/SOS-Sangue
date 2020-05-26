package com.example.sossangue.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sossangue.R;
import com.example.sossangue.model.Doacao;
import com.example.sossangue.model.Hemocentro;
import com.example.sossangue.model.Usuario;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

public class DoacaoController {
	private Context mCtx;

	public DoacaoController(Context context) {
		this.mCtx = context;
	}

	public void agendarDoacao(final LinearLayout bubbleProgress, final DrawerLayout drawerLayout, final Toolbar toolbar, final int codigo_usuario, final String tipo_sangue_doador, final String telefone, final String data_doacao, final int codigo_hemocentro, final Button btnAgendar, final TextView lblMsg) {
		if (FuncoesGlobal.verificaConexao(mCtx)) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						FuncoesGlobal.showCustomProgress(mCtx, drawerLayout, toolbar, bubbleProgress, 1, 0, null);

						Map<String, String> Params = new HashMap<>();
						Params.put("metodo", 				"AgendarDoacao");
						Params.put("codigo_usuario", 		String.valueOf(codigo_usuario));
						Params.put("tipo_sangue_doador", 	tipo_sangue_doador);
						Params.put("telefone", 				telefone);
						Params.put("data_doacao", 			data_doacao);
						Params.put("codigo_hemocentro", 	String.valueOf(codigo_hemocentro));

						String JSON = FuncoesGlobal.executeHttptPostDataFile(Constantes.urlWebservice, Params, "", "", "");

						if (JSON == null || JSON.equals("")) {
							FuncoesGlobal.showCustomProgress(mCtx, drawerLayout, toolbar, bubbleProgress, 0, 1, "Falha ao realizar operação! Serviço temporariamente indisponível.");
							return;
						}

						JSONObject objectJSON = new JSONObject(JSON);

						int valido = objectJSON.getInt("valido");
						final String msg = objectJSON.getString("mensagem");

						if (valido == 1) {
							if (btnAgendar != null || lblMsg != null) {
								((Activity) mCtx).runOnUiThread(new Runnable() {
									@Override
									public void run() {
										if (btnAgendar != null) {
											btnAgendar.setEnabled(false);
											btnAgendar.setVisibility(View.GONE);
										}

										if (lblMsg != null) {
											lblMsg.setVisibility(View.VISIBLE);
											lblMsg.setText(msg);
										}
									}
								});
							}

							FuncoesGlobal.showCustomProgress(mCtx, drawerLayout, toolbar, bubbleProgress, 0, 1, msg); //FINALIZA O PROGRESS
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

	public void historicoDoacoes(final LinearLayout bubbleProgress, final DrawerLayout drawerLayout, final Toolbar toolbar, final int codigo_usuario, final LinearLayout areaCards) {
		if (FuncoesGlobal.verificaConexao(mCtx)) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						FuncoesGlobal.showCustomProgress(mCtx, drawerLayout, toolbar, bubbleProgress, 1, 0, null);

						Map<String, String> Params = new HashMap<>();
						Params.put("metodo", 				"CarregarHistoricoUsuario");
						Params.put("codigo_usuario", 		String.valueOf(codigo_usuario));

						String JSON = FuncoesGlobal.executeHttptPostDataFile(Constantes.urlWebservice, Params, "", "", "");

						if (JSON == null || JSON.equals("")) {
							FuncoesGlobal.showCustomProgress(mCtx, drawerLayout, toolbar, bubbleProgress, 0, 1, "Falha ao realizar operação! Serviço temporariamente indisponível.");
							return;
						}

						JSONObject objectJSON = new JSONObject(JSON);

						int valido = objectJSON.getInt("valido");
						String msg = objectJSON.getString("mensagem");

						ArrayList<Doacao> listDoacoes = new ArrayList<>();

						if (valido == 1) {
							if (objectJSON.has("doacoes")) {
								JSONArray ArrJSON = objectJSON.getJSONArray("doacoes");
								for (int i = 0; i < ArrJSON.length(); i++) {
									JSONObject objectDoacao = new JSONObject(ArrJSON.getString(i));

									Usuario usuarioDoador = new Usuario();
									usuarioDoador.setCodigoUsuario(objectDoacao.getInt("codigo_usuario_doador"));
									usuarioDoador.setTipoSangue(objectDoacao.getString("tipo_sangue_doador"));

									Usuario usuarioSolicitante = new Usuario();
									usuarioSolicitante.setCodigoUsuario(objectDoacao.getInt("codigo_usuario_solicitante"));
									usuarioSolicitante.setTipoSangue(objectDoacao.getString("tipo_sangue_solicitado"));

									Hemocentro hemocentro = new Hemocentro();
									hemocentro.setCodigoHemocentro(objectDoacao.getInt("codigo_hemocentro"));
									hemocentro.setRazaoSocial(objectDoacao.getString("razao_social_hemocentro"));
									hemocentro.setNomeFantasia(objectDoacao.getString("nome_fantasia_hemocentro"));

									Doacao doacao = new Doacao();
									doacao.setCodigoDoacao(objectDoacao.getInt("codigo_doacao"));
									doacao.setFlagTipoDoacao(objectDoacao.getInt("status_doacao"));
									doacao.setStatusDoacao(objectDoacao.getInt("codigo_doacao"));
									doacao.setUsuarioDoador(usuarioDoador);
									doacao.setHemocentro(hemocentro);
									doacao.setDataDoacao(objectDoacao.getString("data_doacao"));
									doacao.setStatusSolicitacao(objectDoacao.getInt("status_solicitacao"));
									doacao.setUsuarioSolicitante(usuarioSolicitante);

									listDoacoes.add(doacao);
								}
							}
							FuncoesGlobal.showCustomProgress(mCtx, drawerLayout, toolbar, bubbleProgress, 0, 0, null); //FINALIZA O PROGRESS
						} else {
							FuncoesGlobal.showCustomProgress(mCtx, drawerLayout, toolbar, bubbleProgress, 0, 1, msg);
						}

						exibirHistoricoDoacoes(areaCards, listDoacoes);
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

	private void exibirHistoricoDoacoes(final LinearLayout areaCards, final ArrayList<Doacao> listDoacoes) {
		((Activity) mCtx).runOnUiThread(new Runnable() {
			@SuppressLint("SetTextI18n")
			@Override
			public void run() {
				try {
					areaCards.removeAllViews();

					if (listDoacoes != null) {
						for (Doacao doacao: listDoacoes) {
							if (doacao != null) {
								LayoutInflater inflaterCamera = LayoutInflater.from(mCtx);
								@SuppressLint("InflateParams")
								View vCard = inflaterCamera.inflate(R.layout.card_historico_doacao, null);

								TextView lblTipo = vCard.findViewById(R.id.lblTipo);
								TextView lblData = vCard.findViewById(R.id.lblData);
								TextView lblHemocentro = vCard.findViewById(R.id.lblHemocentro);
								TextView lblTipoSangue = vCard.findViewById(R.id.lblTipoSangue);

								if (doacao.getFlagTipoDoacao() == 0) {
									lblTipo.setText("Doação");
									lblData.setText(doacao.getDataDoacao());
									lblTipoSangue.setText(doacao.getUsuarioDoador().getTipoSangue());
								} else {
									lblTipo.setText("Solicitação");
									lblData.setText("");
									lblTipoSangue.setText(doacao.getUsuarioSolicitante().getTipoSangue());
								}

								Hemocentro hemocentroDoacao = doacao.getHemocentro();

								lblHemocentro.setText(hemocentroDoacao.getNomeFantasia());

								areaCards.addView(vCard);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
