package com.example.challengegalicia.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challengegalicia.data.local.FavoriteUserEntity
import com.example.challengegalicia.data.local.FavoriteUserRepository
import com.example.challengegalicia.data.toFavoriteUserEntity
import com.example.challengegalicia.presentation.model.UserModel
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

    fun addFavorite(user: UserModel) {
        viewModelScope.launch {
            repository.addFavorite(user.toFavoriteUserEntity())
        }
    }

    fun removeFavorite(uuid: String) {
        viewModelScope.launch {
            repository.removeFavorite(uuid)
        }
    }

    fun isFavorite(uuid: String) = repository.isFavorite(uuid)
}
