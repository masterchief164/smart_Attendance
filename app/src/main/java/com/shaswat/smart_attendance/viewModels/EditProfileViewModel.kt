package com.shaswat.smart_attendance.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shaswat.smart_attendance.data.UpdateProfile
import com.shaswat.smart_attendance.data.User
import com.shaswat.smart_attendance.other.Event
import com.shaswat.smart_attendance.other.Resource
import com.shaswat.smart_attendance.repositories.AuthRepository
import com.shaswat.smart_attendance.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val repository: MainRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _updateProfileStatus = MutableLiveData<Event<Resource<User>>>()
    val updateProfileStatus: LiveData<Event<Resource<User>>> = _updateProfileStatus

    fun updateProfile(userProfile: UpdateProfile) {
        _updateProfileStatus.postValue(Event(Resource.Loading()))
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.updateProfile(userProfile)
            _updateProfileStatus.postValue(Event(result))
        }
    }

    fun saveUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.saveUser(user)
        }
    }
}