package com.vnu.uet.noteapp.presentation.view

import android.os.Bundle
import androidx.activity.viewModels
import com.vnu.uet.noteapp.R
import com.vnu.uet.noteapp.data.model.Note
import com.vnu.uet.noteapp.databinding.ActivityUpdateNoteBinding
import com.vnu.uet.noteapp.presentation.base.BaseActivity
import com.vnu.uet.noteapp.presentation.viewmodel.MainViewModel.Companion.CONTENT
import com.vnu.uet.noteapp.presentation.viewmodel.MainViewModel.Companion.ID
import com.vnu.uet.noteapp.presentation.viewmodel.MainViewModel.Companion.TITLE
import com.vnu.uet.noteapp.presentation.viewmodel.UpdateNoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateNoteActivity : BaseActivity() {

    private val updateNoteViewModel: UpdateNoteViewModel by viewModels()

    private lateinit var binding: ActivityUpdateNoteBinding

    private var oldId : Int? = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        oldId = intent.getIntExtra(ID, 0)
        val title = intent.getStringExtra(TITLE)
        val content = intent.getStringExtra(CONTENT)

        binding.etTitle.setText(title)
        binding.etContent.setText(content)
    }

    override fun initAction() {
        binding.btnUpdate.setOnClickListener {
            oldId?.let { id ->
                updateNoteViewModel.updateNote(
                    id, Note(
                        id = oldId,
                        title = binding.etTitle.text.toString(),
                        content = binding.etContent.text.toString()
                    ))
            }
        }
    }

    override fun initCollectData() {
        updateNoteViewModel.isUpdateNoteSuccess.observe(this) {
            if (it) {
                finish()
            }
        }

        updateNoteViewModel.isFail.observe(this) {
            if (it) {
                showToast(getString(R.string.inform_empty_title_content))
                updateNoteViewModel.resetIsFail()
            }
        }
    }
}