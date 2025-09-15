package app.cooperativa.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import app.cooperativa.presentation.mainflow.socios.pagos.agregarPago.SPagoEnviarScreen
import app.cooperativa.presentation.mainflow.socios.pagos.agregarPago.SPagoEnviarState
import app.cooperativa.theme.CoopTheme
import app.cooperativa.data.model.dto.*

@Preview(showBackground = true, name = "Presentar Pago – Simple")
@Composable
fun SPagoEnviarScreen_Preview() {
    // Estado mínimo para previsualizar; listas vacías están bien para compilar.
    val previewState = SPagoEnviarState(
        nombrePago = "Pago de ejemplo",
        // Asegúrate de que tu State ya tenga este campo:
        montoPagoText = "",              // <-- si aún no lo tienes, agrégalo al state
        numberoCuenta = "",
        numeroBoleta = "",
        hasSentPayment = true,
        cuotasDisponibles = emptyList(),
        prestamosDisponibles = emptyList(),
        multasDisponibles = emptyList(),
        usuariosDisponibles = emptyList(),
        aportesCapital = emptyList()
    )

    CoopTheme {
        SPagoEnviarScreen(
            state = previewState,
            onBackClick = {},
            onPickImage = {},
            onNombreChange = {},
            onMontoChange = {},
            onCuentaChange = {},
            onBoletaChange = {},
            onSend = {},
            onAddCuota = {},
            onRemoveCuota = {},
            onAddLoanQuota = {},
            onRemoveLoanQuota = {},
            onAddFine = {},
            onRemoveFine = {},
            onAddCapitalContribution = { _, _ -> },
            onRemoveCapitalContribution = {}
        )
    }
}