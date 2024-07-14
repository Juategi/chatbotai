package com.example.chatbotai.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.chatbotai.core.DependencyInjection
import com.example.chatbotai.domain.person.PersonEntity

@Composable
fun ChatSelectorScreen(navController: NavHostController) {

    val personRepository = DependencyInjection.getPersonRepository()
    val persons = personRepository.getPersons()

    LazyColumn(
        modifier = Modifier.padding(16.dp),
    ) {
        items(persons) { person ->
            PersonTile(person, navController)
        }
    }
}

@Composable
fun PersonTile(person: PersonEntity, navController: NavHostController) {
    Row(modifier = Modifier.padding(8.dp).clickable { navController.navigate("chat/${person.name}") }
    ) {
        Image(
            painter = painterResource(id = person.image),
            contentDescription = person.name,
            modifier = Modifier
                .size(100.dp)
                .fillMaxWidth()
                .padding(bottom = 8.dp, top = 8.dp, start = 8.dp, end = 8.dp)
                .clip(CircleShape)
        )
        Column {
            Text(text = person.name)
            Text(text = stringResource(person.description))
        }
    }
}
