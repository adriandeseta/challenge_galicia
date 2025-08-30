package com.example.challengegalicia.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.challengegalicia.presentation.model.UserModel
import javax.inject.Inject

class UserPagingSource @Inject constructor(
    private val api: UserListApiService
) : PagingSource<Int, UserModel>() {

    companion object {
        private const val TAG = "UserPagingSource"
        private const val PAGE_SIZE = 10
        private const val SEED = "challenge"
    }

    override fun getRefreshKey(state: PagingState<Int, UserModel>): Int? {
        return state.anchorPosition?.let { anchorPos ->
            state.closestPageToPosition(anchorPos)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPos)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserModel> {
        val page = params.key ?: 1
        return try {
            Log.d(TAG, "=== Loading page $page ===")

            val response = api.getUsers(
                page = page,
                results = PAGE_SIZE,
                seed = SEED
            )
            val users = response.results

            Log.d(TAG, "Users received: ${users.size}")

            users.forEach { user ->
                Log.d(TAG, "User: ${user.name.firstName} ${user.name.lastName}")
            }

            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (users.isNotEmpty()) page + 1 else null

            LoadResult.Page(
                data = users.map { it.toPresentation() },
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: Exception) {
            Log.e(TAG, "Error loading page $page", exception)
            LoadResult.Error(exception)
        }
    }
}
