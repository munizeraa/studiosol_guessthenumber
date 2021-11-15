package com.mnzlabz.guessthenumber.data.repository.interfaces

import com.mnzlabz.guessthenumber.data.model.GTNModel


interface IGTNRepository {
    suspend fun getRandomNumber(min: Int, max: Int): GTNModel?
}