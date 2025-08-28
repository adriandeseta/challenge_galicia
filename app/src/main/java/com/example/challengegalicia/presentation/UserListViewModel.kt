package com.example.challengegalicia.presentation

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.challengegalicia.data.UsersRepository
import com.example.challengegalicia.presentation.model.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(usersRepository: UsersRepository) : ViewModel() {
    val users: Flow<PagingData<UserModel>> = usersRepository.getAllUsers()
}