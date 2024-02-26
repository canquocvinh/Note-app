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
import com.vnu.uet.noteapp.data.model.Note
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


class NoteDatabaseHelper @Inject constructor(@ApplicationContext private val context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)  {

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

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "FeedReader.db"
    }
}