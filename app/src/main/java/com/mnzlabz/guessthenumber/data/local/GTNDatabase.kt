package com.mnzlabz.guessthenumber.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GuessEntity::class], version = 2)
abstract class GTNDatabase: RoomDatabase() {
    abstract fun guessingDao(): GuessingDAO

    companion object {
        private const val DATABASE_NAME: String = "gtn.db"
        @Volatile private var instance: GTNDatabase? = null

        fun getDatabase(context: Context): GTNDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, GTNDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }

}