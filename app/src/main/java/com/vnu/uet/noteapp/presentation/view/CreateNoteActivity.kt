package com.vnu.uet.noteapp.presentation.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.vnu.uet.noteapp.R
import com.vnu.uet.noteapp.databinding.ActivityCreateNoteBinding
import com.vnu.uet.noteapp.presentation.base.BaseActivity
import com.vnu.uet.noteapp.presentation.viewmodel.CreateNoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateNoteActivity : BaseActivity() {

    private val createNoteViewModel : CreateNoteViewModel by viewModels()

    private lateinit var binding: ActivityCreateNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCreateNoteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        super.onCreate(savedInstanceState)
    }

    override fun initAction() {
        binding.btnSave.setOnClickListener {
            createNoteViewModel.createNote(binding.etTitle.text.toString(), binding.etContent.text.toString())
        }
    }

    override fun initCollectData() {
        createNoteViewModel.isCreateNoteSuccess.observe(this) {
            if (it) {
                finish()
            }
        }

        createNoteViewModel.isFail.observe(this) {
            if (it) {
                showToast(getString(R.string.inform_empty_title_content))
                createNoteViewModel.resetIsFail()
            }
        }
    }
}