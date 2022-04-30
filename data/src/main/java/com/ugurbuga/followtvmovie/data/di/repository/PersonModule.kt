package com.ugurbuga.followtvmovie.data.di.repository

import com.ugurbuga.followtvmovie.core.di.IoDispatcher
import com.ugurbuga.followtvmovie.data.api.services.PersonService
import com.ugurbuga.followtvmovie.data.repository.person.PersonRepository
import com.ugurbuga.followtvmovie.data.repository.person.PersonRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object PersonModule {


    @Provides
    @ViewModelScoped
     fun providePersonService(retrofit: Retrofit): PersonService {
        return retrofit.create(PersonService::class.java)
    }

    @Provides
    @ViewModelScoped
     fun providePersonRepository(service: PersonService,@IoDispatcher dispatcher: CoroutineDispatcher): PersonRepository {
        return PersonRepositoryImpl(service,dispatcher)
    }

}