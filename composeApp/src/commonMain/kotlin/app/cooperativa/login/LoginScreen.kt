package app.cooperativa.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.cooperativa.theme.CoopTheme
import app.cooperativa.theme.components.CoopButton
import app.cooperativa.theme.components.CoopIcon
import app.cooperativa.theme.components.CoopIconButton
import app.cooperativa.theme.components.CoopText
import cooperativa.composeapp.generated.resources.Res
import cooperativa.composeapp.generated.resources.login_background
import org.jetbrains.compose.resources.painterResource

@Composable
fun LoginRoute() {
    // Cambiar mas adelante por el STATE y un VIEWMODEL y declarar como EVENTS del screen
    var username = rememberSaveable { mutableStateOf(" ") }
    var onTextChange = { text: String -> username.value = text }
    var password = rememberSaveable { mutableStateOf(" ") }
    var onTextChangePassword = { text: String -> password.value = text }
    var passwordVisible = rememberSaveable { mutableStateOf(false) }
    var onPasswordVisible = { passwordVisible.value = !passwordVisible.value }


    LoginScreen(
        username = username.value,
        onTextChange = onTextChange,
        password = password.value,
        onTextChangePassword = onTextChangePassword,
        onLogin = { /*TODO*/ },
        passwordVisible = passwordVisible.value,
        onPasswordVisibleToggle = onPasswordVisible
    )
}

@Composable
fun LoginScreen(
    username: String,
    onTextChange: (String) -> Unit,
    password: String,
    onTextChangePassword: (String) -> Unit,
    onLogin: () -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibleToggle: () -> Unit
) {
    Column (modifier = Modifier.fillMaxSize().background(CoopTheme.colorScheme.surface)) {
        // Imagen de fondo
        Image(
            painter = painterResource(Res.drawable.login_background),
            contentDescription = "Login background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )

        // Capa blanca redondeada encima
        Column(
            modifier = Modifier
                .offset(y = -40.dp)
                .background(
                    color = CoopTheme.colorScheme.surface,
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                )
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CoopText(
                text = "TU COOPERATIVA\nTU CONFIANZA",
                style = CoopTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 24.dp),
                color = CoopTheme.colorScheme.onSurface
            )

            // Campo de usuario
            // TODO: AGREGAR COMO REUSABLE COMPONENT
            OutlinedTextField(
                value = username,
                onValueChange = onTextChange,
                label = { CoopText("Usuario") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                colors = TextFieldDefaults.colors(
                    // Texto y cursor
                    focusedTextColor            = CoopTheme.colorScheme.onSurface,
                    unfocusedTextColor          = CoopTheme.colorScheme.onSurface,
                    cursorColor                 = CoopTheme.colorScheme.onSurface,

                    // Contenedor (fondo)
                    focusedContainerColor       = CoopTheme.colorScheme.surface,
                    unfocusedContainerColor     = CoopTheme.colorScheme.surface,

                    // Borde / indicador
                    focusedIndicatorColor       = CoopTheme.colorScheme.onSurface,
                    unfocusedIndicatorColor     = CoopTheme.colorScheme.onSurface,

                    // Label / placeholder
                    focusedLabelColor           = CoopTheme.colorScheme.onSurface,
                    unfocusedLabelColor         = CoopTheme.colorScheme.onSurface,
                    focusedPlaceholderColor     = CoopTheme.colorScheme.onSurface,
                    unfocusedPlaceholderColor   = CoopTheme.colorScheme.onSurface,

                    // Iconos
                    focusedTrailingIconColor    = CoopTheme.colorScheme.onSurface,
                    unfocusedTrailingIconColor  = CoopTheme.colorScheme.onSurface
                )
            )

            // Campo de contraseña
            OutlinedTextField(
                value = password,
                onValueChange = onTextChangePassword,
                label = { CoopText("Contraseña") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    CoopIconButton(onClick = onPasswordVisibleToggle) {
                        CoopIcon(imageVector = icon, contentDescription = "Toggle password visibility")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                colors = TextFieldDefaults.colors(
                    // Texto y cursor
                    focusedTextColor            = CoopTheme.colorScheme.onSurface,
                    unfocusedTextColor          = CoopTheme.colorScheme.onSurface,
                    cursorColor                 = CoopTheme.colorScheme.onSurface,

                    // Contenedor (fondo)
                    focusedContainerColor       = CoopTheme.colorScheme.surface,
                    unfocusedContainerColor     = CoopTheme.colorScheme.surface,

                    // Borde / indicador
                    focusedIndicatorColor       = CoopTheme.colorScheme.onSurface,
                    unfocusedIndicatorColor     = CoopTheme.colorScheme.onSurface,

                    // Label / placeholder
                    focusedLabelColor           = CoopTheme.colorScheme.onSurface,
                    unfocusedLabelColor         = CoopTheme.colorScheme.onSurface,
                    focusedPlaceholderColor     = CoopTheme.colorScheme.onSurface,
                    unfocusedPlaceholderColor   = CoopTheme.colorScheme.onSurface,

                    // Iconos
                    focusedTrailingIconColor    = CoopTheme.colorScheme.onSurface,
                    unfocusedTrailingIconColor  = CoopTheme.colorScheme.onSurface
                )
            )

            // Texto de recuperación de contraseña
            CoopText(
                text = "¿Olvidaste tu contraseña?",
                color = CoopTheme.colorScheme.onSurface,
                style = CoopTheme.typography.bodySmall,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 4.dp, bottom = 16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botón de iniciar sesión
            CoopButton(
                onClick = onLogin,
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                CoopText("Iniciar Sesión", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}