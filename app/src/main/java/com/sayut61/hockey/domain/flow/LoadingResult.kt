package com.sayut61.hockey.domain.flow

sealed class LoadingResult<out R>() {
    data class SuccessResult<T>(val data: T): LoadingResult<T>()
    data class ErrorResult(val error: Exception): LoadingResult<Nothing>()
    class Loading(val showOrHideProgressBar: Boolean): LoadingResult<Nothing>()
}