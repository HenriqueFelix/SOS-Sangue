package com.example.sossangue.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sossangue.controller.adapter.DoacaoAdapter;
import com.example.sossangue.controller.adapter.FeedDoacaoAdapter;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

	public void solicitarDoacao(final LinearLayout bubbleProgress, final DrawerLayout drawerLayout, final Toolbar toolbar, final int codigo_usuario, final String tipo_sangue, final String telefone, final String motivo_solicitacao, final int codigo_hemocentro, final Button btnAgendar, final TextView lblMsg) {
		if (FuncoesGlobal.verificaConexao(mCtx)) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						FuncoesGlobal.showCustomProgress(mCtx, drawerLayout, toolbar, bubbleProgress, 1, 0, null);

						Map<String, String> Params = new HashMap<>();
						Params.put("metodo", 				"SolicitarDoacao");
						Params.put("codigo_usuario", 		String.valueOf(codigo_usuario));
						Params.put("tipo_sangue", 			tipo_sangue);
						Params.put("telefone", 				telefone);
						Params.put("motivo_solicitacao", 	motivo_solicitacao);
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

	public void historicoDoacoes(final LinearLayout bubbleProgress, final DrawerLayout drawerLayout, final Toolbar toolbar, final int codigo_usuario, final RecyclerView recyclerView) {
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
									doacao.setFlagTipoDoacao(objectDoacao.getInt("flag_tipo_doacao"));
									doacao.setStatusDoacao(objectDoacao.getInt("status_doacao"));
									doacao.setUsuarioDoador(usuarioDoador);
									doacao.setHemocentro(hemocentro);
									doacao.setDataDoacao(objectDoacao.getString("data_doacao"));
									doacao.setDataHoraCadastro(objectDoacao.getString("data_cadastro"));
									doacao.setStatusSolicitacao(objectDoacao.getInt("status_solicitacao"));
									doacao.setUsuarioSolicitante(usuarioSolicitante);

									listDoacoes.add(doacao);
								}
							}
							FuncoesGlobal.showCustomProgress(mCtx, drawerLayout, toolbar, bubbleProgress, 0, 0, null); //FINALIZA O PROGRESS
						} else {
							FuncoesGlobal.showCustomProgress(mCtx, drawerLayout, toolbar, bubbleProgress, 0, 1, msg);
						}

						exibirHistoricoDoacoes(recyclerView, listDoacoes);
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

	private void exibirHistoricoDoacoes(final RecyclerView recyclerView, final ArrayList<Doacao> listDoacoes) {
		((Activity)mCtx).runOnUiThread(new Runnable() {
			@Override
			public void run() {
				try {
					recyclerView.setLayoutManager(new GridLayoutManager(mCtx,1));

					DoacaoAdapter doacaoAdapter = new DoacaoAdapter(listDoacoes);
					//doacaoAdapter.notifyDataSetChanged();
					//recyclerView.invalidate();
					recyclerView.setAdapter(doacaoAdapter);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void carregarFeedDoacoes(final LinearLayout bubbleProgress, final DrawerLayout drawerLayout, final Toolbar toolbar, final int codigo_usuario, final RecyclerView recyclerView, final String pesquisar) {
		if (FuncoesGlobal.verificaConexao(mCtx)) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						FuncoesGlobal.showCustomProgress(mCtx, drawerLayout, toolbar, bubbleProgress, 1, 0, null);

						String strPesquisar = pesquisar.replace("'", " ");
						strPesquisar = strPesquisar.replace("\'", " ");
						strPesquisar = strPesquisar.replace("\"", " ");
						strPesquisar = strPesquisar.trim();

						Map<String, String> Params = new HashMap<>();
						Params.put("metodo", 				"CarregarFeedSolicitacoes");
						Params.put("codigo_usuario", 		String.valueOf(codigo_usuario));
						Params.put("pesquisar", 			strPesquisar);

						String JSON = FuncoesGlobal.executeHttptPostDataFile(Constantes.urlWebservice, Params, "", "", "");

						if (JSON == null || JSON.equals("")) {
							FuncoesGlobal.showCustomProgress(mCtx, drawerLayout, toolbar, bubbleProgress, 0, 1, "Falha ao realizar operação! Serviço temporariamente indisponível.");
							return;
						}

						JSONObject objectJSON = new JSONObject(JSON);

						int valido = objectJSON.getInt("valido");
						String msg = objectJSON.getString("mensagem");

						ArrayList<Doacao> listFeed = new ArrayList<>();

						if (valido == 1) {
							if (objectJSON.has("feed")) {
								JSONArray ArrJSON = objectJSON.getJSONArray("feed");
								for (int i = 0; i < ArrJSON.length(); i++) {
									JSONObject objectDoacao = new JSONObject(ArrJSON.getString(i));

									Usuario usuarioSolicitante = new Usuario();
									usuarioSolicitante.setCodigoUsuario(objectDoacao.getInt("codigo_usuario_solicitante"));
									usuarioSolicitante.setTipoSangue(objectDoacao.getString("tipo_sangue_solicitado"));
									usuarioSolicitante.setEmail(objectDoacao.getString("emaill_solicitante"));
									usuarioSolicitante.setNome(objectDoacao.getString("nome_solicitante"));

									Hemocentro hemocentro = new Hemocentro();
									hemocentro.setCodigoHemocentro(objectDoacao.getInt("codigo_hemocentro"));
									hemocentro.setRazaoSocial(objectDoacao.getString("razao_social_hemocentro"));
									hemocentro.setNomeFantasia(objectDoacao.getString("nome_fantasia_hemocentro"));

									Doacao doacao = new Doacao();
									doacao.setCodigoDoacao(objectDoacao.getInt("codigo_doacao"));
									doacao.setFlagTipoDoacao(objectDoacao.getInt("flag_tipo_doacao"));
									doacao.setStatusDoacao(0);
									doacao.setUsuarioDoador(null);
									doacao.setDataDoacao(null);
									doacao.setHemocentro(hemocentro);
									doacao.setDataHoraCadastro(objectDoacao.getString("data_cadastro"));
									doacao.setStatusSolicitacao(objectDoacao.getInt("status_solicitacao"));
									doacao.setUsuarioSolicitante(usuarioSolicitante);
									doacao.setMotivoSolicitacao(objectDoacao.getString("motivo_solicitacao"));

									listFeed.add(doacao);
								}
							}
							FuncoesGlobal.showCustomProgress(mCtx, drawerLayout, toolbar, bubbleProgress, 0, 0, null); //FINALIZA O PROGRESS
						} else {
							FuncoesGlobal.showCustomProgress(mCtx, drawerLayout, toolbar, bubbleProgress, 0, 1, msg);
						}

						exibirFeedDoacoes(recyclerView, listFeed, bubbleProgress, drawerLayout, toolbar);
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

	private void exibirFeedDoacoes(final RecyclerView recyclerView, final ArrayList<Doacao> listFeed, final LinearLayout bubbleProgress, final DrawerLayout drawerLayout, final Toolbar toolbar) {
		((Activity)mCtx).runOnUiThread(new Runnable() {
			@Override
			public void run() {
				try {
					recyclerView.setLayoutManager(new GridLayoutManager(mCtx,1));

					FeedDoacaoAdapter feedDoacaoAdapter = new FeedDoacaoAdapter(listFeed, mCtx, bubbleProgress, drawerLayout, toolbar);

					recyclerView.setAdapter(feedDoacaoAdapter);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void doarParaSolicitante(final int codigo_usuario, final String data_doacao, final Doacao doacao, final LinearLayout bubbleProgress, final DrawerLayout drawerLayout, final Toolbar toolbar, final AlertDialog alertDialog, final Button btnAgendar) {
		if (FuncoesGlobal.verificaConexao(mCtx)) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						if (doacao != null && doacao.getFlagTipoDoacao() == 1 && doacao.getUsuarioSolicitante().getCodigoUsuario() != codigo_usuario) {
							FuncoesGlobal.showCustomProgress(mCtx, drawerLayout, toolbar, bubbleProgress, 1, 0, null);

							if (btnAgendar != null) {
								((Activity)mCtx).runOnUiThread(new Runnable() {
									@SuppressLint("SetTextI18n")
									@Override
									public void run() {
										btnAgendar.setEnabled(false);
										btnAgendar.setText("Carregando...");
									}
								});
							}

							Map<String, String> Params = new HashMap<>();
							Params.put("metodo", 			"DoarSangueUsuarioFeed");
							Params.put("codigo_usuario", 	String.valueOf(codigo_usuario));
							Params.put("data_agendamento", 	data_doacao);
							Params.put("codigo_hemocentro", String.valueOf(doacao.getHemocentro().getCodigoHemocentro()));
							Params.put("codigo_doacao", 	String.valueOf(doacao.getCodigoDoacao()));

							String JSON = FuncoesGlobal.executeHttptPostDataFile(Constantes.urlWebservice, Params, "", "", "");

							if (JSON == null || JSON.equals("")) {
								FuncoesGlobal.showCustomProgress(mCtx, drawerLayout, toolbar, bubbleProgress, 0, 1, "Falha ao realizar operação! Serviço temporariamente indisponível.");
								return;
							}

							JSONObject objectJSON = new JSONObject(JSON);

							int valido = objectJSON.getInt("valido");
							String msg = objectJSON.getString("mensagem");

							if (valido == 1) {
								FuncoesGlobal.showCustomProgress(mCtx, drawerLayout, toolbar, bubbleProgress, 0, 1, msg); //FINALIZA O PROGRESS

								if (alertDialog != null) {
									((Activity)mCtx).runOnUiThread(new Runnable() {
										@Override
										public void run() {
											try {
												alertDialog.dismiss();
											} catch (Exception e) {
												e.printStackTrace();
											}
										}
									});
								}
							} else {
								FuncoesGlobal.showCustomProgress(mCtx, drawerLayout, toolbar, bubbleProgress, 0, 1, msg);

								if (btnAgendar != null) {
									((Activity)mCtx).runOnUiThread(new Runnable() {
										@SuppressLint("SetTextI18n")
										@Override
										public void run() {
											btnAgendar.setEnabled(true);
											btnAgendar.setText("AGENDAR");
										}
									});
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();

						FuncoesGlobal.showCustomProgress(mCtx, drawerLayout, toolbar, bubbleProgress, 0, 1, "Ops! Falha ao realizar operação."); //FINALIZA O PROGRESS

						if (btnAgendar != null) {
							((Activity)mCtx).runOnUiThread(new Runnable() {
								@SuppressLint("SetTextI18n")
								@Override
								public void run() {
									btnAgendar.setEnabled(true);
									btnAgendar.setText("AGENDAR");
								}
							});
						}
					}

					System.gc(); //LIBERA ESPAÇO NA MEMORIA
				}
			}).start();
		} else {
			FuncoesGlobal.toastApp("Ops! Verifique sua conexão.", mCtx);
		}
	}
}
