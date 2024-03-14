package com.example.passwordmanagerapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [WebsiteDbModel::class], version = 6, exportSchema = false)
@TypeConverters(DataConverter::class)
abstract class AppDatabase: RoomDatabase() {
    companion object {
        private var db: AppDatabase? = null
        private const val DB_NAME = "websites.db"
        private val LOCK = Any()

        fun getInstance(context: Context): AppDatabase {
            db?.let { return it }
            synchronized(LOCK) {
                db?.let { return it }
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                db = instance
                return instance
            }
        }
    }
    abstract fun recordDao(): WebsiteListDao
}