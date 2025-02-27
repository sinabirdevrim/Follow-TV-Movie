package com.ugurbuga.followtvmovie.ui.tvshowdetail

import com.ugurbuga.followtvmovie.domain.tvshowdetail.detail.TvShowDetailUIModel

data class TvShowDetailViewState(
    val tvShowDetail: TvShowDetailUIModel? = null
) {
    fun hasSeason(): Boolean {
        return tvShowDetail != null && tvShowDetail.seasons.isNotEmpty()
    }
}