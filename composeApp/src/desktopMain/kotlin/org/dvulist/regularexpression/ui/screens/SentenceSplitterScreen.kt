package org.dvulist.regularexpression.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.dvulist.regularexpression.logic.SentenceTokenizer

@Composable
fun SentenceSplitterScreen(onBack: () -> Unit) {
    var textInput by remember { mutableStateOf("") }
    var sentencesList by remember { mutableStateOf<List<String>>(emptyList()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Заголовок и кнопка "Назад"
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
            }
            Text(
                text = "Разбиение текста на предложения",
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
                sentencesList = emptyList()
            },
            label = { Text("Введите текст") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                sentencesList = SentenceTokenizer.tokenize(textInput)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Разбить на предложения")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (sentencesList.isNotEmpty()) {
            Text(
                text = "Найденные предложения:",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                itemsIndexed(sentencesList) { index, sentence ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        elevation = 2.dp
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(text = "Предложение ${index + 1}:", style = MaterialTheme.typography.body2)
                            Text(text = "\"${sentence}\"", style = MaterialTheme.typography.body1)
                        }
                    }
                }
            }
        } else if (textInput.isNotBlank() && sentencesList.isEmpty()) {
            Text(
                text = "Введите текст для разбиения или не найдено предложений.",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Особенности разбиения текстов со списками:",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text("Если элементы списка состоят из нескольких предложений, " +
                "то предложение перед списком завершается до списка, а каждый пункт списка разбивается на независимые предложения, " +
                "причём первое предложение пункта включает в себя маркер списка.")
        Text("В противном случае (обычно пункты таких списков завершаются символом ';' " +
                "за исключением последнего пункта, завершающегося точкой) предложением является весь список и " +
                "предшествующее ему предложение.")
        Text("Год должен быть неотрицательным.")
    }
}