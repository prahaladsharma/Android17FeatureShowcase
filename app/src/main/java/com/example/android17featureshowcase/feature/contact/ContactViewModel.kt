package com.example.android17featureshowcase.feature.contact

import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class ContactViewModel @Inject constructor() : ViewModel() {

    private val _contact = MutableStateFlow<Uri?>(null)
    val contact: StateFlow<Uri?> = _contact

    fun setContact(uri: Uri) {
        _contact.value = uri
    }
}