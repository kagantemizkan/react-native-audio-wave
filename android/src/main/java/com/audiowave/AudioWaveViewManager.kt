package com.audiowave

import android.graphics.Color
import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.core.net.toUri
import com.facebook.react.bridge.Dynamic
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.bridge.ReadableType
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp
import com.masoudss.lib.WaveformSeekBar
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class AudioWaveViewManager : SimpleViewManager<WaveformSeekBar>() {
    private var reactContext: ThemedReactContext? = null

    override fun getName(): String {
        return "AudioWaveView"
    }
    
    override fun createViewInstance(reactContext: ThemedReactContext): WaveformSeekBar {
        this.reactContext = reactContext
        
        return WaveformSeekBar(reactContext).apply {
            progress = 0f
            maxProgress = 100f
            waveWidth = 5f
            waveGap = 2f
            waveMinHeight = 5f
            waveCornerRadius = 2f
            // Note: Not using waveGravity since it's causing issues
            waveBackgroundColor = Color.LTGRAY
            waveProgressColor = Color.BLUE
        }
    }
    
    @ReactProp(name = "progress")
    fun setProgress(view: WaveformSeekBar, progress: Float) {
        view.progress = progress
    }
    
    @ReactProp(name = "maxProgress")
    fun setMaxProgress(view: WaveformSeekBar, maxProgress: Float) {
        view.maxProgress = maxProgress
    }
    
    @ReactProp(name = "visibleProgress")
    fun setVisibleProgress(view: WaveformSeekBar, visibleProgress: Float) {
        view.visibleProgress = visibleProgress
    }
    
    @ReactProp(name = "waveWidth")
    fun setWaveWidth(view: WaveformSeekBar, waveWidth: Float) {
        view.waveWidth = waveWidth
    }
    
    @ReactProp(name = "waveGap")
    fun setWaveGap(view: WaveformSeekBar, waveGap: Float) {
        view.waveGap = waveGap
    }
    
    @ReactProp(name = "waveMinHeight")
    fun setWaveMinHeight(view: WaveformSeekBar, waveMinHeight: Float) {
        view.waveMinHeight = waveMinHeight
    }
    
    @ReactProp(name = "waveCornerRadius")
    fun setWaveCornerRadius(view: WaveformSeekBar, waveCornerRadius: Float) {
        view.waveCornerRadius = waveCornerRadius
    }
    
    @ReactProp(name = "wavePaddingTop")
    fun setWavePaddingTop(view: WaveformSeekBar, padding: Int) {
        view.wavePaddingTop = padding
    }
    
    @ReactProp(name = "wavePaddingBottom")
    fun setWavePaddingBottom(view: WaveformSeekBar, padding: Int) {
        view.wavePaddingBottom = padding
    }
    
    @ReactProp(name = "wavePaddingLeft")
    fun setWavePaddingLeft(view: WaveformSeekBar, padding: Int) {
        view.wavePaddingLeft = padding
    }
    
    @ReactProp(name = "wavePaddingRight")
    fun setWavePaddingRight(view: WaveformSeekBar, padding: Int) {
        view.wavePaddingRight = padding
    }
    
    @ReactProp(name = "waveBackgroundColor")
    fun setWaveBackgroundColor(view: WaveformSeekBar, color: Dynamic) {
        when (color.type) {
            ReadableType.Number -> view.waveBackgroundColor = color.asInt()
            ReadableType.String -> {
                try {
                    view.waveBackgroundColor = Color.parseColor(color.asString())
                } catch (e: Exception) {
                    view.waveBackgroundColor = Color.LTGRAY
                }
            }
            else -> view.waveBackgroundColor = Color.LTGRAY
        }
    }
    
    @ReactProp(name = "waveProgressColor")
    fun setWaveProgressColor(view: WaveformSeekBar, color: Dynamic) {
        when (color.type) {
            ReadableType.Number -> view.waveProgressColor = color.asInt()
            ReadableType.String -> {
                try {
                    view.waveProgressColor = Color.parseColor(color.asString())
                } catch (e: Exception) {
                    view.waveProgressColor = Color.BLUE
                }
            }
            else -> view.waveProgressColor = Color.BLUE
        }
    }
    
    @ReactProp(name = "markerWidth")
    fun setMarkerWidth(view: WaveformSeekBar, width: Float) {
        view.markerWidth = width
    }
    
    @ReactProp(name = "markerColor")
    fun setMarkerColor(view: WaveformSeekBar, color: Dynamic) {
        when (color.type) {
            ReadableType.Number -> view.markerColor = color.asInt()
            ReadableType.String -> {
                try {
                    view.markerColor = Color.parseColor(color.asString())
                } catch (e: Exception) {
                    view.markerColor = Color.GREEN
                }
            }
            else -> view.markerColor = Color.GREEN
        }
    }
    
    @ReactProp(name = "markerTextSize")
    fun setMarkerTextSize(view: WaveformSeekBar, size: Float) {
        view.markerTextSize = size
    }
    
    @ReactProp(name = "markerTextColor")
    fun setMarkerTextColor(view: WaveformSeekBar, color: Dynamic) {
        when (color.type) {
            ReadableType.Number -> view.markerTextColor = color.asInt()
            ReadableType.String -> {
                try {
                    view.markerTextColor = Color.parseColor(color.asString())
                } catch (e: Exception) {
                    view.markerTextColor = Color.RED
                }
            }
            else -> view.markerTextColor = Color.RED
        }
    }
    
    @ReactProp(name = "markerTextPadding")
    fun setMarkerTextPadding(view: WaveformSeekBar, padding: Float) {
        view.markerTextPadding = padding
    }
    
    @ReactProp(name = "audioFileUri")
    fun setAudioFile(view: WaveformSeekBar, uri: String?) {
        if (uri.isNullOrEmpty()) {
            return
        }

        try {
            // Handle local file
            if (uri.startsWith("/") || uri.startsWith("file://")) {
                val path = uri.replace("file://", "")
                val file = File(path)
                if (file.exists()) {
                    view.setSampleFrom(file)
                } else {
                    // Log an error or handle the missing file case
                    android.util.Log.e("AudioWaveViewManager", "Local file not found: $uri")
                }
            } 
            // Handle remote URL
            else if (uri.startsWith("http://") || uri.startsWith("https://")) {
                downloadAndProcess(view, uri)
            } else {
                android.util.Log.e("AudioWaveViewManager", "Unsupported URI format: $uri")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            android.util.Log.e("AudioWaveViewManager", "Error setting audio file: ${e.message}")
        }
    }
    
    @ReactProp(name = "samples")
    fun setSamples(view: WaveformSeekBar, samples: Dynamic?) {
        if (samples == null || samples.type != ReadableType.Array) {
            return
        }
        
        try {
            val array = samples.asArray()
            val sampleArray = IntArray(array.size())
            for (i in 0 until array.size()) {
                sampleArray[i] = array.getInt(i)
            }
            view.setSampleFrom(sampleArray)
        } catch (e: Exception) {
            e.printStackTrace()
            android.util.Log.e("AudioWaveViewManager", "Error setting samples: ${e.message}")
        }
    }
    
    private fun downloadAndProcess(view: WaveformSeekBar, url: String) {
        val context = reactContext ?: return
        val cacheDir = context.cacheDir
        
        // Generate a unique filename to avoid conflicts
        val fileName = "audio_${UUID.randomUUID()}.mp3"
        val outputFile = File(cacheDir, fileName)
        
        // Use coroutines for background operations
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Download the file
                URL(url).openStream().use { input ->
                    FileOutputStream(outputFile).use { output ->
                        input.copyTo(output)
                    }
                }
                
                // Update UI on main thread
                withContext(Dispatchers.Main) {
                    if (outputFile.exists()) {
                        view.setSampleFrom(outputFile)
                        android.util.Log.d("AudioWaveViewManager", "Successfully loaded: $url")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                android.util.Log.e("AudioWaveViewManager", "Error downloading file: ${e.message}")
            }
        }
    }
}