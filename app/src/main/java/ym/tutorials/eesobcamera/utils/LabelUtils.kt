package ym.tutorials.eesobcamera.utils

import android.content.Context
import ym.tutorials.eesobcamera.utils.Constants.LABELS_PATH
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

object LabelsUtils {

    fun getAllLabels(context: Context):List<String>{
        val labels = mutableListOf<String>()
        try {
            val inputStream: InputStream = context.assets.open(LABELS_PATH)
            val reader = BufferedReader(InputStreamReader(inputStream))

            var line: String? = reader.readLine()
            while (line != null && line != ""){
                labels.add(line)
                line = reader.readLine()
            }

            reader.close()
            inputStream.close()
        } catch (e: IOException){
            e.printStackTrace()
        }

        return labels
    }
}