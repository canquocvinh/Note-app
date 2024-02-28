package com.vnu.uet.noteapp.presentation.view

import android.annotation.SuppressLint
import android.os.Bundle
import com.vnu.uet.noteapp.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.vnu.uet.noteapp.R
import com.vnu.uet.noteapp.data.model.Note
import com.vnu.uet.noteapp.databinding.ActivityMainBinding
import com.vnu.uet.noteapp.presentation.base.BaseActivity

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    private var notes = ArrayList<Note>()

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        binding.rvNotes.layoutManager = LinearLayoutManager(this)
        adapter = NoteAdapter()
        binding.rvNotes.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.getNotes()
        resetSearch()
    }

    private fun resetSearch() {
        binding.etText.setText("")
    }

    override fun initAction() {
        adapter.itemClick = { position ->
            notes[position].id?.let {
                mainViewModel.navigateUpdateNote(this,
                    it, notes[position].title, notes[position].content)
            }
        }

        binding.ibCreateNote.setOnClickListener {
            mainViewModel.navigateCreateNote(this)
        }

        binding.ibSearch.setOnClickListener {
            mainViewModel.search(binding.etText.text.toString())
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun initCollectData() {
        mainViewModel.listNote.observe(this) {
            adapter.clearNotes()
            if (it.isEmpty()) {
                showToast(getString(R.string.no_result_search))
            } else {
                adapter.setNotes(it)
            }
            adapter.notifyDataSetChanged()
            notes.clear()
            notes.addAll(it)
        }
    }

}