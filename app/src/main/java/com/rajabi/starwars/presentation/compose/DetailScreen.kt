package com.rajabi.starwars.presentation.compose

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rajabi.starwars.presentation.viewmodel.detail.DetailAction
import com.rajabi.starwars.presentation.viewmodel.detail.DetailEvent
import com.rajabi.starwars.presentation.viewmodel.detail.DetailUiState
import com.rajabi.starwars.presentation.viewmodel.detail.DetailViewModel

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
        is DetailUiState.Idle -> {} // No UI shown for idle
        is DetailUiState.Success -> {
            val details = state.details
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Name: ${details.name}", style = MaterialTheme.typography.headlineMedium)
                Text("Birth Year: ${details.birthYear}")
                Text("Height: ${details.height} cm")
                Text("Name(species): ${details.species} ")

                Text("Films:", style = MaterialTheme.typography.headlineSmall)
                details.films.forEach { filmUrl ->
                    Text("- $filmUrl")
                }

            }
        }

        is DetailUiState.Error -> {

                Text(
                    text = "Error: ${state.message}",
                    color = MaterialTheme.colorScheme.error,
//                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        DetailUiState.Loading -> {
            CircularProgressIndicator(
//                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
    }
}
}
