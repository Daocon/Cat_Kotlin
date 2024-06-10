package com.daco.cat_kotlin.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.daco.cat_kotlin.model.Cat

@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = viewModel()
    val catsState = viewModel.cats.observeAsState(emptyList())
    val cats = catsState.value

    var selectedCat by remember { mutableStateOf<Cat?>(null) }

    LazyColumn {
    items(cats.size) { index ->
        val cat = cats[index]
        Column(modifier = Modifier.clickable { selectedCat = cat }) {
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