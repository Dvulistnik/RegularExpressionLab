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
import org.dvulist.regularexpression.logic.WebColorValidator

@Composable
fun WebColorScreen(onBack: () -> Unit) {
    var colorInput by remember { mutableStateOf("") }
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
                text = "Проверка корректности web цвета",
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = colorInput,
            onValueChange = {
                colorInput = it
                validationResult = null // Сбрасываем результат при изменении ввода
            },
            label = { Text("Введите цвет (HEX, RGB, HSL)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Вызываем валидатор для проверки цвета
                validationResult = WebColorValidator.isValidWebColor(colorInput)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Проверить цвет")
        }

        Spacer(modifier = Modifier.height(16.dp))

        validationResult?.let { isValid ->
            val message = if (isValid) "Цвет корректен!" else "Цвет некорректен. Проверьте форматы."
            val color = if (isValid) Color.Green else Color.Red
            Text(
                text = message,
                color = color,
                style = MaterialTheme.typography.body1
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Требования к цвету
        Text(
            text = "Форматы цветов:",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text("HEX: #rrggbb или #rgb (например, #21148D, #888)")
        Text("RGB: rgb(r, g, b) - r, g, b от 0 до 255 или 0% до 100% (например, rgb(255, 255, 255), rgb(50%, 0%, 100%))")
        Text("HSL: hsl(h, s%, l%) - h от 0 до 360, s% и l% от 0% до 100% (например, hsl(200, 100%, 50%))")
    }
}