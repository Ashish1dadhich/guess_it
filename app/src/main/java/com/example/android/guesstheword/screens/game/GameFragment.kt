

package com.example.android.guesstheword.screens.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.GameFragmentBinding

/**
 * Fragment where the game is played
 */
class GameFragment : Fragment() {

    private lateinit var viewModel: GameViewModel


    private lateinit var binding: GameFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.game_fragment,
            container,
            false
        )
        Log.i("GameFragment", "Called ViewModelProvider.of!")
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)


       binding.gameViewModel =viewModel
        binding.lifecycleOwner = this
//        binding.correctButton.setOnClickListener {
//            viewModel.onCorrect()
//   i comment all out this because we  use data binding so view model get communicate directly to xml
//   no need to use ui controller as intermediate
//
//        }
//        binding.skipButton.setOnClickListener {
//            viewModel.onSkip()
//
//
//        }

        viewModel.score.observe(viewLifecycleOwner, Observer { newScore ->
            binding.scoreText.text = newScore.toString()
        })
//     why we comment this because we use data binding to live data to communicate directly to views(define in xml)
//    so no need for this code   we can do same for other but we do not for my references
//        viewModel.word.observe(viewLifecycleOwner, Observer { newWord ->
//            binding.wordText.text = newWord.toString()
//        })
        viewModel.eventGameFinish.observe(viewLifecycleOwner, Observer { hasFinished ->
         if(hasFinished){
             gameFinished()
             viewModel.onGameFinishComplete()
         }
        })
//        we use Transformation.map in game view model to comment this
//        viewModel.currentTimer.observe(viewLifecycleOwner, Observer { newTime->
//        binding.timerText.text=DateUtils.formatElapsedTime(newTime)
//
//        })

        return binding.root

    }

    /**
     * Resets the list of words and randomizes the order
     */


    /**
     * Called when the game is finished
     */
    private fun gameFinished() {
        val action = GameFragmentDirections.actionGameToScore(viewModel.score.value ?: 0)
        findNavController(this).navigate(action)
    }

}




