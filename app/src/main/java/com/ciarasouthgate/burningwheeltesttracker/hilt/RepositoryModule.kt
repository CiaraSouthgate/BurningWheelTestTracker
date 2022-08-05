package com.ciarasouthgate.burningwheeltesttracker.hilt

import com.ciarasouthgate.burningwheeltesttracker.data.AppRepository
import com.ciarasouthgate.burningwheeltesttracker.data.AppRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAppRepository(appRepository: AppRepositoryImpl): AppRepository
}