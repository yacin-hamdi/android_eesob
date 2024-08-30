package ym.tutorials.eesobcamera.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ym.tutorials.eesobcamera.data.manager.objectDetection.ObjectDetectionManagerImpl
import ym.tutorials.eesobcamera.domain.manager.objectDetection.ObjectDetectionManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesObjectDetectionManager(
        @ApplicationContext context: Context
    ): ObjectDetectionManager {
        return ObjectDetectionManagerImpl(context)
    }
}