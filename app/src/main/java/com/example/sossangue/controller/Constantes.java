package com.example.sossangue.controller;

import com.example.sossangue.model.Hemocentro;
import com.example.sossangue.model.Usuario;

import java.util.ArrayList;

public class Constantes
{
	static String urlWebservice = "https://sanguesos.000webhostapp.com/webservice/mobile.php";

	private static Usuario usuarioLogado;

	private static String ultimaLatitude = "";
	private static String ultimaLongitude = "";

	private static ArrayList<Hemocentro> listHemocentros;

	public static ArrayList<String> getListTiposSangues() {
		ArrayList<String> arrTiposSangues = new ArrayList<>();

		arrTiposSangues.add("A+");
		arrTiposSangues.add("A-");
		arrTiposSangues.add("B+");
		arrTiposSangues.add("B-");
		arrTiposSangues.add("AB+");
		arrTiposSangues.add("AB-");
		arrTiposSangues.add("O+");
		arrTiposSangues.add("O-");

		return arrTiposSangues;
	}

	public static ArrayList<String> getListSexos() {
		ArrayList<String> arrSexos = new ArrayList<>();
		arrSexos.add("Masculino");
		arrSexos.add("Feminino");
		arrSexos.add("Outro");

		return arrSexos;
	}

	public static Usuario getUsuarioLogado()
	{
		return usuarioLogado;
	}

	static void setUsuarioLogado(Usuario usuarioLogado)
	{
		Constantes.usuarioLogado = usuarioLogado;
	}

	static String getUltimaLatitude() {
		return ultimaLatitude;
	}

	static void setUltimaLatitude(String ultimaLatitude) {
		Constantes.ultimaLatitude = ultimaLatitude;
	}

	static String getUltimaLongitude() {
		return ultimaLongitude;
	}

	static void setUltimaLongitude(String ultimaLongitude) {
		Constantes.ultimaLongitude = ultimaLongitude;
	}

	public static ArrayList<Hemocentro> getListHemocentros() {
		return listHemocentros;
	}

	static void setListHemocentros(ArrayList<Hemocentro> listHemocentros) {
		Constantes.listHemocentros = listHemocentros;
	}
}
