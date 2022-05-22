package com.pride.test4ksoft.repository

import android.provider.BaseColumns

object DbClass {
    const val TABLE_NAME = "Notes"
    const val COLUMN_NAME_TITLE = "title"
    const val COLUMN_NAME_DESCRIPTION = "description"
    const val COLUMN_NAME_DATE = "date"

    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "Notes.db"

    const val SQL_CREATE_ENTRIES =
        "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "$COLUMN_NAME_TITLE TEXT,$COLUMN_NAME_DESCRIPTION TEXT, $COLUMN_NAME_DATE TEXT)"

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"

}
