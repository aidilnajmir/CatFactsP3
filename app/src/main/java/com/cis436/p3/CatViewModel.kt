package com.cis436.p3

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CatViewModel : ViewModel() {

    // LiveData to store selected cat name
    private val _selectedCatName = MutableLiveData<String>()
    val selectedCatName: LiveData<String> get() = _selectedCatName

    // LiveData to store selected cat temperament
    private val _selectedCatTemperament = MutableLiveData<String>()
    val selectedCatTemperament: LiveData<String> get() = _selectedCatTemperament

    // LiveData to store selected cat origin
    private val _selectedCatOrigin = MutableLiveData<String>()
    val selectedCatOrigin: LiveData<String> get() = _selectedCatOrigin

    // LiveData to store selected cat reference image id
    private val _selectedCatReferenceImageId = MutableLiveData<String>()
    val selectedCatReferenceImageId: LiveData<String> get() = _selectedCatReferenceImageId

    // LiveData to store selected cat image URL
    private val _selectedCatImageUrl = MutableLiveData<String>()
    val selectedCatImageUrl: LiveData<String> get() = _selectedCatImageUrl

    // Function to set the selected cat details
    fun setSelectedCatInfo(name: String, temperament: String, origin: String, referenceImageId: String, imageUrl: String ) {
        // Update LiveData with the provided cat details
        _selectedCatName.value = name
        _selectedCatTemperament.value = temperament
        _selectedCatOrigin.value = origin
        _selectedCatReferenceImageId.value = referenceImageId
        _selectedCatImageUrl.value = imageUrl
    }
}
