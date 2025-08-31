package com.example.challengegalicia.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challengegalicia.data.local.FavoriteUserRepository
import com.example.challengegalicia.data.toFavoriteUserEntity
import com.example.challengegalicia.presentation.model.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: FavoriteUserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<FavoritesUiState>(FavoritesUiState.Idle)
    val uiState: StateFlow<FavoritesUiState> = _uiState

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            _uiState.value = FavoritesUiState.Loading
            repository.getFavorites()
                .collect { list ->
                    _uiState.value = FavoritesUiState.Success(list)
                }
        }
    }

    fun addFavorite(user: UserModel) {
        viewModelScope.launch {
            repository.addFavorite(user.toFavoriteUserEntity())
            loadFavorites() // refrescar la lista
        }
    }

    fun removeFavorite(uuid: String) {
        viewModelScope.launch {
            repository.removeFavorite(uuid)
            loadFavorites() // refrescar la lista
        }
    }

    fun isFavorite(uuid: String) = repository.isFavorite(uuid)
}
