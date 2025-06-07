package org.dvulist.regularexpression.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.dvulist.regularexpression.Screen

@Composable
fun MainMenuScreen(onNavigate: (Screen) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Выберите задание:",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Button(
            onClick = { onNavigate(Screen.Password) },
            modifier = Modifier.fillMaxWidth(0.6f).height(50.dp)
        ) {
            Text("1.1 Проверка корректности пароля (Regex)")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onNavigate(Screen.WebColor) },
            modifier = Modifier.fillMaxWidth(0.6f).height(50.dp)
        ) {
            Text("1.2 Проверка корректности Web цвета (Regex)")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onNavigate(Screen.MathTokenizer) },
            modifier = Modifier.fillMaxWidth(0.6f).height(50.dp)
        ) {
            Text("1.3 Токенизация мат. выражения (Без Regex)")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onNavigate(Screen.DateValidation) },
            modifier = Modifier.fillMaxWidth(0.6f).height(50.dp)
        ) {
            Text("1.4 Проверка корректности даты (Без Regex)")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onNavigate(Screen.BracketValidation) },
            modifier = Modifier.fillMaxWidth(0.6f).height(50.dp)
        ) {
            Text("2.1. Проверка корректности скобочного выражения")
        }

//        Spacer(modifier = Modifier.height(16.dp))
//
//        Button(
//            onClick = { onNavigate(Screen.SentenceSplitter) },
//            modifier = Modifier.fillMaxWidth(0.6f).height(50.dp)
//        ) {
//            Text("2.2. Разбиение текста на предложения")
//        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onNavigate(Screen.PersonRecognition) },
            modifier = Modifier.fillMaxWidth(0.6f).height(50.dp)
        ) {
            Text("2.3. Поиск в тексте именованных сущностей типа PERSON")
        }
    }
}