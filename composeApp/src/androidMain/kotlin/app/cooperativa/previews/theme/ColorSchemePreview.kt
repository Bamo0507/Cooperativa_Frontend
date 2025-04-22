package app.cooperativa.previews.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.cooperativa.theme.CoopTheme
import app.cooperativa.theme.components.CoopButton
import app.cooperativa.theme.components.CoopSurface
import app.cooperativa.theme.components.CoopText

@Preview(showBackground = true)
@Composable
fun CoopSchemePreview(){
    ThemeComparison(
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun ThemeComparison(
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
    ){
        ThemePreview(
            darkTheme = true,
            modifier = Modifier.fillMaxWidth().weight(1f)
        )
        ThemePreview(
            darkTheme = false,
            modifier = Modifier.fillMaxWidth().weight(1f)
        )
    }
}

@Composable
private fun ThemePreview(
    darkTheme: Boolean,
    modifier: Modifier = Modifier
){
    CoopTheme (
        darkTheme = darkTheme
    ){
        CoopSurface(
            modifier = modifier
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                CoopText(
                    text = if (darkTheme) "Dark Theme" else "Light Theme",
                    style = CoopTheme.typography.headlineSmall
                )
                RowOfDotsNormal(
                    modifier = Modifier.fillMaxWidth()
                )
                RowOfDotsON(
                    modifier = Modifier.fillMaxWidth()
                )
                RowOfButtons(
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun SimpleDot(
    modifier: Modifier = Modifier,
    color: Color
){
    Box(
        modifier = modifier
            .size(32.dp)
            .background(
                color = color,
                shape = CircleShape
            ),
    )
}

@Composable
fun RowOfDotsNormal(
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        SimpleDot(
            color = CoopTheme.colorScheme.primary
        )
        SimpleDot(
            color = CoopTheme.colorScheme.secondary
        )
        SimpleDot(
            color = CoopTheme.colorScheme.tertiary
        )

    }
}

@Composable
fun RowOfDotsON(
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        SimpleDot(
            color = CoopTheme.colorScheme.onPrimary
        )
        SimpleDot(
            color = CoopTheme.colorScheme.onSecondary
        )
        SimpleDot(
            color = CoopTheme.colorScheme.onTertiary
        )

    }
}

@Composable
private fun RowOfButtons(
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CoopButton(
            onClick = {},
            modifier = Modifier.weight(1f)
        ) {
            CoopText(
                "Click Me"
            )
        }
    }
}

