package com.example.challengegalicia.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.challengegalicia.data.UsersRepository
import com.example.challengegalicia.presentation.model.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(usersRepository: UsersRepository) : ViewModel() {
    val users: Flow<PagingData<UserModel>> = usersRepository.getAllUsers()
    private val _events = MutableSharedFlow<UserListEvent>()
    val events = _events.asSharedFlow()

    fun onUserClicked(user: UserModel) {
        viewModelScope.launch {
            _events.emit(UserListEvent.NavigateToDetail(user))
        }
    }
}

sealed class UserListEvent {
    data class NavigateToDetail(val user: UserModel) : UserListEvent()
}
