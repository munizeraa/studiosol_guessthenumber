package com.mnzlabz.guessthenumber.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "guess")
data class GuessEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val userGuessing: Int,
    val generatedNumber: Int,
    val userHasWon: Boolean
)