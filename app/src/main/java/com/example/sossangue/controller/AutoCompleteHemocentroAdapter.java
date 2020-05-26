package com.example.sossangue.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.sossangue.R;
import com.example.sossangue.model.Hemocentro;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteHemocentroAdapter extends ArrayAdapter<Hemocentro> {
	private LayoutInflater layoutInflater;
	private List<Hemocentro> mHemocentros;

	private Filter mFilter = new Filter() {
		@Override
		public String convertResultToString(Object resultValue) {
			return ((Hemocentro)resultValue).getNomeFantasia();
		}

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults results = new FilterResults();

			if (constraint != null) {
				ArrayList<Hemocentro> suggestions = new ArrayList<>();
				for (Hemocentro customer : mHemocentros) {
					// Note: change the "contains" to "startsWith" if you only want starting matches
					if (customer.getNomeFantasia().toLowerCase().contains(constraint.toString().toLowerCase())) {
						suggestions.add(customer);
					} else if (customer.getRazaoSocial().toLowerCase().contains(constraint.toString().toLowerCase())) {
						suggestions.add(customer);
					}
				}

				results.values = suggestions;
				results.count = suggestions.size();
			}

			return results;
		}

		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			clear();
			if (results != null && results.count > 0) {
				// we have filtered results
				addAll((ArrayList<Hemocentro>) results.values);
			} else {
				// no filter, add entire original list back in
				addAll(mHemocentros);
			}
			notifyDataSetChanged();
		}
	};

	public AutoCompleteHemocentroAdapter(Context context, int textViewResourceId, List<Hemocentro> customers) {
		super(context, textViewResourceId, customers);
		// copy all the customers into a master list
		mHemocentros = new ArrayList<>(customers.size());
		mHemocentros.addAll(customers);
		layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;

		if (view == null) {
			view = layoutInflater.inflate(R.layout.customer_auto, null);
		}

		Hemocentro customer = getItem(position);

		TextView name = view.findViewById(R.id.customerNameLabel);
		name.setText(customer.getNomeFantasia());

		return view;
	}

	@Override
	public Filter getFilter() {
		return mFilter;
	}
}