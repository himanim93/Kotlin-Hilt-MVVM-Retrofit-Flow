package com.hm.users.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hm.users.data.UserRepository
import com.hm.users.data.model.UserDetails
import com.hm.users.remote.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: UserRepository) :
    ViewModel() {

    private val _getUserResult = MutableLiveData<Resource<UserDetails>>()
    val getUserResult: LiveData<Resource<UserDetails>> = _getUserResult

    var empList: MutableList<UserDetails.User> = mutableListOf()

    private val _showError = MutableLiveData<Int>()
    val showError: LiveData<Int> = _showError

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    init {
        getUserList()
    }

    fun getUserList() = viewModelScope.launch {
        _loading.postValue(true)
        repository.getUserList().collect {
            _getUserResult.postValue(it)
        }
    }

    fun applySearching(searchTxt: String) {
        if (searchTxt.isEmpty()) {
            _getUserResult.postValue(Resource.Success(data = UserDetails(empList)))
            return
        }

        val sortingList: MutableList<UserDetails.User> =
            empList.filter {
                it.name.lowercase().contains(searchTxt.lowercase())
            } as MutableList<UserDetails.User>
        _getUserResult.postValue(Resource.Success(data = UserDetails(sortingList)))
    }
}