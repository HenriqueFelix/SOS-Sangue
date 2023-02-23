package com.example.sossangue.model;

public class Doacao {
	private int codigo_doacao;
	private String data_doacao;
	private int status_doacao;
	private int flag_tipo_doacao;
	private Usuario usuarioDoador;
	private Hemocentro hemocentro;
	private int status_solicitacao;
	private Usuario usuarioSolicitante;
	private String data_hora_cadastro;
	private String motivo_solicitacao;

	public Doacao() { }

	public int getCodigoDoacao() {
		return codigo_doacao;
	}

	public void setCodigoDoacao(int codigo) {
		this.codigo_doacao = codigo;
	}

	public String getDataDoacao() {
		return data_doacao;
	}

	public void setDataDoacao(String data) {
		this.data_doacao = data;
	}

	public Usuario getUsuarioDoador() {
		return usuarioDoador;
	}

	public void setUsuarioDoador(Usuario usuarioDoador) {
		this.usuarioDoador = usuarioDoador;
	}

	public Hemocentro getHemocentro() {
		return hemocentro;
	}

	public void setHemocentro(Hemocentro hemocentro) {
		this.hemocentro = hemocentro;
	}

	public int getStatusDoacao() {
		return status_doacao;
	}

	public void setStatusDoacao(int status_doacao) {
		this.status_doacao = status_doacao;
	}

	public int getFlagTipoDoacao() {
		return flag_tipo_doacao;
	}

	public void setFlagTipoDoacao(int flag_tipo) {
		this.flag_tipo_doacao = flag_tipo;
	}

	public int getStatusSolicitacao() {
		return status_solicitacao;
	}

	public void setStatusSolicitacao(int status_solicitacao) {
		this.status_solicitacao = status_solicitacao;
	}

	public Usuario getUsuarioSolicitante() {
		return usuarioSolicitante;
	}

	public void setUsuarioSolicitante(Usuario usuarioSolicitante) {
		this.usuarioSolicitante = usuarioSolicitante;
	}

	public String getDataHoraCadastro() {
		return data_hora_cadastro;
	}

	public void setDataHoraCadastro(String data_hora_cadastro) {
		this.data_hora_cadastro = data_hora_cadastro;
	}

	public String getMotivoSolicitacao() {
		return motivo_solicitacao;
	}

	public void setMotivoSolicitacao(String obs) {
		this.motivo_solicitacao = obs;
	}
}
