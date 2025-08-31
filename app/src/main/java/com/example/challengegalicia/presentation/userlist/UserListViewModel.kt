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

/**
 * ViewModel responsable de gestionar el estado de la lista de usuarios.
 * Se conecta al repositorio para obtener datos paginados desde la API
 * y expone el estado UI en forma de StateFlow.
 */
@HiltViewModel
class UserListViewModel @Inject constructor(
    private val usersRepository: UsersRepository
) : ViewModel() {

    // Flow que contiene el texto actual del campo de búsqueda
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    // Flow que expone el estado actual de la UI (Idle, Loading, Success, Error)
    private val _uiState = MutableStateFlow<UserListUiState>(UserListUiState.Idle)
    val uiState: StateFlow<UserListUiState> = _uiState

    init {
        // Observa continuamente los cambios en la query de búsqueda y actualiza la lista
        observeUsers()
    }

    /**
     * Inicia un flujo reactivo que:
     * - Escucha los cambios en la query de búsqueda
     * - Aplica un debounce de 300ms para evitar búsquedas excesivas
     * - Emite estado Loading cada vez que se recibe una nueva query
     * - Llama al repositorio para obtener los usuarios paginados
     * - Filtra la lista en función del nombre o país que coincidan con la query
     * - Actualiza el estado UI con Success (datos filtrados) o Error en caso de falla
     */
    private fun observeUsers() {
        viewModelScope.launch {
            _searchQuery
                .debounce(300)
                .onEach { _uiState.value = UserListUiState.Loading }
                .mapLatest { query ->
                    // Obtiene el flujo de usuarios paginados y aplica el filtro de búsqueda
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

    /**
     * Actualiza el valor de la query de búsqueda.
     * Esto dispara el flujo de observeUsers para actualizar la lista.
     */
    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }
}
