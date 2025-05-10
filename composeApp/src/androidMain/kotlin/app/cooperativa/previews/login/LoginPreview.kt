package app.cooperativa.previews.login

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import app.cooperativa.presentation.login.LoginScreen
import app.cooperativa.theme.CoopTheme

@Preview(
    name = "Login Light Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = Devices.PIXEL_4
)
@Composable
fun LoginScreenPreviewLight() {
    CoopTheme {
        LoginScreen(
            username = "usuario",
            onTextChange = {},
            password = "password",
            onTextChangePassword = {},
            passwordVisible = false,
            onPasswordVisibleToggle = {},
            onLogin = {}
    )
    }
}

@Preview(
    name = "Login Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_4
)
@Composable
fun LoginScreenPreviewDark() {
    CoopTheme {
        LoginScreen(
            username = "usuario",
            onTextChange = {},
            password = "password",
            onTextChangePassword = {},
            passwordVisible = true,
            onPasswordVisibleToggle = {},
            onLogin = {}
        )
    }
}