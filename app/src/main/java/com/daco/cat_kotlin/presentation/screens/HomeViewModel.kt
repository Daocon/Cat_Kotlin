package com.daco.cat_kotlin.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.daco.cat_kotlin.data.CatRepository
import com.daco.cat_kotlin.model.Cat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: CatRepository) : ViewModel() {
    private val _cats = MutableStateFlow<List<Cat>>(emptyList())
    val cats = _cats.asStateFlow()

    init {

        getCats()

    }

    private fun getCats() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _cats.value = repository.getCats()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}