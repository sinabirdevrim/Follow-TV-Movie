package com.ugurbuga.followtvmovie.data.di.repository

import com.ugurbuga.followtvmovie.data.repository.common.CommonRepository
import com.ugurbuga.followtvmovie.data.repository.common.CommonRepositoryImpl
import com.ugurbuga.followtvmovie.data.repository.movie.MovieRepository
import com.ugurbuga.followtvmovie.data.repository.movie.MovieRepositoryImpl
import com.ugurbuga.followtvmovie.data.repository.person.PersonRepository
import com.ugurbuga.followtvmovie.data.repository.person.PersonRepositoryImpl
import com.ugurbuga.followtvmovie.data.repository.search.SearchRepository
import com.ugurbuga.followtvmovie.data.repository.search.SearchRepositoryImpl
import com.ugurbuga.followtvmovie.data.repository.tvshow.TvShowRepository
import com.ugurbuga.followtvmovie.data.repository.tvshow.TvShowRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindApiRepository(apiDataRepositoryImpl: CommonRepositoryImpl): CommonRepository

    @Binds
    @ViewModelScoped
    abstract fun provideMovieRepository(apiDataRepositoryImpl: MovieRepositoryImpl): MovieRepository


    @Binds
    @ViewModelScoped
    abstract fun providePersonRepository(apiDataRepositoryImpl: PersonRepositoryImpl): PersonRepository

    @Binds
    @ViewModelScoped
    abstract fun provideSearchRepository(apiDataRepositoryImpl: SearchRepositoryImpl): SearchRepository

    @Binds
    @ViewModelScoped
    abstract fun provideTvShowRepository(apiDataRepositoryImpl: TvShowRepositoryImpl): TvShowRepository


}