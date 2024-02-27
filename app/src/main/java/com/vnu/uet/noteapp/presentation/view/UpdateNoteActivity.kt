package com.vnu.uet.noteapp.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.vnu.uet.noteapp.R
import com.vnu.uet.noteapp.databinding.ActivityMainBinding
import com.vnu.uet.noteapp.databinding.ActivityUpdateNoteBinding
import com.vnu.uet.noteapp.presentation.base.BaseActivity
import com.vnu.uet.noteapp.presentation.viewmodel.MainViewModel.Companion.CONTENT
import com.vnu.uet.noteapp.presentation.viewmodel.MainViewModel.Companion.TITLE
import com.vnu.uet.noteapp.presentation.viewmodel.UpdateNoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateNoteActivity : BaseActivity() {

    private val updateNoteViewModel: UpdateNoteViewModel by viewModels()

    private lateinit var binding: ActivityUpdateNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        val title = intent.getStringExtra(TITLE)
        val content = intent.getStringExtra(CONTENT)

        binding.etTitle.setText(title)
        binding.etContent.setText(content)
    }

    override fun initAction() {

    }

    override fun initCollectData() {

    }
}