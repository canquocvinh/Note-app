package com.vnu.uet.noteapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vnu.uet.noteapp.data.model.Note
import com.vnu.uet.noteapp.domain.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UpdateNoteViewModel @Inject constructor(
    private val useCase: UseCase
) : ViewModel() {

    private val _isUpdateNoteSuccess = MutableLiveData(false)
    val isUpdateNoteSuccess : LiveData<Boolean>
        get() = _isUpdateNoteSuccess

    private val _isFail = MutableLiveData(false)
    val isFail : LiveData<Boolean>
        get() = _isFail
    fun updateNote(title: String, content: String) {
        if (title.isEmpty() || content.isEmpty()) {
            _isFail.value = true
            return
        }
        //update can sua
        _isUpdateNoteSuccess.value = true
    }

    fun resetIsFail () {
        _isFail.value = false
    }
}