package org.dvulist.regularexpression.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.dvulist.regularexpression.logic.BracketValidator

@Composable
fun BracketValidationScreen(onBack: () -> Unit) {
    var bracketInput by remember { mutableStateOf("") }
    var validationResult by remember { mutableStateOf<Boolean?>(null) }

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
                text = "Проверка скобочного выражения",
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = bracketInput,
            onValueChange = {
                bracketInput = it
                validationResult = null
            },
            label = { Text("Введите скобочное выражение") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                validationResult = BracketValidator.isValidBracketExpression(bracketInput)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Проверить выражение")
        }

        Spacer(modifier = Modifier.height(16.dp))

        validationResult?.let { isValid ->
            val message = if (isValid) "Выражение корректно!" else "Выражение некорректно. Проверьте баланс и порядок скобок."
            val color = if (isValid) Color.Green else Color.Red
            Text(
                text = message,
                color = color,
                style = MaterialTheme.typography.body1
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Правила корректного скобочного выражения:",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text("Используются три типа скобок: (), [], {}.")
        Text("Пустая строка является правильной последовательностью.")
        Text("Правильная последовательность, взятая в скобки, тоже правильная.")
        Text("Две правильные последовательности, приписанные друг к другу, тоже правильные.")
        Text("Примеры корректных: (), {}, {[{[()]}]} ")
        Text("Примеры некорректных: ), (}[] , {{[{ )((}{ ")
    }
}