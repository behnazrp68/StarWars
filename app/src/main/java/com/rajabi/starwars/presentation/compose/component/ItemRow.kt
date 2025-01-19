package com.rajabi.starwars.presentation.compose.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun CharacterItem(
    character: com.rajabi.starwars.data.model.search.Character,
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