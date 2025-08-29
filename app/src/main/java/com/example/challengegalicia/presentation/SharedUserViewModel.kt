package com.example.challengegalicia.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.challengegalicia.presentation.model.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedUserViewModel @Inject constructor() : ViewModel() {
    private val _selectedUser = mutableStateOf<UserModel?>(null)
    val selectedUser: State<UserModel?> = _selectedUser

    fun selectUser(user: UserModel) {
        _selectedUser.value = user
    }
}