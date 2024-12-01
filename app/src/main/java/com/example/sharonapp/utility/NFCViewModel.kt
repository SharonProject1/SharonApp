package com.example.sharonapp.utility

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NFCViewModel : ViewModel() {
    private val _isTagged = MutableStateFlow(false)
    val isTagged: StateFlow<Boolean> = _isTagged

    private val _hasMotionDetected = MutableStateFlow(false)
    val hasMotionDetected: StateFlow<Boolean> = _hasMotionDetected

    private val _isGameOver = MutableStateFlow(false)
    val isGameOver: StateFlow<Boolean> = _isGameOver

    fun setIsTagged(isTagged: Boolean) {
        _isTagged.value = isTagged
    }

    fun setHasMotionDetected(detected: Boolean) {
        _hasMotionDetected.value = detected
    }

    fun setGameOver(gameOver: Boolean) {
        _isGameOver.value = gameOver
    }
}
