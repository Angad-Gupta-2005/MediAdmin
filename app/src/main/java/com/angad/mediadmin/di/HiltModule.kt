package com.angad.mediadmin.di

import com.angad.mediadmin.api.ApiBuilder
import com.angad.mediadmin.repo.Repo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    @Singleton
    fun provideApi(): ApiBuilder{
        return ApiBuilder
    }

//    For injecting the repo into the viewModels
    @Provides
    @Singleton
    fun provideRepo( apiBuilder: ApiBuilder): Repo{
        return Repo(apiBuilder)
    }

}