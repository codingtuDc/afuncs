package cn.yuanye1818.autils.core.view.textwatcher;

import android.text.Editable;
import android.text.TextWatcher;

public abstract class OnTextChange implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
