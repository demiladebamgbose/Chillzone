package net.androidbootcamp.chillzone.ui.gallery

import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GalleryViewModel : ViewModel(), Observable {
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    val text = MutableLiveData<String>().apply {
        value = "This is gallery Fragment"
    }

    @Bindable
    fun getTextWatcher() : TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(var1: CharSequence, var2: Int, var3: Int, var4: Int){

            }

            override fun onTextChanged(var1: CharSequence, var2: Int, var3: Int, var4: Int) {
                text.value = var1.toString()
            }

            override fun afterTextChanged(var1: Editable) {

            }
        }
    }

}