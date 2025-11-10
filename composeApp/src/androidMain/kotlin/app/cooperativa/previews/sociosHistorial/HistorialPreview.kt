package app.cooperativa.previews.sociosHistorial

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.cooperativa.data.model.dto.Loan
import app.cooperativa.data.model.dto.LoanUiStatus
import app.cooperativa.presentation.mainflow.socios.historial.mainHistorial.SHistorialScreen
import app.cooperativa.presentation.mainflow.socios.historial.mainHistorial.SHistorialState
import app.cooperativa.theme.CoopTheme

// Datos de prueba (nuevo modelo Loan)
private val dummyLoans = listOf(
    Loan(
        id = "L-001",
        presentedByName = "Laura Martínez",
        reason = "Compra Casa",
        total = 10_000.0f,
        payed = 10_000.0f,
        debt = 0.0f,
        interestRate = 5.0f,
        quotas = 12,
        status = LoanUiStatus.PAYED
    ),
    Loan(
        id = "L-002",
        presentedByName = "Laura Martínez",
        reason = "Compra Auto",
        total = 5_000.0f,
        payed = 1_500.0f,
        debt = 3_500.0f,
        interestRate = 7.5f,
        quotas = 24,
        status = LoanUiStatus.ACTIVE
    )
)

// Estado base para previews
private val dummyState = SHistorialState(
    selectedTabIndex = 0,
    totalAportado = 1200.0f,
    capitalPorPagar = 3000.0f,
    prestamos = dummyLoans
)

// === PREVIEWS ===

@Preview(
    name = "Historial Pagos - Light",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun SHistorialPagosPreviewLight() {
    CoopTheme {
        SHistorialScreen(
            state = dummyState.copy(selectedTabIndex = 0),
            loadData = {},
            onTabSelected = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(
    name = "Historial Pagos - Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SHistorialPagosPreviewDark() {
    CoopTheme {
        SHistorialScreen(
            state = dummyState.copy(selectedTabIndex = 0),
            loadData = {},
            onTabSelected = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(
    name = "Historial Préstamos - Light",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun SHistorialPrestamosPreviewLight() {
    CoopTheme {
        SHistorialScreen(
            state = dummyState.copy(selectedTabIndex = 1),
            loadData = {},
            onTabSelected = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(
    name = "Historial Préstamos - Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SHistorialPrestamosPreviewDark() {
    CoopTheme {
        SHistorialScreen(
            state = dummyState.copy(selectedTabIndex = 1),
            loadData = {},
            onTabSelected = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}