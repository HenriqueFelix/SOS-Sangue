package com.example.sossangue.model;

public class Hemocentro {
	private int codigo_hemocentro;
	private String razao_social;
	private String nome_fantasia;
	private String latitude;
	private String longitude;
	private String logradouro;
	private String bairro;
	private String cidade;
	private String estado;
	private String num_endereco;
	private String cep;
	private String telefone;
	private String email;
	private String site;

	public Hemocentro() { }

	/*
	@Override
	public String toString() {
		return getNomeFantasia();
	}
	*/

	public int getCodigoHemocentro() {
		return codigo_hemocentro;
	}

	public void setCodigoHemocentro(int codigo) {
		this.codigo_hemocentro = codigo;
	}

	public String getRazaoSocial()
	{
		return razao_social;
	}

	public void setRazaoSocial(String razao) {
		this.razao_social = razao;
	}

	public String getNomeFantasia() {
		return nome_fantasia;
	}

	public void setNomeFantasia(String fantasia) {
		this.nome_fantasia = fantasia;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro)
	{
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNumEndereco() {
		return num_endereco;
	}

	public void setNumEndereco(String num_endereco) {
		this.num_endereco = num_endereco;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}
}
