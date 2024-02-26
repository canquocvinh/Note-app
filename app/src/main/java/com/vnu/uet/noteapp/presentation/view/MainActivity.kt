package com.vnu.uet.noteapp.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vnu.uet.noteapp.R
import com.vnu.uet.noteapp.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}