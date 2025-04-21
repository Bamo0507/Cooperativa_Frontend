package cooperativa.composeapp.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import cooperativa.composeapp.generated.resources.Poppins_Black
import cooperativa.composeapp.generated.resources.Poppins_BlackItalic
import cooperativa.composeapp.generated.resources.Poppins_Bold
import cooperativa.composeapp.generated.resources.Poppins_BoldItalic
import cooperativa.composeapp.generated.resources.Poppins_ExtraBold
import cooperativa.composeapp.generated.resources.Poppins_ExtraBoldItalic
import cooperativa.composeapp.generated.resources.Poppins_ExtraLight
import cooperativa.composeapp.generated.resources.Poppins_ExtraLightItalic
import cooperativa.composeapp.generated.resources.Poppins_Italic
import cooperativa.composeapp.generated.resources.Poppins_Light
import cooperativa.composeapp.generated.resources.Poppins_LightItalic
import cooperativa.composeapp.generated.resources.Poppins_Medium
import cooperativa.composeapp.generated.resources.Poppins_MediumItalic
import cooperativa.composeapp.generated.resources.Poppins_Regular
import cooperativa.composeapp.generated.resources.Poppins_SemiBold
import cooperativa.composeapp.generated.resources.Poppins_SemiBoldItalic
import cooperativa.composeapp.generated.resources.Poppins_Thin
import cooperativa.composeapp.generated.resources.Poppins_ThinItalic
import cooperativa.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font

@Composable
fun PoppinsFontFamily() = FontFamily(
    // Thin
    Font(Res.font.Poppins_Thin, weight = FontWeight.Thin),
    Font(Res.font.Poppins_ThinItalic, weight = FontWeight.Thin, style = FontStyle.Italic),

    // ExtraLight
    Font(Res.font.Poppins_ExtraLight, weight = FontWeight.ExtraLight),
    Font(Res.font.Poppins_ExtraLightItalic, weight = FontWeight.ExtraLight, style = FontStyle.Italic),

    // Light
    Font(Res.font.Poppins_Light, weight = FontWeight.Light),
    Font(Res.font.Poppins_LightItalic, weight = FontWeight.Light, style = FontStyle.Italic),

    // Regular
    Font(Res.font.Poppins_Regular, weight = FontWeight.Normal),
    Font(Res.font.Poppins_Italic, weight = FontWeight.Normal, style = FontStyle.Italic),

    // Medium
    Font(Res.font.Poppins_Medium, weight = FontWeight.Medium),
    Font(Res.font.Poppins_MediumItalic, weight = FontWeight.Medium, style = FontStyle.Italic),

    // SemiBold
    Font(Res.font.Poppins_SemiBold, weight = FontWeight.SemiBold),
    Font(Res.font.Poppins_SemiBoldItalic, weight = FontWeight.SemiBold, style = FontStyle.Italic),

    // Bold
    Font(Res.font.Poppins_Bold, weight = FontWeight.Bold),
    Font(Res.font.Poppins_BoldItalic, weight = FontWeight.Bold, style = FontStyle.Italic),

    // ExtraBold
    Font(Res.font.Poppins_ExtraBold, weight = FontWeight.ExtraBold),
    Font(Res.font.Poppins_ExtraBoldItalic, weight = FontWeight.ExtraBold, style = FontStyle.Italic),

    // Black
    Font(Res.font.Poppins_Black, weight = FontWeight.Black),
    Font(Res.font.Poppins_BlackItalic, weight = FontWeight.Black, style = FontStyle.Italic),
)

@Composable
fun PoppinsTypography() = Typography().run {
    val fontFamily = PoppinsFontFamily()
    copy(
        displayLarge    = displayLarge.copy(fontFamily = fontFamily),
        displayMedium   = displayMedium.copy(fontFamily = fontFamily),
        displaySmall    = displaySmall.copy(fontFamily = fontFamily),

        headlineLarge   = headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium  = headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall   = headlineSmall.copy(fontFamily = fontFamily),

        titleLarge      = titleLarge.copy(fontFamily = fontFamily),
        titleMedium     = titleMedium.copy(fontFamily = fontFamily),
        titleSmall      = titleSmall.copy(fontFamily = fontFamily),

        bodyLarge       = bodyLarge.copy(fontFamily = fontFamily),
        bodyMedium      = bodyMedium.copy(fontFamily = fontFamily),
        bodySmall       = bodySmall.copy(fontFamily = fontFamily),

        labelLarge      = labelLarge.copy(fontFamily = fontFamily),
        labelMedium     = labelMedium.copy(fontFamily = fontFamily),
        labelSmall      = labelSmall.copy(fontFamily = fontFamily),
    )
}