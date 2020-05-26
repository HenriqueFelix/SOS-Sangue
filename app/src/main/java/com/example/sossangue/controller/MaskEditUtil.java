package com.example.sossangue.controller;


import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

//EditText input; input.addTextChangedListener(MaskEditUtil.mask(input, MaskEditUtil.FORMAT_PERCENT));

public abstract class MaskEditUtil {

	public static final String FORMAT_CPF = "###.###.###-##";
	public static final String FORMAT_FONE = "(##)#####-####";
	public static final String FORMAT_CEP = "#####-###";
	public static final String FORMAT_DATE = "##/##/####";
	public static final String FORMAT_DATE_HOUR = "##/##/#### ##:##:##";
	public static final String FORMAT_HOUR = "##:##:##";
	public static final String FORMAT_NUMBER_2 = "##";
	public static final String FORMAT_NUMBER_3 = "###";
	public static final String FORMAT_NUMBER_4 = "####";
	public static final String FORMAT_MOEDA_3 = "#,##";
	public static final String FORMAT_MOEDA_4 = "##,##";
	public static final String FORMAT_MOEDA_5 = "###,##";
	public static final String FORMAT_MOEDA_6 = "#.###,##";
	public static final String FORMAT_MOEDA_7 = "##.###,##";
	public static final String FORMAT_MOEDA_8 = "###.###,##";
	public static final String FORMAT_MOEDA_9 = "####.###,##";
	public static final String FORMAT_INT_1 = "#";
	public static final String FORMAT_INT_2 = "##";
	public static final String FORMAT_INT_3 = "###";
	public static final String FORMAT_INT_4 = "####";
	public static final String FORMAT_INT_5 = "#####";
	public static final String FORMAT_INT_6 = "######";
	public static final String FORMAT_INT_7 = "#######";
	public static final String FORMAT_INT_8 = "########";
	public static final String FORMAT_INT_9 = "#########";

	/**
	 * Método que deve ser chamado para realizar a formatação
	 *
	 * @param ediTxt
	 * @param mask
	 * @return
	 */
	public static TextWatcher mask(final EditText ediTxt, final String mask) {
		return new TextWatcher() {
			boolean isUpdating;
			String old = "";

			@Override
			public void afterTextChanged(final Editable s) {}

			@Override
			public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {}

			@Override
			public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
				final String str = MaskEditUtil.unmask(s.toString());
				String mascara = "";
				if (isUpdating) {
					old = str;
					isUpdating = false;
					return;
				}
				int i = 0;
				for (final char m : mask.toCharArray()) {
					if (m != '#' && str.length() > old.length()) {
						mascara += m;
						continue;
					}
					try {
						mascara += str.charAt(i);
					} catch (final Exception e) {
						break;
					}
					i++;
				}
				isUpdating = true;
				ediTxt.setText(mascara);
				ediTxt.setSelection(mascara.length());
			}
		};
	}

	public static String unmask(final String s) {
		return s.replaceAll("[.]", "").replaceAll("[-]", "").replaceAll("[/]", "").replaceAll("[(]", "").replaceAll("[ ]","").replaceAll("[:]", "").replaceAll("[)]", "").replaceAll("[,]", "");
	}
}
