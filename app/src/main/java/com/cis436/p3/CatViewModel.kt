package com.cis436.p3

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CatViewModel : ViewModel() {

    private val _selectedCatName = MutableLiveData<String>()
    val selectedCatName: LiveData<String> get() = _selectedCatName

    private val _selectedCatTemperament = MutableLiveData<String>()
    val selectedCatTemperament: LiveData<String> get() = _selectedCatTemperament

    private val _selectedCatOrigin = MutableLiveData<String>()
    val selectedCatOrigin: LiveData<String> get() = _selectedCatOrigin

    private val _selectedCatReferenceImageId = MutableLiveData<String>()
    val selectedCatReferenceImageId: LiveData<String> get() = _selectedCatReferenceImageId

    private val _selectedCatImageUrl = MutableLiveData<String>()
    val selectedCatImageUrl: LiveData<String> get() = _selectedCatImageUrl

    fun setSelectedCatInfo(name: String, temperament: String, origin: String, referenceImageId: String, imageUrl: String ) {
        _selectedCatName.value = name
        _selectedCatTemperament.value = temperament
        _selectedCatOrigin.value = origin
        _selectedCatReferenceImageId.value = referenceImageId
        _selectedCatImageUrl.value = imageUrl
    }
}