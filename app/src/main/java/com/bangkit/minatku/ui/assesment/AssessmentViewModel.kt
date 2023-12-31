package com.bangkit.minatku.ui.assesment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.minatku.data.Hasil
import com.bangkit.minatku.data.repository.MinatkuRepository
import com.bangkit.minatku.data.response.PertanyaanDataItem
import kotlinx.coroutines.launch

class AssessmentViewModel(private val repository: MinatkuRepository) : ViewModel() {

    private val _assessmentResult = MutableLiveData<Hasil<Boolean>>()
    val assessmentResult: LiveData<Hasil<Boolean>> get() = _assessmentResult

    private val _questions = MutableLiveData<Hasil<List<PertanyaanDataItem>>>()
    val questions: LiveData<Hasil<List<PertanyaanDataItem>>> get() = _questions

    private var originalSelectedOptionsSize = 0

    init {
        // Fetch questions when the ViewModel is created
        getQuestions()
    }
    private val _navigateToFinish = MutableLiveData<Boolean>()
    val navigateToFinish: LiveData<Boolean> get() = _navigateToFinish
    fun submitAssessment(answers: List<Int>) {
        viewModelScope.launch {
            _assessmentResult.value = Hasil.Loading
            try {
                val result = repository.submitAssessment(answers)
                _assessmentResult.value = result
                if (result is Hasil.Success && result.data) {
                    // Reset the originalSelectedOptionsSize when assessment is submitted
                    originalSelectedOptionsSize = selectedOptions.size
                    // Set the navigation event to true
                    _navigateToFinish.value = true
                } else if (result is Hasil.Error) {
                    // Handle error, e.g., show a toast or display an error message
                }
            } catch (e: Exception) {
                _assessmentResult.value = Hasil.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun onNavigateToFinishComplete() {
        _navigateToFinish.value = false
    }

    fun getSelectedOptions(): List<Int> {
        return selectedOptions
    }

    fun getQuestions() {
        viewModelScope.launch {
            _questions.value = Hasil.Loading
            try {
                val response = repository.getQuestions()
                response.pertanyaanData?.let {
                    val filteredList = it.filterNotNull()
                    _questions.value = Hasil.Success(filteredList)
                } ?: run {
                    _questions.value = Hasil.Error("Failed to fetch questions")
                }
            } catch (e: Exception) {
                _questions.value = Hasil.Error(e.message ?: "Unknown error")
            }
        }
    }

    private val _currentQuestion = MutableLiveData<Hasil<PertanyaanDataItem>>()
    val currentQuestion: LiveData<Hasil<PertanyaanDataItem>> get() = _currentQuestion

    private val selectedOptions = mutableListOf<Int>()
    private var currentQuestionIndex = 0

    fun updateSelectedOption(option: Int) {
        selectedOptions.add(currentQuestionIndex, option)
    }


    private var currentQuestionId: Int = 1

    private fun updateCurrentQuestionId(id: Int) {
        currentQuestionId = id
    }

    fun getNextQuestion() {
        val hasil = _questions.value

        if (hasil is Hasil.Success) {
            val dataSize = hasil.data.size

            if (currentQuestionIndex < dataSize - 1) {
                currentQuestionIndex++
                val nextQuestion = hasil.data[currentQuestionIndex]
                _currentQuestion.value = Hasil.Success(nextQuestion)
                updateCurrentQuestionId(nextQuestion.idPertanyaan ?: 0)
            } else {
                // If there are no more questions, submit the assessment
                submitAssessment(selectedOptions)
            }
        } else if (hasil is Hasil.Loading) {
            // Handle loading state if needed
        } else if (hasil is Hasil.Error) {
            // Handle error state if needed
        } else {
            // Handle the case where _questions.value is null
        }
    }

    fun decrementCurrentQuestionIndex() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--
        }
    }

    fun removeLastSelectedOption() {
        if (selectedOptions.isNotEmpty() && selectedOptions.size > originalSelectedOptionsSize) {
            selectedOptions.removeAt(selectedOptions.size - 1)
        }
    }

    fun getPreviousQuestion() {
        val hasil = _questions.value

        if (hasil is Hasil.Success) {
            val dataSize = hasil.data.size

            if (currentQuestionIndex >= 0 && currentQuestionIndex < dataSize) {
                val previousQuestion = hasil.data[currentQuestionIndex]
                _currentQuestion.value = Hasil.Success(previousQuestion)
                updateCurrentQuestionId(previousQuestion.idPertanyaan ?: 0)
            }
        } else if (hasil is Hasil.Loading) {
            // Handle loading state if needed
        } else if (hasil is Hasil.Error) {
            // Handle error state if needed
        } else {
            // Handle the case where _questions.value is null
        }
    }

    fun getCurrentQuestionId(): Int {
        return currentQuestionId
    }

    fun getCurrentQuestionIndex(): Int {
        return currentQuestionIndex
    }

    fun resetOriginalSelectedOptionsSize() {
        originalSelectedOptionsSize = selectedOptions.size
    }

}
