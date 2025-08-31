package com.example.challengegalicia.presentation.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.challengegalicia.data.remote.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val usersRepository: UsersRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _uiState = MutableStateFlow<UserListUiState>(UserListUiState.Idle)
    val uiState: StateFlow<UserListUiState> = _uiState

    init {
        observeUsers()
    }

    private fun observeUsers() {
        viewModelScope.launch {
            _searchQuery
                .debounce(300)
                .onEach { _uiState.value = UserListUiState.Loading }
                .mapLatest { query ->
                    usersRepository.getAllUsers()
                        .map { pagingData ->
                            pagingData.filter { user ->
                                val nameMatches = user.name.firstName.contains(query, true)
                                val countryMatches = user.country?.contains(query, true) ?: false
                                nameMatches || countryMatches
                            }
                        }
                        .cachedIn(viewModelScope)
                }
                .catch { e ->
                    _uiState.value = UserListUiState.Error(e.message ?: "Unknown error")
                }
                .collect { flow ->
                    _uiState.value = UserListUiState.Success(flow)
                }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }
}
