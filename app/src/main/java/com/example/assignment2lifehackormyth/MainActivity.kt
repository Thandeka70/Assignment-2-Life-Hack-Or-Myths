package com.example.assignment2lifehackormyth

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.assignment2lifehackormyth.ui.theme.Assignment2LifeHackOrMythTheme
class MainActivity : ComponentActivity() {
    /*
    The array used here hold the quiz questions as string
     */
    private val questions = arrayOf(
        "The Great Wall Of China is visible from space",
        "Bananas grow on trees",
        "Workouts in the morning are  more productive and effective",
        "Coca-Cola can remove rust",
        "Bulls hate the colour red"
    )
    // The array used here stores the answers of the quiz questions from the above array corresponding to each question in the same oder as the quiz questions.

    private val answers = arrayOf(
        false, false, true, true, false
    )
    // the array used here stores the explanations for each quiz answer while mating the same index as the quiz questions ans quiz answers.

    private val explanations = arrayOf(
        "The great wall of china cannot be seen from space.To be more precise it can't be easily seen be seen with the naked eye.",
        "Bananas do not grow on trees, they grow on the worlds largest perennial herbaceous plant.",
        "Finish your gym workouts in the morning , it's less likely to get done in the evening.",
        "when you sock rusted metal in Coca-Cola it can remove the surface rust off the metal after 24 hours or more.",
        "Bulls are actually colour blind to the colour red due to them being dichromats and they are unable to distinguish the colour."
    )

    private var currentQuestion = 0
    private var score = 0
    private val feedbackList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
        showWelcomeScreen()
    }

    /*
     Here were starting displaying our first interface which is activity_welcome
     We are programming so that the interface is connected to the main activity so that it can work
     We then declare the start button , that is going to lead user to the next interface
     the setOnClickListener stores the information of the start button and the actions it should execute
     */

    private fun showWelcomeScreen(){
        setContentView(R.layout.activity_welcome)
        // declarations for the start Button for the activity_welcome
        val btnStart = findViewById<Button>(R.id.btnStart)
        btnStart.setOnClickListener {
            currentQuestion = 0
            score = 0
            feedbackList.clear()
            showQuizScreen()
        }
    }

    /*
    Here is where the second interface will be displayed which is the activity_quiz
    This is where the quiz questions will be displayed as well as the true or false buttons and the next button
     */

    private fun showQuizScreen(){
        setContentView(R.layout.activity_quiz)

        // declarations of the TextViews and buttons for the activity_quiz

        val txtQuiz = findViewById<TextView>(R.id.txtQuiz)
        val btnTrue = findViewById<Button>(R.id.btnTrue)
        val btnFalse = findViewById<Button>(R.id.btnFalse)
        val btnNext = findViewById<Button>(R.id.btnNext)
        val feedbackText = findViewById<TextView>(R.id.feedbackText)

        txtQuiz.text = questions[currentQuestion]
        feedbackText.text = ""
        var answered = false

        btnTrue.setOnClickListener {
            if(!answered){
                checkAnswer(true,feedbackText)  // if answer is true the feedback will be true
                answered = true
            }
        }

        btnFalse.setOnClickListener {
            if (!answered){
                checkAnswer(false, feedbackText)  // if the answer is false the feedback will be false
                answered = true
            }
        }

        btnNext.setOnClickListener {
            currentQuestion++
            if (currentQuestion < questions.size){   // takes user to the score screen to review the results
                showQuizScreen()
            }else{
                showScoreScreen()
            }
        }
    }

    // Here is where the feedback of the users answer will display when they press the true of false buttons
    // After pressing a button the feedback answer will be displayed correct or incorrect


    private fun checkAnswer(userAnswer: Boolean, feedbackText: TextView ){
        val correct = answers[currentQuestion]
        if (userAnswer == correct){
            feedbackText.text = "Correct!"
            score++
            feedbackList.add("Q${currentQuestion + 1}: ")
        }else{
            feedbackText.text = "Incorrect!"
            feedbackList.add("Q${currentQuestion + 1}: ")
        }
    }

    /*
    Here we display our last interface which is the activity_score
    where the user will view their score and find explanations of the answers they got correct and the explanations the answers they got incorrect
     */

    private fun showScoreScreen() {
        setContentView(R.layout.activity_score)

        // declarations of the TextViews and the Buttons for the activity_score

        val txtScore = findViewById<TextView>(R.id.txtScore)
        val txtReview = findViewById<TextView>(R.id.txtReview)
        val btnReview = findViewById<Button>(R.id.btnReview)
        val btnExit = findViewById<Button>(R.id.btnExit)

        txtScore.text = "You scored $score out of ${questions.size}"
        txtReview.text =
            if (score >= 3) "Awesome! You know your life Hacks!" else "Keep learning your life Hacks and Myths!"
        
        btnReview.setOnClickListener { 
            val facts = questions.mapIndexed { index, q->
                "${index + 1}. $q\nAnswer: ${answers[index]} \nExplanation: ${explanations[index]}" // included my explanations where the user can review the correct answers to the quiz.
            }.joinToString ("\n\n")
            Toast.makeText(this, facts,Toast.LENGTH_LONG).show()
        }

        // thew button here with exit the app and start from the beginning of the quiz

        btnExit.setOnClickListener {
            finish()
        }
    }
}
