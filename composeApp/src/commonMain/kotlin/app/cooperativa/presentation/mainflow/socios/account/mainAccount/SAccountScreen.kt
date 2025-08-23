package app.cooperativa.presentation.mainflow.socios.account.mainAccount

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.cooperativa.theme.CoopTheme
import app.cooperativa.theme.components.CoopIconButton
import app.cooperativa.theme.components.CoopText
import cooperativa.composeapp.generated.resources.Res
import cooperativa.composeapp.generated.resources.account_background
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@Composable
fun SAccountRoute(
    onLogOutClick: () -> Unit,
    onChangeToDirectiva: () -> Unit,
    viewModel: SAccountViewModel = koinInject()
){
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    SAccountScreen(
        onLogOutClick = onLogOutClick,
        onChangeToDirectiva = onChangeToDirectiva,
        state = state,
        clearPrefs = viewModel::logout
    )
}

@Composable
fun SAccountScreen(
    state: SAccountState,
    onLogOutClick: () -> Unit,
    onChangeToDirectiva: () -> Unit,
    clearPrefs: () -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = CoopTheme.colorScheme.surface)
    ) {
        // Header image
        Image(
            painter = painterResource(Res.drawable.account_background),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )

        // Subtle gradient overlay for better contrast
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            CoopTheme.colorScheme.tertiary.copy(alpha = 0.35f)
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Space down from header
            Spacer(modifier = Modifier.height(140.dp))

            // Floating profile card (minimal, no heavy shadows)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(CoopTheme.colorScheme.surfaceVariant)
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Avatar placeholder
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(CoopTheme.colorScheme.secondary.copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = null,
                            tint = CoopTheme.colorScheme.secondary,
                            modifier = Modifier.size(56.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    CoopText(
                        text = "Cuenta",
                        fontWeight = FontWeight.Bold,
                        color = CoopTheme.colorScheme.onSurface
                    )

                    if (state.userType.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        CoopText(
                            text = if(state.userType == "General") "Asociado a CSPI" else "Miembro de Directiva",
                            color = CoopTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            // Cambiar a Directiva (shown based on current state)
            if (state.userType == "Directive") {
                CoopIconButton(
                    onClick = onChangeToDirectiva,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = CoopTheme.colorScheme.primary.copy(alpha = 0.65f),
                        contentColor = CoopTheme.colorScheme.onSurface
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Groups,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        CoopText(
                            text = "Cambiar a Directiva",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
            }

            // Cerrar sesión (destructive, clearer semantics)
            CoopIconButton(
                onClick = {
                    onLogOutClick()
                    clearPrefs()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clip(RoundedCornerShape(12.dp)),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = CoopTheme.colorScheme.rejected.copy(alpha = 0.15f),
                    contentColor = CoopTheme.colorScheme.rejected
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    CoopText(
                        text = "Cerrar Sesión",
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 2.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    CoopText(
                        text = "Política de Privacidad",
                        color = CoopTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        modifier = Modifier.clickable { /* TODO: Open Privacy Policy */ },
                        style = CoopTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    CoopText(
                        text = "  ·  ",
                        color = CoopTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                    )
                    CoopText(
                        text = "Términos",
                        color = CoopTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        modifier = Modifier.clickable { /* TODO: Open Terms */ },
                        style = CoopTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
