package com.vnu.uet.noteapp.presentation.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vnu.uet.noteapp.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.vnu.uet.noteapp.data.model.Note
import com.vnu.uet.noteapp.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    private var notes = ArrayList<Note>()

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NoteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initView()
        initAction()
        initCollectData()
    }

    private fun initView() {
        binding.rvNotes.layoutManager = LinearLayoutManager(this)
        adapter = NoteAdapter()
        binding.rvNotes.adapter = adapter
    }

    private fun initAction() {
        adapter.itemClick = {position ->
            mainViewModel.navigateUpdateNote(this, notes[position].title, notes[position].content)
        }

        binding.ibCreateNote.setOnClickListener {
            mainViewModel.navigateCreateNote(this)
        }

        binding.ibSearch.setOnClickListener {
            mainViewModel.search(binding.etText.text.toString())
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initCollectData() {
        mainViewModel.listNote.observe(this, Observer {
            adapter.clearNotes()
            adapter.setNotes(it)
            adapter.notifyDataSetChanged()
        })
    }

}