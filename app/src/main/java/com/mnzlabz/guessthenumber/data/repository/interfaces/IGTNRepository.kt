package com.mnzlabz.guessthenumber.data.repository.interfaces

import com.mnzlabz.guessthenumber.data.local.GuessEntity
import com.mnzlabz.guessthenumber.data.model.GTNModel


interface IGTNRepository {
    suspend fun getRandomNumber(min: Int, max: Int): GTNModel?
    suspend fun insertGuess(guessingEntiy: GuessEntity): Long
    suspend fun getAllGuesses(): List<GuessEntity>?
    suspend fun deleteAllGuesses()
}