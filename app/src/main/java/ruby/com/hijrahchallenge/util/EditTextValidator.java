package ruby.com.hijrahchallenge.util;

import android.text.TextUtils;

public class EditTextValidator {

    public final static boolean isValidEmail(CharSequence text){
            if (TextUtils.isEmpty(text)) {
                return false;
            } else {
                return android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches();
            }


    }

    public final static boolean isValid(CharSequence text) {
        if (TextUtils.isEmpty(text)) {
            return false;
        }
        return true;
    }

    public final static boolean isValidPassword(CharSequence text) {
        if (TextUtils.isEmpty(text)) {
            return false;
        }
        if(text.length() < 7){
            return false;
        }
        return true;
    }



    }
