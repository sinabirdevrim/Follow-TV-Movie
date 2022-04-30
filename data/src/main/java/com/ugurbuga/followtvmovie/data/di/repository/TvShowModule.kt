package com.ugurbuga.followtvmovie.data.di.repository

import com.ugurbuga.followtvmovie.core.di.IoDispatcher
import com.ugurbuga.followtvmovie.data.api.services.TvShowService
import com.ugurbuga.followtvmovie.data.repository.tvshow.TvShowRepository
import com.ugurbuga.followtvmovie.data.repository.tvshow.TvShowRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object TvShowModule {

    @Provides
    @ViewModelScoped
    fun provideTvShowService(retrofit: Retrofit): TvShowService {
        return retrofit.create(TvShowService::class.java)
    }

    @Provides
    @ViewModelScoped
    fun provideTvShowRepository(
        service: TvShowService,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): TvShowRepository {
        return TvShowRepositoryImpl(service,dispatcher)
    }

}