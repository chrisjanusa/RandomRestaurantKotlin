package com.chrisjanusa.randomizer.database_transition.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

// Disgusting class to transfer old database to new storage mechanism
class BlockDBHelper(context: Context) : SQLiteOpenHelper(
    context,
    blocksDatabaseName, null,
    databaseVersion
) {

    override fun onCreate(db: SQLiteDatabase?) {

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun getBlockedRestaurants(): List<DatabaseRestaurant> {
        try {
            val database = this.writableDatabase
            val cursor = database.query(tableName, null, null, null, null, null, null)
            val resNames = ArrayList<DatabaseRestaurant>()
            while (cursor.moveToNext()) {
                val name = cursor.getString(cursor.getColumnIndex(nameColumn))
                val lat = cursor.getDouble(cursor.getColumnIndex(latColumn))
                val lon = cursor.getDouble(cursor.getColumnIndex(lonColumn))
                resNames.add(DatabaseRestaurant(name, lat, lon))
            }
            cursor.close()
            database.close()
            return resNames
        } catch (exception: SQLiteException) {
            return ArrayList()
        }
    }

    fun delete(resName: String) {
        val database = this.writableDatabase
        database.delete(tableName, "$nameColumn LIKE ?", arrayOf(resName))
        database.close()
    }

    companion object {
        const val databaseVersion = 1
        const val blocksDatabaseName = "dislikeRestaurantsDB.db"
        const val tableName = "dislikeRestaurants"
        const val nameColumn = "restaurantname"
        const val latColumn = "lat"
        const val lonColumn = "lon"
    }
}