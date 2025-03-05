package com.audiowave

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.module.annotations.ReactModule

@ReactModule(name = AudioWaveModule.NAME)
class AudioWaveModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
    companion object {
        const val NAME = "AudioWaveView"
    }
    
    override fun getName(): String {
        return NAME
    }
}