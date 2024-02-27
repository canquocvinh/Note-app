package com.vnu.uet.noteapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vnu.uet.noteapp.data.model.Note
import com.vnu.uet.noteapp.domain.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: UseCase
) : ViewModel() {

    private val _listNote = MutableLiveData<List<Note>>()
    val listNote : LiveData<List<Note>>
        get() = _listNote

    fun getListNote() {
        _listNote.value = useCase.readData()
    }
}