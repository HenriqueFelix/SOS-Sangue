package com.example.sossangue.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.sossangue.model.Hemocentro;
import com.example.sossangue.model.Usuario;
import com.example.sossangue.view.LoginActivity;
import com.example.sossangue.view.MainNavigationActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.widget.Toolbar;

public class UsuarioController {

	private Context mCtx;
	private Usuario mUsuario;

	public UsuarioController(Context context) {
		this.mCtx = context;
	}

	public UsuarioController(Context context, Usuario usu) {
		this.mCtx 		= context;
		this.mUsuario 	= usu;
	}

	public void loginApp(final String login, final String senha, final LinearLayout bubbleProgress, final Button btnLogin) {
		if (FuncoesGlobal.verificaConexao(mCtx)) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						//INICIA O PROGRESS
						FuncoesGlobal.showCustomProgress(mCtx, null, null, bubbleProgress, 1, 0, null);

						Map<String, String> Params = new HashMap<>();
						Params.put("metodo", 	"VerificarLogin");
						Params.put("login", 	login);
						Params.put("senha", 	senha);

						String JSON = FuncoesGlobal.executeHttptPostDataFile(Constantes.urlWebservice, Params, "", "", "");

						if (JSON == null || JSON.equals("")) {
							FuncoesGlobal.showCustomProgress(mCtx, null, null, bubbleProgress, 0, 1, "Falha ao realizar operação! Serviço temporariamente indisponível.");
							FuncoesGlobal.ativarBotao(mCtx, btnLogin);
							return;
						}

						JSONObject objectJSON = new JSONObject(JSON);

						int valido = objectJSON.getInt("valido");
						String msg = objectJSON.getString("mensagem");

						if (valido == 1) {
							Usuario usuario = new Usuario();
							usuario.setCodigoUsuario(objectJSON.getInt("codigo_usuario"));
							usuario.setNome(objectJSON.getString("nome"));
							usuario.setCpf(objectJSON.getString("cpf"));
							usuario.setEmail(login);
							usuario.setSenha(senha);
							usuario.setTelefone(objectJSON.getString("telefone"));
							usuario.setDataNascimento(objectJSON.getString("data_nascimento"));
							usuario.setTipoSangue(objectJSON.getString("tipo_sangue"));
							usuario.setSexo(objectJSON.getInt("sexo"));
							usuario.setPeso(objectJSON.getDouble("peso"));
							usuario.setAltura(objectJSON.getDouble("altura"));

							Constantes.setUsuarioLogado(usuario);

							if (objectJSON.has("hemocentros_lista")) {
								ArrayList<Hemocentro> listHemocentros = new ArrayList<>();

								JSONArray ArrJSON = objectJSON.getJSONArray("hemocentros_lista");
								for (int i = 0; i < ArrJSON.length(); i++) {
									JSONObject objectHemocentro = new JSONObject(ArrJSON.getString(i));

									int codigo_hemocentro 	= objectHemocentro.getInt("codigo_hemocentro");
									String razao_social 	= objectHemocentro.getString("razao_social");
									String nome_fantasia 	= objectHemocentro.getString("nome_fantasia");
									String latitude 		= objectHemocentro.getString("latitude");
									String longitude 		= objectHemocentro.getString("longitude");
									String logradouro 		= objectHemocentro.getString("logradouro");
									String bairro 			= objectHemocentro.getString("bairro");
									String cidade 			= objectHemocentro.getString("cidade");
									String estado 			= objectHemocentro.getString("estado");
									String num_endereco 	= objectHemocentro.getString("num_endereco");
									String cep 				= objectHemocentro.getString("cep");
									String telefone 		= objectHemocentro.getString("telefone");
									String email 			= objectHemocentro.getString("email");
									String site 			= objectHemocentro.getString("site");

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

								Constantes.setListHemocentros(listHemocentros);
							}


							Intent menuIntent = new Intent(mCtx, MainNavigationActivity.class); //ABRE A TELA DE CADASTRO
							mCtx.startActivity(menuIntent);
							((Activity) mCtx).finish();

							FuncoesGlobal.showCustomProgress(mCtx, null, null, bubbleProgress, 0, 0, null); //FINALIZA O PROGRESS
						} else {
							FuncoesGlobal.showCustomProgress(mCtx, null, null, bubbleProgress, 0, 1, msg);
						}

						System.gc(); //LIBERA ESPAÇO NA MEMORIA
					} catch (Exception e) {
						e.printStackTrace();
						System.gc(); //LIBERA ESPAÇO NA MEMORIA

						FuncoesGlobal.showCustomProgress(mCtx, null, null, bubbleProgress, 0, 1, "Ops! Falha ao realizar operação."); //FINALIZA O PROGRESS
					}

					FuncoesGlobal.ativarBotao(mCtx, btnLogin);
				}
			}).start();
		} else {
			FuncoesGlobal.toastApp("Falha ao realizar operação! Verifique sua conexão.", mCtx);
			FuncoesGlobal.ativarBotao(mCtx, btnLogin);
		}
	}

	public void cadastrarUsuario(final String nome, final String cpf, final String email, final String telefone, final String senha, final String contra_senha, final String tipo_sangue, final int sexo, final String data_nasc, final LinearLayout bubbleProgress, final Toolbar toolbar) {
		if (FuncoesGlobal.verificaConexao(mCtx)) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						//INICIA O PROGRESS
						FuncoesGlobal.showCustomProgress(mCtx, null, toolbar, bubbleProgress, 1, 0, null);

						Map<String, String> Params = new HashMap<>();
						Params.put("metodo", 		"CadastrarUsuario");
						Params.put("nome", 			nome);
						Params.put("cpf", 			cpf);
						Params.put("email", 		email);
						Params.put("telefone", 		telefone);
						Params.put("senha", 		senha);
						Params.put("contra_senha", 	contra_senha);
						Params.put("tipo_sangue", 	tipo_sangue);
						Params.put("sexo", 			String.valueOf(sexo));
						Params.put("data_nasc", 	data_nasc);

						String JSON = FuncoesGlobal.executeHttptPostDataFile(Constantes.urlWebservice, Params, "", "", "");

						if (JSON == null || JSON.equals("")) {
							FuncoesGlobal.showCustomProgress(mCtx, null, toolbar, bubbleProgress, 0, 1, "Falha ao realizar operação! Serviço temporariamente indisponível.");
							return;
						}

						JSONObject objectJSON = new JSONObject(JSON);

						int valido = objectJSON.getInt("valido");
						String msg = objectJSON.getString("mensagem");

						if (valido == 1) {
							FuncoesGlobal.showCustomProgress(mCtx, null, toolbar, bubbleProgress, 0, 1, msg);

							Intent intent = new Intent(mCtx, LoginActivity.class); //ABRE A TELA DE LOGIN
							mCtx.startActivity(intent);
							((Activity) mCtx).finish();
						} else {
							FuncoesGlobal.showCustomProgress(mCtx, null, toolbar, bubbleProgress, 0, 1, msg);
						}

						System.gc(); //LIBERA ESPAÇO NA MEMORIA

					} catch (Exception e) {
						e.printStackTrace();
						System.gc(); //LIBERA ESPAÇO NA MEMORIA

						FuncoesGlobal.showCustomProgress(mCtx, null, toolbar, bubbleProgress, 0, 1, "Ops! Falha ao realizar operação."); //FINALIZA O PROGRESS
					}
				}
			}).start();
		} else {
			FuncoesGlobal.toastApp("Falha ao realizar operação! Verifique sua conexão.", mCtx);
		}
	}

	public void editarUsuario(final int codigo_usuario, final String nome, final String telefone, final String senha, final String contra_senha, final String tipo_sangue, final int sexo, final String data_nasc, final LinearLayout bubbleProgress, final Toolbar toolbar) {
		if (FuncoesGlobal.verificaConexao(mCtx)) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						//INICIA O PROGRESS
						FuncoesGlobal.showCustomProgress(mCtx, null, toolbar, bubbleProgress, 1, 0, null);

						Map<String, String> Params = new HashMap<>();
						Params.put("metodo", 			"EditarUsuario");
						Params.put("codigo_usuario", 	String.valueOf(codigo_usuario));
						Params.put("nome", 				nome);
						Params.put("telefone", 			telefone);
						Params.put("senha", 			senha);
						Params.put("contra_senha", 		contra_senha);
						Params.put("tipo_sangue", 		tipo_sangue);
						Params.put("sexo", 				String.valueOf(sexo));
						Params.put("data_nasc", 		data_nasc);

						String JSON = FuncoesGlobal.executeHttptPostDataFile(Constantes.urlWebservice, Params, "", "", "");

						if (JSON == null || JSON.equals("")) {
							FuncoesGlobal.showCustomProgress(mCtx, null, toolbar, bubbleProgress, 0, 1, "Falha ao realizar operação! Serviço temporariamente indisponível.");
							return;
						}

						JSONObject objectJSON = new JSONObject(JSON);

						int valido = objectJSON.getInt("valido");
						String msg = objectJSON.getString("mensagem");

						if (valido == 1) {
							Usuario usuario = new Usuario();
							usuario.setCodigoUsuario(Constantes.getUsuarioLogado().getCodigoUsuario());
							usuario.setCpf(Constantes.getUsuarioLogado().getCpf());
							usuario.setEmail(Constantes.getUsuarioLogado().getEmail());
							usuario.setNome(nome);
							usuario.setSenha(senha);
							usuario.setTelefone(telefone);
							usuario.setDataNascimento(data_nasc);
							usuario.setTipoSangue(tipo_sangue);
							usuario.setSexo(sexo);
							usuario.setPeso(0);
							usuario.setAltura(0);

							Constantes.setUsuarioLogado(usuario);

							FuncoesGlobal.showCustomProgress(mCtx, null, toolbar, bubbleProgress, 0, 1, msg);
						} else {
							FuncoesGlobal.showCustomProgress(mCtx, null, toolbar, bubbleProgress, 0, 1, msg);
						}

						System.gc(); //LIBERA ESPAÇO NA MEMORIA
					} catch (Exception e) {
						e.printStackTrace();
						System.gc(); //LIBERA ESPAÇO NA MEMORIA

						FuncoesGlobal.showCustomProgress(mCtx, null, toolbar, bubbleProgress, 0, 1, "Ops! Falha ao realizar operação."); //FINALIZA O PROGRESS
					}
				}
			}).start();
		} else {
			FuncoesGlobal.toastApp("Falha ao realizar operação! Verifique sua conexão.", mCtx);
		}
	}

	public void enviarMensagem(final int codigo_usuario, final String mensagem, final String telefone, final String email, final LinearLayout bubbleProgress, final Toolbar toolbar, final Button btnEnviar) {
		if (FuncoesGlobal.verificaConexao(mCtx)) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						//INICIA O PROGRESS
						FuncoesGlobal.showCustomProgress(mCtx, null, toolbar, bubbleProgress, 1, 0, null);

						Map<String, String> Params = new HashMap<>();
						Params.put("metodo", 			"EnviarMensagem");
						Params.put("codigo_usuario", 	String.valueOf(codigo_usuario));
						Params.put("mensagem", 			mensagem);
						Params.put("email", 			email);
						Params.put("telefone", 			telefone);

						String JSON = FuncoesGlobal.executeHttptPostDataFile(Constantes.urlWebservice, Params, "", "", "");

						if (JSON == null || JSON.equals("")) {
							FuncoesGlobal.showCustomProgress(mCtx, null, toolbar, bubbleProgress, 0, 1, "Falha ao realizar operação! Serviço temporariamente indisponível.");
							return;
						}

						JSONObject objectJSON = new JSONObject(JSON);

						int valido = objectJSON.getInt("valido");
						String msg = objectJSON.getString("mensagem");

						if (valido == 1) {
							if (btnEnviar != null) {
								((Activity)mCtx).runOnUiThread(new Runnable() {
									@Override
									public void run() {
										btnEnviar.setEnabled(false);
										btnEnviar.setVisibility(View.GONE);
									}
								});
							}

							FuncoesGlobal.showCustomProgress(mCtx, null, toolbar, bubbleProgress, 0, 1, msg);
						} else {
							FuncoesGlobal.showCustomProgress(mCtx, null, toolbar, bubbleProgress, 0, 1, msg);
						}

						System.gc(); //LIBERA ESPAÇO NA MEMORIA
					} catch (Exception e) {
						e.printStackTrace();
						System.gc(); //LIBERA ESPAÇO NA MEMORIA

						FuncoesGlobal.showCustomProgress(mCtx, null, toolbar, bubbleProgress, 0, 1, "Ops! Falha ao realizar operação."); //FINALIZA O PROGRESS
					}
				}
			}).start();
		} else {
			FuncoesGlobal.toastApp("Falha ao realizar operação! Verifique sua conexão.", mCtx);
		}
	}
}
