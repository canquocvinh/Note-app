package com.vnu.uet.noteapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.vnu.uet.noteapp.domain.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: UseCase
) : ViewModel() {


}