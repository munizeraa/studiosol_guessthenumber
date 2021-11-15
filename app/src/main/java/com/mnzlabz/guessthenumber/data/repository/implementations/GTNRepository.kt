package com.mnzlabz.guessthenumber.data.repository.implementations

import android.util.Log
import com.mnzlabz.guessthenumber.data.model.GTNModel
import com.mnzlabz.guessthenumber.data.remote.IGTNService
import com.mnzlabz.guessthenumber.data.repository.interfaces.IGTNRepository
import javax.inject.Inject

class GTNRepository @Inject constructor(private val service: IGTNService): IGTNRepository {
    companion object {
        const val TAG = "GTNRepository"
    }

    override suspend fun getRandomNumber(min: Int, max: Int): GTNModel? {
        var gtn: GTNModel? = null

        try {
            service.getRandomNumber(min, max).let { response ->
                response.body()?.let {
                    gtn = GTNModel(value = it.value,
                        statusCode = response.code(),
                        isSuccessful = response.isSuccessful,
                        message = if(response.isSuccessful) response.message() else "Erro"
                    )
                }
            }
        } catch (exception: Exception) {
            gtn = GTNModel(value = null,
                statusCode = -1,
                isSuccessful = false,
                message = exception.message.toString()
            )
        }

        return gtn
    }
}