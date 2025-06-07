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
import org.dvulist.regularexpression.logic.DateValidator

@Composable
fun DateValidationScreen(onBack: () -> Unit) {
    var dateInput by remember { mutableStateOf("") }
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
                text = "Проверка корректности даты",
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = dateInput,
            onValueChange = {
                dateInput = it
                validationResult = null
            },
            label = { Text("Введите дату") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                validationResult = DateValidator.isValidDate(dateInput)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Проверить дату")
        }

        Spacer(modifier = Modifier.height(16.dp))

        validationResult?.let { isValid ->
            val message = if (isValid) "Дата корректна!" else "Дата некорректна. Проверьте форматы и значения."
            val color = if (isValid) Color.Green else Color.Red
            Text(
                text = message,
                color = color,
                style = MaterialTheme.typography.body1
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Требования к дате
        Text(
            text = "Допустимые форматы:",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text("День.Месяц.Год (например, 14.09.2023)")
        Text("День/Месяц/Год (например, 14/09/2023)")
        Text("День-Месяц-Год (например, 14-09-2023)")
        Text("Год.Месяц.День (например, 2023.09.14)")
        Text("Год/Месяц/День (например, 2023/09/14)")
        Text("Год-Месяц-День (например, 2023-09-14)")
        Text("День Месяц_рус Год (например, 14 сентября 2023)")
        Text("Месяц_eng День, Год (например, September 14, 2023)")
        Text("Мес_eng День, Год (например, Sep 14, 2023)")
        Text("Год, Месяц_eng День (например, 2023, September 14)")
        Text("Год, Мес_eng День (например, 2023, Sep 14)")
        Text("Примечание: год должен быть неотрицательным.")
    }
}