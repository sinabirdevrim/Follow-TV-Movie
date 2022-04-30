package com.ugurbuga.followtvmovie.data.di.repository

import com.ugurbuga.followtvmovie.core.di.IoDispatcher
import com.ugurbuga.followtvmovie.data.api.services.CommonService
import com.ugurbuga.followtvmovie.data.repository.common.CommonRepository
import com.ugurbuga.followtvmovie.data.repository.common.CommonRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object CommonModule {

    @Provides
    @ViewModelScoped
    fun provideCommonService(retrofit: Retrofit): CommonService {
        return retrofit.create(CommonService::class.java)
    }


    @Provides
    @ViewModelScoped
    fun provideCommonRepository(
        service: CommonService,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): CommonRepository {
        return CommonRepositoryImpl(service, dispatcher)
    }

}