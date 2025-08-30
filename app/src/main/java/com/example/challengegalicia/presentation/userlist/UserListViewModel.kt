package com.example.challengegalicia.presentation.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.challengegalicia.data.remote.UsersRepository
import com.example.challengegalicia.presentation.model.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val usersRepository: UsersRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val users: Flow<PagingData<UserModel>> = _searchQuery
        .debounce(300)
        .flatMapLatest { query ->
            usersRepository.getAllUsers()
                .map { pagingData ->
                    pagingData.filter { user ->
                        val nameMatches = user.name.firstName.contains(query, true)
                        val countryMatches = user.country?.contains(query, true) ?: false
                        nameMatches || countryMatches
                    }
                }
        }
        .cachedIn(viewModelScope)

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

}

