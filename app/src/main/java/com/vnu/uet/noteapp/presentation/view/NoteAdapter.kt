package com.vnu.uet.noteapp.presentation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vnu.uet.noteapp.R
import com.vnu.uet.noteapp.data.model.Note

class NoteAdapter : RecyclerView.Adapter<NoteViewHolder>(){

    private var notes = ArrayList<Note>()

    var itemClick: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_adapter, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.title.text = note.title
        holder.content.text = note.content

        holder.itemView.setOnClickListener {
            itemClick?.invoke(position)
        }
    }

    override fun getItemCount() = notes.size

    fun setNotes(notes: ArrayList<Note>) {
        this.notes = notes
    }

    fun clearNotes() {
        notes.clear()
    }
}

class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title: TextView = itemView.findViewById(R.id.note_title)
    val content: TextView = itemView.findViewById(R.id.note_content)
}