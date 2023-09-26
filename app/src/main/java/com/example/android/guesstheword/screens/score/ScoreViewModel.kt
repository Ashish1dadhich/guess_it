package com.example.android.guesstheword.screens.score

import android.util.Log
import androidx.lifecycle.ViewModel

class ScoreViewModel(finalScore: String): ViewModel() {
    init {
        Log.i("ScoreViewModel","Final Score is $finalScore")
    }
}