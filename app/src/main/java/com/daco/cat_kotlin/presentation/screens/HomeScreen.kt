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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.daco.cat_kotlin.data.ApiService
import com.daco.cat_kotlin.data.CatRepository
import com.daco.cat_kotlin.model.Cat
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel =
        viewModel(factory = HomeViewModelFactory(CatRepository(ApiService().catApi)))
    val cats by viewModel.cats.collectAsState(emptyList())
    var selectedCat by remember { mutableStateOf<Cat?>(null) }

    LazyColumn {
        items(cats.size) { index ->
            val cat = cats[index]
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, Color.Gray)
            ) {
                Column(
                    modifier = Modifier
                        .clickable { selectedCat = cat }
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "ID: " + cat.id,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    AsyncImage(
                        model = "https://cataas.com/cat/${cat.id}",
                        contentDescription = null
                    )
                }
            }
        }
    }

    if (selectedCat != null) {
        AlertDialog(
            onDismissRequest = { selectedCat = null },
            title = { Text(selectedCat!!.id) },
            text = {
                Column {
                    Text(selectedCat!!.tags.joinToString())
                    Spacer(modifier = Modifier.height(8.dp))
                    AsyncImage(
                        model = "https://cataas.com/cat/${selectedCat!!.id}",
                        contentDescription = null
                    )
                }
            },
            confirmButton = {
                Button(onClick = { selectedCat = null }) {
                    Text("OK")
                }
            }
        )
    }
}