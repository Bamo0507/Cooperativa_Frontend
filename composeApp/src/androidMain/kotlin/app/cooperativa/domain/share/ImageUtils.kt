package app.cooperativa.domain.share

import android.graphics.BitmapFactory
import android.graphics.Bitmap
import java.io.ByteArrayOutputStream

actual fun convertToJpeg(bytes: ByteArray, quality: Int): ByteArray {
    val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        ?: return bytes // si no se pudo decodificar, devolvemos original (evita crash)
    val out = ByteArrayOutputStream()
    bmp.compress(Bitmap.CompressFormat.JPEG, quality.coerceIn(60, 100), out)
    return out.toByteArray()
}