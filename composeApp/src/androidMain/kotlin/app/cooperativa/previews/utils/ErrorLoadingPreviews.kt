package app.cooperativa.previews.utils

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import app.cooperativa.presentation.utils.ErrorScreen
import app.cooperativa.presentation.utils.LoadingScreen
import app.cooperativa.theme.CoopTheme

@Preview(
    name = "Loading Screen Light",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = Devices.PIXEL_4
)
@Composable
fun LoadingScreenPreviewLight() {
    CoopTheme(darkTheme = false) {
        LoadingScreen(
            message = "Cargando datos…",
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(
    name = "Loading Screen Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_4
)
@Composable
fun LoadingScreenPreviewDark() {
    CoopTheme(darkTheme = true) {
        LoadingScreen(
            message = "Cargando datos…",
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(
    name = "Error Screen Light",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = Devices.PIXEL_4
)
@Composable
fun ErrorScreenPreviewLight() {
    CoopTheme(darkTheme = false) {
        ErrorScreen(
            message = "Algo salió mal. Intenta de nuevo.",
            onRetry = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(
    name = "Error Screen Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_4
)
@Composable
fun ErrorScreenPreviewDark() {
    CoopTheme(darkTheme = true) {
        ErrorScreen(
            message = "Algo salió mal. Intenta de nuevo.",
            onRetry = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}