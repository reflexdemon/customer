package com.example.vprasanna.mycustomerapp.utilities;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by vprasanna on 5/10/17.
 */

public class TextUtils {

    /**
     * http://stackoverflow.com/questions/6384004/make-edittext-readonly
     * <p>
     * to make an EditText read-only, just put it as:
     * <code>TextWatcher tw = setReadOnly(editText, true, null);</code>
     * to make it normal use tw from previous statement:
     * <code>setReadOnly(editText, false, tw);</code>
     *
     * @param edt           The EditText that needs to be acted upon
     * @param readOnlyState true; if you want it to be readonly;false to make it editable
     * @param remove        the reference to the previously disabled editText
     * @return reference to the TextWatcher that can be used later to disable
     */
    public static TextWatcher setReadOnly(final EditText edt, final boolean readOnlyState, TextWatcher remove) {
        edt.setCursorVisible(!readOnlyState);
        TextWatcher tw = null;
        final String text = edt.getText().toString();
        if (readOnlyState) {
            tw = new TextWatcher() {

                @Override
                public void afterTextChanged(Editable s) {

                }

                @Override
                //saving the text before change
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                // and replace it with content if it is about to change
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    edt.removeTextChangedListener(this);
                    edt.setText(text);
                    edt.addTextChangedListener(this);
                }
            };
            edt.addTextChangedListener(tw);
            return tw;
        } else {
            edt.removeTextChangedListener(remove);
            return remove;
        }
    }
}