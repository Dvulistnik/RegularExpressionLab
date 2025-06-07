package org.dvulist.regularexpression

import androidx.compose.runtime.*
import org.dvulist.regularexpression.ui.screens.* // Убедитесь, что импорты корректны

sealed class Screen {
    object MainMenu : Screen()
    object Password : Screen()
    object WebColor : Screen()
    object MathTokenizer : Screen()
    object DateValidation : Screen()
    object SentenceSplitter: Screen()
    object BracketValidation: Screen()
    object PersonRecognition: Screen()
}

@Composable
fun App() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.MainMenu) }

    when (currentScreen) {
        Screen.MainMenu -> MainMenuScreen { screen -> currentScreen = screen }
        Screen.Password -> PasswordScreen(onBack = { currentScreen = Screen.MainMenu })
        Screen.WebColor -> WebColorScreen(onBack = { currentScreen = Screen.MainMenu })
        Screen.MathTokenizer -> MathTokenizerScreen(onBack = { currentScreen = Screen.MainMenu })
        Screen.DateValidation -> DateValidationScreen(onBack = { currentScreen = Screen.MainMenu })
        Screen.SentenceSplitter -> SentenceSplitterScreen(onBack = { currentScreen = Screen.MainMenu })
        Screen.BracketValidation -> BracketValidationScreen(onBack = { currentScreen = Screen.MainMenu })
        Screen.PersonRecognition -> PersonRecognitionScreen(onBack = { currentScreen = Screen.MainMenu })
    }
}