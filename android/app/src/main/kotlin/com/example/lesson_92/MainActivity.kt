package com.example.lesson_92

import android.hardware.camera2.CameraManager
import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity: FlutterActivity() {
    private val CHANNEL = "myPhone"

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
                call, result ->
            if (call.method == "toggleFlashlight") {
                val isOn = call.argument<Boolean>("isOn")
                if (isOn != null) {
                    toggleFlashlight(isOn)
                    result.success("Flashlight toggled")
                } else {
                    result.error("INVALID_ARGUMENT", "Argument 'isOn' is required", null)
                }
            } else {
                result.notImplemented()
            }
        }
    }

    private fun toggleFlashlight(isOn: Boolean) {
        val cameraManager = getSystemService(CAMERA_SERVICE) as CameraManager
        try {
            val cameraId = cameraManager.cameraIdList[0] // Assumes device has a camera at index 0
            cameraManager.setTorchMode(cameraId, isOn)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
