package app.cooperativa.previews.account

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import app.cooperativa.presentation.mainflow.directiva.account.AccountScreen
import app.cooperativa.theme.CoopTheme
import app.cooperativa.theme.components.CoopSurface


@Preview(name = "Light Mode", showBackground = true)
@Composable
fun AccountScreenPreviewLight() {
    CoopTheme {
        CoopSurface {
            AccountScreen(
                onChangeToMember = {},
                onLogout = {}
            )
        }
    }
}

@Preview(name = "Dark Mode", showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AccountScreenPreviewDark() {
    CoopTheme {
        CoopSurface {
            AccountScreen(
                onChangeToMember = {},
                onLogout = {}
            )
        }
    }
}