package com.example.smart_attendance.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smart_attendance.data.QRData
import com.example.smart_attendance.data.User
import com.example.smart_attendance.other.Event
import com.example.smart_attendance.other.Resource
import com.example.smart_attendance.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _getUserStatus = MutableLiveData<Event<Resource<User>>>()
    val getUserStatus: LiveData<Event<Resource<User>>> = _getUserStatus

    private val _getQRDataStatus = MutableLiveData<Event<Resource<QRData>>>()
    val getQRDataStatus: LiveData<Event<Resource<QRData>>> = _getQRDataStatus

    private val _attendSessionStatus = MutableLiveData<Event<Resource<Boolean>>>()
    val attendSessionStatus: LiveData<Event<Resource<Boolean>>> = _attendSessionStatus

    fun getUser() {
        _getUserStatus.postValue(Event(Resource.Loading()))
        viewModelScope.launch(Dispatchers.IO) {
            val user = repository.getUser()
            _getUserStatus.postValue(Event(user))
        }
    }

    fun getQRData(qrString: String) {
        _getQRDataStatus.postValue(Event(Resource.Loading()))
        viewModelScope.launch(Dispatchers.IO) {
            val qrData = repository.getQRData(qrString)
            _getQRDataStatus.postValue(Event(qrData))
        }
    }

    fun attendSession(sessionDetails: QRData) {
        _attendSessionStatus.postValue(Event(Resource.Loading()))
        viewModelScope.launch(Dispatchers.IO) {
            val resp = repository.attendSession(sessionDetails)
            _attendSessionStatus.postValue(Event(resp))
        }
    }

    fun logoutUser() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.logoutUser()
        }
    }
}