package com.jaqen.buhuaxin.utils

import android.content.Context
import android.os.StatFs
import android.text.format.Formatter
import java.io.File

object FileUtil {
    fun getCacheSize(context: Context): Long{
        var size = 0L

        context.externalCacheDir?.run {
            size += getFileSize(this)
        }
        context.getExternalFilesDir(null)?.run {
            size += getFileSize(this)
        }

        return size
    }

    fun getFileSize(file: File): Long{
        if (!file.exists())
            return 0

        var size = 0L

        val files = file.listFiles()

        for (fl in files){
            if (fl.isDirectory)
               size += getFileSize(fl)
            else
                size += fl.length()
        }

        return size
    }

    fun deleteCache(context: Context){

        context.externalCacheDir?.run {
            for (file in this.listFiles())
                file.deleteRecursively()
        }

        context.getExternalFilesDir(null)?.run {
            for (file in this.listFiles())
                file.deleteRecursively()
        }
    }
}