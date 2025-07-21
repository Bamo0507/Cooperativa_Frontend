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
actual fun convertHeicToJpeg(heicBytes: ByteArray): ByteArray? {
    val nsData = heicBytes.usePinned {
        NSData.dataWithBytes(it.addressOf(0), heicBytes.size.toULong())!!
    }

    val uiImage = UIImage(data = nsData)
    val jpegData: NSData? = UIImageJPEGRepresentation(uiImage, 1.0)

    return jpegData?.toByteString()?.toByteArray()
}