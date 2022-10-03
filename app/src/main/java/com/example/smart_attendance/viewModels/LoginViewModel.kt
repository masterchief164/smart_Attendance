package com.example.smart_attendance.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smart_attendance.data.User
import com.example.smart_attendance.other.Event
import com.example.smart_attendance.other.Resource
import com.example.smart_attendance.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    // Mutable Value
    private val _loginStatus = MutableLiveData<Event<Resource<User>>>()

    // Immutable Value
    val loginStatus: LiveData<Event<Resource<User>>> = _loginStatus

    fun login(idToken: String) {
        _loginStatus.postValue(Event(Resource.Loading()))
        viewModelScope.launch(Dispatchers.IO){
            val resp = authRepository.loginUser(idToken)
            _loginStatus.postValue(Event(resp))
        }
    }
}