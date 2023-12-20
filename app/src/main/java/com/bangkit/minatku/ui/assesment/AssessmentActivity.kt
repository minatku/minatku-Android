package com.bangkit.minatku.ui.assesment

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bangkit.minatku.R
import com.bangkit.minatku.data.Hasil
import com.bangkit.minatku.data.ViewModelFactory
import com.bangkit.minatku.data.response.PertanyaanDataItem
import com.bangkit.minatku.databinding.ActivityAssesmentBinding

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

        // Fetch questions when the activity is created
        viewModel.getQuestions()

        // Set click listeners for option buttons
        binding.option1Button.setOnClickListener { onOptionSelected(0) }
        binding.option2Button.setOnClickListener { onOptionSelected(1) }
        binding.option3Button.setOnClickListener { onOptionSelected(2) }

        // Set click listeners for navigation buttons
        binding.nextButton.setOnClickListener { onNextClicked() }
        binding.backButton.setOnClickListener { onBackClicked() }
    }

    private fun onOptionSelected(option: Int) {
        // Update ViewModel with the selected option
        viewModel.updateSelectedOption(option)

        // Reset background color for all buttonsc 
        binding.option1Button.setBackgroundResource(R.drawable.button_default)
        binding.option2Button.setBackgroundResource(R.drawable.button_default)
        binding.option3Button.setBackgroundResource(R.drawable.button_default)

        // Set background color for the selected button
        when (option) {
            0 -> binding.option1Button.setBackgroundColor(ContextCompat.getColor(this, R.color.blue))
            1 -> binding.option2Button.setBackgroundColor(ContextCompat.getColor(this, R.color.blue))
            2 -> binding.option3Button.setBackgroundColor(ContextCompat.getColor(this, R.color.blue))
        }
    }

    private fun onNextClicked() {
        // Fetch the next question when the Next button is clicked
        viewModel.getNextQuestion()
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
}
