package ym.tutorials.eesobcamera.common

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import androidx.core.graphics.alpha
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import ym.tutorials.eesobcamera.domain.model.Detection
import kotlin.random.Random

object DrawDetectionBox{

     fun onDrawBox(
        bitmap: Bitmap,
        box: Detection
    ){
        val bitmapWidth = bitmap.width * 1f
        val bitmapHeight = bitmap.height * 1f

        val left = box.x1 * bitmapWidth
        val top = box.y1 * bitmapHeight
        val right = box.x2 * bitmapWidth
        val bottom = box.y2 * bitmapHeight

        val canvas = Canvas(bitmap)
        val paint = Paint().apply {
            style = Paint.Style.STROKE
            strokeWidth = 8f
            color = getColorForLabel(box.clsName)      // Assigning a color based on the object label
        }

        val scaledBox = RectF(
            left ,
            top ,
            right,
            bottom
        ).also {
            // Ensure the bounding box doesn't go outside of the screen dimensions
            it.left = it.left.coerceAtLeast(0f)
            it.top = it.top.coerceAtLeast(0f)
            it.right = it.right.coerceAtMost(bitmapWidth)
            it.bottom = it.bottom.coerceAtMost(bitmapHeight)
        }

        val androidColor = android.graphics.Color.argb(
            (paint.color.alpha * 255),
            (paint.color.red * 255),
            (paint.color.green * 255),
            (paint.color.blue * 255)
        )

//        val density = bitmap.density
        val pixelSize = 60f
        val text = "${box.clsName} ${(box.cnf * 100).toInt()}%"

        canvas.drawRect(scaledBox.left,scaledBox.top, scaledBox.right, scaledBox.bottom, paint)
        canvas.drawText(
            text,
            scaledBox.left,
            scaledBox.top - 10,
            Paint().apply{
                color = androidColor
                textSize = pixelSize
            })




    }


    private val labelColorMap = mutableMapOf<String, Int>()

    /**
     * Gets a color associated with a particular label. If a color is not already assigned,
     * it generates a random color and associates it with the label for consistent coloring.
     *
     * @param label The label for which a color is required.
     * @return The color associated with the given label.
     */
    private fun getColorForLabel(label: String): Int {
        return labelColorMap.getOrPut(label) {
            // Generates a random color for the label if it doesn't exist in the map.
            Random.nextInt()
        }
    }




}

