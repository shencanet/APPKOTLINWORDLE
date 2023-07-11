package com.example.wordle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var game: WordleGame
    private lateinit var wordTextView: TextView
    private lateinit var guessEditText: EditText
    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        game = WordleGame()
        wordTextView = findViewById(R.id.letter1TextView) // Inicializa wordTextView
        guessEditText = findViewById(R.id.guessEditText)
        resultTextView = findViewById(R.id.resultTextView)

        game.generateNewWord()
        wordTextView.text = game.getWordToGuess() // Actualiza el TextView con la palabra a adivinar

        val checkButton: Button = findViewById(R.id.checkButton)

        checkButton.setOnClickListener {
            val guess = guessEditText.text.toString()
            val result = game.checkGuess(guess)
            resultTextView.text = result // Actualiza el TextView con el resultado de la suposición

            if (game.hasWon()) {


            } else if (game.hasLost()) {
                // Implementa la lógica para mostrar un mensaje de derrota
            }
        }
    }
}

class WordleGame {
    private var wordToGuess: String = ""
    private var attemptsLeft: Int = 10

    fun resetGame() {
        wordToGuess = ""


    }


    fun generateNewWord() {
        val words = listOf("apple", "banana", "carrot", "dragon", "elephant")
        val randomIndex = (0 until words.size).random()
        wordToGuess = words[randomIndex]
        attemptsLeft = 10
    }

    fun getWordToGuess(): String {
        return wordToGuess
    }

    fun checkGuess(guess: String): String {
        if (guess.length != wordToGuess.length) {
            return "Invalid guess. The word has ${wordToGuess.length} letters."
        }

        var exactMatches = 0
        var partialMatches = 0

        val wordToGuessChars = wordToGuess.toCharArray()
        val guessChars = guess.toCharArray()

        for (i in wordToGuessChars.indices) {
            if (wordToGuessChars[i] == guessChars[i]) {
                exactMatches++
            } else if (guessChars.contains(wordToGuessChars[i])) {
                partialMatches++
            }
        }

        attemptsLeft--

        return when {
            exactMatches == wordToGuess.length -> "Congratulations! You guessed the word correctly."
            attemptsLeft == 0 -> "Game over. You ran out of attempts. The word was $wordToGuess."
            else -> "Exact matches: $exactMatches, Partial matches: $partialMatches, Attempts left: $attemptsLeft"
        }
    }

    fun hasWon(): Boolean {
        return attemptsLeft > 0 && wordToGuess.isNotEmpty()
    }

    fun hasLost(): Boolean {
        return attemptsLeft == 0 && wordToGuess.isNotEmpty()
    }
}




