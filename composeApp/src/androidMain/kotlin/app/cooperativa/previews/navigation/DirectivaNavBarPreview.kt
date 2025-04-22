package app.cooperativa.previews.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import android.content.res.Configuration
import app.cooperativa.navigation.DirectivaNavigationBar
import app.cooperativa.theme.CoopTheme

@Preview(name = "Light - Pagos", showBackground = true)
@Composable
fun DirectivaNavigationBarPreview_Light0() {
    CoopTheme {
        DirectivaNavigationBar(selectedIndex = 0, onItemSelected = {})
    }
}

@Preview(name = "Dark - Pagos", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DirectivaNavigationBarPreview_Dark0() {
    CoopTheme {
        DirectivaNavigationBar(selectedIndex = 0, onItemSelected = {})
    }
}

@Preview(name = "Light - Préstamos", showBackground = true)
@Composable
fun DirectivaNavigationBarPreview_Light1() {
    CoopTheme {
        DirectivaNavigationBar(selectedIndex = 1, onItemSelected = {})
    }
}

@Preview(name = "Dark - Préstamos", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DirectivaNavigationBarPreview_Dark1() {
    CoopTheme {
        DirectivaNavigationBar(selectedIndex = 1, onItemSelected = {})
    }
}

@Preview(name = "Light - Cuenta", showBackground = true)
@Composable
fun DirectivaNavigationBarPreview_Light2() {
    CoopTheme {
        DirectivaNavigationBar(selectedIndex = 2, onItemSelected = {})
    }
}

@Preview(name = "Dark - Cuenta", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DirectivaNavigationBarPreview_Dark2() {
    CoopTheme {
        DirectivaNavigationBar(selectedIndex = 2, onItemSelected = {})
    }
}