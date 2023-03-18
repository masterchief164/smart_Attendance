package com.example.smart_attendance.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smart_attendance.data.Course
import com.example.smart_attendance.other.Event
import com.example.smart_attendance.other.Resource
import com.example.smart_attendance.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoursesViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {
    private val _getCoursesStatus = MutableLiveData<Event<Resource<List<Course>>>>()
    val getCoursesStatus: LiveData<Event<Resource<List<Course>>>> = _getCoursesStatus

    init{
        getCourses()
    }

    fun getCourses() {
        _getCoursesStatus.postValue(Event(Resource.Loading()))
        viewModelScope.launch(Dispatchers.IO) {
            val courses = repository.getCourses()
            _getCoursesStatus.postValue(Event(courses))
        }
    }
}