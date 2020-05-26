package com.example.sossangue.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.sossangue.R;
import com.example.sossangue.model.Hemocentro;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

public class FuncoesGlobal {
	static boolean verificaConexao(Context ctx) {
		boolean conectado = false;

		try {
			ConnectivityManager conmag;
			conmag = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
			conmag.getActiveNetworkInfo();

			if(conmag.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()) { //Verifica o WIFI
				conectado = true;
			} else if(conmag.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected()) {//Verifica o 3G
				conectado = true;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

		return conectado;
	}

	static String executeHttptPostDataFile(String urlTo, Map<String, String> parmas, String filepath, String filefield, String fileMimeType) {
		boolean enviarFile = false;
		if (!filepath.trim().equals(""))
		{
			enviarFile = true;
		}

		HttpURLConnection connection;
		DataOutputStream outputStream;
		InputStream inputStream;

		String twoHyphens = "--";
		String boundary = "**" + Long.toString(System.currentTimeMillis()) + "**";
		String lineEnd = "\r\n";

		String result = "";

		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1 * 1024 * 1024;

		String[] q = new String[1];
		int idx = 0;
		if (enviarFile) {
			q = filepath.split("/");
			idx = q.length - 1;
		}

		try {
			FileInputStream fileInputStream = null;
			File file = new File(filepath);
			if (file.exists()) {
				fileInputStream = new FileInputStream(file);
			}

			URL url = new URL(urlTo.toLowerCase());
			connection = (HttpURLConnection) url.openConnection();

			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);

			connection.setRequestMethod("POST");
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setRequestProperty("User-Agent", "Android Multipart HTTP Client 1.0");
			connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

			outputStream = new DataOutputStream(connection.getOutputStream());
			outputStream.writeBytes(twoHyphens + boundary + lineEnd);

			if (enviarFile) {
				outputStream.writeBytes("Content-Disposition: form-data; name=\"" + filefield + "\"; filename=\"" + q[idx] + "\"" + lineEnd);
				outputStream.writeBytes("Content-Type: " + fileMimeType + lineEnd);
				outputStream.writeBytes("Content-Transfer-Encoding: binary" + lineEnd);

				outputStream.writeBytes(lineEnd);
			}

			if (enviarFile) {
				if (file.exists()) {
					bytesAvailable = fileInputStream.available();
					bufferSize = Math.min(bytesAvailable, maxBufferSize);
					buffer = new byte[bufferSize];

					bytesRead = fileInputStream.read(buffer, 0, bufferSize);
					while (bytesRead > 0) {
						outputStream.write(buffer, 0, bufferSize);
						bytesAvailable = fileInputStream.available();
						bufferSize = Math.min(bytesAvailable, maxBufferSize);
						bytesRead = fileInputStream.read(buffer, 0, bufferSize);
					}
				}
				outputStream.writeBytes(lineEnd);
			}

			// Upload POST Data
			Iterator<String> keys = parmas.keySet().iterator();
			while (keys.hasNext()) {
				String key = keys.next();
				String value = parmas.get(key);

				outputStream.writeBytes(twoHyphens + boundary + lineEnd);
				outputStream.writeBytes("Content-Disposition: form-data; name=\"" + key + "\"" + lineEnd);
				outputStream.writeBytes("Content-Type: text/plain" + lineEnd);
				outputStream.writeBytes(lineEnd);
				outputStream.writeBytes(value);
				outputStream.writeBytes(lineEnd);
			}

			outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

			inputStream = connection.getInputStream();

			result = convertStreamToString(inputStream);

			if (enviarFile) {
				if (file.exists()) {
					fileInputStream.close();
				}
			}
			inputStream.close();
			outputStream.flush();
			outputStream.close();

			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	private static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return sb.toString();
	}

	public static void toastApp(final String msg, final Context ctx) {
		((Activity) ctx).runOnUiThread(new Runnable() {
			public void run() {
				try {
					Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	static void showCustomProgress(final Context ctx, final DrawerLayout drawerLayout, final Toolbar toolbar, final LinearLayout progressArea, final int flag_visivel, final int flag_msg, final String msg) {
		((Activity) ctx).runOnUiThread(new Runnable() {
			public void run() {
				try {
					if (progressArea != null) {
						if (flag_visivel == 1) {
							if (drawerLayout != null) {
								drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
							}

							if (toolbar != null) {
								toolbar.setVisibility(View.GONE);
								//getSupportActionBar().setDisplayHomeAsUpEnabled(false);
								//getSupportActionBar().setDisplayShowHomeEnabled(false);
							}

							progressArea.setVisibility(View.VISIBLE);
							progressArea.setClickable(true);
							((Activity) ctx).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
						} else {
							progressArea.setVisibility(View.GONE);
							progressArea.setClickable(false);

							if (flag_msg == 1 && msg != null && !msg.trim().equals("")) {
								toastApp(msg, ctx);
							}

							if (drawerLayout != null) {
								drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
							}

							if (toolbar != null) {
								toolbar.setVisibility(View.VISIBLE);
								//getSupportActionBar().setDisplayHomeAsUpEnabled(true);
								//getSupportActionBar().setDisplayShowHomeEnabled(true);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private static String getDataAtual(String formato) {
		//Data = "dd/MM/yyyy"
		//DataHora = "dd/MM/yyyy HH:mm:ss"

		@SuppressLint("SimpleDateFormat")
		SimpleDateFormat dateFormat = new SimpleDateFormat(formato);

		Date data = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		Date data_atual = cal.getTime();

		return dateFormat.format(data_atual);
	}

	public static boolean validarData(String strData, int flag_nascimento) {
		if (strData != null && !strData.trim().equals("") && strData.trim().length() == 10) {
			String[] arrDt = strData.split("/");

			if (arrDt.length != 3) {
				return false;
			}

			String strDia = arrDt[0];
			String strMes = arrDt[1];
			String strAno = arrDt[2];

			if (Integer.parseInt(strDia) > 31 || Integer.parseInt(strDia) <= 0) {
				return false;
			} else if (Integer.parseInt(strMes) > 12 || Integer.parseInt(strMes) <= 0) {
				return false;
			} else if (Integer.parseInt(strAno) <= 0) {
				return false;
			}

			if (flag_nascimento == 1) {
				String diaAtual = getDataAtual("dd");
				String mesAtual = getDataAtual("MM");
				String anoAtual = getDataAtual("yyyy");

				if (Integer.parseInt(strAno) > Integer.parseInt(anoAtual)) {
					return false;
				} else if (Integer.parseInt(strAno) == Integer.parseInt(anoAtual)) {
					if (Integer.parseInt(strMes) > Integer.parseInt(mesAtual)) {
						return false;
					} else if (Integer.parseInt(strMes) == Integer.parseInt(mesAtual)) {
						if (Integer.parseInt(strDia) > Integer.parseInt(diaAtual)) {
							return false;
						}
					}
				}
			}

			return true;
		}

		return false;
	}

	public static Hemocentro hemocentroSelecionado;
	public static void setupAutoCompleteHemocentro(final AutoCompleteTextView viewAucHemocentro, final List<Hemocentro> objects, Context ctx) {
		//ArrayAdapter<Hemocentro> adapter = new ArrayAdapter<>(ctx, android.R.layout.simple_dropdown_item_1line, objects);
		//viewAucHemocentro.setAdapter(adapter);

		hemocentroSelecionado = null;

		AutoCompleteHemocentroAdapter customerAdapter = new AutoCompleteHemocentroAdapter(ctx, R.layout.customer_auto, objects);
		viewAucHemocentro.setAdapter(customerAdapter);

		viewAucHemocentro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				try {
					Hemocentro selectedObject = (Hemocentro) parent.getAdapter().getItem(position);
					//System.out.println(selectedObject);
					hemocentroSelecionado = selectedObject;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
}
