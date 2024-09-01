package ym.tutorials.eesobcamera.data.manager.objectDetection


import android.content.Context
import android.graphics.Bitmap
import android.os.SystemClock

import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.gpu.CompatibilityList
import org.tensorflow.lite.gpu.GpuDelegate

import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.common.ops.CastOp
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import ym.tutorials.eesobcamera.domain.manager.objectDetection.ObjectDetectionManager
import ym.tutorials.eesobcamera.domain.model.Detection
import ym.tutorials.eesobcamera.utils.BoxesUtils.bestBox
import ym.tutorials.eesobcamera.utils.Constants.INPUT_IMAGE_TYPE
import ym.tutorials.eesobcamera.utils.Constants.INPUT_MEAN
import ym.tutorials.eesobcamera.utils.Constants.INPUT_STANDARD_DEVIATION
import ym.tutorials.eesobcamera.utils.Constants.MODEL_PATH
import ym.tutorials.eesobcamera.utils.Constants.OUTPUT_IMAGE_TYPE
import ym.tutorials.eesobcamera.utils.LabelsUtils.getAllLabels

import javax.inject.Inject

class ObjectDetectionManagerImpl @Inject constructor(
    private val context: Context,
): ObjectDetectionManager {



    private var interpreter: Interpreter? = null
    private var labels = listOf<String>()

    private var tensorWidth = 0
    private var tensorHeight = 0
    private var numChannel = 0
    private var numElements = 0

    private val imageProcessor = ImageProcessor.Builder()
        .add(NormalizeOp(INPUT_MEAN, INPUT_STANDARD_DEVIATION))
        .add(CastOp(INPUT_IMAGE_TYPE))
        .build()
    init {
        initializeDetector()
    }

    private fun initializeDetector(){
        val model = FileUtil.loadMappedFile(context, MODEL_PATH)
        val options = Interpreter.Options().apply{
            if(CompatibilityList().isDelegateSupportedOnThisDevice){
                this.addDelegate(GpuDelegate(CompatibilityList().bestOptionsForThisDevice))
            }else{
                this.setNumThreads(4)
            }
        }
        interpreter = Interpreter(model, options)

        val inputShape = interpreter?.getInputTensor(0)?.shape() ?: return
        val outputShape = interpreter?.getOutputTensor(0)?.shape() ?: return

        tensorWidth = inputShape[1]
        tensorHeight = inputShape[2]
        numChannel = outputShape[1]
        numElements = outputShape[2]

        labels = getAllLabels(context)

    }

    override fun clear(){
        interpreter?.close()
        interpreter = null
    }



    override fun detectObjectsInBitmap(bitmap: Bitmap): List<Detection>{
        interpreter?: return emptyList()
        if (tensorWidth == 0) return emptyList()
        if (tensorHeight == 0) return emptyList()
        if (numChannel == 0) return emptyList()
        if (numElements == 0) return emptyList()

        var inferenceTime = SystemClock.uptimeMillis()

        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, tensorWidth, tensorHeight, false)

        val tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(resizedBitmap)
        val processedImage = imageProcessor.process(tensorImage)
        val imageBuffer = processedImage.buffer

        val output = TensorBuffer.createFixedSize(intArrayOf(1, numChannel, numElements), OUTPUT_IMAGE_TYPE)
        interpreter?.run(imageBuffer, output.buffer)


        val bestBoxes = bestBox(
            output.floatArray,
            numElements,
            numChannel,
            labels)

        resizedBitmap.recycle()
        inferenceTime = SystemClock.uptimeMillis() - inferenceTime

        if(bestBoxes == null) {
            return emptyList()
        }

        return bestBoxes

    }










}