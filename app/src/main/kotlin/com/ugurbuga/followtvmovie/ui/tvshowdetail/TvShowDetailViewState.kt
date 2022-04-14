package com.ugurbuga.followtvmovie.ui.tvshowdetail

import com.ugurbuga.followtvmovie.domain.moviedetail.external.ExternalIdsUIModel
import com.ugurbuga.followtvmovie.domain.moviedetail.image.ImageUIModel
import com.ugurbuga.followtvmovie.domain.moviedetail.model.detail.CastUIModel
import com.ugurbuga.followtvmovie.domain.moviedetail.model.detail.TrailerUIModel
import com.ugurbuga.followtvmovie.domain.poster.model.PosterUIModel
import com.ugurbuga.followtvmovie.domain.tvshowdetail.detail.TvShowDetailUIModel

data class TvShowDetailViewState(
    val tvShowDetail: TvShowDetailUIModel? = null,
    val isFavorite: Boolean = false,
    val trailers: ArrayList<TrailerUIModel> = arrayListOf(),
    val casts: ArrayList<CastUIModel> = arrayListOf(),
    val images: ArrayList<ImageUIModel> = arrayListOf(),
    val externalUrls: ExternalIdsUIModel = ExternalIdsUIModel(),
    val recommendation: PosterUIModel = PosterUIModel(),
    val similarMovie: PosterUIModel = PosterUIModel(),
)