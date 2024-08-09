package com.arziman_off.getrandomjoke;


import android.text.InputFilter;
import android.text.Spanned;

import android.widget.TextView;

public class EditTextListener {

    public static InputFilter getEditTextFilter(TextView tv){
        return new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                try {
                    // Формируем строку, которая будет после ввода
                    String input = dest.subSequence(0, dstart).toString() + source.toString() + dest.subSequence(dend, dest.length()).toString();
                    int inputNumber = Integer.parseInt(input);

                    // Если число больше 451, возвращаем пустую строку (запрещаем ввод)
                    if (inputNumber > 451) {
                        tv.setTextColor(tv.getResources().getColor(R.color.warning_color));
                        return "";
                    } else {
                        tv.setTextColor(tv.getResources().getColor(R.color.inactive_rb_color));
                    }
                } catch (NumberFormatException e) {
                    // Если не удалось преобразовать строку в число, ничего не делаем
                }
                return null;
            }
        };
    }
}
