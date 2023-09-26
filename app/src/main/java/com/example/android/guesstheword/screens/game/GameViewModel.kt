package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class GameViewModel:ViewModel(){

    companion object{
      // this is when game is over
        private const val  DONE =0L

    //this is the number of milliseconds in a sec
      private const val ONE_SECOND =1000L

      //this is the total time of game
        private const val  COUNTDOWN_TIME=10000L
    }
     private val timer:CountDownTimer
     private val _currentTimer= MutableLiveData<Long>()
    val currentTimer:LiveData<Long>
        get()=_currentTimer
    val currentTimeString = Transformations.map(currentTimer,{ time->
        DateUtils.formatElapsedTime(time)
    })

    // The current word
   private var _word = MutableLiveData<String>()
    val word:LiveData<String>
        get() = _word



    // The current score
   private  var _score = MutableLiveData<Int>()
    val score:LiveData<Int>
        get() =_score

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    private  val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish:LiveData<Boolean>
        get()=_eventGameFinish
    init {
        _eventGameFinish.value = false
        Log.i("GameViewModel", "GameViewModel created!")
        resetList()
        nextWord()
        _score.value = 0
        //creates a timer which trigger the end of the game when its finish
        timer = object :CountDownTimer(COUNTDOWN_TIME, ONE_SECOND){

            override fun onTick(millisUntilFinished:Long){
                _currentTimer.value=(millisUntilFinished/ ONE_SECOND)
            }

            override fun onFinish() {
                _currentTimer.value = DONE
                _eventGameFinish.value =true
            }
        }
        timer.start()
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
        Log.i("GameViewModel","GameViewModel destroyed!")
    }  private fun resetList() {
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        wordList.shuffle()
    } private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
         resetList()
        }
        _word.value = wordList.removeAt(0)


    }
    fun onSkip() {
        _score.value =(score.value)?.minus(1)
        nextWord()
    }

     fun onCorrect() {
        _score.value=(score.value)?.plus(1)
        nextWord()
    }
    fun onGameFinishComplete(){
        _eventGameFinish.value = false
    }


}
