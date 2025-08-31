package com.example.challengegalicia.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.challengegalicia.presentation.model.UserModel
import javax.inject.Inject

/**
 * Implementación de PagingSource que se encarga de obtener datos de usuarios de forma paginada
 * desde el servicio remoto (API).
 *
 * Esta clase traduce las solicitudes de datos del Paging 3 Library
 * en llamadas a la API con parámetros de paginación.
 */
class UserPagingSource @Inject constructor(
    private val api: UserListApiService
) : PagingSource<Int, UserModel>() {

    companion object {
        private const val TAG = "UserPagingSource"
        private const val PAGE_SIZE = 10     // Cantidad de usuarios a cargar por página
        private const val SEED = "challenge" // Semilla para mantener datos consistentes entre llamadas
    }

    /**
     * Determina la clave (página) que se debe usar cuando se refresca la lista.
     * Se basa en la posición más cercana al ítem ancla (anchorPosition) en el estado actual.
     */
    override fun getRefreshKey(state: PagingState<Int, UserModel>): Int? {
        return state.anchorPosition?.let { anchorPos ->
            state.closestPageToPosition(anchorPos)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPos)?.nextKey?.minus(1)
        }
    }

    /**
     * Carga una página de datos desde la API remota.
     *
     * @param params Parámetros de la carga (incluye la clave actual de paginación).
     * @return Un objeto LoadResult que puede ser:
     *   - Page: si la carga es exitosa, con la lista de usuarios y claves prev/next
     *   - Error: si ocurre algún problema durante la obtención de datos
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserModel> {
        val page = params.key ?: 1 // Si no hay clave, comienza desde la página 1
        return try {
            Log.d(TAG, "=== Loading page $page ===")

            // Llamada a la API para obtener usuarios
            val response = api.getUsers(
                page = page,
                results = PAGE_SIZE,
                seed = SEED
            )
            val users = response.results

            Log.d(TAG, "Users received: ${users.size}")

            // Logging adicional de cada usuario recibido (útil para debug)
            users.forEach { user ->
                Log.d(TAG, "User: ${user.name.firstName} ${user.name.lastName}")
            }

            // Determina claves prev y next para la siguiente o anterior carga
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (users.isNotEmpty()) page + 1 else null

            // Retorna el resultado con los datos convertidos a modelos de presentación
            LoadResult.Page(
                data = users.map { it.toPresentation() },
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: Exception) {
            // En caso de error, lo loguea y retorna LoadResult.Error
            Log.e(TAG, "Error loading page $page", exception)
            LoadResult.Error(exception)
        }
    }
}
