package com.ugurbuga.followtvmovie.data.repository.search

import com.ugurbuga.followtvmovie.core.base.BaseRepository
import com.ugurbuga.followtvmovie.core.common.ApiState
import com.ugurbuga.followtvmovie.data.api.services.SearchService
import com.ugurbuga.followtvmovie.data.model.response.search.SearchResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchService: SearchService,
    dispatcher: CoroutineDispatcher
) :
    SearchRepository, BaseRepository(dispatcher) {

    override fun getSearch(query: String, page: Int): Flow<ApiState<SearchResponse>> {
        return onApiCall { searchService.getSearch(query, page) }
    }

}
