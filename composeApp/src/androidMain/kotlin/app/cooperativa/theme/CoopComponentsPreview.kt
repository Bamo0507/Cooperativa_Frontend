package app.cooperativa.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.cooperativa.theme.components.CoopButton
import app.cooperativa.theme.components.CoopIcon
import app.cooperativa.theme.components.CoopIconButton
import app.cooperativa.theme.components.CoopOutlinedButton
import app.cooperativa.theme.components.CoopSurface
import app.cooperativa.theme.components.CoopText


@Preview(showBackground = true)
@Composable
fun CoopComponentsPreviewLight() {
    CoopTheme {
        CoopSurface {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                CoopText("Components")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    CoopIconButton(
                        onClick = {}
                    ) {
                        CoopIcon(
                            Icons.Default.Face,
                            contentDescription = "Face",
                            tint = CoopTheme.colorScheme.onPrimary
                        )
                    }
                    CoopIconButton(
                        onClick = {},
                        enabled = false
                    ) {
                        CoopIcon(
                            Icons.Default.Face,
                            contentDescription = "Face",
                            tint = CoopTheme.colorScheme.secondary
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    CoopButton(
                        onClick = {}
                    ) {
                        CoopText("Click!")
                    }
                    CoopButton(
                        onClick = {},
                        enabled = false
                    ) {
                        CoopText("Disabled!")
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    CoopOutlinedButton(
                        onClick = {}
                    ) {
                        CoopText("Click!")
                    }
                    CoopOutlinedButton(
                        onClick = {},
                        enabled = false
                    ) {
                        CoopText("Disabled!")
                    }
                }
            }
        }
    }
}