package com.ugurbuga.followtvmovie.data.di

import com.ugurbuga.followtvmovie.data.api.services.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object ServiceModul {

    @Provides
    @ViewModelScoped
    fun provideCommonService(retrofit: Retrofit): CommonService {
        return retrofit.create(CommonService::class.java)
    }

    @Provides
    @ViewModelScoped
    fun provideMovieService(retrofit: Retrofit): MovieService {
        return retrofit.create(MovieService::class.java)
    }

    @Provides
    @ViewModelScoped
    fun providePersonService(retrofit: Retrofit): PersonService {
        return retrofit.create(PersonService::class.java)
    }

    @Provides
    @ViewModelScoped
    fun provideSearchService(retrofit: Retrofit): SearchService {
        return retrofit.create(SearchService::class.java)
    }

    @Provides
    @ViewModelScoped
    fun provideTvShowService(retrofit: Retrofit): TvShowService {
        return retrofit.create(TvShowService::class.java)
    }
}