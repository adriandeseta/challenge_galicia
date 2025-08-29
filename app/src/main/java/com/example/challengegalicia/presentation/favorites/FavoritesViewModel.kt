package com.example.challengegalicia.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challengegalicia.data.FavoriteUserEntity
import com.example.challengegalicia.data.FavoriteUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: FavoriteUserRepository
) : ViewModel() {

    val favorites: StateFlow<List<FavoriteUserEntity>> =
        repository.getFavorites()
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val favoriteUsers: Flow<List<FavoriteUserEntity>> = repository.getFavorites()

    fun addFavorite(user: FavoriteUserEntity) {
        viewModelScope.launch {
            repository.addFavorite(user)
        }
    }

    fun isFavorite(email: String): Flow<Boolean> {
        return favoriteUsers.map { list ->
            list.any { it.email == email }
        }
    }
}
