package com.daco.cat_kotlin.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.daco.cat_kotlin.data.CatRepository

class HomeViewModel( private val repository: CatRepository): ViewModel() {
    val cats = liveData {
        emit(repository.getCats())
    }
}