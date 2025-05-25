//package app.cooperativa.previews.boardLoans
//
//import android.content.res.Configuration
//import androidx.compose.foundation.layout.padding
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import app.cooperativa.data.localdb.SolicitudPrestamoMockData
//import app.cooperativa.data.localdb.PrestamoMockData
//import app.cooperativa.presentation.mainflow.directiva.prestamos.prestamosGeneral.PrestamoScreen
//import app.cooperativa.presentation.mainflow.directiva.prestamos.prestamosGeneral.DPrestamoState
//import app.cooperativa.theme.CoopTheme
//
//// Datos de ejemplo (mock)
//private val sampleReqLoans = SolicitudPrestamoMockData.getAllBasicInfo()
//private val sampleAllLoans = PrestamoMockData.getAllPrestamos()
//
//// Estado base que reutilizan todos los previews
//private val dummyState = DPrestamoState(
//    selectedTabIndex = 0,
//    reqLoans = sampleReqLoans,
//    allLoans = sampleAllLoans,
//    prestamosVigentes = sampleAllLoans.filter { it.mensualidadesPrestamo.size < it.plazoMeses },
//    prestamosCompletados = sampleAllLoans.filter { it.mensualidadesPrestamo.size >= it.plazoMeses }
//)
//
//@Preview(
//    name = "Solicitudes Light Mode",
//    showBackground = true,
//    uiMode = Configuration.UI_MODE_NIGHT_NO
//)
//@Composable
//fun PrestamosSolicitudesPreviewLight() {
//    CoopTheme {
//        PrestamoScreen(
//            state = dummyState.copy(selectedTabIndex = 0),
//            onTabSelected = {},
//            onSearchQueryChanged = {},
//            onPendingLoanClick = {},
//            modifier = Modifier.padding(16.dp)
//        )
//    }
//}
//
//@Preview(
//    name = "Solicitudes Dark Mode",
//    showBackground = true,
//    uiMode = Configuration.UI_MODE_NIGHT_YES
//)
//@Composable
//fun PrestamosSolicitudesPreviewDark() {
//    CoopTheme {
//        PrestamoScreen(
//            state = dummyState.copy(selectedTabIndex = 0),
//            onTabSelected = {},
//            onSearchQueryChanged = {},
//            onPendingLoanClick = {},
//            modifier = Modifier.padding(16.dp)
//        )
//    }
//}
//
//@Preview(
//    name = "Vigentes Light Mode",
//    showBackground = true,
//    uiMode = Configuration.UI_MODE_NIGHT_NO
//)
//@Composable
//fun PrestamosVigentesPreviewLight() {
//    CoopTheme {
//        PrestamoScreen(
//            state = dummyState.copy(selectedTabIndex = 1),
//            onTabSelected = {},
//            onSearchQueryChanged = {},
//            onPendingLoanClick = {},
//            modifier = Modifier.padding(16.dp)
//        )
//    }
//}
//
//@Preview(
//    name = "Vigentes Dark Mode",
//    showBackground = true,
//    uiMode = Configuration.UI_MODE_NIGHT_YES
//)
//@Composable
//fun PrestamosVigentesPreviewDark() {
//    CoopTheme {
//        PrestamoScreen(
//            state = dummyState.copy(selectedTabIndex = 1),
//            onTabSelected = {},
//            onSearchQueryChanged = {},
//            onPendingLoanClick = {},
//            modifier = Modifier.padding(16.dp)
//        )
//    }
//}
//
//@Preview(
//    name = "Completados Light Mode",
//    showBackground = true,
//    uiMode = Configuration.UI_MODE_NIGHT_NO
//)
//@Composable
//fun PrestamosCompletadosPreviewLight() {
//    CoopTheme {
//        PrestamoScreen(
//            state = dummyState.copy(selectedTabIndex = 2),
//            onTabSelected = {},
//            onSearchQueryChanged = {},
//            onPendingLoanClick = {},
//            modifier = Modifier.padding(16.dp)
//        )
//    }
//}
//
//@Preview(
//    name = "Completados Dark Mode",
//    showBackground = true,
//    uiMode = Configuration.UI_MODE_NIGHT_YES
//)
//@Composable
//fun PrestamosCompletadosPreviewDark() {
//    CoopTheme {
//        PrestamoScreen(
//            state = dummyState.copy(selectedTabIndex = 2),
//            onTabSelected = {},
//            onSearchQueryChanged = {},
//            onPendingLoanClick = {},
//            modifier = Modifier.padding(16.dp)
//        )
//    }
//}