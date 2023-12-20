package com.bangkit.minatku.data

sealed class Hasil<out R> {
    data class Success<out T>(val data: T) : Hasil<T>()
    data class Error(val error: String) : Hasil<Nothing>()
    object Loading : Hasil<Nothing>()
}