package ru.frozenpriest.taskautomaton.utils

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.core.net.toUri
import java.io.File
import java.io.FileOutputStream

object CopyInInternalStorage {
    fun copyFileToInternalStorage(uri: Uri, newDirName: String, context: Context): Uri? {
        val returnCursor: Cursor? = context.contentResolver.query(
            uri, arrayOf(
                OpenableColumns.DISPLAY_NAME, OpenableColumns.SIZE
            ), null, null, null
        )


        /*
         * Get the column indexes of the data in the Cursor,
         *     * move to the first row in the Cursor, get the data,
         *     * and display it.
         * */
        returnCursor?.let {
            val nameIndex: Int = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            val sizeIndex: Int = returnCursor.getColumnIndex(OpenableColumns.SIZE)
            returnCursor.moveToFirst()
            val name: String = returnCursor.getString(nameIndex)
            val size = returnCursor.getLong(sizeIndex).toString()
            val output: File = if (newDirName != "") {
                val dir = File(context.filesDir.toString() + "/" + newDirName)
                if (!dir.exists()) {
                    dir.mkdir()
                }
                File(context.filesDir.toString() + "/" + newDirName + "/" + name)
            } else {
                File(context.filesDir.toString() + "/" + name)
            }
            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                val outputStream = FileOutputStream(output)
                var read: Int
                val bufferSize = 1024
                val buffers = ByteArray(bufferSize)
                inputStream?.let {
                    while (inputStream.read(buffers).also { read = it } != -1) {
                        outputStream.write(buffers, 0, read)
                    }
                    inputStream.close()
                }
                outputStream.close()
            } catch (e: Exception) {
                Log.e("Exception", e.toString())
            }
            return output.toUri()
        }
        return null
    }
}