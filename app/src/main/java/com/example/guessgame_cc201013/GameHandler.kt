package com.example.guessgame_cc201013
import kotlin.random.Random

class GameHandler {
    private var lettersUsed: String = ""
    private lateinit var underscoreWord: String
    private lateinit var wordToGuess: String
    private val maxlives = 6
    private var currentlives = 0

    fun startNewGame(): GameMode {
        lettersUsed = ""
        currentlives = 0

        //select a random number from 0 till array.length/size
        val randomIndex = Random.nextInt(0, CapitalArray.words.size)

        //select the word from array words[index]
        wordToGuess = CapitalArray.words[randomIndex]
        // generate the wordToGuess number of letters
        generateUnderscores(wordToGuess)
        return getGameState()
    }

    fun generateUnderscores(word: String) {
        val sb = StringBuilder()
        word.forEach { char ->
            if (char == ' ') {
                sb.append(' ')
            }else if(char == '\'') {
                sb.append('\'')
            }
            else {
                sb.append("_")
            }
        }
        underscoreWord = sb.toString()
    }


    //prevent letter from being pressed twice
    fun play(letter: Char): GameMode {
        if (lettersUsed.contains(letter)) {
            return GameMode.Running(currentlives , underscoreWord)
        }
        lettersUsed += letter
        val indexes = mutableListOf<Int>()
        //Match if any of the letters i clicked present in the "wordToGuess"
        wordToGuess.forEachIndexed { index, char ->
            if (char.equals(letter, true)) {
                indexes.add(index)
            }
        }

        var finalUnderscoreWord = "" + underscoreWord // _ _ _ _ _ _ _ -> E _ _ _ _ _ _
        indexes.forEach { index ->
            val sb = StringBuilder(finalUnderscoreWord).also { it.setCharAt(index, letter) }
            finalUnderscoreWord = sb.toString()
        }

        if (indexes.isEmpty()) {
            currentlives++
            //Log.d("NumberofTries" , currentTries.toString())
        }

        underscoreWord = finalUnderscoreWord
        return getGameState()
    }


    private fun getGameState(): GameMode {
        if (underscoreWord.equals(wordToGuess, true)) {
            return GameMode.Won(wordToGuess)
        }
        if (currentlives == maxlives) {
            return GameMode.Lost(wordToGuess)
        }

        return GameMode.Running(currentlives , underscoreWord)
    }
}