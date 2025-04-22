package app.cooperativa.previews.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import android.content.res.Configuration
import app.cooperativa.navigation.SociosNavigationBar
import app.cooperativa.theme.CoopTheme

@Preview(name = "Light - Historial", showBackground = true)
@Composable
fun SociosNavigationBarPreview_Light0() {
    CoopTheme {
        SociosNavigationBar(selectedIndex = 0, onItemSelected = {})
    }
}

@Preview(name = "Dark - Historial", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SociosNavigationBarPreview_Dark0() {
    CoopTheme {
        SociosNavigationBar(selectedIndex = 0, onItemSelected = {})
    }
}

@Preview(name = "Light - Préstamos", showBackground = true)
@Composable
fun SociosNavigationBarPreview_Light1() {
    CoopTheme {
        SociosNavigationBar(selectedIndex = 1, onItemSelected = {})
    }
}

@Preview(name = "Dark - Préstamos", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SociosNavigationBarPreview_Dark1() {
    CoopTheme {
        SociosNavigationBar(selectedIndex = 1, onItemSelected = {})
    }
}

@Preview(name = "Light - FAQs", showBackground = true)
@Composable
fun SociosNavigationBarPreview_Light2() {
    CoopTheme {
        SociosNavigationBar(selectedIndex = 2, onItemSelected = {})
    }
}

@Preview(name = "Dark - FAQs", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SociosNavigationBarPreview_Dark2() {
    CoopTheme {
        SociosNavigationBar(selectedIndex = 2, onItemSelected = {})
    }
}

@Preview(name = "Light - Cuenta", showBackground = true)
@Composable
fun SociosNavigationBarPreview_Light3() {
    CoopTheme {
        SociosNavigationBar(selectedIndex = 3, onItemSelected = {})
    }
}

@Preview(name = "Dark - Cuenta", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SociosNavigationBarPreview_Dark3() {
    CoopTheme {
        SociosNavigationBar(selectedIndex = 3, onItemSelected = {})
    }
}