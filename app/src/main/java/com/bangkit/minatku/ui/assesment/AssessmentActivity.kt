package com.bangkit.minatku.ui.assesment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bangkit.minatku.R
import com.bangkit.minatku.data.Hasil
import com.bangkit.minatku.data.ViewModelFactory
import com.bangkit.minatku.data.response.PertanyaanDataItem
import com.bangkit.minatku.databinding.ActivityAssesmentBinding
import com.bangkit.minatku.ui.endtest.EndTestActivity

class AssessmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAssesmentBinding
    private lateinit var viewModel: AssessmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssesmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel
        viewModel = viewModels<AssessmentViewModel> {
            ViewModelFactory.getInstance(applicationContext)
        }.value

        // Observe questions
        viewModel.questions.observe(this, Observer { result ->
            when (result) {
                is Hasil.Loading -> {
                    // Show loading progress
                    binding.pbAssessment.visibility = View.VISIBLE
                }
                is Hasil.Success -> {
                    // Update UI with the first question
                    binding.pbAssessment.visibility = View.GONE
                    updateQuestion(result.data.firstOrNull())
                    updateNextButton(result.data.size > 1)
                }
                is Hasil.Error -> {
                    // Handle error
                    binding.pbAssessment.visibility = View.GONE
                    // Handle error case, e.g., show a toast or display an error message
                }
            }
        })

        // Observe assessment result
        viewModel.assessmentResult.observe(this, Observer { result ->
            when (result) {
                is Hasil.Loading -> {
                    // Show loading progress if needed
                }
                is Hasil.Success -> {
                    // Handle success, e.g., navigate to the next screen
                }
                is Hasil.Error -> {
                    // Handle error, e.g., show a toast or display an error message
                }
            }
        })

        viewModel.navigateToFinish.observe(this, Observer { navigate ->
            if (navigate) {
                // Navigate to EndTestActivity
                navigateToEndTestActivity()
                // Reset the navigation event
                viewModel.onNavigateToFinishComplete()
            }
        })

        // Fetch questions when the activity is created
        viewModel.getQuestions()

        // Set click listeners for option buttons
        binding.option1Button.setOnClickListener { onOptionSelected(0) }
        binding.option2Button.setOnClickListener { onOptionSelected(1) }
        binding.option3Button.setOnClickListener { onOptionSelected(2) }

        // Set click listeners for navigation buttons
        binding.nextButton.setOnClickListener { onNextClicked() }
        binding.backButton.setOnClickListener { onBackClicked() }

        viewModel.getQuestions()

        // Display the first question
        viewModel.questions.observe(this, Observer { result ->
            if (result is Hasil.Success) {
                updateQuestion(result.data.firstOrNull())
                updateNextButton(result.data.size > 1)
            }
        })
    }

    private fun onNextClicked() {
        // Reset button styles for all options
        resetButtonStyle(binding.option1Button)
        resetButtonStyle(binding.option2Button)
        resetButtonStyle(binding.option3Button)

        // Fetch the next question when the Next button is clicked
        viewModel.getNextQuestion()

        // Display the updated question
        viewModel.currentQuestion.observe(this, Observer { result ->
            if (result is Hasil.Success) {
                updateQuestion(result.data)
                updateNextButton(true)
            }
        })

        // Update the UI with the current question ID
        val currentQuestionId = viewModel.getCurrentQuestionId()
        // Handle the logic to update UI based on currentQuestionId
        // For example, you can update a TextView to display the current question ID.
    }

    private fun onOptionSelected(option: Int) {
        // Reset style for all buttons
        resetButtonStyle(binding.option1Button)
        resetButtonStyle(binding.option2Button)
        resetButtonStyle(binding.option3Button)

        // Set style for the selected button
        when (option) {
            0 -> setButtonStyle(binding.option1Button, R.color.blue, android.R.color.black)
            1 -> setButtonStyle(binding.option2Button, R.color.blue, android.R.color.black)
            2 -> setButtonStyle(binding.option3Button, R.color.blue, android.R.color.black)
        }

        // Update ViewModel with the selected option
        viewModel.updateSelectedOption(option)
    }

    private fun resetButtonStyle(button: Button) {
        button.setBackgroundResource(R.drawable.button_default)
        button.setTextColor(ContextCompat.getColor(this, android.R.color.white))
    }

    private fun setButtonStyle(button: Button, backgroundColor: Int, textColor: Int) {
        button.setBackgroundColor(ContextCompat.getColor(this, backgroundColor))
        button.setTextColor(ContextCompat.getColor(this, textColor))
    }


    private fun onBackClicked() {
        // Handle the Back button click if needed
    }

    private fun updateQuestion(question: PertanyaanDataItem?) {
        // Update UI with the new question
        question?.let {
            binding.questionText.text = it.isiPertanyaan
            // Update other UI elements if needed
        }
    }

    private fun updateNextButton(hasNextQuestion: Boolean) {
        val currentQuestionIndex = viewModel.getCurrentQuestionIndex()

        if (currentQuestionIndex >= 11) {
            // If it's the 12th question or beyond, show the Finish button
            binding.nextButton.visibility = View.GONE
            binding.finishbutton.visibility = View.VISIBLE
            binding.finishbutton.setOnClickListener { onFinishClicked() }
        } else {
            // Otherwise, show the Next button and hide the Finish button
            binding.nextButton.visibility = View.VISIBLE
            binding.finishbutton.visibility = View.GONE
            binding.nextButton.setOnClickListener { onNextClicked() }
        }

        // Hide the Back button if it's the first question
        binding.backButton.visibility = if (currentQuestionIndex == 0) View.GONE else View.VISIBLE
    }

    private fun onFinishClicked() {
        // Show loading indicator
        showLoading()

        // Get the selected options
        val selectedOptions = viewModel.getSelectedOptions()

        // Submit the assessment
        viewModel.submitAssessment(selectedOptions)

        // Observe the assessment result
        viewModel.assessmentResult.observe(this, Observer { result ->
            when (result) {
                is Hasil.Success -> {
                    // Hide loading indicator
                    hideLoading()

                    // Handle success, e.g., navigate to the next screen
                    navigateToEndTestActivity()
                }
                is Hasil.Error -> {
                    // Hide loading indicator
                    hideLoading()
                    navigateToEndTestActivity()
                    // Handle error, e.g., show a toast or display an error message
                    // For example: Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                }
                is Hasil.Loading -> {
                    // Do nothing or update UI for ongoing loading
                }
            }
        })
    }

    private fun showLoading() {
        // Show loading indicator (e.g., a ProgressBar)
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        // Hide loading indicator (e.g., a ProgressBar)
        binding.progressBar.visibility = View.GONE
    }


    private fun navigateToEndTestActivity() {
        // Intent to EndTestActivity
        val intent = Intent(this, EndTestActivity::class.java)
        startActivity(intent)
        finish() // Optional: finish the current activity if needed
    }
}
