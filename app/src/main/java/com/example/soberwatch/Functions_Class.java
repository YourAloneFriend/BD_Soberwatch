package com.example.soberwatch;

import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

public class Functions_Class
{
    public static boolean isEmpty(EditText text)
    {
        CharSequence str = text.getText().toString().trim();
        return TextUtils.isEmpty(str);
    }

    public static boolean isEmail(EditText text)
    {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public static boolean isPassword(EditText text)
    {
        CharSequence password  = text.getText().toString();
        return (!TextUtils.isEmpty(password) && password.length() >= 8);
    }
}
