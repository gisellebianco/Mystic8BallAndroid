package com.example.mystic8ball

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var answerTextView: TextView
    private lateinit var eightBallCard: CardView
    private lateinit var resetButton: Button

    // Array of possible answers for the Mystic 8 Ball
    private val answers = arrayOf(
        "It is certain.",
        "It is decidedly so.",
        "Without a doubt.",
        "Yes, definitely.",
        "You may rely on it.",
        "As I see it, yes.",
        "Most likely.",
        "Outlook good.",
        "Yes.",
        "Signs point to yes.",
        "Reply hazy, try again.",
        "Ask again later.",
        "Better not tell you now.",
        "Cannot predict now.",
        "Concentrate and ask again.",
        "Don't count on it.",
        "My reply is no.",
        "My sources say no.",
        "Outlook not so good.",
        "Very doubtful."
    )

    // State to track if an answer has already been revealed for the current question
    private var hasAnswered: Boolean = false

    // Key for saving instance state
    private val KEY_HAS_ANSWERED = "has_answered"
    private val KEY_CURRENT_ANSWER = "current_answer"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // Initialize UI elements
        answerTextView = findViewById(R.id.answerTextView)
        eightBallCard = findViewById(R.id.eightBallCard)
        resetButton = findViewById(R.id.resetButton)

        // Apply gradient background programmatically for better control
        val colors = intArrayOf(
            ContextCompat.getColor(this, R.color.gray_900), // from-gray-900
            ContextCompat.getColor(this, R.color.gray_700)  // to-gray-700
        )
        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.BL_TR, // Bottom-left to top-right for 'to-br'
            colors
        )
        findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.rootLayout).background = gradientDrawable

        // Restore state if available (e.g., after screen rotation)
        if (savedInstanceState != null) {
            hasAnswered = savedInstanceState.getBoolean(KEY_HAS_ANSWERED, false)
            val savedAnswer = savedInstanceState.getString(KEY_CURRENT_ANSWER, "Tap to reveal...")
            answerTextView.text = savedAnswer
        }

        // Set click listener for the 8-ball
        eightBallCard.setOnClickListener {
            getNewAnswer()
        }

        // Set click listener for the reset button
        resetButton.setOnClickListener {
            resetEightBall()
        }
    }

    /**
     * Function to get a new random answer from the 'answers' array
     * and update the state.
     */
    private fun getNewAnswer() {
        // Only generate a new answer if one hasn't been revealed yet
        if (!hasAnswered) {
            val randomIndex = (0 until answers.size).random()
            answerTextView.text = answers[randomIndex] // Set the new answer
            hasAnswered = true // Mark that an answer has been revealed
        }
    }

    /**
     * Function to reset the 8-ball to its initial state,
     * allowing a new question to be asked.
     */
    private fun resetEightBall() {
        answerTextView.text = "Tap to reveal..." // Reset the answer display
        hasAnswered = false // Reset the answered flag
    }

    /**
     * Called to retrieve per-instance state from an activity before being killed
     * so that the state can be restored in onCreate(Bundle) or onRestoreInstanceState(Bundle).
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_HAS_ANSWERED, hasAnswered)
        outState.putString(KEY_CURRENT_ANSWER, answerTextView.text.toString())
    }
}
