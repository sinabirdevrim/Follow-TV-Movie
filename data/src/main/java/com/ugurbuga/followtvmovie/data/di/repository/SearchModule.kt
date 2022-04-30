package com.ugurbuga.followtvmovie.data.di.repository

import com.ugurbuga.followtvmovie.core.di.IoDispatcher
import com.ugurbuga.followtvmovie.data.api.services.SearchService
import com.ugurbuga.followtvmovie.data.repository.search.SearchRepository
import com.ugurbuga.followtvmovie.data.repository.search.SearchRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object SearchModule {

    @Provides
    @ViewModelScoped
     fun provideSearchService(retrofit: Retrofit): SearchService {
        return retrofit.create(SearchService::class.java)
    }

    @Provides
    @ViewModelScoped
     fun provideSearchRepository(service: SearchService,  @IoDispatcher dispatcher: CoroutineDispatcher): SearchRepository {
        return SearchRepositoryImpl(service,dispatcher)
    }

}