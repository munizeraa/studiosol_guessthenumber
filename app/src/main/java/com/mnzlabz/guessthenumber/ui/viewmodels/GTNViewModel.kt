package com.mnzlabz.guessthenumber.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mnzlabz.guessthenumber.data.model.GTNModel
import com.mnzlabz.guessthenumber.data.repository.interfaces.IGTNRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GTNViewModel @Inject constructor(private val repository: IGTNRepository): ViewModel() {

    private var _inputUser = MutableLiveData<Int>()
    private var _gtnModel = MutableLiveData<GTNModel>()

    val inputUser: LiveData<Int> = _inputUser
    val gtnModel: LiveData<GTNModel> = _gtnModel

    fun getRandomNumber() {
        viewModelScope.launch(IO) {
            try {
                repository.getRandomNumber(111, 222)?.let {
                    _gtnModel.postValue(it)
                }
            } catch (exception: Exception) {
                Log.e("GNTViewModel", exception.message.toString())
            }
        }
    }
}