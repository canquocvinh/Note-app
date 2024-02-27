package com.vnu.uet.noteapp.presentation.viewmodel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vnu.uet.noteapp.data.model.Note
import com.vnu.uet.noteapp.domain.UseCase
import com.vnu.uet.noteapp.presentation.view.CreateNoteActivity
import com.vnu.uet.noteapp.presentation.view.UpdateNoteActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: UseCase
) : ViewModel() {

    private val _listNote = MutableLiveData<ArrayList<Note>>()
    val listNote : LiveData<ArrayList<Note>>
        get() = _listNote

    init {
        getNotes()
    }

    fun getNotes() {
        _listNote.value = useCase.readData()
    }

    fun navigateUpdateNote(context: Context, title: String, content: String) {
        val intent = Intent(context, UpdateNoteActivity::class.java)
        intent.putExtra(TITLE, title)
        intent.putExtra(CONTENT, content)
        context.startActivity(intent)
    }

    fun navigateCreateNote(context: Context) {
        val intent = Intent(context, CreateNoteActivity::class.java)
        context.startActivity(intent)
    }

    fun search(text: String) {
        _listNote.value = useCase.searchNotes(text)
    }

    companion object {
        const val TITLE = "title"
        const val CONTENT = "content"
    }
}