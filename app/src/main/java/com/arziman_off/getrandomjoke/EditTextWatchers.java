package com.arziman_off.getrandomjoke;


import android.text.Editable;
import android.text.TextWatcher;

import android.widget.TextView;

public class EditTextWatchers {

    public static TextWatcher getEtTextWatcher(TextView tv) {
        return new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // Проверяем введенное значение
                String input = s.toString();
                Integer inputNumber = null;
                try {
                    inputNumber = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    // Обработка ошибки при преобразовании строки в число
                }
                if (inputNumber != null && inputNumber > 451) {
                    // Если число больше 451, показываем предупреждение
                    tv.setTextColor(tv.getResources().getColor(R.color.warning_color));
                } else {
                    // Если число меньше или равно 451, скрываем предупреждение
                    tv.setTextColor(tv.getResources().getColor(R.color.inactive_rb_color));
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        };
    }

    public static TextWatcher getEtNeedJokesCntInputTextWatcher(TextView tv) {
        return new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // Проверяем введенное значение
                String input = s.toString();
                Integer inputNumber = null;
                try {
                    inputNumber = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    // Обработка ошибки при преобразовании строки в число
                }
                if (inputNumber != null && inputNumber > 451) {
                    // Если число больше 451, показываем предупреждение
                    tv.setTextColor(tv.getResources().getColor(R.color.warning_color));
                } else {
                    // Если число меньше или равно 451, скрываем предупреждение
                    tv.setTextColor(tv.getResources().getColor(R.color.inactive_rb_color));
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        };
    }
}
