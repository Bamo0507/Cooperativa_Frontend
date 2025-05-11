package app.cooperativa.previews.navigation

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import app.cooperativa.navigation.DirectivaBottomNavBar
import app.cooperativa.navigation.navigationItemsDirectiva
import app.cooperativa.theme.CoopTheme

@Preview(name = "Light - Pagos", showBackground = true)
@Composable
fun DirectivaNavBarPreview_Light_Pagos() {
    CoopTheme {
        DirectivaBottomNavBar(
            checkItemSelected = { dest -> dest == navigationItemsDirectiva[0].destination },
            onNavItemClick = {}
        )
    }
}

@Preview(
    name = "Dark - Pagos",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun DirectivaNavBarPreview_Dark_Pagos() {
    CoopTheme {
        DirectivaBottomNavBar(
            checkItemSelected = { dest -> dest == navigationItemsDirectiva[0].destination },
            onNavItemClick = {}
        )
    }
}

@Preview(name = "Light - Préstamos", showBackground = true)
@Composable
fun DirectivaNavBarPreview_Light_Prestamos() {
    CoopTheme {
        DirectivaBottomNavBar(
            checkItemSelected = { dest -> dest == navigationItemsDirectiva[1].destination },
            onNavItemClick = {}
        )
    }
}

@Preview(
    name = "Dark - Préstamos",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun DirectivaNavBarPreview_Dark_Prestamos() {
    CoopTheme {
        DirectivaBottomNavBar(
            checkItemSelected = { dest -> dest == navigationItemsDirectiva[1].destination },
            onNavItemClick = {}
        )
    }
}

@Preview(name = "Light - Cuenta", showBackground = true)
@Composable
fun DirectivaNavBarPreview_Light_Cuenta() {
    CoopTheme {
        DirectivaBottomNavBar(
            checkItemSelected = { dest -> dest == navigationItemsDirectiva[2].destination },
            onNavItemClick = {}
        )
    }
}

@Preview(
    name = "Dark - Cuenta",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun DirectivaNavBarPreview_Dark_Cuenta() {
    CoopTheme {
        DirectivaBottomNavBar(
            checkItemSelected = { dest -> dest == navigationItemsDirectiva[2].destination },
            onNavItemClick = {}
        )
    }
}