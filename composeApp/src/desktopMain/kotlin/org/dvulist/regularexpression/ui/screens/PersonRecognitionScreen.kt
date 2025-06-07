package org.dvulist.regularexpression.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.dvulist.regularexpression.logic.Person
import org.dvulist.regularexpression.logic.PersonRecognizer

@Composable
fun PersonRecognitionScreen(onBack: () -> Unit) {
    var textInput by remember { mutableStateOf("") }
    var recognizedPersons by remember { mutableStateOf<List<Person>>(emptyList()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
            }
            Text(
                text = "Поиск персон в тексте",
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = textInput,
            onValueChange = {
                textInput = it
                recognizedPersons = emptyList()
            },
            label = { Text("Введите текст на русском языке") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                recognizedPersons = PersonRecognizer.findPersons(textInput)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = textInput.isNotBlank()
        ) {
            Text("Найти персоны")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (recognizedPersons.isNotEmpty()) {
            Text(
                text = "Найденные персоны:",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(recognizedPersons) { person ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        elevation = 2.dp
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(
                                text = "Имя: \"${person.name}\"",
                                style = MaterialTheme.typography.body1
                            )
                        }
                    }
                }
            }
        } else if (textInput.isNotBlank() && recognizedPersons.isEmpty()) {
            Text(
                text = "Персоны не найдены. Попробуйте ввести другой текст.",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
            )
        } else {
            Text(
                text = "Введите текст выше, чтобы найти именованные сущности (персоны).",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}