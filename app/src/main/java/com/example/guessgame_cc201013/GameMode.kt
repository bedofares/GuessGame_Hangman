package com.example.guessgame_cc201013

sealed class GameMode {
    class Running(var currentlives: Int,
                  val underscoreWord: String,
        //val drawable: Int
    ) : GameMode()
    class Lost(val wordToGuess: String) : GameMode()
    class Won(val wordToGuess: String) : GameMode()
}
