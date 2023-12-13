package com.example.buycar

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class CarDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "car.db"
        private const val DATABASE_VERSION = 1
    }

    fun clearAllData() {
        val db = writableDatabase
        db.delete(CarContract.CarEntry.TABLE_NAME, null, null)
        db.close()
    }

    override fun onCreate(db: SQLiteDatabase) {
        val SQL_CREATE_CARS_TABLE = "CREATE TABLE " + CarContract.CarEntry.TABLE_NAME + " (" +
                CarContract.CarEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CarContract.CarEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                CarContract.CarEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                CarContract.CarEntry.COLUMN_PRICE + " REAL NOT NULL, " +
                CarContract.CarEntry.COLUMN_PHOTO + " TEXT NOT NULL);"

        db.execSQL(SQL_CREATE_CARS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database schema upgrades if needed in the future.
    }
    data class Car(val id: Long, val title: String, val description: String, val price: Double, val photoUri: String)

    fun getAllCars(): List<Car> {
        val cars = mutableListOf<Car>()
        val db = readableDatabase
        val projection = arrayOf(
            CarContract.CarEntry._ID,
            CarContract.CarEntry.COLUMN_TITLE,
            CarContract.CarEntry.COLUMN_DESCRIPTION,
            CarContract.CarEntry.COLUMN_PRICE,
            CarContract.CarEntry.COLUMN_PHOTO
        )

        val cursor = db.query(
            CarContract.CarEntry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )

        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(CarContract.CarEntry._ID))
                val title = getString(getColumnIndexOrThrow(CarContract.CarEntry.COLUMN_TITLE))
                val description = getString(getColumnIndexOrThrow(CarContract.CarEntry.COLUMN_DESCRIPTION))
                val price = getDouble(getColumnIndexOrThrow(CarContract.CarEntry.COLUMN_PRICE))
                val photoUri = getString(getColumnIndexOrThrow(CarContract.CarEntry.COLUMN_PHOTO))

                cars.add(Car(id, title, description, price, photoUri))
            }
        }


        cursor.close()
        return cars
    }


    fun getCarById(carId: Long): Car? {
        val db = readableDatabase
        val projection = arrayOf(
            CarContract.CarEntry._ID,
            CarContract.CarEntry.COLUMN_TITLE,
            CarContract.CarEntry.COLUMN_DESCRIPTION,
            CarContract.CarEntry.COLUMN_PRICE,
            CarContract.CarEntry.COLUMN_PHOTO
        )
        val selection = "${CarContract.CarEntry._ID} = ?"
        val selectionArgs = arrayOf(carId.toString())

        val cursor = db.query(
            CarContract.CarEntry.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow(CarContract.CarEntry._ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(CarContract.CarEntry.COLUMN_TITLE))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(CarContract.CarEntry.COLUMN_DESCRIPTION))
            val price = cursor.getDouble(cursor.getColumnIndexOrThrow(CarContract.CarEntry.COLUMN_PRICE))
            val photoUri = cursor.getString(cursor.getColumnIndexOrThrow(CarContract.CarEntry.COLUMN_PHOTO))

            return Car(id, title, description, price, photoUri)
        }

        cursor.close()
        return null // Car with the specified ID not found
    }
}

