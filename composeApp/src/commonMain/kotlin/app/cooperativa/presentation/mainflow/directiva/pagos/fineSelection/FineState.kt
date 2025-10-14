package app.cooperativa.presentation.mainflow.directiva.pagos.fineSelection

import app.cooperativa.data.model.dto.FineType
import app.cooperativa.utils.todayLocalDate
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate

data class FineSelectionState(
    val userName: String = "",
    val fineDetails: List<FineDetailUiState> = emptyList(),
    val isLoading: Boolean = true,
    val isSaving: Boolean = false,
    val errorMessage: String? = null
)

data class FineDetailUiState(
    val id: Int,
    val name: String,
    val date: LocalDate,
    val amount: String,
)