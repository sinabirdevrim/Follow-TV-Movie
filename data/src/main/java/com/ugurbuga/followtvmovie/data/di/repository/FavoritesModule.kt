package com.ugurbuga.followtvmovie.data.di.repository

import com.ugurbuga.followtvmovie.core.di.IoDispatcher
import com.ugurbuga.followtvmovie.data.dao.FavoritesDao
import com.ugurbuga.followtvmovie.data.repository.favorites.FavoritesRepository
import com.ugurbuga.followtvmovie.data.repository.favorites.FavoritesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FavoritesModule {

    @Singleton
    @Provides
     fun provideFavoriteRepository(favoritesDao: FavoritesDao,@IoDispatcher dispatcher: CoroutineDispatcher): FavoritesRepository {
        return FavoritesRepositoryImpl(favoritesDao,dispatcher)
    }
}