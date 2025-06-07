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
import org.dvulist.regularexpression.logic.PasswordValidator

@Composable
fun PasswordScreen(onBack: () -> Unit) {
    var passwordInput by remember { mutableStateOf("") }
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
            IconButton(onClick = onBack) { // Кнопка "Назад"
                Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
            }
            Text(
                text = "Проверка корректности пароля",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.weight(1f).padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = passwordInput,
            onValueChange = {
                passwordInput = it
                validationResult = null
            },
            label = { Text("Введите пароль") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                validationResult = PasswordValidator.isValidPassword(passwordInput)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Проверить пароль")
        }

        Spacer(modifier = Modifier.height(16.dp))

        validationResult?.let { isValid ->
            val message = if (isValid) "Пароль корректен!" else "Пароль некорректен. Проверьте требования."
            val color = if (isValid) Color.Green else Color.Red
            Text(
                text = message,
                color = color,
                style = MaterialTheme.typography.body1
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Требования к паролю:",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text("Только латинские символы, цифры и ^$%@#&*!?")
        Text("Не менее 8 символов")
        Text("Минимум один латинский символ в верхнем регистре")
        Text("Минимум один латинский символ в нижнем регистре")
        Text("Минимум одна цифра")
        Text("Минимум два различных специальных символа")
        Text("Не содержит двух одинаковых символов подряд")
    }
}