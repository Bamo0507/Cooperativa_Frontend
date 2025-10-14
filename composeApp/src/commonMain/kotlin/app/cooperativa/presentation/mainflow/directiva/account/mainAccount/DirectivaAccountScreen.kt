package app.cooperativa.presentation.mainflow.directiva.account.mainAccount

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.TextButton
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.cooperativa.theme.CoopTheme
import app.cooperativa.theme.components.CoopIconButton
import app.cooperativa.theme.components.CoopText
import cooperativa.composeapp.generated.resources.Res
import cooperativa.composeapp.generated.resources.account_background
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@Composable
fun DirectivaAccountRoute(
    onChangeToMember: () -> Unit,
    onLogout: () -> Unit,
    viewModel: DirectivaAccountViewModel = koinInject()
) {
    DirectivaAccountScreen(
        onChangeToMember = onChangeToMember,
        onLogout = onLogout,
        clearPrefs = viewModel::logout
    )
}

@Composable
fun DirectivaAccountScreen(
    onChangeToMember: () -> Unit,
    onLogout: () -> Unit,
    clearPrefs: () -> Unit
) {
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
            val uriHandler = LocalUriHandler.current

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

                    // Fixed subtitle for Directiva account
                    CoopText(
                        text = "Miembro de Directiva",
                        color = CoopTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            // Cambiar a Socio
            CoopIconButton(
                onClick = onChangeToMember,
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
                        text = "Cambiar a Socio",
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Reglamento (abre documento externo)
            CoopIconButton(
                onClick = {
                    uriHandler.openUri("https://docs.google.com/document/d/1MFyeO61mIqF7tCS0mNMbDVYPaw8Hmsrn/edit?usp=sharing&amp;ouid=112943840371733396643&amp;rtpof=true&amp;sd=true")
                },
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
                        imageVector = Icons.Default.Description,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    CoopText(
                        text = "Reglamento",
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Cerrar sesión (destructive)
            CoopIconButton(
                onClick = {
                    onLogout()
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
        }
    }
}
