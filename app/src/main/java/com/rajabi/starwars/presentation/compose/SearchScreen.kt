import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.rajabi.starwars.data.model.Character
import com.rajabi.starwars.presentation.viewmodel.search.SearchUiAction
import com.rajabi.starwars.presentation.viewmodel.search.SearchUiEvent
import com.rajabi.starwars.presentation.viewmodel.search.SearchUiState
import com.rajabi.starwars.presentation.viewmodel.search.SearchViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun SearchScreen(navController: NavHostController, viewModel: SearchViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            if (event is SearchUiEvent.ShowToast) {
                Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    var query by remember { mutableStateOf("") }
    Spacer(modifier = Modifier.height(20.dp))
    Column(modifier = Modifier.padding(16.dp)) {
        // Search Input
        TextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search Characters") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.processAction(SearchUiAction.Search(query)) },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // UI State Handling
        when (val state = uiState) {
            is SearchUiState.Idle -> Text("Enter a query to search characters.")
            is SearchUiState.Loading -> CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            is SearchUiState.Success -> CharacterList(state.characters, navController)
            is SearchUiState.Error -> Text(
                text = "Error: ${state.message}",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun CharacterList(characters: List<Character>, navController: NavHostController) {
    if (characters.isEmpty()) {
        Text("No characters found.")
    } else {
        LazyColumn {
            items(characters) { character ->
                CharacterItem(character, navController)
            }
        }
    }
}

@Composable
fun CharacterItem(
    character: com.rajabi.starwars.data.model.Character,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                val encodedUrl = URLEncoder.encode(character.url, StandardCharsets.UTF_8.toString())
                navController.navigate("character_detail/$encodedUrl")
            }
    ) {
        Text("Name: ${character.name}")
        Text("Birth Year: ${character.birthYear}")
        Spacer(modifier = Modifier.height(8.dp))
    }
}
