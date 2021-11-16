package com.mnzlabz.guessthenumber.data.repository.implementations

import android.util.Log
import com.mnzlabz.guessthenumber.data.model.GTNModel
import com.mnzlabz.guessthenumber.data.remote.IGTNService
import com.mnzlabz.guessthenumber.data.repository.interfaces.IGTNRepository
import retrofit2.HttpException
import javax.inject.Inject

class GTNRepository @Inject constructor(private val service: IGTNService): IGTNRepository {
    companion object {
        const val TAG = "GTNRepository"
    }

    override suspend fun getRandomNumber(min: Int, max: Int): GTNModel? {
        var gtn: GTNModel? = null

        try {
            service.getRandomNumber(min, max).let { response ->
                if(response.isSuccessful) {
                    response.body()?.let {
                        gtn = it.value?.let { generatedValue ->
                            GTNModel(value = generatedValue,
                                statusCode = response.code(),
                                isSuccessful = response.isSuccessful,
                                message = "Success"
                            )
                        }
                    }

                } else {
                    response.errorBody()?.let {
                        gtn = GTNModel(value = response.raw().code,
                            statusCode = response.raw().code,
                            isSuccessful = response.isSuccessful,
                            message = "Error"
                        )
                    }
                }
            }
        } catch (exception: Exception) {
            gtn = GTNModel(value = -1,
                statusCode = -1,
                isSuccessful = false,
                message = exception.message.toString()
            )
        }

        return gtn
    }
}