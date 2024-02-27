package com.vnu.uet.noteapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vnu.uet.noteapp.data.model.Note
import com.vnu.uet.noteapp.domain.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateNoteViewModel @Inject constructor(
    private val useCase: UseCase
) : ViewModel() {

    private val _isCreateNoteSuccess = MutableLiveData(false)
    val isCreateNoteSuccess : LiveData<Boolean>
        get() = _isCreateNoteSuccess

    private val _isFail = MutableLiveData(false)
    val isFail : LiveData<Boolean>
        get() = _isFail
    fun createNote(title: String, content: String) {
        if (title.isEmpty() || content.isEmpty()) {
            _isFail.value = true
            return
        }
        useCase.insertData(Note(title = title, content = content))
        _isCreateNoteSuccess.value = true
    }

    fun resetIsFail () {
        _isFail.value = false
    }
}