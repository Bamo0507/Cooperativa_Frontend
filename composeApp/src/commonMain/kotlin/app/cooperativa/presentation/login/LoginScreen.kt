package app.cooperativa.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.cooperativa.theme.CoopTheme
import app.cooperativa.theme.components.CoopButton
import app.cooperativa.theme.components.CoopIcon
import app.cooperativa.theme.components.CoopIconButton
import app.cooperativa.theme.components.CoopText
import cooperativa.composeapp.generated.resources.Res
import cooperativa.composeapp.generated.resources.family_photo
import cooperativa.composeapp.generated.resources.login_background
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@Composable
fun LoginRoute(
    viewModel: LoginViewModel = koinInject(),
    onLogin: (user_type: String) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(state.isLoggedIn, state.userType) {
        if (state.isLoggedIn && state.userType != null) {
            onLogin(state.userType ?: "General")
        }
    }

    LoginScreen(
        username = state.username,
        onTextChange = viewModel::onUsernameChange,
        password = state.password,
        onTextChangePassword = viewModel::onPasswordChange,
        passwordVisible = passwordVisible,
        onPasswordVisibleToggle = { passwordVisible = !passwordVisible },
        onLogin = {
            viewModel.submitLoginIfValid { userType -> onLogin(userType) }
        },
        isLoading = state.isLoading,
        errorMessage = state.error
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
    onPasswordVisibleToggle: () -> Unit,
    isLoading: Boolean,
    errorMessage: String?
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(CoopTheme.colorScheme.surface)
        ) {
            // Wavy hero image at the top
            val waveShape = GenericShape { size, _ ->
                moveTo(0f, 0f)
                lineTo(0f, size.height * 0.80f)
                cubicTo(
                    size.width * 0.25f, size.height * 0.65f,
                    size.width * 0.75f, size.height * 0.95f,
                    size.width,         size.height * 0.75f
                )
                lineTo(size.width, 0f)
                close()
            }

            Image(
                painter = painterResource(Res.drawable.family_photo),
                contentDescription = "Login background",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(waveShape)
            )

            // Titles
            CoopText(
                text = "Bienvenido de Vuelta",
                style = CoopTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = CoopTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            CoopText(
                text = "Inicia sesión en tu cuenta",
                style = CoopTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = CoopTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                // Username
                OutlinedTextField(
                    value = username,
                    onValueChange = onTextChange,
                    label = { CoopText("Usuario") },
                    shape = RoundedCornerShape(16.dp),
                    maxLines = 1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor           = CoopTheme.colorScheme.onSurface,
                        unfocusedTextColor         = CoopTheme.colorScheme.onSurface,
                        cursorColor                = CoopTheme.colorScheme.onSurface,
                        focusedContainerColor      = CoopTheme.colorScheme.surface,
                        unfocusedContainerColor    = CoopTheme.colorScheme.surface,
                        focusedIndicatorColor      = CoopTheme.colorScheme.onSurface,
                        unfocusedIndicatorColor    = CoopTheme.colorScheme.onSurface,
                        focusedLabelColor          = CoopTheme.colorScheme.onSurface,
                        unfocusedLabelColor        = CoopTheme.colorScheme.onSurface,
                        focusedPlaceholderColor    = CoopTheme.colorScheme.onSurface,
                        unfocusedPlaceholderColor  = CoopTheme.colorScheme.onSurface,
                        focusedTrailingIconColor   = CoopTheme.colorScheme.onSurface,
                        unfocusedTrailingIconColor = CoopTheme.colorScheme.onSurface
                    )
                )

                // Password
                OutlinedTextField(
                    maxLines = 1,
                    value = password,
                    onValueChange = onTextChangePassword,
                    label = { CoopText("Contraseña") },
                    shape = RoundedCornerShape(16.dp),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val icon = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                        CoopIconButton(onClick = onPasswordVisibleToggle) {
                            CoopIcon(imageVector = icon, contentDescription = "Toggle password visibility")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor           = CoopTheme.colorScheme.onSurface,
                        unfocusedTextColor         = CoopTheme.colorScheme.onSurface,
                        cursorColor                = CoopTheme.colorScheme.onSurface,
                        focusedContainerColor      = CoopTheme.colorScheme.surface,
                        unfocusedContainerColor    = CoopTheme.colorScheme.surface,
                        focusedIndicatorColor      = CoopTheme.colorScheme.onSurface,
                        unfocusedIndicatorColor    = CoopTheme.colorScheme.onSurface,
                        focusedLabelColor          = CoopTheme.colorScheme.onSurface,
                        unfocusedLabelColor        = CoopTheme.colorScheme.onSurface,
                        focusedPlaceholderColor    = CoopTheme.colorScheme.onSurface,
                        unfocusedPlaceholderColor  = CoopTheme.colorScheme.onSurface,
                        focusedTrailingIconColor   = CoopTheme.colorScheme.onSurface,
                        unfocusedTrailingIconColor = CoopTheme.colorScheme.onSurface
                    )
                )
                // Forgot password
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ){
                    TextButton(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = CoopTheme.colorScheme.onPrimary,
                            disabledContainerColor = CoopTheme.colorScheme.primary.copy(alpha = 0.65f),
                            disabledContentColor = CoopTheme.colorScheme.onPrimary.copy(alpha = 0.65f)
                        )
                    ){
                        CoopText(
                            text = "¿Olvidaste tu contraseña?",
                            color = CoopTheme.colorScheme.onSurface,
                            style = CoopTheme.typography.bodySmall,
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                if (errorMessage != null) {
                    CoopText(
                        text = errorMessage,
                        color = CoopTheme.colorScheme.error,
                        style = CoopTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }

                // Login button
                CoopButton(
                    onClick = onLogin,
                    shape = RoundedCornerShape(50),
                    enabled = !isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    CoopText("Iniciar Sesión", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
