package com.example.challengegalicia.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.challengegalicia.presentation.model.UserModel
import okio.IOException
import javax.inject.Inject

class UserPagingSource @Inject constructor(
    private val api: UserListApiService
) : PagingSource<Int, UserModel>() {
    override fun getRefreshKey(state: PagingState<Int, UserModel>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserModel> {

        return try {
            val page = params.key ?: 1
            val response = api.getUsers(page)
            val users = response.results

            val prevKey = if (page > 0) page - 1 else null
            val nextKey = page + 1

            LoadResult.Page(
                data = users.map { it.toPresentation() },
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        }

    }

}