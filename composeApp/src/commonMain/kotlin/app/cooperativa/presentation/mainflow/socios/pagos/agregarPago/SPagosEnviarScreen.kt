package app.cooperativa.presentation.mainflow.socios.pagos.agregarPago

import app.cooperativa.data.model.dto.BasicUserInfo
import app.cooperativa.data.model.dto.QuotaAffiliate
import app.cooperativa.data.model.dto.LoanQuota
import app.cooperativa.data.model.dto.FinePayAffiliate
import app.cooperativa.presentation.mainflow.socios.pagos.agregarPago.SPagoEnviarViewModel
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import app.cooperativa.theme.components.CoopText
import app.cooperativa.theme.components.CoopOutlinedButton
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.cooperativa.theme.CoopTheme
import app.cooperativa.theme.components.CoopOutlinedTextField
import app.cooperativa.theme.components.CoopTopBar
import org.koin.compose.koinInject
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import app.cooperativa.theme.components.CoopDropdown
import androidx.compose.ui.Modifier
import app.cooperativa.theme.components.CoopText
import app.cooperativa.data.model.dto.CapitalContribution
import app.cooperativa.utils.formatMoney

@Composable
fun SPagoEnviarRoute(
    onBackClick: () -> Unit,
    viewModel: SPagoEnviarViewModel = koinInject()
){
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    SPagoEnviarScreen(
        state = state,
        onBackClick = onBackClick,
        onNombreChange = viewModel::updateNombrePago,
        onMontoChange = { text ->
            text.toFloatOrNull()?.let { viewModel.updateMontoPago(it) }
        },
        onCuentaChange = viewModel::updateNumeroCuenta,
        onBoletaChange = viewModel::updateNumeroBoleta,
        onSend = { viewModel.validateDeclaredAmount() },
        onAddCuota = viewModel::addCuota,
        onRemoveCuota = viewModel::removeCuota,
        onAddLoanQuota = viewModel::addLoanQuota,
        onRemoveLoanQuota = viewModel::removeLoanQuota,
        onAddFine = viewModel::addFine,
        onRemoveFine = viewModel::removeFine,
        onAddCapitalContribution = viewModel::addCapitalContribution,
        onRemoveCapitalContribution = viewModel::removeCapitalContribution
    )
}

@Composable
fun SPagoEnviarScreen(
    state: SPagoEnviarState,
    onBackClick: () -> Unit,
    onNombreChange: (String) -> Unit,
    onMontoChange: (String) -> Unit,
    onCuentaChange: (String) -> Unit,
    onBoletaChange: (String) -> Unit,
    onSend: () -> Unit,
    onAddCuota: (QuotaAffiliate) -> Unit,
    onRemoveCuota: (QuotaAffiliate) -> Unit,
    onAddLoanQuota: (LoanQuota) -> Unit,
    onRemoveLoanQuota: (LoanQuota) -> Unit,
    onAddFine: (FinePayAffiliate) -> Unit,
    onRemoveFine: (FinePayAffiliate) -> Unit,
    onAddCapitalContribution: (BasicUserInfo, Float) -> Unit,
    onRemoveCapitalContribution: (CapitalContribution) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            CoopTopBar(
                title = "Presentar Pago",
                leadingArrow = true,
                onBackClick = { onBackClick() },
            )
        },
        containerColor = CoopTheme.colorScheme.surface
        ,floatingActionButton = {
            ExtendedFloatingActionButton(
                icon = { Icon(Icons.Default.Send, contentDescription = "Enviar") },
                text = { Text("Enviar") },
                onClick = onSend,
                containerColor = CoopTheme.colorScheme.primary,
                contentColor = CoopTheme.colorScheme.onPrimary
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = modifier.padding(padding).padding(horizontal = 26.dp, vertical = 6.dp).padding(top=4.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            item {
                CoopOutlinedTextField(
                    value = state.nombrePago,
                    onValueChange = onNombreChange,
                    label = { Text("Nombre") },
                    placeholder = { Text("Ingrese nombre") },
                    isError = false,
                    modifier = Modifier.padding(vertical = 6.dp)
                )
            }
            item {
                CoopOutlinedTextField(
                    value = state.montoPago.toString(),
                    onValueChange = onMontoChange,
                    label = { Text("Monto") },
                    placeholder = { Text("0.00") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = false,
                    modifier = Modifier.padding(vertical = 6.dp)
                )
            }
            item {
                CoopOutlinedTextField(
                    value = state.numberoCuenta,
                    onValueChange = onCuentaChange,
                    label = { Text("Número de cuenta") },
                    placeholder = { Text("Solo dígitos") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = false,
                    modifier = Modifier.padding(vertical = 6.dp)
                )
            }
            item {
                CoopOutlinedTextField(
                    value = state.numeroBoleta,
                    onValueChange = onBoletaChange,
                    label = { Text("Número de boleta") },
                    placeholder = { Text("Solo dígitos") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = false,
                    modifier = Modifier.padding(vertical = 6.dp)
                )
            }
            // Sección Cuotas
            item {
                CoopText(
                    text = "Cuotas",
                    style = CoopTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
            item {
                var cuotaToAdd by remember { mutableStateOf<QuotaAffiliate?>(null) }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CoopDropdown(
                        items = state.cuotasDisponibles,
                        selectedItem = cuotaToAdd,
                        onItemSelected = { cuotaToAdd = it },
                        itemToString = { it.nombreAsociado },
                        modifier = Modifier.weight(1.3f),
                        label = { CoopText("Selecciona...") },
                        placeholder = { CoopText("Elige") }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    CoopOutlinedTextField(
                        value = cuotaToAdd?.montoCuota?.toString() ?: "",
                        onValueChange = {},
                        label = { CoopText("Monto") },
                        placeholder = { CoopText("Monto") },
                        readOnly = true,
                        enabled = true,
                        isError = false,
                        containerColor = CoopTheme.colorScheme.surfaceVariant,
                        contentColor = CoopTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(0.7f),
                        borderColor = CoopTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = {
                            cuotaToAdd?.let {
                                onAddCuota(it)
                                cuotaToAdd = null
                            }
                        },
                        enabled = cuotaToAdd != null
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Agregar cuota")
                    }
                }
            }
            items(state.selectedCuotas.size) { cuotaIndex ->
                val cuota = state.selectedCuotas[cuotaIndex]
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CoopText(cuota.nombreAsociado, style = CoopTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.weight(1f))
                    CoopText(formatMoney(cuota.montoCuota), style = CoopTheme.typography.bodyLarge)
                    IconButton(onClick = { onRemoveCuota(cuota) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar cuota")
                    }
                }
            }

            // Sección Préstamos
            item {
                CoopText(
                    text = "Préstamos",
                    style = CoopTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
            item {
                var loanToAdd by remember { mutableStateOf<LoanQuota?>(null) }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CoopDropdown(
                        items = state.prestamosDisponibles,
                        selectedItem = loanToAdd,
                        onItemSelected = { loanToAdd = it },
                        itemToString = { it.nombrePago },
                        modifier = Modifier.weight(1.3f),
                        label = { CoopText("Selecciona...") },
                        placeholder = { CoopText("Elige") }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    CoopOutlinedTextField(
                        value = loanToAdd?.monto?.toString() ?: "",
                        onValueChange = {},
                        label = { CoopText("Monto") },
                        placeholder = { CoopText("Monto") },
                        readOnly = true,
                        enabled = true,
                        isError = false,
                        containerColor = CoopTheme.colorScheme.surfaceVariant,
                        contentColor = CoopTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(0.7f),
                        borderColor = CoopTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = {
                            loanToAdd?.let {
                                onAddLoanQuota(it)
                                loanToAdd = null
                            }
                        },
                        enabled = loanToAdd != null
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Agregar préstamo")
                    }
                }
            }
            items(state.selectedLoanQuotas.size) { loanIndex ->
                val loan = state.selectedLoanQuotas[loanIndex]
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CoopText(loan.nombrePago, style = CoopTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.weight(1f))
                    CoopText(formatMoney(loan.monto), style = CoopTheme.typography.bodyLarge)
                    IconButton(onClick = { onRemoveLoanQuota(loan) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar préstamo")
                    }
                }
            }

            // Sección Multas
            item {
                CoopText(
                    text = "Multas",
                    style = CoopTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
            item {
                var fineToAdd by remember { mutableStateOf<FinePayAffiliate?>(null) }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CoopDropdown(
                        items = state.multasDisponibles,
                        selectedItem = fineToAdd,
                        onItemSelected = { fineToAdd = it },
                        itemToString = { it.fineName },
                        modifier = Modifier.weight(1.3f),
                        label = { CoopText("Selecciona...") },
                        placeholder = { CoopText("Elige") }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    CoopOutlinedTextField(
                        value = fineToAdd?.fineAmount?.toString() ?: "",
                        onValueChange = {},
                        label = { CoopText("Monto") },
                        placeholder = { CoopText("Monto") },
                        readOnly = true,
                        enabled = true,
                        isError = false,
                        containerColor = CoopTheme.colorScheme.surfaceVariant,
                        contentColor = CoopTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(0.7f),
                        borderColor = CoopTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = {
                            fineToAdd?.let {
                                onAddFine(it)
                                fineToAdd = null
                            }
                        },
                        enabled = fineToAdd != null
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Agregar multa")
                    }
                }
            }
            items(state.selectedFines.size) { fineIndex ->
                val fine = state.selectedFines[fineIndex]
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CoopText(fine.fineName, style = CoopTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.weight(1f))
                    CoopText(formatMoney(fine.fineAmount), style = CoopTheme.typography.bodyLarge)
                    IconButton(onClick = { onRemoveFine(fine) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar multa")
                    }
                }
            }

            // Sección Aportes de Capital
            item {
                CoopText(
                    text = "Aportes de Capital",
                    style = CoopTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            item {
                var userToAdd by remember { mutableStateOf<BasicUserInfo?>(null) }
                var capitalText by remember { mutableStateOf("") }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CoopDropdown(
                        items = state.usuariosDisponibles,
                        selectedItem = userToAdd,
                        onItemSelected = { userToAdd = it },
                        itemToString = { it.name },
                        modifier = Modifier.weight(1.3f),
                        label = { CoopText("Selecciona...") },
                        placeholder = { CoopText("Elige") }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    CoopOutlinedTextField(
                        value = capitalText,
                        onValueChange = { capitalText = it.filter { c -> c.isDigit() || c == '.' } },
                        label = { Text("Monto") },
                        placeholder = { Text("0.00") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = false,
                        modifier = Modifier.weight(0.7f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = {
                            val amount = capitalText.toFloatOrNull()
                            if (userToAdd != null && amount != null) {
                                onAddCapitalContribution(userToAdd!!, amount)
                                userToAdd = null
                                capitalText = ""
                            }
                        },
                        enabled = userToAdd != null && capitalText.toFloatOrNull() != null
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Agregar aporte")
                    }
                }
            }
            items(state.aportesCapital.size) { aporteIndex ->
                val aporte = state.aportesCapital[aporteIndex]
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CoopText(aporte.userName, style = CoopTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.weight(1f))
                    CoopText(formatMoney(aporte.amount), style = CoopTheme.typography.bodyLarge)
                    IconButton(onClick = { onRemoveCapitalContribution(aporte) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar aporte")
                    }
                }
            }

            // Carga de Imagen
            item {
                CoopOutlinedButton(
                    onClick = { /* TODO: implementar carga de imagen */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    shape = RoundedCornerShape(50)
                ) {
                    Icon(
                        imageVector = Icons.Default.CloudUpload,
                        contentDescription = "Cargar imagen"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    CoopText("Cargar imagen", style = CoopTheme.typography.bodyLarge)
                }
            }

            // Mensaje de validación de monto
            item {
                if (state.errorMontoPago) {
                    CoopText(
                        text = "El monto declarado no coincide con los datos seleccionados.",
                        style = CoopTheme.typography.bodyMedium,
                        color = CoopTheme.colorScheme.error,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(56.dp))
            }
        }
    }
}