package app.cooperativa.domain.share

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import okio.ByteString.Companion.toByteString
import platform.Foundation.NSData
import platform.Foundation.dataWithBytes
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation

@OptIn(ExperimentalForeignApi::class)
actual fun convertToJpeg(bytes: ByteArray, quality: Int): ByteArray {
    val nsData = bytes.usePinned { NSData.dataWithBytes(it.addressOf(0), bytes.size.toULong())!! }
    val image = UIImage(data = nsData) ?: return bytes
    val q = (quality.coerceIn(60, 100).toDouble() / 100.0)
    val jpeg = UIImageJPEGRepresentation(image, q) ?: return bytes
    return jpeg.toByteString().toByteArray()
}