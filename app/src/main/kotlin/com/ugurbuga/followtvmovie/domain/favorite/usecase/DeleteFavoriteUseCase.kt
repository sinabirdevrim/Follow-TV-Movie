package com.ugurbuga.followtvmovie.domain.favorite.usecase

import com.ugurbuga.followtvmovie.base.FTMUseCase
import com.ugurbuga.followtvmovie.common.ApiState
import com.ugurbuga.followtvmovie.repository.favorites.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteFavoriteUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) :
    FTMUseCase<DeleteFavoriteUseCase.DeleteFavoriteParams, Unit>() {

    data class DeleteFavoriteParams(val id: String)

    override fun execute(params: DeleteFavoriteParams): Flow<ApiState<Unit>> {
        return favoritesRepository.delete(params.id)
    }
}