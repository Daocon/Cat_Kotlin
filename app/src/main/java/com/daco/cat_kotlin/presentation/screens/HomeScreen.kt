package com.daco.cat_kotlin.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.daco.cat_kotlin.data.ApiService
import com.daco.cat_kotlin.data.CatRepository
import com.daco.cat_kotlin.model.Cat

@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory(CatRepository(ApiService().catApi)))

    val cats by viewModel.cats.collectAsState(emptyList())

    var selectedCat by remember { mutableStateOf<Cat?>(null) }

    LazyColumn {
    items(cats.size) { index ->
        val cat = cats[index]
        Column(modifier = Modifier.clickable { selectedCat = cat }) {
            // In HomeScreen.kt
            Text(text = cat.id)
            Text(text = cat.tags.joinToString())
            AsyncImage(
                model = "https://cataas.com/cat/${cat.id}",
                contentDescription = null
            )
        }
    }
}

    if (selectedCat != null) {
        AlertDialog(
            onDismissRequest = { selectedCat = null },
            title = { Text(selectedCat!!.id) },
            text = { Text(selectedCat!!.tags.joinToString()) },
            confirmButton = {
                Button(onClick = { selectedCat = null }) {
                    Text("OK")
                }
            }
        )
    }
}

class HomeViewModelFactory(private val repository: CatRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}