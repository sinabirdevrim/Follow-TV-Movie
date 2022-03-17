package com.ugurbuga.followtvmovie.ui.moviedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ugurbuga.followtvmovie.R
import com.ugurbuga.followtvmovie.base.FTMBaseViewModel
import com.ugurbuga.followtvmovie.common.Argument
import com.ugurbuga.followtvmovie.common.Util
import com.ugurbuga.followtvmovie.domain.favorite.AddFavoriteUseCase
import com.ugurbuga.followtvmovie.domain.favorite.DeleteFavoriteUseCase
import com.ugurbuga.followtvmovie.domain.favorite.GetFavoriteUseCase
import com.ugurbuga.followtvmovie.domain.moviedetail.usecase.GetMovieCastsUseCase
import com.ugurbuga.followtvmovie.domain.moviedetail.usecase.GetMovieDetailUseCase
import com.ugurbuga.followtvmovie.domain.moviedetail.usecase.GetMovieExternalUrlsUseCase
import com.ugurbuga.followtvmovie.domain.moviedetail.usecase.GetMovieImagesUseCase
import com.ugurbuga.followtvmovie.domain.moviedetail.usecase.GetMovieTrailersUseCase
import com.ugurbuga.followtvmovie.domain.moviedetail.usecase.GetRecommendationsUseCase
import com.ugurbuga.followtvmovie.domain.moviedetail.usecase.GetSimilarMoviesUseCase
import com.ugurbuga.followtvmovie.domain.poster.model.LoadingUIModel
import com.ugurbuga.followtvmovie.domain.poster.model.PosterUIModel
import com.ugurbuga.followtvmovie.extensions.doOnStatusChanged
import com.ugurbuga.followtvmovie.extensions.doOnSuccess
import com.ugurbuga.followtvmovie.ui.discover.MediaType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val getFavoriteUseCase: GetFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val getMovieTrailersUseCase: GetMovieTrailersUseCase,
    private val getMovieImagesUseCase: GetMovieImagesUseCase,
    private val getMovieCastsUseCase: GetMovieCastsUseCase,
    private val getMovieExternalUrlsUseCase: GetMovieExternalUrlsUseCase,
    private val getRecommendationsUseCase: GetRecommendationsUseCase,
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase,
    savedStateHandle: SavedStateHandle,
) : FTMBaseViewModel() {

    private var _movieDetailViewEvent = MutableSharedFlow<MovieDetailViewEvent>()
    val movieDetailViewEvent: SharedFlow<MovieDetailViewEvent> = _movieDetailViewEvent

    private val _movieDetailViewState = MutableStateFlow(MovieDetailViewState())
    val movieDetailViewState: StateFlow<MovieDetailViewState> get() = _movieDetailViewState

    private var movieId: String = savedStateHandle[Argument.ID] ?: Util.EMPTY_STRING

    private var isCanLoadNewItemRecommendations = false
    private var isCanLoadNewItemSimilarMovies = false

    init {
        getMovieDetail()
        getTrailers()
        getCasts()
        getImages()
        getExternalUrls()
        getRecommendations()
        getSimilarMovies()
    }

    private fun isFavorite() {
        getFavoriteUseCase(
            GetFavoriteUseCase.GetFavoriteParams(
                MediaType.MOVIE, movieId
            )
        ).doOnSuccess {
            _movieDetailViewState.value = _movieDetailViewState.value.copy(
                isFavorite = it != null
            )
        }.launchIn(viewModelScope)
    }

    private fun getMovieDetail() {
        getMovieDetailUseCase(GetMovieDetailUseCase.MovieDetailParams(movieId)).doOnStatusChanged {
            initStatusState(
                it, isShowLoading = false
            )
        }.doOnSuccess {
            _movieDetailViewState.value = _movieDetailViewState.value.copy(
                movieDetail = it, isFavorite = false
            )
            isFavorite()
        }.launchIn(viewModelScope)
    }

    fun changeFavoriteState() {
        if (movieDetailViewState.value.isFavorite) {
            movieDetailViewState.value.movieDetail?.let {
                deleteFavoriteUseCase(DeleteFavoriteUseCase.DeleteFavoriteParams(it.id)).doOnSuccess {
                    _movieDetailViewEvent.emit(MovieDetailViewEvent.ShowSnackbar(R.string.removed_movie_list))
                }.launchIn(viewModelScope)
            }
        } else {
            val isReleased =
                Util.isReleased(movieDetailViewState.value.movieDetail?.releaseDateLong)
            if (isReleased) {
                _movieDetailViewEvent.emitSuspending(
                    MovieDetailViewEvent.ShowWatchedOrWatchLaterDialog(
                        movieDetailViewState.value.movieDetail?.title ?: Util.EMPTY_STRING
                    )
                )

            } else {
                addFavorite(isWatched = false)
            }

        }
    }

    private fun getTrailers() {
        getMovieTrailersUseCase(GetMovieTrailersUseCase.MovieTrailerParams(movieId)).doOnStatusChanged {
            initStatusState(
                it, isShowLoading = false
            )
        }.doOnSuccess {
            _movieDetailViewState.value = movieDetailViewState.value.copy(trailers = it)
            isFavorite()
        }.launchIn(viewModelScope)
    }

    private fun getCasts() {
        getMovieCastsUseCase(GetMovieCastsUseCase.CastParams(movieId)).doOnStatusChanged {
            initStatusState(
                it, isShowLoading = false
            )
        }.doOnSuccess {
            _movieDetailViewState.value = movieDetailViewState.value.copy(casts = it)
            isFavorite()
        }.launchIn(viewModelScope)
    }

    private fun getImages() {
        getMovieImagesUseCase(GetMovieImagesUseCase.MovieImageParams(movieId)).doOnStatusChanged {
            initStatusState(
                it, isShowLoading = false
            )
        }.doOnSuccess {
            _movieDetailViewState.value = movieDetailViewState.value.copy(images = it)
            isFavorite()
        }.launchIn(viewModelScope)
    }

    private fun getExternalUrls() {
        getMovieExternalUrlsUseCase(GetMovieExternalUrlsUseCase.ExternalUrlParams(movieId)).doOnStatusChanged {
            initStatusState(
                it, isShowLoading = false
            )
        }.doOnSuccess {
            _movieDetailViewState.value = movieDetailViewState.value.copy(externalUrls = it)
            isFavorite()
        }.launchIn(viewModelScope)
    }

    fun reviewsClicked() {
        _movieDetailViewEvent.emitSuspending(MovieDetailViewEvent.NavigateToReviews(movieId))

    }

    fun imageClicked(position: Int) {
        _movieDetailViewEvent.emitSuspending(
            MovieDetailViewEvent.NavigateToImages(
                imageList = movieDetailViewState.value.images, position = position
            )
        )
    }

    fun imdbClicked(packageEnabled: Boolean) {
        if (packageEnabled) {
            navigateToOtherApp(movieDetailViewState.value.externalUrls.getImdbUrl())
        } else {
            navigateToWebView(movieDetailViewState.value.externalUrls.getImdbUrl())
        }
    }

    fun facebookClicked(packageEnabled: Boolean) {
        if (packageEnabled) {
            navigateToOtherApp(movieDetailViewState.value.externalUrls.getFacebookDeeplink())
        } else {
            navigateToWebView(movieDetailViewState.value.externalUrls.getFacebookUrl())
        }
    }

    fun twitterClicked(packageEnabled: Boolean) {
        if (packageEnabled) {
            navigateToOtherApp(movieDetailViewState.value.externalUrls.getTwitterUrl())
        } else {
            navigateToWebView(movieDetailViewState.value.externalUrls.getTwitterUrl())
        }
    }

    fun instagramClicked(packageEnabled: Boolean) {
        if (packageEnabled) {
            navigateToOtherApp(movieDetailViewState.value.externalUrls.getInstagramUrl())
        } else {
            navigateToWebView(movieDetailViewState.value.externalUrls.getInstagramUrl())
        }
    }

    private fun navigateToOtherApp(url: String) {
        _movieDetailViewEvent.emitSuspending(MovieDetailViewEvent.NavigateToOtherApp(url))
    }

    private fun navigateToWebView(url: String) {
        _movieDetailViewEvent.emitSuspending(MovieDetailViewEvent.NavigateToWebView(url))
    }

    fun addFavorite(isWatched: Boolean) {
        val message =
            if (isWatched) R.string.added_watched_list else R.string.added_watch_later_list

        movieDetailViewState.value.movieDetail?.let {
            addFavoriteUseCase(AddFavoriteUseCase.AddFavoriteParams(it, isWatched))
                .doOnSuccess {
                    _movieDetailViewEvent.emit(MovieDetailViewEvent.ShowSnackbar(message))
                }.launchIn(viewModelScope)
        }
    }

    private fun getRecommendations() {
        addRecommendationLoading()
        getRecommendationsUseCase(
            GetRecommendationsUseCase.Recommendations(
                movieId,
                ++movieDetailViewState.value.recommendation.page
            )
        ).doOnStatusChanged {
            initStatusState(
                it, isShowLoading = false
            )
        }.doOnSuccess {
            setRecommendationList(it)
            isCanLoadNewItemRecommendations = it.totalPages > it.page
            isFavorite()
        }.launchIn(viewModelScope)
    }

    fun getNewRecommendations() {
        if (isCanLoadNewItemRecommendations) {
            isCanLoadNewItemRecommendations = false
            getRecommendations()
        }
    }

    private fun addRecommendationLoading() {
        val list = movieDetailViewState.value.recommendation.posterList.toMutableList()
        list.add(LoadingUIModel())
        val recommendation = movieDetailViewState.value.recommendation.copy()
        recommendation.posterList = list
        _movieDetailViewState.value =
            movieDetailViewState.value.copy(recommendation = recommendation)
    }

    private fun setRecommendationList(recommendation: PosterUIModel) {
        val list = movieDetailViewState.value.recommendation.posterList.toMutableList()
        list.remove(LoadingUIModel())
        list.addAll(recommendation.posterList)
        recommendation.posterList = list
        _movieDetailViewState.value =
            movieDetailViewState.value.copy(recommendation = recommendation)
    }

    /////////////

    private fun getSimilarMovies() {
        addSimilarMoviesLoading()
        getSimilarMoviesUseCase(
            GetSimilarMoviesUseCase.SimilarMovies(
                movieId,
                ++movieDetailViewState.value.similarMovie.page
            )
        ).doOnStatusChanged {
            initStatusState(
                it, isShowLoading = false
            )
        }.doOnSuccess {
            setSimilarMovieList(it)
            isCanLoadNewItemSimilarMovies = it.totalPages > it.page
            isFavorite()
        }.launchIn(viewModelScope)
    }

    fun getNewSimilarMovies() {
        if (isCanLoadNewItemSimilarMovies) {
            isCanLoadNewItemSimilarMovies = false
            getSimilarMovies()
        }
    }

    private fun addSimilarMoviesLoading() {
        val list = movieDetailViewState.value.similarMovie.posterList.toMutableList()
        list.add(LoadingUIModel())
        val similarMovie = movieDetailViewState.value.similarMovie.copy()
        similarMovie.posterList = list
        _movieDetailViewState.value =
            movieDetailViewState.value.copy(similarMovie = similarMovie)
    }

    private fun setSimilarMovieList(similarMovie: PosterUIModel) {
        val list = movieDetailViewState.value.similarMovie.posterList.toMutableList()
        list.remove(LoadingUIModel())
        list.addAll(similarMovie.posterList)
        similarMovie.posterList = list
        _movieDetailViewState.value =
            movieDetailViewState.value.copy(similarMovie = similarMovie)
    }
}