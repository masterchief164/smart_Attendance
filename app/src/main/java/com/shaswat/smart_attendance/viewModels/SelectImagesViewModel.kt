package com.shaswat.smart_attendance.viewModels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shaswat.smart_attendance.other.Event
import com.shaswat.smart_attendance.other.Resource
import com.shaswat.smart_attendance.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.File
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SelectImagesViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _uploadFileStatus = MutableLiveData<Event<Resource<Boolean>>>()
    val uploadFileStatus: LiveData<Event<Resource<Boolean>>> = _uploadFileStatus

    private val _imagesUri = MutableLiveData<Resource<ArrayList<Uri>>>()
    val imagesUri: LiveData<Resource<ArrayList<Uri>>> = _imagesUri

    fun uploadImages(files: ArrayList<File>) {
        _uploadFileStatus.postValue(Event(Resource.Loading()))
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.uploadImages(files)
            _uploadFileStatus.postValue(Event(response))

        }
    }

}