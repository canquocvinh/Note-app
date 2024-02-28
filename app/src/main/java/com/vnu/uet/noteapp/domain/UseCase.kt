package com.vnu.uet.noteapp.domain

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import com.vnu.uet.noteapp.data.NoteDatabaseHelper
import com.vnu.uet.noteapp.data.NoteDatabaseHelper.FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE
import com.vnu.uet.noteapp.data.NoteDatabaseHelper.FeedReaderContract.FeedEntry.COLUMN_NAME_CONTENT
import com.vnu.uet.noteapp.data.NoteDatabaseHelper.FeedReaderContract.FeedEntry.TABLE_NAME
import com.vnu.uet.noteapp.data.model.Note
import javax.inject.Inject

class UseCase @Inject constructor(
    private val db: NoteDatabaseHelper
) {
    fun insertData(note: Note) {
        val db = db.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_NAME_TITLE, note.title)
        contentValues.put(COLUMN_NAME_CONTENT, note.content)
        db.insert(TABLE_NAME, null, contentValues)
        db.close()
    }

    // sua lai
    fun updateData(oldId: Int, newNote: Note) {
        val db = db.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME_TITLE, newNote.title)
            put(COLUMN_NAME_CONTENT, newNote.content)
        }
        val selection = "${BaseColumns._ID} LIKE ?"
        val selectionArgs = arrayOf(oldId.toString())
        db.update(
            TABLE_NAME,
            values,
            selection,
            selectionArgs)
        db.close()
    }

    @SuppressLint("Range")
    fun searchNotes(query: String): ArrayList<Note> {
        val notes = ArrayList<Note>()
        val db = db.readableDatabase
        val searchQuery = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_NAME_TITLE LIKE '%$query%' OR $COLUMN_NAME_CONTENT LIKE '%$query%'"
        val cursor: Cursor? = db.rawQuery(searchQuery, null)

        cursor?.use {
            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
                val title = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_TITLE))
                val content = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_CONTENT))
                val note = Note(id, title, content)
                notes.add(note)
            }
        }

        cursor?.close()
        db.close()

        return notes
    }

    @SuppressLint("Range")
    fun readData(): ArrayList<Note> {
        val dataList = ArrayList<Note>()
        val db = db.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)
        cursor?.use {
            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
                val title = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_TITLE))
                val content = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_CONTENT))
                val note = Note(id, title, content)
                dataList.add(note)
            }
        }
        cursor?.close()
        db.close()
        return dataList
    }
}