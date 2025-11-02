package app.cooperativa.presentation.mainflow.directiva.pagos.pagosGeneral

import app.cooperativa.data.model.dto.Fine
import app.cooperativa.data.model.ui.BasicInfoPayment

data class DPaymentsState(
    val selectedTabIndex: Int = 0,
    val searchQuery: String = "",
    val pendingPayments: List<BasicInfoPayment> = emptyList(),
    val paidPayments: List<BasicInfoPayment> = emptyList(),
    val fines: List<Fine> = emptyList(),
    val allPaidPayments: List<BasicInfoPayment> = emptyList(),
    val allPendingPayments: List<BasicInfoPayment> = emptyList(),
    val allFinesList: List<Fine> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,

    // Diálogo de edición
    val isEditDialogVisible: Boolean = false,
    val editFineId: String? = null,
    val editUserId: String? = null,
    val editReason: String = "",
    val editAmountText: String = "",

    val isSubmittingEdit: Boolean = false,
    val editErrorMessage: String? = null,

    // Confetti overlay
    val showConfetti: Boolean = false
)
