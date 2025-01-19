package com.rajabi.starwars.presentation.compose.detail

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DetailScreen(
    id: String,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Handle events like showing a toast for errors
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is DetailEvent.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Fetch details when the screen is launched
    LaunchedEffect(id) {
        viewModel.processAction(DetailAction.LoadCharacterDetails(id))
    }

    // Render the UI based on the state
    when (val state = uiState) {
        is DetailUiState.Idle -> {}
        is DetailUiState.Success -> {
            val details = state.details
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()) // Make the content scrollable
            ) {
                Spacer(modifier = Modifier.height(58.dp))
                Text("Name: ${details.name}", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Birth Year: ${details.birthYear}")
                Text("Height: ${details.height} cm")
                Text("Species Name: ${details.nameSpecies}")
                Text("Species Language: ${details.language}")
                Text("Home World: ${details.homeWorld}")
                Text("Species Population: ${details.population}")

                Spacer(modifier = Modifier.height(16.dp))
                Text("Opening Crawl:", style = MaterialTheme.typography.headlineSmall)

                Spacer(modifier = Modifier.height(16.dp))
                Text("Films:", style = MaterialTheme.typography.headlineSmall)

                details.openingCrawl.forEach{
                    Text("- $it")

                }
                details.filmsTitle.forEach { title ->
                    Text("- $title")
                }
            }
        }

        is DetailUiState.Error -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            ) {
                Text(
                    text = "Error: ${state.message}",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        DetailUiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
