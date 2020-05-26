package com.example.sossangue.model;

import com.example.sossangue.controller.Constantes;

public class Usuario {
	private int codigo_usuario;
	private String nome;
	private String cpf;
	private String email;
	private String senha;
	private String telefone;
	private String data_nascimento;
	private String tipo_sangue;
	private int sexo;
	private double peso;
	private double altura;

	public Usuario() { }

	public int getCodigoUsuario() {
		return codigo_usuario;
	}

	public void setCodigoUsuario(int codigo) {
		this.codigo_usuario = codigo;
	}

	public String getNome() {
		return nome;
	}

	public String getPrimeiroNome() {
		if (!nome.equals("")) {
			String[] arrNomeUsu = nome.split(" ");
			if (arrNomeUsu.length > 0) {
				if (!arrNomeUsu[0].trim().equals("")) {
					return arrNomeUsu[0];
				}
			}
		}

		return "";
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getDataNascimento() {
		return data_nascimento;
	}

	public void setDataNascimento(String data) {
		this.data_nascimento = data;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public int getSexo() {
		return sexo;
	}

	public void setSexo(int sexo) {
		this.sexo = sexo;
	}

	public String getTipoSangue() {
		return tipo_sangue;
	}

	public void setTipoSangue(String sangue) {
		this.tipo_sangue = sangue;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public double getAltura() {
		return altura;
	}

	public void setAltura(double altura) {
		this.altura = altura;
	}
}
