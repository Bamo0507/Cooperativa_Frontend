package app.cooperativa.presentation.mainflow.directiva.account.mainAccount

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
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

@Composable
fun DirectivaAccountRoute(
    onChangeToMember: () -> Unit,
    onLogout: () -> Unit
) {
    DirectivaAccountScreen(
        onChangeToMember = onChangeToMember,
        onLogout = onLogout
    )
}

@Composable
fun DirectivaAccountScreen(
    onChangeToMember: () -> Unit,
    onLogout: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent, // para que la imagen no tape todo
                        CoopTheme.colorScheme.surface
                    )
                )
            )
    ) {
        Image(
            painter = painterResource(Res.drawable.account_background),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(120.dp))

            // Ícono de perfil
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .background(CoopTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                // TODO: Cargar imagen desde la galeria, se tendra que hacer un expect y actual para manejar en android e ios
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null,
                    tint = CoopTheme.colorScheme.tertiary,
                    modifier = Modifier
                        .fillMaxSize(1.25f) // Agranda para que sobresalga y tape los bordes internos
                )
            }


            Spacer(modifier = Modifier.height(40.dp))

            // Botón "Cambiar a Socio"
            CoopIconButton(
                onClick = onChangeToMember,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(50))
                    .background(CoopTheme.colorScheme.primary),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = CoopTheme.colorScheme.primary,
                    contentColor = CoopTheme.colorScheme.onPrimary
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

            Spacer(modifier = Modifier.height(20.dp))

            // Botón "Cerrar Sesión"
            CoopIconButton(
                onClick = onLogout,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(50))
                    .background(CoopTheme.colorScheme.primary),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = CoopTheme.colorScheme.primary,
                    contentColor = CoopTheme.colorScheme.onPrimary
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
        }
    }
}