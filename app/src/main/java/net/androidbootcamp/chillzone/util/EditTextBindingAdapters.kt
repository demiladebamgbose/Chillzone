package net.androidbootcamp.chillzone.util

import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.BindingAdapter

@BindingAdapter("addTextChangedListener")
fun bindTextWatcher(editText: EditText, textWatcher: TextWatcher) {
    editText.addTextChangedListener(textWatcher)
}
