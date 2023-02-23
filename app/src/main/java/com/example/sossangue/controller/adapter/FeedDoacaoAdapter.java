package com.example.sossangue.controller.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sossangue.R;
import com.example.sossangue.controller.Constantes;
import com.example.sossangue.model.Doacao;
import com.example.sossangue.model.Hemocentro;
import com.example.sossangue.model.Usuario;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

public class FeedDoacaoAdapter extends RecyclerView.Adapter<FeedDoacaoAdapter.ViewHolder> {
	private Context mCtx;
	private List<Doacao> doacaosList;
	private LinearLayout bubbleProgress;
	private DrawerLayout drawerLayout;
	private Toolbar toolbar;

	public FeedDoacaoAdapter(List<Doacao> listDoacao, Context ctx, LinearLayout bubbleProgress, DrawerLayout drawerLayout, Toolbar toolbar) {
		this.doacaosList 	= listDoacao;
		this.mCtx 			= ctx;
		this.bubbleProgress = bubbleProgress;
		this.drawerLayout 	= drawerLayout;
		this.toolbar 		= toolbar;
	}

	@NonNull
	@Override
	public FeedDoacaoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_feed_doacao, parent,false);

		return new ViewHolder(itemView);
	}

	@SuppressLint("SetTextI18n")
	@Override
	public void onBindViewHolder(@NonNull final FeedDoacaoAdapter.ViewHolder holder, final int position) {
		if (doacaosList.get(position).getFlagTipoDoacao() == 1) {
			Usuario usuarioSolicitante = doacaosList.get(position).getUsuarioSolicitante();

			holder.lblNome.setText(usuarioSolicitante.getNome().toUpperCase());

			holder.lblData.setText(doacaosList.get(position).getDataHoraCadastro());

			Hemocentro hemocentroDoacao = doacaosList.get(position).getHemocentro();
			holder.lblHemocentro.setText(hemocentroDoacao.getNomeFantasia().toUpperCase());

			holder.lblMotivo.setText(doacaosList.get(position).getMotivoSolicitacao());

			holder.lblTipoSangue.setText(usuarioSolicitante.getTipoSangue());

			holder.btnDoar.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					ModalDoarSangue modalDoarSangue = new ModalDoarSangue(mCtx, bubbleProgress, drawerLayout, toolbar, doacaosList.get(position));
					modalDoarSangue.openModal();
				}
			});

			if (usuarioSolicitante.getCodigoUsuario() == Constantes.getUsuarioLogado().getCodigoUsuario()) { //SE O USUARIO SOLICITANTE FOR O USUARIO LOGADO, OCULTA O BOTAO DE DOACAO
				holder.btnDoar.setVisibility(View.GONE);
			}
		}
	}

	@Override
	public int getItemCount() {
		return doacaosList.size();
	}

	static class ViewHolder extends  RecyclerView.ViewHolder {
		TextView lblNome, lblData, lblHemocentro, lblMotivo, lblTipoSangue;
		Button btnDoar;

		private ViewHolder(View itemView) {
			super(itemView);
			lblNome 		= itemView.findViewById(R.id.lblNome);
			lblData 		= itemView.findViewById(R.id.lblData);
			lblHemocentro 	= itemView.findViewById(R.id.lblHemocentro);
			lblMotivo 		= itemView.findViewById(R.id.lblMotivo);
			lblTipoSangue 	= itemView.findViewById(R.id.lblTipoSangue);
			btnDoar 		= itemView.findViewById(R.id.btnDoar);
		}
	}
}
