package com.nxt.katalisreading.utils

import android.content.Context
import android.net.Uri
import java.io.File


object FileUtils {
    fun uriToFile(uri: Uri, context: Context): File {
        val inputStream = context.contentResolver.openInputStream(uri)!!
        val file = File(
            context.cacheDir,
            "img_${System.currentTimeMillis()}.jpg"
        )
        file.outputStream().use { output ->
            inputStream.copyTo(output)
        }
        return file
    }
}