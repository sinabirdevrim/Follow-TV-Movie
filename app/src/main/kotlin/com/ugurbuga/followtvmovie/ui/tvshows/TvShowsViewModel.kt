package com.ugurbuga.followtvmovie.ui.tvshows

import com.ugurbuga.followtvmovie.base.FTMBaseViewModel
import com.ugurbuga.followtvmovie.common.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class TvShowsViewModel @Inject constructor() : FTMBaseViewModel() {

    private val _query = MutableStateFlow(Util.EMPTY_STRING)
    val query: StateFlow<String> get() = _query

    fun setQuery(query: String) {
        _query.value = query
    }

}