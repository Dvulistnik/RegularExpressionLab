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
import org.dvulist.regularexpression.logic.ExpressionTokenizer
import org.dvulist.regularexpression.logic.Token

@Composable
fun MathTokenizerScreen(onBack: () -> Unit) {
    var expressionInput by remember { mutableStateOf("") }
    var tokenList by remember { mutableStateOf<List<Token>>(emptyList()) }

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
                text = "Токенизация мат. выражения",
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = expressionInput,
            onValueChange = {
                expressionInput = it
                tokenList = emptyList()
            },
            label = { Text("Введите мат. выражение") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Вызываем токенизатор
                tokenList = ExpressionTokenizer.tokenize(expressionInput)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Токенизировать")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Отображение списка токенов
        if (tokenList.isNotEmpty()) {
            Text(
                text = "Найденные токены:",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(tokenList) { token ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        elevation = 2.dp
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(text = "Тип: ${token.type}", style = MaterialTheme.typography.body2)
                            Text(text = "Значение: \"${token.value}\"", style = MaterialTheme.typography.body1)
                        }
                    }
                }
            }
        } else if (expressionInput.isNotBlank() && tokenList.isEmpty()) {
            Text(
                text = "Введите выражение для токенизации или не найдено токенов.",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Типы токенов:",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text("Переменные: латинские буквы, цифры и '_', не начинаются с цифры (например, 'a', 'var123')")
        Text("Числа: целые или вещественные (например, '42', '23.567')")
        Text("Константы: pi, e, sqrt2, ln2, ln10")
        Text("Функции: sin, cos, tg, ctg, ln, lg, log, exp, sqrt")
        Text("Операторы: ^, *, /, -, +")
        Text("Скобки: ( и )")
    }
}