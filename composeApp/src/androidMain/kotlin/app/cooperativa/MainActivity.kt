package app.cooperativa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import android.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.WindowInsets
import androidx.core.view.WindowCompat
import app.cooperativa.di.getKoinModules
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidContext

@Suppress("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startKoin {
            androidContext(this@MainActivity)
            modules(getKoinModules())
        }

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                /* lightScrim = */ Color.TRANSPARENT,
                /* darkScrim  = */ Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.auto(
                /* lightScrim = */ Color.TRANSPARENT,
                /* darkScrim  = */ Color.TRANSPARENT
            )
        )

        window.statusBarColor = android.graphics.Color.TRANSPARENT
        WindowCompat.getInsetsController(window, window.decorView)?.isAppearanceLightStatusBars = true

        setContent {
            Scaffold(contentWindowInsets = WindowInsets(0)) {
                App()
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}