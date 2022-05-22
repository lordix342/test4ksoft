package com.pride.test4ksoft.repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class DbManager(context: Context) {
    private val dbHelper = Helper(context)
    private var db: SQLiteDatabase? = null

    fun openDb() {
        db = dbHelper.writableDatabase
    }

    fun closeDb() {
        dbHelper.close()
    }

    fun insert(title: String?, description: String?, date: String?) {
        val values = ContentValues().apply {
            put(DbClass.COLUMN_NAME_TITLE, title)
            put(DbClass.COLUMN_NAME_DESCRIPTION, description)
            put(DbClass.COLUMN_NAME_DATE, date)
        }
        db?.insert(DbClass.TABLE_NAME, null, values)
    }


    fun sortDb(): ArrayList<NoteClass> {
        val datalist = ArrayList<NoteClass>()
        val sortOrder = "${BaseColumns._ID} DESC"
        val cursor = db?.query(
            DbClass.TABLE_NAME,   // The table to query
            null,             // The array of columns to return (pass null to get all)
            null,              // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            sortOrder               // The sort order
        )
        while (cursor?.moveToNext()!!) {
            val dataTextId = cursor.getString(cursor.getColumnIndexOrThrow(BaseColumns._ID))
            val dataTextTitle =
                cursor.getString(cursor.getColumnIndexOrThrow(DbClass.COLUMN_NAME_TITLE))
            val dataTextDescription =
                cursor.getString(cursor.getColumnIndexOrThrow(DbClass.COLUMN_NAME_DESCRIPTION))
            val dataTextDate =
                cursor.getString(cursor.getColumnIndexOrThrow(DbClass.COLUMN_NAME_DATE))
            val note = NoteClass(dataTextId, dataTextTitle, dataTextDescription, dataTextDate)
            datalist.add(note)
        }
        cursor.close()
        return datalist
    }


    fun selectFromDb(id: String): ArrayList<NoteClass> {
        val datalist = ArrayList<NoteClass>()
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf(id)
        val cursor = db?.query(
            DbClass.TABLE_NAME,   // The table to query
            null,             // The array of columns to return (pass null to get all)
            selection,              // The columns for the WHERE clause
            selectionArgs,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            null               // The sort order
        )
        while (cursor?.moveToNext()!!) {
            val dataTextId = cursor.getString(cursor.getColumnIndexOrThrow(BaseColumns._ID))
            val dataTextTitle =
                cursor.getString(cursor.getColumnIndexOrThrow(DbClass.COLUMN_NAME_TITLE))
            val dataTextDescription =
                cursor.getString(cursor.getColumnIndexOrThrow(DbClass.COLUMN_NAME_DESCRIPTION))
            val dataTextDate =
                cursor.getString(cursor.getColumnIndexOrThrow(DbClass.COLUMN_NAME_DATE))
            val note = NoteClass(dataTextId, dataTextTitle, dataTextDescription, dataTextDate)
            datalist.add(note)
        }
        cursor.close()
        return datalist
    }


    fun readDb(): ArrayList<NoteClass> {
        val datalist = ArrayList<NoteClass>()
        val cursor = db?.query(
            DbClass.TABLE_NAME,   // The table to query
            null,             // The array of columns to return (pass null to get all)
            null,              // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            null               // The sort order
        )

        while (cursor?.moveToNext()!!) {
            val dataTextId = cursor.getString(cursor.getColumnIndexOrThrow(BaseColumns._ID))
            val dataTextTitle =
                cursor.getString(cursor.getColumnIndexOrThrow(DbClass.COLUMN_NAME_TITLE))
            val dataTextDescription =
                cursor.getString(cursor.getColumnIndexOrThrow(DbClass.COLUMN_NAME_DESCRIPTION))
            val dataTextDate =
                cursor.getString(cursor.getColumnIndexOrThrow(DbClass.COLUMN_NAME_DATE))
            val note = NoteClass(dataTextId, dataTextTitle, dataTextDescription, dataTextDate)
            datalist.add(note)
        }

        cursor.close()
        return datalist
    }


    fun deleteNote(id: String?) {
        val selection = "${BaseColumns._ID} LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(id)
        db?.delete(DbClass.TABLE_NAME, selection, selectionArgs)
    }


    fun updateNote(id: String, title: String, description: String, date: String) {
        val values = ContentValues().apply {
            put(DbClass.COLUMN_NAME_TITLE, title)
            put(DbClass.COLUMN_NAME_DESCRIPTION, description)
            put(DbClass.COLUMN_NAME_DATE, date)
        }
        // Which row to update, based on the id
        val selection = "${BaseColumns._ID} LIKE ?"
        val selectionArgs = arrayOf(id)
        db?.update(DbClass.TABLE_NAME, values, selection, selectionArgs)
    }
}