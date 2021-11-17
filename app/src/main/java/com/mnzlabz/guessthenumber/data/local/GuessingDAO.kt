package com.mnzlabz.guessthenumber.data.local

import androidx.room.*

@Dao
interface GuessingDAO {
    @Insert
    suspend fun insert(guessing: GuessEntity): Long

    @Query("DELETE FROM guess")
    suspend fun deleteAll()

    @Query("SELECT * FROM guess")
    fun getAll(): List<GuessEntity>
}