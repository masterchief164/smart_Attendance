package com.shaswat.smart_attendance.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shaswat.smart_attendance.data.SessionsData
import com.shaswat.smart_attendance.other.Event
import com.shaswat.smart_attendance.other.Resource
import com.shaswat.smart_attendance.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseDetailViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {
    private val _getSessionsStatus = MutableLiveData<Event<Resource<SessionsData>>>()
    val getSessionsStatus: LiveData<Event<Resource<SessionsData>>> = _getSessionsStatus

    fun getSessions(courseId:String) {
        _getSessionsStatus.postValue(Event(Resource.Loading()))
        viewModelScope.launch(Dispatchers.IO) {
            val sessionDetails = repository.getSessionsDetail(courseId)
            _getSessionsStatus.postValue(Event(sessionDetails))
        }
    }
}