package app.cooperativa.presentation.mainflow.socios.pagos.agregarPago

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.cooperativa.data.model.dto.BasicUserInfo
import app.cooperativa.data.model.dto.CapitalContribution
import app.cooperativa.data.model.dto.FinePayAffiliate
import app.cooperativa.data.model.dto.LoanQuota
import app.cooperativa.data.model.dto.QuotaAffiliate
import app.cooperativa.presentation.utils.ErrorScreen
import app.cooperativa.presentation.utils.LoadingScreen
import app.cooperativa.theme.CoopTheme
import app.cooperativa.theme.components.CoopButton
import app.cooperativa.theme.components.CoopDropdown
import app.cooperativa.theme.components.CoopOutlinedButton
import app.cooperativa.theme.components.CoopOutlinedCard
import app.cooperativa.theme.components.CoopOutlinedTextField
import app.cooperativa.theme.components.CoopText
import app.cooperativa.theme.components.CoopTopBar
import app.cooperativa.utils.formatMoney
import coil3.compose.AsyncImage
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.io.KmpFile
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import cooperativa.composeapp.generated.resources.Res
import cooperativa.composeapp.generated.resources.ic_payment_error
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@Composable
fun SPagoEnviarRoute(
    onBackClick: () -> Unit,
    onBackWithConfettiClick: () -> Unit,
    viewModel: SPagoEnviarViewModel = koinInject()
){
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalPlatformContext.current

    SPagoEnviarScreen(
        state = state,
        onBackClick = onBackClick,
        onNombreChange = viewModel::updateNombrePago,
        onMontoChange = viewModel::updateMontoPago,
        onPickImage = { image ->
            viewModel.handleImagePicked(context, image)
        },
        onCuentaChange = viewModel::updateNumeroCuenta,
        onBoletaChange = viewModel::updateNumeroBoleta,
        onSend = {
            viewModel.submitPayment()
        },
        onBackWithConfettiClick = onBackWithConfettiClick,
        onAddCuota = viewModel::addCuota,
        onRemoveCuota = viewModel::removeCuota,
        onAddLoanQuota = viewModel::addLoanQuota,
        onRemoveLoanQuota = viewModel::removeLoanQuota,
        loadData = viewModel::loadData,
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
    onBackWithConfettiClick: () -> Unit,
    onPickImage: (KmpFile) -> Unit,
    onNombreChange: (String) -> Unit,
    onMontoChange: (String) -> Unit,
    onCuentaChange: (String) -> Unit,
    onBoletaChange: (String) -> Unit,
    onSend: () -> Unit,
    loadData: () -> Unit,
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
    LaunchedEffect(
        state.paymentSentSuccesffully
    ){
        if(state.paymentSentSuccesffully){
            onBackWithConfettiClick()
        }
    }

    var showFirstTimeHelp by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(state.hasSentPayment) {
        showFirstTimeHelp = !state.hasSentPayment
    }
    var showAmountMismatchDialog by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(state.errorMontoPago) {
        if (state.errorMontoPago) {
            showAmountMismatchDialog = true
        }
    }
    // File picker for selecting or changing the proof‑of‑payment image
    val picker = rememberFilePickerLauncher(
        type = FilePickerFileType.Image,
        selectionMode = FilePickerSelectionMode.Single
    ) { files ->
        files.firstOrNull()?.let { file ->
            onPickImage(file)
        }
    }
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
            if(!state.isLoading && state.errorMessage == null ){
                ExtendedFloatingActionButton(
                    icon = { Icon(Icons.Default.Send, contentDescription = "Enviar") },
                    text = { Text("Enviar") },
                    onClick = onSend,
                    containerColor = CoopTheme.colorScheme.primary,
                    contentColor = CoopTheme.colorScheme.onPrimary
                )
            }
        }
    ) { padding ->
        if (state.isLoading) {
            LoadingScreen(
                message = "Cargando..."
            )
        } else if (state.errorMessage != null) {
            ErrorScreen(
                message = "Error al cargar información.",
                onRetry = loadData
            )
        } else {
            LazyColumn(
                modifier = modifier.padding(padding).padding(horizontal = 26.dp, vertical = 6.dp).padding(top=4.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                item {
                    CoopText(
                        text = "Información Básica",
                        style = CoopTheme.typography.titleMedium,
                        color = CoopTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
                item {
                    CoopOutlinedTextField(
                        value = state.nombrePago,
                        onValueChange = { input ->
                            // Sin saltos de línea y con tope de 20 chars
                            val sanitized = input.replace("\n", " ").replace("\r", " ").take(20)
                            onNombreChange(sanitized)
                        },
                        label = { Text("Nombre") },
                        placeholder = { Text("Ingrese nombre") },
                        isError = false,
                        singleLine = true,
                        maxLines = 1,
                        unfocusedBorderColor = CoopTheme.colorScheme.primary,
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(4.dp))
                }
                item {
                    CoopOutlinedTextField(
                        value = state.montoPagoText,
                        onValueChange = onMontoChange,
                        label = { Text("Monto") },
                        placeholder = { Text("0.00") },
                        prefix = { Text("Q ") },
                        isError = false,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true,
                        digitsOnly = true,
                        allowDecimal = true,
                        allowNegative = false,
                        maxDecimalPlaces = 2,
                        unfocusedBorderColor = CoopTheme.colorScheme.primary,
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(4.dp))
                }
                item {
                    CoopOutlinedTextField(
                        value = state.numberoCuenta,
                        onValueChange = onCuentaChange,
                        label = { Text("Número de cuenta") },
                        placeholder = { Text("Solo dígitos") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = false,
                        unfocusedBorderColor = CoopTheme.colorScheme.primary,
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(4.dp))
                }
                item {
                    CoopOutlinedTextField(
                        value = state.numeroBoleta,
                        onValueChange = onBoletaChange,
                        label = { Text("Número de boleta") },
                        placeholder = { Text("Solo dígitos") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = false,
                        unfocusedBorderColor = CoopTheme.colorScheme.primary,
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(4.dp))
                }
                // Sección Cuotas
                item {
                    CoopText(
                        text = "Cuotas",
                        style = CoopTheme.typography.titleMedium,
                        color = CoopTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(top = 12.dp, bottom = 8.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
                item {
                    var cuotaToAdd by remember { mutableStateOf<QuotaAffiliate?>(null) }
                    CoopDropdown(
                        items = state.cuotasDisponibles,
                        selectedItem = cuotaToAdd,
                        onItemSelected = { cuotaToAdd = it },
                        itemToString = { it.identifier },
                        placeholder = "Elige",
                        modifier = Modifier.fillMaxWidth(),
                        enableSearch = true,
                        radioSize = 20.dp,
                        optionMaxLines = 2
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    CoopOutlinedTextField(
                        value = cuotaToAdd?.montoCuota?.toString() ?: "",
                        onValueChange = {},
                        label = { CoopText("Monto", color = CoopTheme.colorScheme.onSurface) },
                        placeholder = { CoopText("Monto", color = CoopTheme.colorScheme.onSurface) },
                        prefix = { Text("Q ") },
                        readOnly = true,
                        enabled = true,
                        isError = false,
                        containerColor = CoopTheme.colorScheme.surfaceVariant,
                        contentColor = CoopTheme.colorScheme.onSurface,
                        modifier = Modifier.fillMaxWidth(),
                        borderColor = CoopTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    CoopButton(
                        onClick = {
                            cuotaToAdd?.let {
                                onAddCuota(it)
                                cuotaToAdd = null
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = cuotaToAdd != null
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.Add, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            CoopText("Agregar cuota", style = CoopTheme.typography.bodyMedium)
                        }
                    }
                }
                items(state.selectedCuotas.size) { cuotaIndex ->
                    val cuota = state.selectedCuotas[cuotaIndex]
                    SelectedItemRow(
                        title = cuota.identifier,
                        amountText = formatMoney(cuota.montoCuota),
                        leadingIcon = Icons.Filled.Receipt,
                        onRemove = { onRemoveCuota(cuota) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    )
                }

                // Sección Préstamos
                item {
                    CoopText(
                        text = "Préstamos",
                        style = CoopTheme.typography.titleMedium,
                        color = CoopTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(top = 12.dp, bottom = 8.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
                item {
                    var loanToAdd by remember { mutableStateOf<LoanQuota?>(null) }
                    CoopDropdown(
                        items = state.prestamosDisponibles,
                        selectedItem = loanToAdd,
                        onItemSelected = { loanToAdd = it },
                        itemToString = { it.nombrePago },
                        placeholder = "Elige",
                        modifier = Modifier.fillMaxWidth(),
                        enableSearch = false,
                        radioSize = 20.dp,
                        optionMaxLines = 2
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    CoopOutlinedTextField(
                        value = loanToAdd?.monto?.toString() ?: "",
                        onValueChange = {},
                        label = { CoopText("Monto", color = CoopTheme.colorScheme.onSurface) },
                        placeholder = { CoopText("Monto", color = CoopTheme.colorScheme.onSurface) },
                        prefix = { Text("Q ") },
                        readOnly = true,
                        enabled = true,
                        isError = false,
                        containerColor = CoopTheme.colorScheme.surfaceVariant,
                        contentColor = CoopTheme.colorScheme.onSurface,
                        modifier = Modifier.fillMaxWidth(),
                        borderColor = CoopTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    CoopButton(
                        onClick = {
                            loanToAdd?.let {
                                onAddLoanQuota(it)
                                loanToAdd = null
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = loanToAdd != null
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.Add, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            CoopText("Agregar préstamo", style = CoopTheme.typography.bodyMedium)
                        }
                    }
                }
                items(state.selectedLoanQuotas.size) { loanIndex ->
                    val loan = state.selectedLoanQuotas[loanIndex]
                    SelectedItemRow(
                        title = loan.nombrePago,
                        amountText = formatMoney(loan.monto),
                        leadingIcon = Icons.Filled.CreditCard,
                        onRemove = { onRemoveLoanQuota(loan) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    )
                }

                // Sección Multas
                item {
                    CoopText(
                        text = "Multas",
                        style = CoopTheme.typography.titleMedium,
                        color = CoopTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(top = 12.dp, bottom = 8.dp),
                        fontWeight = FontWeight.Bold,
                    )
                }
                item {
                    var fineToAdd by remember { mutableStateOf<FinePayAffiliate?>(null) }
                    CoopDropdown(
                        items = state.multasDisponibles,
                        selectedItem = fineToAdd,
                        onItemSelected = { fineToAdd = it },
                        itemToString = { it.fineName },
                        placeholder = "Elige",
                        modifier = Modifier.fillMaxWidth(),
                        enableSearch = false,
                        radioSize = 20.dp,
                        optionMaxLines = 2
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    CoopOutlinedTextField(
                        value = fineToAdd?.fineAmount?.toString() ?: "",
                        onValueChange = {},
                        label = { CoopText("Monto", color = CoopTheme.colorScheme.onSurface) },
                        placeholder = { CoopText("Monto", color = CoopTheme.colorScheme.onSurface) },
                        prefix = { Text("Q ") },
                        readOnly = true,
                        enabled = true,
                        isError = false,
                        containerColor = CoopTheme.colorScheme.surfaceVariant,
                        contentColor = CoopTheme.colorScheme.onSurface,
                        modifier = Modifier.fillMaxWidth(),
                        borderColor = CoopTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    CoopButton(
                        onClick = {
                            fineToAdd?.let {
                                onAddFine(it)
                                fineToAdd = null
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = fineToAdd != null
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.Add, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            CoopText("Agregar multa", style = CoopTheme.typography.bodyMedium)
                        }
                    }
                }
                items(state.selectedFines.size) { fineIndex ->
                    val fine = state.selectedFines[fineIndex]
                    SelectedItemRow(
                        title = fine.fineName,
                        amountText = formatMoney(fine.fineAmount),
                        leadingIcon = Icons.Filled.Receipt,
                        onRemove = { onRemoveFine(fine) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    )
                }

                // Sección Aportes de Capital
                item {
                    CoopText(
                        text = "Aportes de Capital",
                        style = CoopTheme.typography.titleMedium,
                        color = CoopTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(top = 12.dp, bottom = 8.dp),
                        fontWeight = FontWeight.Bold
                    )
                }

                item {
                    var userToAdd by remember { mutableStateOf<BasicUserInfo?>(null) }
                    var capitalText by remember { mutableStateOf("") }
                    CoopDropdown(
                        items = state.usuariosDisponibles,
                        selectedItem = userToAdd,
                        onItemSelected = { userToAdd = it },
                        itemToString = { it.name },
                        placeholder = "Elige",
                        modifier = Modifier.fillMaxWidth(),
                        enableSearch = false,
                        radioSize = 20.dp,
                        optionMaxLines = 2
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    CoopOutlinedTextField(
                        value = capitalText,
                        onValueChange = { capitalText = it },
                        label = { Text("Monto") },
                        placeholder = { Text("0.00") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true,
                        isError = false,
                        unfocusedBorderColor = CoopTheme.colorScheme.primary,
                        modifier = Modifier.fillMaxWidth(),
                        digitsOnly = true,
                        allowDecimal = true,
                        allowNegative = false,
                        maxDecimalPlaces = 2,
                        prefix = { Text("Q ") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    CoopButton(
                        onClick = {
                            val amount = capitalText.toFloatOrNull()
                            if (userToAdd != null && amount != null) {
                                onAddCapitalContribution(userToAdd!!, amount)
                                userToAdd = null
                                capitalText = ""
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = userToAdd != null && capitalText.toFloatOrNull() != null
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.Add, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            CoopText("Agregar aporte", style = CoopTheme.typography.bodyMedium)
                        }
                    }
                }
                items(state.aportesCapital.size) { aporteIndex ->
                    val aporte = state.aportesCapital[aporteIndex]
                    SelectedItemRow(
                        title = aporte.userName,
                        amountText = formatMoney(aporte.amount),
                        leadingIcon = Icons.Filled.Wallet,
                        onRemove = { onRemoveCapitalContribution(aporte) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    )
                }

                // Sección Imagen
                if (state.bytesImagen != null) {
                    item {
                        Spacer(modifier = Modifier.height(12.dp))

                        ImagePreview(
                            image = state.bytesImagen,
                            onSwitchImage = { picker.launch() },
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }

                // Carga de Imagen
                item {
                    CoopOutlinedButton(
                        onClick = { picker.launch() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        shape = RoundedCornerShape(50)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CloudUpload,
                            contentDescription = if (state.bytesImagen == null) "Cargar imagen" else "Cambiar imagen"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        CoopText(
                            text = if (state.bytesImagen == null) "Cargar imagen" else "Cambiar imagen",
                            style = CoopTheme.typography.bodyLarge,
                            color = CoopTheme.colorScheme.onSurface
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(64.dp))
                }
            }
        }
        if (showFirstTimeHelp) {
            FirstTimeHelpDialog(onDismiss = { showFirstTimeHelp = false })
        }
        if (showAmountMismatchDialog) {
            AmountMismatchDialog(onDismiss = { showAmountMismatchDialog = false })
        }
    }
}

@Composable
fun ImagePreview(
    image: ByteArray,
    onSwitchImage: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(16f/9f)
            .clip(RoundedCornerShape(16.dp))
            .padding(vertical = 8.dp)
    ){
        AsyncImage(
            model = image,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }
}

@Composable
private fun FirstTimeHelpDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = Icons.Default.CloudUpload,
                contentDescription = null,
                tint = CoopTheme.colorScheme.onSecondary,
                modifier = Modifier.size(36.dp)
            )
        },
        title = {
            CoopText(
                text = "¿Cómo presentar tu pago?",
                style = CoopTheme.typography.titleMedium,
                color = CoopTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            CoopText(
                text = "1) Completa Nombre, monto, número de cuenta y boleta.\n" +
                       "2) Agregar datos: Cuotas, Préstamos, Multas y Aportes de Capital.\n" +
                       "3) Carga una foto del comprobante con “Cargar imagen”.\n" +
                       "4) Verifica que el Monto declarado coincida con la suma de lo indicado.\n" +
                       "5) Toca “Enviar” para presentar tu pago.",
                style = CoopTheme.typography.bodyMedium,
                color = CoopTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
        },
        confirmButton = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CoopButton(
                    onClick = onDismiss
                ) {
                    CoopText(
                        text = "Entendido",
                        color = CoopTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        containerColor = CoopTheme.colorScheme.surface
    )
}

@Composable
private fun AmountMismatchDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Image(
                painter = painterResource(Res.drawable.ic_payment_error),
                contentDescription = null,
                modifier = Modifier.size(56.dp)
            )
        },
        title = {
            CoopText(
                text = "Montos no coinciden",
                style = CoopTheme.typography.titleMedium,
                color = CoopTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            CoopText(
                text = "El total que declaraste no coincide con la suma de los pagos.\nRevisa los montos antes de continuar.",
                style = CoopTheme.typography.bodyMedium,
                color = CoopTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
        },
        confirmButton = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CoopButton(onClick = onDismiss) {
                    CoopText(
                        text = "Entendido",
                        color = CoopTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        containerColor = CoopTheme.colorScheme.surface
    )
}

@Composable
private fun SelectedItemRow(
    title: String,
    amountText: String,
    leadingIcon: ImageVector,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    CoopOutlinedCard(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(12.dp))

            // Título: hasta 2 líneas, elipsis si se excede, centrado verticalmente
            CoopText(
                text = title,
                style = CoopTheme.typography.bodyLarge,
                color = CoopTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f).padding(vertical = 8.dp),
            )

            // Monto: ancho mínimo para evitar saltos y alineado al final
            CoopText(
                text = amountText,
                style = CoopTheme.typography.bodyLarge,
                color = CoopTheme.colorScheme.onSurface,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .widthIn(min = 84.dp)
                    .padding(start = 8.dp)
            )

            IconButton(onClick = onRemove) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Eliminar",
                    tint = CoopTheme.colorScheme.onSecondary,
                )
            }
        }
    }
}
