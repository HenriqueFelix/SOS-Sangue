package com.example.sossangue.controller.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sossangue.R;
import com.example.sossangue.model.Doacao;
import com.example.sossangue.model.Hemocentro;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class DoacaoAdapter extends RecyclerView.Adapter<DoacaoAdapter.ViewHolder> {

	private List<Doacao> doacaosList;

	public DoacaoAdapter(List<Doacao> listDoacao) {
		this.doacaosList = listDoacao;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_historico_doacao, parent,false);

		return new ViewHolder(itemView);
	}

	@SuppressLint("SetTextI18n")
	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		if (doacaosList.get(position).getFlagTipoDoacao() == 0) {
			holder.lblTipo.setText("Doação");
			holder.lblData.setText(doacaosList.get(position).getDataDoacao());
			holder.lblTipoSangue.setText(doacaosList.get(position).getUsuarioDoador().getTipoSangue());
		} else {
			holder.lblTipo.setText("Solicitação");
			holder.lblData.setText(doacaosList.get(position).getDataHoraCadastro());
			holder.lblTipoSangue.setText(doacaosList.get(position).getUsuarioSolicitante().getTipoSangue());
		}

		Hemocentro hemocentroDoacao = doacaosList.get(position).getHemocentro();

		holder.lblHemocentro.setText(hemocentroDoacao.getNomeFantasia());
	}

	@Override
	public int getItemCount() {
		return doacaosList.size();
	}

	class ViewHolder extends  RecyclerView.ViewHolder {
		TextView lblTipo, lblData, lblHemocentro, lblTipoSangue;

		private ViewHolder(View itemView) {
			super(itemView);
			lblTipo 		= itemView.findViewById(R.id.lblTipo);
			lblData 		= itemView.findViewById(R.id.lblData);
			lblHemocentro 	= itemView.findViewById(R.id.lblHemocentro);
			lblTipoSangue 	= itemView.findViewById(R.id.lblTipoSangue);
		}
	}
}