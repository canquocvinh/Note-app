package com.vnu.uet.noteapp.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.vnu.uet.noteapp.data.NoteDatabaseHelper.FeedReaderContract.FeedEntry.COLUMN_NAME_CONTENT
import com.vnu.uet.noteapp.data.NoteDatabaseHelper.FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE
import com.vnu.uet.noteapp.data.NoteDatabaseHelper.FeedReaderContract.FeedEntry.TABLE_NAME
import com.vnu.uet.noteapp.model.Note

class NoteDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)  {

    object FeedReaderContract {
        object FeedEntry : BaseColumns {
            const val TABLE_NAME = "notetable"
            const val COLUMN_NAME_TITLE = "title"
            const val COLUMN_NAME_CONTENT = "content"
        }
    }

    private val SQL_CREATE_ENTRIES =
        "CREATE TABLE $TABLE_NAME (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "$COLUMN_NAME_TITLE TEXT," +
                "$COLUMN_NAME_CONTENT TEXT)"

    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun insertData(note: Note) {
        val db = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_NAME_TITLE, note.title)
        contentValues.put(COLUMN_NAME_CONTENT, note.content)
        db.insert(TABLE_NAME, null, contentValues)
        db.close()
    }

    fun updateData(title: String, oldTitle: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME_TITLE, title)
        }
        val selection = "$COLUMN_NAME_TITLE LIKE ?"
        val selectionArgs = arrayOf(oldTitle)
        db.update(
            TABLE_NAME,
            values,
            selection,
            selectionArgs)
    }

    fun deleteData(title: String) {
        val db = writableDatabase
        val selection = "$COLUMN_NAME_TITLE LIKE ?"
        val selectionArgs = arrayOf(title)
        db.delete(TABLE_NAME, selection, selectionArgs)
    }

    @SuppressLint("Range")
    fun searchNotes(query: String): ArrayList<Note> {
        val notes = ArrayList<Note>()
        val db = readableDatabase
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
        val db = readableDatabase
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

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "FeedReader.db"
    }
}